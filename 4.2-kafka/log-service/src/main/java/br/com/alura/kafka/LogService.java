package br.com.alura.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;

public class LogService {
    public static void main(String[] args) {
        LogService logService = new LogService();

        try (KafkaService service =
                     new KafkaService(groupId(), Pattern.compile(Topics.ECOMMERCE_REGEX), logService::parse, String.class,
                             Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            service.run();
        }
    }

    private static String groupId() {
        return LogService.class.getSimpleName();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("================================================");
        System.out.println("LOG: " + record.topic());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println((record).partition());
        System.out.println((record).offset());
        System.out.println((record).timestamp());
    }
}
