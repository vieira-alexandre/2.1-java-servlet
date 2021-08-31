package br.com.alura.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher<T> implements Closeable {
    private final KafkaProducer<String, T> producer;

    public KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    public void send(String topic, String key, T value) throws ExecutionException, InterruptedException {
        ProducerRecord<String, T> record = new ProducerRecord<String, T>(topic, key, value);
        this.producer.send(record, callback()).get();
    }

    private static Callback callback() {
        return (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }

            System.out.println("Enviado: " + metadata.topic() + ":::partition " + metadata.partition() + "/ offset " +
                    metadata.offset() + "/ timestamp " + metadata.timestamp());
        };
    }

    private static Properties properties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    @Override
    public void close()  {
        this.producer.close();
    }
}
