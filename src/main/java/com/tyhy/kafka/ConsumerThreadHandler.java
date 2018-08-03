package com.tyhy.kafka;

import java.lang.reflect.Method;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerThreadHandler implements Runnable {
	public Logger logger = LoggerFactory.getLogger(ConsumerThreadHandler.class);
    private ConsumerRecord consumerRecord;
    private ConsumerRecordHandler callback;

    public ConsumerThreadHandler(ConsumerRecord consumerRecord, ConsumerRecordHandler callback){
        this.consumerRecord = consumerRecord;
        this.callback = callback;
    }

    @Override
    public void run() {
		try {
			callback.onCompletion(consumerRecord);
		} catch (Exception e) {
			logger.debug("getKafakaMessageExpception", e);
		}
        
    }

}
