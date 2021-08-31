package br.com.alura.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.Instant;

public class FraudDetectorService {
    public static void main(String[] args) {
        FraudDetectorService fraudService = new FraudDetectorService();
        try(KafkaService service = new KafkaService(groupId(), Topics.ECOMMERCE_NEW_ORDER, fraudService::parse, Order.class)) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("================================================");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.value());
        System.out.println("Partition: " + (record).partition());
        System.out.println("OffSet: " + (record).offset());
        System.out.println("Timestamp: " + Instant.ofEpochMilli(record.timestamp()));

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            //ignoring
            e.printStackTrace();
        }
    }

    private static String groupId() {
        return FraudDetectorService.class.getSimpleName();
    }
}
