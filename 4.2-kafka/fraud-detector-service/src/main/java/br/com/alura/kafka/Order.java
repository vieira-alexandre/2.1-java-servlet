package br.com.alura.kafka;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class Order {
    private final String userId, id;
    private final BigDecimal amount;

    public Order(String userId, String id, BigDecimal amount) {
        this.userId = userId;
        this.id = id;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("userId='" + userId + "'")
                .add("id='" + id + "'")
                .add("amount=" + amount)
                .toString();
    }
}
