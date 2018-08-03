package com.tyhy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsumerRecordHandlerImpl implements ConsumerRecordHandler {

	@Override
	public void onCompletion(ConsumerRecord consumerRecord) {
		System.err.println("ConsumerRecordHandlerImpl.onCompletion" + consumerRecord);
	}

}
