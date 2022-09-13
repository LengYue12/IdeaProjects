package test;

import org.csource.fastdfs.*;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @BelongsProject lagou-fastdfs
 * @Author lengy
 * @CreateTime 2022/7/30 18:12
 * @Description 文件下载
 */
public class TestDownload {

    public static void main(String[] args) {
        try {

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


            byte[] bytes = client.download_file1("group1/M00/00/00/wKhygGLk_ZSACN60AAMAmcJr0Gg784.jpg");

            // 通过io流将字节数组，转换成一个文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File("F:/xxxx.jpg"));
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            trackerServer.close();
            System.out.println("下载完毕！");


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
