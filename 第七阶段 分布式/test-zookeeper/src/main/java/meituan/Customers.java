package meituan;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lengy
 * @date 2022/7/16 19:54
 * @description 客户消费者
 */
public class Customers {


    private String connectString = "192.168.114.128:2181,192.168.114.129:2181,192.168.114.130:2181";
    private int sessionTimeout = 60*1000;
    private ZooKeeper zkClient;

    // 创建客户端，连接到Zookeeper
    public void connect() throws Exception{
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                // 监听到了数据变化，或者子节点的变化
                // 当有数据变化时再次获取商家列表
                try {
                    getShopList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // 获取子节点列表（获取商家列表）
    private void getShopList() throws KeeperException, InterruptedException {
        // 1.获取服务器的子节点信息，并且对父节点进行监听
        List<String> shops = zkClient.getChildren("/meituan", true);
        // 2.声明存储服务器信息的集合
        ArrayList<String> shopList = new ArrayList<>();

        for (String s : shops) {
            // 获取meituan下每个子节点的数据
            byte[] bytes = zkClient.getData("/meituan/" + s, false, new Stat());
            // 获取到节点中的数据并存到集合里
            shopList.add(new String(bytes));
        }

        System.out.println("目前正在营业的商家：" + shopList);
    }
    public static void main(String[] args) throws Exception {
        Customers client = new Customers();
        // 1.获得Zookeeper的连接（用户打开美团）
        client.connect();
        // 2.获取美团下的所有子节点列表（获取商家列表）
        client.getShopList();
        // 3.业务逻辑处理（对比商家，下单点餐）
        client.busines();
    }

    private void busines() throws IOException {
        System.out.println("用户正在浏览商家...");
        System.in.read();
    }


}
