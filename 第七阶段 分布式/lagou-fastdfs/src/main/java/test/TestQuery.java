package test;

import org.csource.fastdfs.*;

/**
 * @BelongsProject lagou-fastdfs
 * @Author lengy
 * @CreateTime 2022/7/30 18:03
 * @Description 文件查询
 */
public class TestQuery {
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

            FileInfo fileInfo = client.query_file_info1("group1/M00/00/00/wKhygGLk_ZSACN60AAMAmcJr0Gg784.jpg");
            if (fileInfo != null) {
                System.out.println("fileInfo = " + fileInfo);
            }else {
                System.out.println("查无此文件");
            }

            trackerServer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
