package com.vouchergameapps.RESTApi.broker;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RESTReceiver {
    private final static String QUEUE_NAME = "GAMEVOUCHERAPPSKEREST";
    private static final String EXCHANGE_NAME = "APPVOUCHER";

    public String receiveFromDB() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        final String[] msg = new String[1];
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//        boolean durable = true;
        boolean ack = false;

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        GetResponse response = channel.basicGet(QUEUE_NAME, ack);
        while (response == null) {
            response = channel.basicGet(QUEUE_NAME, ack);
        }
        channel.basicAck(response.getEnvelope().getDeliveryTag(),false);
        System.out.println(new String(response.getBody(), "UTF-8"));
        return new String(response.getBody(), "UTF-8");
    }
}
