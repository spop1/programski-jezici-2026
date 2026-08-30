package rs.ac.singidunum.pj.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE_NAME = "reservation_exchange";
    public static final String QUEUE_NAME = "notification_queue";
    public static final String ROUTING_KEY = "reservation.created";

    // Define the durable Queue
    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange reservationExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue notificationQueue, DirectExchange reservationExchange) {
        return BindingBuilder.bind(notificationQueue).to(reservationExchange).with(ROUTING_KEY);
    }

}