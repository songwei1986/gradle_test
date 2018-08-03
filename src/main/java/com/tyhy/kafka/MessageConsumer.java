package com.tyhy.kafka;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageConsumer {
	public Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    
    private Properties kafkaProperties = null;
	private KafkaConsumer<String, String> consumer = null;
	private ExecutorService executor;
	private String topic;
	private String groupId;
	private Class clazz;
	private String methodName;
	
	public MessageConsumer(String topic, String groupId, Class clazz, String methodName){
        this.topic = topic;
        this.groupId = groupId;
        this.clazz = clazz;
        this.methodName = methodName;
    }
	
    private void init() {
    	kafkaProperties = new Properties();
    	kafkaProperties.put("bootstrap.servers", "192.168.16.121:9093, 192.168.16.121:9094");
    	kafkaProperties.put("group.id", groupId);
    	kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    	kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    	consumer = new KafkaConsumer<String, String>(kafkaProperties);
    	this.consumer.subscribe(Arrays.asList(this.topic));
    }
    
    public void start(int threadNumber){
    	init();
    	// TODO
        executor = new ThreadPoolExecutor(threadNumber, threadNumber, 0l, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(1000));
        while (true){
            ConsumerRecords<String,String> consumerRecords = consumer.poll(100);
            for (ConsumerRecord<String, String> item : consumerRecords){
                executor.submit(new ConsumerThreadHandler(item, clazz, methodName));
            }
        }
    }
}
