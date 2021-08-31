package br.com.alura.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction consumerFunction;

    public KafkaService(String groupId, String topic, ConsumerFunction consumerFunction, Class<T> type) {
        this.consumerFunction = consumerFunction;
        this.consumer = new KafkaConsumer<>(getProperties(type, groupId));
        this.consumer.subscribe(Collections.singletonList(topic));
    }

    public KafkaService(String groupId, Pattern topics, ConsumerFunction consumerFunction, Class<T> type,
                        Map<String, String> extraProperties) {
        this.consumerFunction = consumerFunction;
        this.consumer = new KafkaConsumer<>(getProperties(type, groupId, extraProperties));
        this.consumer.subscribe(topics);
    }

    public void run() {
        while (true) {
            ConsumerRecords<String, T> records = this.consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) System.out.println(records.count() + " records found");

            for (ConsumerRecord<String, T> record : records) {
                consumerFunction.consume(record);
            }
        }
    }

    private Properties getProperties(Class<T> type, String groupId, Map<String, String> overrideProperties) {
        var properties = getProperties(type, groupId);
        properties.putAll(overrideProperties);
        return properties;
    }

    private Properties getProperties(Class<T> type, String groupId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        return properties;
    }

    @Override
    public void close() {
        this.consumer.close();
    }
}
