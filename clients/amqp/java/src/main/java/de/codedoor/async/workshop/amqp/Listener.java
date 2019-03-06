package de.codedoor.async.workshop.amqp;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.qpid.jms.JmsConnectionFactory;

class Listener {

	private static final String TOPIC_PREFIX = "topic://";

	public Listener() {
		String host = "localhost";
		String port = "5672";

		String connectionURI = "amqp://" + host + ":" + port;
		String destinationName = TOPIC_PREFIX + "asyncAppTopic";

		try {
			JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);

			Connection connection = factory.createConnection();
			connection.setClientID("YOUR_NAME");
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = null;
			MessageConsumer consumer;
			if (destinationName.startsWith(TOPIC_PREFIX)) {
				destination = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));
				consumer = session.createDurableSubscriber((Topic) destination, "YOUR_NAME");
			} else {
				destination = session.createQueue(destinationName);
				consumer = session.createConsumer(destination);
			}

			System.out.println("Waiting for messages...");
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					if (message instanceof TextMessage) {
						try {
							System.out.println(((TextMessage) message).getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println(message);
					}
				}
			});

//			consumer.setMessageListener(msg -> {
//				if (msg instanceof TextMessage) {
//					try {
//						System.out.println(((TextMessage) msg).getText());
//					} catch (JMSException e) {
//						e.printStackTrace();
//					}
//				} else {
//					System.out.println(msg);
//				}
//			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Listener();
	}

}