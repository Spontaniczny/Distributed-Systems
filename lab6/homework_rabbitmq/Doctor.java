import com.rabbitmq.client.*;

import java.util.Scanner;

public class Doctor {
    private final String name;
    private final Channel channel;

    public Doctor(String name, Channel channel) {
        this.name = name;
        this.channel = channel;
    }

    public void sendExaminationRequest(String patientName, String examinationType) throws Exception {
        String message = String.format("Request: %s, %s, %s", patientName, examinationType, name);
        channel.basicPublish(RabbitMQConfig.EXCHANGE_NAME, "examination." + examinationType, null, message.getBytes());
        channel.basicPublish(RabbitMQConfig.EXCHANGE_NAME, "admin.log", null, ("Sent: " + message).getBytes());
    }

    public void receiveExaminationResult() throws Exception {
        String queueName = "result." + name;
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, RabbitMQConfig.EXCHANGE_NAME, queueName);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(name + " received result: " + message);
            channel.basicPublish(RabbitMQConfig.EXCHANGE_NAME, "admin.log", null, ("Received: " + message).getBytes());
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    public void receiveInfo() throws Exception {
        String queueName = "info." + name;
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, RabbitMQConfig.EXCHANGE_NAME, "info.all");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(name + " received info: " + message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    public void requestLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter patient name: ");
                String patientName = scanner.nextLine();
                System.out.print("Enter examination type (hip, knee, elbow): ");
                String examinationType = scanner.nextLine();
                sendExaminationRequest(patientName, examinationType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConfig.createConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.EXCHANGE_NAME, "topic");

        Doctor doctor = new Doctor("Dr. Marek Marucha", channel);
        doctor.receiveExaminationResult();
        doctor.receiveInfo();
        doctor.requestLoop();
    }
}
