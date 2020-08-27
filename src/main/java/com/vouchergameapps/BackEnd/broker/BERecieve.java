package com.vouchergameapps.BackEnd.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.vouchergameapps.BackEnd.controller.ServiceController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BERecieve {
    ConnectionFactory factory = null;
    Channel channel = null;
    Connection connection = null;
    ServiceController sc = new ServiceController();
    final String QUEUE_NAME = "GAMEVOUCHERAPPSKEBE";
//        final String EXCHANGE_NAME = "APPVOUCHERKEBE";

    public void connectRabbit() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void receiveInputFromRest() throws IOException, TimeoutException {
        connectRabbit();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                sc.run(message);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
//                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck, deliverCallback, consumerTag -> { });
    }
}
