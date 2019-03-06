package de.codedoor.async.workshop.amqp;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;

class Publisher {

	private static final String TOPIC_PREFIX = "topic://";

	public Publisher() {
		String host = "localhost";
		String port = "5672";

		String connectionURI = "amqp://" + host + ":" + port;
		String destinationName = TOPIC_PREFIX + "asyncAppTopic";
		destinationName = "asyncAppQueue";

		int messages = 10;

		try {
			JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);

			Connection connection = factory.createConnection();
			connection.setClientID("pubFlorian");
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = null;
			if (destinationName.startsWith(TOPIC_PREFIX)) {
				destination = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));
			} else {
				destination = session.createQueue(destinationName);
			}

			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			for (int i = 1; i <= messages; i++) {
				TextMessage msg = session.createTextMessage("#:" + i);
				msg.setIntProperty("id", i);
				producer.send(msg);
				if ((i % 1000) == 0) {
					System.out.println(String.format("Sent %d messages", i));
				}
			}
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Publisher();
	}

}