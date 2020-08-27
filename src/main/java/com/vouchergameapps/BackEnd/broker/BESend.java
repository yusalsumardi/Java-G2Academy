package com.vouchergameapps.BackEnd.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class BESend {
    private final static String QUEUE_NAME = "GAMEVOUCHERAPPSKEREST";
    private static final String EXCHANGE_NAME = "APPVOUCHER";
    boolean durable = true;

    public void SendLog(String a) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = a;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
