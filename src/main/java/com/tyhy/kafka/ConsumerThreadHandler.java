package com.tyhy.kafka;

import java.lang.reflect.Method;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerThreadHandler implements Runnable {
	public Logger logger = LoggerFactory.getLogger(ConsumerThreadHandler.class);
    private ConsumerRecord consumerRecord;
    private Class clazz;
    private String methodName;

    public ConsumerThreadHandler(ConsumerRecord consumerRecord, Class clazz, String methodName){
        this.consumerRecord = consumerRecord;
        this.clazz = clazz;
        this.methodName = methodName;
    }

    @Override
    public void run() {
		try {
			Object obj = Class.forName(clazz.getName()).newInstance();
			Method method = clazz.getMethod(methodName, String.class, String.class, String.class);
			method.invoke(obj, consumerRecord.topic(), consumerRecord.key(), consumerRecord.value());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
        
    }

}
