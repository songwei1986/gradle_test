package com.tyhy.kafka;

public class TestConsumer {
	public static void main(String[] args) throws InterruptedException {
		String topic = "CustomerCountry";
		String groupId = "CountryCounter";
		MessageConsumer messageConsumer = new MessageConsumer(topic, groupId, ConsumerRecordHandlerImpl.class);
		messageConsumer.start(10);
	}
}
