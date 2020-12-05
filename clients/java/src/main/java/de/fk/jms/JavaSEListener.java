package de.fk.jms;

import static javax.jms.Session.SESSION_TRANSACTED;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JavaSEListener {

    public JavaSEListener() {
        try {
            ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection c = cf.createConnection();
            c.start();

            Session session = c.createSession(true, SESSION_TRANSACTED);
            Queue queue = session.createQueue("seAsyncAppQueue");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(msg -> {
                System.out.println(msg);
                try {
                    // Do Stuff
                    session.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                    try {
                        session.rollback();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                        // hier ist dann alles zu spät.
                    }
                }
            });
//            Avoid this:
//            Message message = consumer.receive();
//            System.out.println(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JavaSEListener();
    }

}
