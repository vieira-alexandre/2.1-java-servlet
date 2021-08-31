package br.com.alura.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.Instant;

public class EmailService {
    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        try(KafkaService service = new KafkaService(groupId(), Topics.ECOMMERCE_SEND_EMAIL, emailService::parse, Email.class)) {
            service.run();
        }
    }

    private static String groupId() {
        return EmailService.class.getSimpleName();
    }

    private void parse(ConsumerRecord<String, Email> record) {
        System.out.println("================================================");
        System.out.println("Partition: " + record.partition());
        System.out.println("OffSet: " + record.offset());
        System.out.println("Timestamp: " + Instant.ofEpochMilli(record.timestamp()));
        System.out.println("\nSending email");
        System.out.println("Subject: " + record.value().getSubject());
        System.out.println("Text: " + record.value().getText());
        System.out.println("================================================");


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
