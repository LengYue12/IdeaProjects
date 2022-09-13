package test;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

/**
 * @BelongsProject lagou-fastdfs
 * @Author lengy
 * @CreateTime 2022/7/30 17:30
 * @Description 文件上传
 */
public class TestUpload {
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

            // 定义文件元信息
            NameValuePair[] list = new NameValuePair[1];
            list[0] = new NameValuePair("fileName","294255.jpg");

            String fileID = client.upload_file1("C:\\Users\\lengy\\Pictures\\Saved Pictures\\294255.jpg", "jpg", list);
            System.out.println("fileID = " + fileID);
            // group1/M00/00/00/wKhygGLk_ZSACN60AAMAmcJr0Gg784.jpg
            trackerServer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
