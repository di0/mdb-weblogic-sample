package develdio.com.archmdb.scheduler.message;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@RequestScoped
@Transactional(value=TxType.NOT_SUPPORTED)
public class Sender {
	@Resource(mappedName = "RELEASECF")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "RELEASEIN")
	private Queue queue;

	public void send(String strMessage) throws JMSException {
		Connection connection = null;
		Session session = null;
		TextMessage message = null;
		MessageProducer producer = null;

		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(queue);
			TextMessage msg1 = session.createTextMessage();
			msg1.setText("Text");
			producer.send(msg1);
		} catch (JMSException e) {
			throw new JMSException("An error occurred at put the message on queue.", e.getMessage());
		} finally {
			try {
				if (producer != null)
					producer.close();
			} catch (Exception ignored) {
				/* For explanatory purposes. Doesn't ignore it within real scenario ... */
			}

			try {
				if (session != null)
					session.close();
			} catch (Exception ignored) {
				/* For explanatory purposes. Doesn't ignore it within real scenario ... */
			}

			try {
				if (connection != null)
					connection.close();
			} catch (Exception ignored) {
				/* For explanatory purposes. Doesn't ignore it within real scenario ... */
			}
		}
	}
}
