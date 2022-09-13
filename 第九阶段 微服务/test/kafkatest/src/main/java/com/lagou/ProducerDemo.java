package com.lagou;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @BelongsProject kafkatest
 * @Author lengy
 * @CreateTime 2022/9/6 17:15
 * @Description 消息生产者
 *  先指定配置信息（kafka集群等相关配置）封装到Properties对象中
 *  构建一个生产者对象并传入Properties，获取连接
 *  指定主题topic，通过for循环构造消息
 *  把消息放到消息对象后，直接使用生产对象send
 */
public class ProducerDemo {

    public static void main(String[] args) {

        // 要构建一个消息生产者对象，关于Kafka集群等相关配置，可以从Properties文件中加载，也可以从一个Properties对象中加载
        // KafkaProducer按照固定的key取出对应的value
        Properties properties = new Properties();
        // 指定集群节点
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.200.20:9092,192.168.200.20:9093,192.168.200.20:9094");
        // 发送消息，涉及到网络传输，需要对key和value指定对应的序列化类
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 创建消息生产者对象
        KafkaProducer<String,String> kafkaProducer = new KafkaProducer<String, String>(properties);
        // 主题
        String topic = "lagou";
        // 发送出100条消息
        for (int i = 0; i < 100; i++) {
            // 设置消息内容
            String msg = "hello"+i;
            // 构建一个消息对象：主题(如果不存在，kafka会帮我们创建一个一个分区一个副本的主题)，消息
            ProducerRecord<String,String> record = new ProducerRecord<>(topic,msg);
            // 发送
            kafkaProducer.send(record);
            System.out.println("消息发送成功:"+ msg);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 关闭消息生产对象
        kafkaProducer.close();
    }
}
