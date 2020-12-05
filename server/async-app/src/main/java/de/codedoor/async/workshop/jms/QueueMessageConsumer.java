package de.codedoor.async.workshop.jms;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@MessageDriven(name = "QueueMessageConsumer")
public class QueueMessageConsumer implements MessageListener {

    @Resource
    private MessageDrivenContext mdcContext;

    @Resource(name = "jms/asyncAppConnectionFactory")
    private ConnectionFactory asyncAppConnectionFactory;

    @Resource(name = "jms/asyncAppTopic")
    private Topic topic;

    @Override
    public void onMessage(Message message) {
        System.out.println("QueueMessageConsumer: " + message);
        new JmsTemplate(asyncAppConnectionFactory).send(topic, (MessageCreator) session -> message);

//        mdcContext.setRollbackOnly();
//		Connection con = null;
//		try {
//			con = topicConnectionFactory.createConnection();
//			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
//			MessageProducer producer = session.createProducer(topic);
//			if (message instanceof TextMessage) {
//				producer.send(session.createTextMessage(((TextMessage) message).getText()));
//			} else {
//				producer.send(message);
//			}
//			con.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//		}
    }

}
