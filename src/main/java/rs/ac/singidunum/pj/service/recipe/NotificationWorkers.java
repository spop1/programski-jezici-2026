package rs.ac.singidunum.pj.service.recipe;
import rs.ac.singidunum.pj.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationWorkers {

   @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void processNotification(String message) {
        System.out.println("--- RABBITMQ WORKER STARTED ---");
        System.out.println("Received tst for Reservation ID: " +message);
        System.out.println("Generating PDF receipt and connecting to Email server...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Email with PDF successfully sent to the user in the background.");
        System.out.println("--- WORKER FINISHED ---");
    }
}
