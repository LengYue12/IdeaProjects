package controller;

import entity.FileSystem;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject lagou-imageServer
 * @Author lengy
 * @CreateTime 2022/7/30 19:54
 * @Description 处理上传文件的控制器
 */
@Controller
public class FileAction {


    @Autowired
    private Redisson redisson;



    @Bean
    public Redisson redisson(){
        Config config = new Config();
        // 使用单个Redis服务器
        config.useSingleServer().setAddress("redis://192.168.114.128:6379").setDatabase(0);
        // 使用集群Redis
        // config.useClusterServers().setScanInterval(200).addNodeAddress("","","");


        RedissonClient redissonClient = Redisson.create(config);
        return (Redisson) redissonClient;
    }

    private final String UPLOAD_LOCK_STR = "upload-lock-redis";


    /**
     *
     *
     * @return  上传文件对象的json对象
     * @throws Exception
     *
     *
     * 上传文件的流程：
     *  1.先把文件保存到web服务器上
     *  2.再从web服务器上将文件 上传到 FastDFS上
     */
    @RequestMapping("/upload")
    public String upload(Model model, MultipartFile filePic) throws Exception{
        FileSystem fileSystem = new FileSystem();

        /*开启分布式锁*/
        if (filePic != null) {
            // 通过Redisson 获得锁
            RLock rlock = redisson.getLock(UPLOAD_LOCK_STR); // 底层源码就是集成了setnx，过期时间等操作
            // 上锁(过期时间为30秒)
            rlock.lock(30, TimeUnit.SECONDS);
            try {
                // 上传文件
                fileSystem = uploadFileToFastDfs(filePic);
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                // 释放锁
                rlock.unlock();
            }
        }

        /* 不开启分布式锁*/
      // fileSystem = uploadFileToFastDfs(filePic);
        model.addAttribute("fileSystem",fileSystem);
        return "index";
    }


    /**
     *上传文件的流程：
     *1.先把文件保存到web服务器上
     *2.再从web服务器上将文件 上传到 FastDFS上
     *
     * @param file 待上传的文件
     * @return
     * @throws Exception
     */
    private FileSystem uploadFileToFastDfs(MultipartFile file) throws Exception{

        FileSystem fileSystem = new FileSystem();

        // 1.把文件保存到web服务器
        // 从文件对象中获取文件的原始名
        String oldFileName = file.getOriginalFilename();
        // 通过字符串截取的方式，从文件原始名中获取文件的后缀名
        String hou = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
        // 为了避免文件因为同名而覆盖，生成全新的文件名
        String newFileName = UUID.randomUUID().toString() + "." + hou;
        // 创建web服务器保存文件的目录
        File toSaveFile = new File("D:/upload/" + newFileName);
        // 将路径转换成文件
        file.transferTo(toSaveFile);
        // 获取服务器的绝对路径
        String newFilePath = toSaveFile.getAbsolutePath();

        // 模拟网络延迟
        Thread.sleep(1000);

        // 2.把文件从web服务器上传到FastDFS

        // 加载配置文件
        ClientGlobal.initByProperties("config/fastdfs-client.properties");

        // 创建tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        // 通过tracker客户端获取tracker的连接服务并返回
        TrackerServer trackerServer = trackerClient.getConnection();
        // 声明storage服务
        StorageServer storageServer = null;

        // 定义storage客户端
        StorageClient1 client = new StorageClient1(trackerServer, storageServer);

        NameValuePair[] list = new NameValuePair[1];
        list[0] = new NameValuePair("fileName",oldFileName);
        String fileId = client.upload_file1(newFilePath, hou, list);
        System.out.println("fileId = " + fileId);
        trackerServer.close();


        // 封装fileSystem数据对象
        fileSystem.setFileId(fileId);
        fileSystem.setFileName(oldFileName);
        fileSystem.setFilePath(fileId);   // 已经上传到FastDFS上，通过fileId来访问图片，所以fileId即为文件路径
        System.out.println(fileSystem);

        return fileSystem;
    }
}
