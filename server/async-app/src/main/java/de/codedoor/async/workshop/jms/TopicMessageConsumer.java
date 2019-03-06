package de.codedoor.async.workshop.jms;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "TopicMessageConsumer")
public class TopicMessageConsumer implements MessageListener {

	@Resource
	private MessageDrivenContext mdcContext;

	@Override
	public void onMessage(Message message) {
		System.out.println("TopicMessageConsumer: " + message);
		if (message instanceof BytesMessage) {
			try {
				byte[] byteData = new byte[(int) ((BytesMessage) message).getBodyLength()];
				((BytesMessage) message).readBytes(byteData);
//				System.out.println(new String(byteData));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof TextMessage) {
			try {
				System.out.println(((TextMessage) message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
//		mdcContext.setRollbackOnly();
	}

}
