package com.lagou;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

/**
 * @BelongsProject kafkatest
 * @Author lengy
 * @CreateTime 2022/9/6 17:32
 * @Description 消息消费者
 */
public class ConsumerDemo {

    public static void main(String[] args) {

        // 属性对象
        Properties properties = new Properties();
        // 指定集群节点
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.200.20:9092,192.168.200.20:9093,192.168.200.20:9094");
        // 拉取消息，进行反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 指定分组名称
        // 就是指定分组，避免消费者二次消费
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"lagou_grop1");
        // 消息消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        // 主题
        String topic = "lagou";
        // 订阅消息，就是当目标主题有了消息后，消费者就可以进行消费了
        consumer.subscribe(Collections.singletonList(topic));
        // 接收消息
        // 一直处于监听状态，只要有消息，就进行拉取消费
        // 每次拉取完，偏移量都会+1
        while (true) {
            // 获取消息的方法是一个阻断式方法  只要没有接收到消息，就会一直等
            // poll方法时，就是从kafka中心去拉取数据。如果没有数据，就会一直等
            ConsumerRecords<String, String> records = consumer.poll(500);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("主题：" + record.topic() + ",偏移量：" + record.offset() + ",msg" + record.value());
            }
        }
    }
}
