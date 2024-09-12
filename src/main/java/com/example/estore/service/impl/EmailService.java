package com.example.estore.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Async
    public void sendTransactionEmailConfirmation(String to, String transactionDetail) {
        try {
            Thread.sleep(5000);
            System.out.println("Email sent to " + to + " with detail: " + transactionDetail);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.out.println(e);
        }
    }
}
