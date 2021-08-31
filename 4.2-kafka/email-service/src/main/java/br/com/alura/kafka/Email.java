package br.com.alura.kafka;

public class Email {
    private String subject, text;

    public Email(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
