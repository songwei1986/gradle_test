package com.tyhy.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProducer {
	public Logger logger = LoggerFactory.getLogger(MessageProducer.class);
	
	private static MessageProducer messageProducer;
    public static MessageProducer getInstance(){
        if (messageProducer == null) {
            synchronized (MessageProducer.class) {
                if (messageProducer == null) {
                	messageProducer = new MessageProducer();
                	messageProducer.init();
                }
            }
        }
        return messageProducer;
    }
    
    private Properties kafkaProperties = null;
	private Producer<String, String> producer = null;
	
    private void init() {
    	kafkaProperties = new Properties();
    	kafkaProperties.put("bootstrap.servers", "192.168.16.121:9093, 192.168.16.121:9094");
    	kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	producer = new KafkaProducer<String, String>(kafkaProperties);
    }
    
	public RecordMetadata syncSend(String topic, String key, String value) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
		try {
			return producer.send(record).get();
		} catch (Exception e) {
			logger.debug("sendKafakaMessageExpception", e);
		} finally {
			producer.close();
		}
		return null;
	}
	
	public void asyncSend(String topic, String key, String value, Callback callback) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
		try {
			producer.send(record, callback);
		} catch (Exception e) {
			logger.debug("sendKafakaMessageExpception", e);
		} finally {
			producer.close();
		}
	}

	
	

}
