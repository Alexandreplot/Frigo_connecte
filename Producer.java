package com.learn.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

public class Producer {
    static final String EXCHANGE_NAME = "DemoExchange";
    static final String QUEUE_NAME = "Frigo";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        for (int i = 0; i < 10; i++) {
            String msg = "msgid:" + ThreadLocalRandom.current().nextInt(100, 200);
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
            System.out.println("Produced: " + msg);
        }

        channel.close();
        connection.close();
    }
}
