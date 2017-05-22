package com.walid.gcd.mdb;

import com.walid.gcd.jdbc.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * <p>
 * A Message Driven Bean that asynchronously receives and persists the numbers from the JMS topic to the JNDI relational database.
 * </p>
 *
 * @author waleedaleem@hotmail.com
 *
 */


@MessageDriven(name = "HelloWorldQTopicMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/jms/topic/numbersTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class NumberTopicPersister implements MessageListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(NumberTopicPersister.class);

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                String strNumber = msg.getText();

                LOGGER.info("Received Message from topic: " + strNumber);

                // persist received number to NUMBERS database table
                new DbManager().persistNumber(Integer.parseInt(strNumber), DbManager.NumberTable.NUMBERS);
            } else {
                LOGGER.error("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}