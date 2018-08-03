package com.tyhy.kafka;

public class TestConsumer {
	public static void main(String[] args) throws InterruptedException {
		String topic = "CustomerCountry";
		String groupId = "CountryCounter";
		Class clazz = TestConsumer.class;
		String methodName = "getMessage";
		MessageConsumer messageConsumer = new MessageConsumer(topic, groupId, clazz, methodName);
		messageConsumer.start(10);
	}
	
	public void getMessage(String topic, String key, String value) {
		System.err.println("Test.getMessage start");
		System.err.println("topic: " + topic);
		System.err.println("key: " + key);
		System.err.println("value: " + value);
		System.err.println("Test.getMessage end");
	}
}
