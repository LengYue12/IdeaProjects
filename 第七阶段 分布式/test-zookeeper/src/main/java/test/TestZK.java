package test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author lengy
 * @date 2022/7/16 11:17
 * @description
 */
public class TestZK {

    // Zookeeper  集群的ip和端口
    private String connectString = "192.168.114.128:2181,192.168.114.129:2181,192.168.114.130:2181";

    /* session超时的时间 ：时间不宜设置太小，因为Zookeeper和加载集群环境会因为性能等原因而延迟略高
        如果时间太少，还没有创建客户端，就开始操作节点，会报错的
     *
     */
    private int sessionTimeout = 60*1000;

    // 声明客户端
    // Zookeeper的客户端对象
    private ZooKeeper zkClient;

    @Before
    // 创建客户端的
    public void init() throws Exception {

        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            // 负责监听的线程处理代码
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("得到监听反馈，进行业务处理的代码！");
                // NodeChildrenChanged
                // WatchedEvent state:SyncConnected type:NodeCreatedpath:/usa/NewYork
                System.out.println(watchedEvent.getType());
            }
        });
    }


    // 创建节点
    @Test
    public void createNode() throws Exception{

        // 使用Zookeeper的客户端创建节点
        // 在根目录下 创建lagou目录，是持久节点
        String str = zkClient.create("/lagou","laosun".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("已创建节点:" + str);
    }


    // 获取节点上的值
    @Test
    public void getNodeData() throws Exception{
        // 返回的字节数组
        byte[] bytes = zkClient.getData("/lagou", false, new Stat());

        System.out.println("/lagou节点的数据：" + new String(bytes));
    }


    // 修改节点上的数据
    @Test
    public void updateDate() throws Exception {
        Stat stat = zkClient.setData("/lagou", "laosunA".getBytes(), 0);
        System.out.println(stat);
    }


    // 删除节点上的数据
    @Test
    public void deleteDate() throws Exception {
        zkClient.delete("/lagou",1);
        System.out.println("删除成功！");
    }



    // 获取子节点
    @Test
    public void getChildren() throws Exception{
        List<String> children = zkClient.getChildren("/china", false);

        for (String child : children) {
            System.out.println(child);
        }
    }



    // 监听根节点下面的变化
    @Test
    public void watchNode() throws Exception{
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
            // 让线程无限的等待下去
            System.in.read();

            // 线程睡眠
            //Thread.sleep(2000000000);
    }




    // 判断节点是否存在
    @Test
    public void exists() throws Exception{
        Stat exists = zkClient.exists("/lagou", false);
        if (exists == null) {
            System.out.println("不存在");
        } else {
            System.out.println("存在");
        }
    }
}
