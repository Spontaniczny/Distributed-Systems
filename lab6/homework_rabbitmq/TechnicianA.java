import com.rabbitmq.client.*;

public class TechnicianA {
    private final Channel channel;
    private final String name = "Technician A";

    public TechnicianA(Channel channel) {
        this.channel = channel;
    }

    public void receiveExaminationRequest() throws Exception {
        String[] skills = {"knee", "hip"};
        for (String skill : skills) {
            String queueName = "examination." + skill;
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, RabbitMQConfig.EXCHANGE_NAME, queueName);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                String[] parts = message.split(", ");
                String patientName = parts[0].split(": ")[1];
                String examinationType = parts[1];
                String doctorName = parts[2];
                String result = String.format("%s, %s done by %s", patientName, examinationType, name);
                System.out.println(name + " processed: " + result);
                channel.basicPublish(RabbitMQConfig.EXCHANGE_NAME, "result." + doctorName, null, result.getBytes());
                channel.basicPublish(RabbitMQConfig.EXCHANGE_NAME, "admin.log", null, ("Processed: " + result).getBytes());
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        }
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

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConfig.createConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.EXCHANGE_NAME, "topic");

        TechnicianA technician = new TechnicianA(channel);
        technician.receiveExaminationRequest();
        technician.receiveInfo();
    }
}
