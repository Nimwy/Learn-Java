package com.example.iocdi;

import org.springframework.stereotype.Component;

@Component
public class ZaloSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("[ZALO] " + message);
    }
}