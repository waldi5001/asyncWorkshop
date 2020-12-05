package de.fk.jms;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JavaSESender {
    
    public JavaSESender() {
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
//        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.send("seAsyncAppQueue", new MessageCreator() {
            @Override
            public Message createMessage(Session s) throws JMSException {
                return s.createTextMessage("Es war einmal, vor langer, langer Zeit");
            }
        });
    }

    public static void main(String[] args) {
        new JavaSESender();
    }

}
