package com.tyhy.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;

public class TestProducer {
	public static void main(String[] args) {
		String topic = "CustomerCountry";
		String key = "key123";
		String value = "value123";
		
		// 有返回值
//		String result = MessageProducer.getInstance().syncSend("", "testKey99", "testValue99");
//		System.err.println(result);
		
		// 有回调
		MessageProducer.getInstance().asyncSend(topic, key, value, TestProducer.class, "customCallback");
	}
	
	public void customCallback(RecordMetadata recordMetadata) {
		System.err.println("asyncSend customCallback");
	}
}
