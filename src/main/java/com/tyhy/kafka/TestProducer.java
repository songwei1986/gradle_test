package com.tyhy.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;

public class TestProducer {
	public static void main(String[] args) {
		String topic = "CustomerCountry";
		String key = "key123";
		String value = "value123";
		
		// 有返回值
		RecordMetadata result = MessageProducer.getInstance().syncSend(topic, key, value);
		System.err.println("syncSendMessageResult: " + result.toString());
		
		// 有回调
//		MessageProducer.getInstance().asyncSend(topic, key, value, new AsyncSendCallback());
	}
}
