import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "hospital_exchange";
    public static final String ADMIN_QUEUE = "admin_queue";
    public static final String INFO_QUEUE = "info_queue";

    public static Connection createConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }
}
