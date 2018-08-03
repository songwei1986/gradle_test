package com.example.kafkacomsumer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestConsumer {
	public static Logger logger = LoggerFactory.getLogger(TestConsumer.class);
	private static KafkaConsumer<String, String> consumer;

	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.16.121:9093, 192.168.16.121:9094");
		props.put("group.id", "CountryCounter");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		consumer = new KafkaConsumer<>(props);
//		consumer.subscribe("test.*");
		consumer.subscribe(Collections.singletonList("CustomerCountry"));
		
		try {
			Map<String, Integer> custCountryMap = new HashMap<>();
			while(true) {
				
				// poll(timeout ms) poll方法的阻塞时间：超时强制返回；返回的快也被阻塞这么长时间；和应用程序处理时间互不影响，所以最好大于处理时间，否则可能处理不过来
				ConsumerRecords<String, String> records = consumer.poll(5000);
//				System.err.println("while---");
				for(ConsumerRecord<String, String> record: records) {
//					Thread.sleep(3000);
					System.err.println("for---");
					logger.debug("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n", 
							record.topic(), record.partition(), record.offset(),
							record.key(), record.value());
					
					int updatedCount = 1;
					if(custCountryMap.containsKey(record.value())) {
						updatedCount = custCountryMap.get(record.value()) + 1;
					}
					custCountryMap.put(record.value(), updatedCount);
					Gson gson = new GsonBuilder().create();
					System.err.println(gson.toJson(custCountryMap));
					
				}
				/*try {
//						consumer.commitSync();
					consumer.commitAsync(new OffsetCommitCallback() {
						@Override
						public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
							if(exception != null) {
								logger.error("Commit failed for offsets {}", offsets, exception);
							}
						}
					});
				} catch (Exception e) {
					logger.debug(e.getMessage());
				}*/
			}
		} finally {
			try {
				consumer.commitSync();
			} finally {
				consumer.close();
			}
		}
	}
	
	
}
