package com.tyhy.kafka;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncSendCallback implements Callback {
	public Logger logger = LoggerFactory.getLogger(AsyncSendCallback.class);
	
	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if(exception != null) {
			logger.info(exception.getMessage());
		}else {
			System.err.println("AsyncSendCallback" + metadata.toString());
		}

	}

}
