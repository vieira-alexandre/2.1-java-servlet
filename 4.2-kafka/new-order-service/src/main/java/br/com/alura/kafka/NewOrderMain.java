package br.com.alura.kafka;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher()) {
            try (KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>()) {
                for (int i = 0; i < 10; i++) {
                    String userId = UUID.randomUUID().toString();
                    String orderId = UUID.randomUUID().toString();
                    BigDecimal amount = BigDecimal.valueOf((Math.random() * 5000) + 1);
                    Order order = new Order(userId, orderId, amount);
                    String key = UUID.randomUUID().toString();
                    orderDispatcher.send(Topics.ECOMMERCE_NEW_ORDER, userId, order);
                    Email email = new Email("We received your order", "Thank you for your order! We are processing it.");
                    emailDispatcher.send(Topics.ECOMMERCE_SEND_EMAIL, key, email);
                }
            }
        }
    }


}
