package com.walid.gcd.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * A JMS publisher that pushes numbers to topic on JBoss AS
 *
 * @author waleedaleem@hotmail.com
 */

@ApplicationScoped
public class JMSClient {

    private static final String DEFAULT_CONNECTION_FACTORY = "java:/ConnectionFactory";
    private static final String DEFAULT_DESTINATION = "java:jboss/jms/topic/numbersTopic";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
    Destination destination;
    Connection connection;
    private Session session;
    private MessageProducer publisher;
    private MessageConsumer subscriber;

    private Logger LOGGER = LoggerFactory.getLogger(JMSClient.class);

    /**
     * Constructor to setup JMS connection.
     */
    public JMSClient() {
        LOGGER.info("Setting up JMS connection");

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
            destination = (Destination) namingContext.lookup(destinationString);
            LOGGER.info("Found destination \"" + destinationString + "\" in JNDI");

            try {
                connection = connectionFactory.createConnection();
                session = connection
                        .createSession(false, Session.AUTO_ACKNOWLEDGE);

                publisher =
                        session.createProducer(destination);
                connection.start();
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
    }

    /**
     * initialise the publisher role of the JMS client
     *
     * @return JMSClient
     */
    public JMSClient asPublisher() {
        try {
            publisher =
                    session.createProducer(destination);
            connection.start();
        } catch (JMSException jmse) {
            LOGGER.error("Error sending JMS message", jmse);
        }
        return this;
    }

    /**
     * pushes ano integers to a JMS topic
     *
     * @return void
     */
    public boolean enqueueNumber(int number) {
        LOGGER.debug("enqueueing number {}", number);
        try {
            final TextMessage message =
                    session.createTextMessage(String.valueOf(number));
            publisher.send(message);
            LOGGER.info("enqueued number {}", number);
        } catch (JMSException jmse) {
            LOGGER.error("Error sending JMS message", jmse);
        }

        LOGGER.info("enqueued number {}", number);
        return true;
    }

    /**
     * initialise the subscriber role of the JMS client
     *
     * @return JMSClient
     */
    public JMSClient asSubscriber() {
        try {
            subscriber = session.createConsumer(destination);
            connection.start();
        } catch (JMSException jmse) {
            LOGGER.error("Error sending JMS message", jmse);
        }
        return this;
    }

    /**
     * synchronously consumes an arbitrary number of integers from the JMS topic
     *
     * @return List<Integer> a list of consumed numbers from the JMS topic
     */
    public List<Integer> synchConsume(int numberCount) {
        List<Integer> numbers = new ArrayList<>(numberCount);

        LOGGER.info("consuming {} numbers from topic", numberCount);
        for (int n = 0; n < numberCount; n++) {
            try {
                String msg = ((TextMessage)subscriber.receive()).getText();
                LOGGER.info("received message {}", msg);
                numbers.add(Integer.parseInt(msg));
            } catch (JMSException jmse) {
                LOGGER.error("Error consuming JMS message", jmse);
            }
        }

        LOGGER.info("gcd() consumed {} from JMS numbers topic",
                String.join(", ", numbers
                        .stream()
                        .map(n -> String.valueOf(n)).collect(Collectors.toList())));
        return numbers;
    }
}