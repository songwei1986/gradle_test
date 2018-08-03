package com.tyhy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerRecordHandler {
	public void onCompletion(ConsumerRecord consumerRecord);
}
