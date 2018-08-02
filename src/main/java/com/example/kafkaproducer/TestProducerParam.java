package com.example.kafkaproducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.record.CompressionType;

public class TestProducerParam {

	public static void main(String[] args) {
		TestProducerParam tfp = new TestProducerParam();
		tfp.testSimpleSend();
	}
	
	public void testSimpleSend() {
		Properties kafkaProperties = new Properties();
		kafkaProperties.put("bootstrap.servers", "192.168.16.121:9093, 192.168.16.121:9094");
		kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		kafkaProperties.put("acks", "all");
		kafkaProperties.put("buffer.memory", 16384);//bytes
		kafkaProperties.put("compression.type", CompressionType.SNAPPY.name);
		kafkaProperties.put("retries", 3);//重试次数
		kafkaProperties.put("retry.backoff.ms", 30);//重试时间间隔
		kafkaProperties.put("batch.size", 300);//一个批次可以使用的内存大小，单位byte, 超过"buffer.memory"时报错： Attempt to allocate 30000 bytes, but there is a hard limit of 16384 on memory allocations.
		kafkaProperties.put("linger.ms", 5);//发送批次之前等待更多消息加入的时间
		kafkaProperties.put("client.id", "testProducerClientId1");
		kafkaProperties.put("max.in.flight.requests.per.connection", 1);//设置位1能保证顺序，但是严重影响吞吐量
		// other params
		
		Producer<String, String> producer = new KafkaProducer<String, String>(kafkaProperties);
		ProducerRecord<String, String> record = new ProducerRecord<String, String>("CustomerCountry", "Precision Products", "France");
		
		try {
			System.err.println(producer.send(record).get().toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

}
