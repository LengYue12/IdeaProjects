package meituan;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author lengy
 * @date 2022/7/16 17:47
 * @description
 */
public class ShopServer {

    private String connectString = "192.168.114.128:2181,192.168.114.129:2181,192.168.114.130:2181";
    private int sessionTimeout = 60*1000;
    private ZooKeeper zkClient;

    // 创建客户端，连接到Zookeeper
    public void connect() throws Exception{
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    // 注册到Zookeeper
    public void register(String shopName) throws Exception{
        // 一定要创建EPHEMERAL_SEQUENTIAL 临时有序的节点(营业)
        // 一来可以自动编号，二来断开时，节点自动删除(打烊)
        String s = zkClient.create("/meituan/shop", shopName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("【"+ shopName + "】开始营业了"+s);
    }

    public static void main(String[] args) throws Exception {
        // 1.开饭店
        ShopServer shopServer = new ShopServer();

        // 2.连接Zookeeper集群（和美团取得联系）
        shopServer.connect();

        // 3.将服务节点注册到Zookeeper（入住美团）
        shopServer.register(args[0]);

        // 4.业务逻辑处理（做生意）
        shopServer.business(args[0]);

    }

    // 做买卖
    private void business(String shopName) throws IOException {
        System.out.println("【" + shopName + "】正在火爆营业中");
        System.in.read();
    }
}
