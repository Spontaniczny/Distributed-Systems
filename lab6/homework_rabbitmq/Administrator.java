import com.rabbitmq.client.*;

import java.util.Scanner;

public class Administrator {
    private final Channel channel;

    public Administrator(Channel channel) {
        this.channel = channel;
    }

    public void logActivity() throws Exception {
        channel.queueDeclare(RabbitMQConfig.ADMIN_QUEUE, false, false, false, null);
        channel.queueBind(RabbitMQConfig.ADMIN_QUEUE, RabbitMQConfig.EXCHANGE_NAME, "admin.log");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Admin log: " + message);
        };
        channel.basicConsume(RabbitMQConfig.ADMIN_QUEUE, true, deliverCallback, consumerTag -> { });
    }

    public void receiveInfo() throws Exception {
        channel.queueDeclare(RabbitMQConfig.INFO_QUEUE, false, false, false, null);
        channel.queueBind(RabbitMQConfig.INFO_QUEUE, RabbitMQConfig.EXCHANGE_NAME, "info.all");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Admin received info: " + message);
        };
        channel.basicConsume(RabbitMQConfig.INFO_QUEUE, true, deliverCallback, consumerTag -> { });
    }

    public void sendInfo(String message) throws Exception {
        channel.basicPublish(RabbitMQConfig.EXCHANGE_NAME, "info.all", null, message.getBytes());
        channel.basicPublish(RabbitMQConfig.EXCHANGE_NAME, "admin.log", null, ("Sent info: " + message).getBytes());
    }

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConfig.createConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.EXCHANGE_NAME, "topic");

        Administrator admin = new Administrator(channel);
        admin.logActivity();
        admin.receiveInfo();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter info message to send to all: ");
            String message = scanner.nextLine();
            admin.sendInfo(message);
        }
    }
}
