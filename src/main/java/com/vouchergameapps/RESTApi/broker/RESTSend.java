package com.vouchergameapps.RESTApi.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class RESTSend {
    //============================SEND STRING=================================
    public void sendJson(String jsonstring) throws Exception {
        final String QUEUE_NAME = "GAMEVOUCHERAPPSKEBE";
//        final String EXCHANGE_NAME = "APPVOUCHERKEBE";
        System.out.println("Data JSON sukses di terima RESTAPI dan akan dikirim ke RabbitMQ");
        boolean durable = true;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = jsonstring;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
