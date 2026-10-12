package com.example.iocdi;

// KHÔNG có @Component → Spring bỏ qua.
// Thử gắn @Component vào đây để thấy NoUniqueBeanDefinitionException.
public class EmailSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("[EMAIL] " + message);
    }
}