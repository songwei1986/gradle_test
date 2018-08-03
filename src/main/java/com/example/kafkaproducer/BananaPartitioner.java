package com.example.kafkaproducer;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.network.InvalidReceiveException;
import org.apache.kafka.common.utils.Utils;

public class BananaPartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		
		if((keyBytes == null) || (!(key instanceof String))) 
			throw new InvalidReceiveException("We except all messages to have customer name as key");
		if(((String)key).equals("Banana"))
			return numPartitions;
		return (Math.abs(Utils.murmur2(keyBytes)) % (numPartitions - 1));
	}

	@Override
	public void close() {
	}

}
