package develdio.com.archmdb.scheduler.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.JMSException;

import develdio.com.archmdb.scheduler.constants.Constants;

@MessageDriven(
		name = "develdio.com.archmdb.scheduler.mdb.EntityResourceMDB",
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = Constants.QUEUE_NAME_IN)
		})
public class EntityResourceMDB implements MessageListener {
	@Override
	public void onMessage(Message message) {
		System.out.println("Message received ...");

		try {
			TextMessage msg = null;
			if (message instanceof TextMessage) {
				msg = (TextMessage) message;
				System.out.println(msg.getText());
			} else {
				System.out.println("Invalid format ...");
			}
		} catch (JMSException j) {
			System.out.println("Error: " + j.getMessage());
		}
	}
}
