package com.example.kafkaproducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TestKafkaProducer {

	public static void main(String[] args) {
		TestKafkaProducer tfp = new TestKafkaProducer();
		
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "192.168.16.121:9093, 192.168.16.121:9094");
		kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> producer = new KafkaProducer<String, String>(kafkaProperties);
		
//		tfp.testSimpleSend(kafkaProperties, producer);
//		tfp.testSyncSend(kafkaProperties, producer);
		tfp.testAsyncSend(kafkaProperties, producer);
	}
	
	public void testSimpleSend(Properties kafkaProperties, Producer<String, String> producer) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>("CustomerCountry", "Precision Products", "France");
		
		try {
			producer.send(record);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
	
	public void testSyncSend(Properties kafkaProperties, Producer<String, String> producer) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>("CustomerCountry", "Precision Products", "France");
		
		try {
			RecordMetadata metaData = producer.send(record).get();
			System.err.println(metaData.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
	
	public void testAsyncSend(Properties kafkaProperties, Producer<String, String> producer) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>("CustomerCountry", "Biomedical Materials", "USA");
		try {
			RecordMetadata metaData = producer.send(record, new DemoProducerCallback()).get();
			System.err.println(metaData.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

	private class DemoProducerCallback implements Callback{
		@Override
		public void onCompletion(RecordMetadata metadata, Exception exception) {
			if(exception != null) {
				exception.printStackTrace();
			}else {
				System.out.println("demo product callback");
			}
		}
		
	}
}
