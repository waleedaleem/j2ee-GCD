package com.walid.gcd.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * A JMS publisher that pushes numbers to topic on JBoss AS
 *
 * @author waleedaleem@hotmail.com
 */

@ApplicationScoped
public class NumberPublisher {

    private static final String DEFAULT_CONNECTION_FACTORY = "java:/ConnectionFactory";
    private static final String DEFAULT_DESTINATION = "java:jboss/jms/topic/numbersTopic";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

    private Logger LOGGER = LoggerFactory.getLogger(NumberPublisher.class);

    /**
     * pushes ano integers to a JMS topic
     *
     * @return void
     */
    public boolean enqueueNumber(int number) {
        LOGGER.debug("enqueueing number {}", number);

        Context namingContext = null;

        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(PROVIDER_URL, System.getProperty(PROVIDER_URL, PROVIDER_URL));
        try {
            namingContext = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            LOGGER.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            LOGGER.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
            LOGGER.info("Attempting to acquire destination \"" + destinationString + "\"");
            Destination destination = (Destination) namingContext.lookup(destinationString);
            LOGGER.info("Found destination \"" + destinationString + "\" in JNDI");

            try {
                Connection connection = connectionFactory.createConnection();
                LOGGER.info("Sending JMS message with content: " + number);
                Session session = connection
                        .createSession(false, Session.AUTO_ACKNOWLEDGE);

                final MessageProducer publisher =
                        session.createProducer(destination);
                connection.start();

                final TextMessage message =
                        session.createTextMessage(String.valueOf(number));
                publisher.send(message);
                LOGGER.info("enqueued number {}", number);
            } catch (JMSException jmse) {
                LOGGER.error("Error sending JMS message", jmse);
            }

        } catch (NamingException e) {
            LOGGER.error("Error enqueueing number", e);
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException ignore) {
                }
            }
        }

        LOGGER.info("enqueued number {}", number);
        return true;
    }
}