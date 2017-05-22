package com.walid.gcd.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 * A JMS publisher that pushes numbers to topic on JBoss AS
 *
 * @author waleedaleem@hotmail.com
 */

@ApplicationScoped
public class NumberPublisher {

    private Logger LOGGER = LoggerFactory.getLogger(NumberPublisher.class);

    @Inject
    private JMSContext context;
    @Resource(mappedName = NumbersTopicDefinition.NUMBERS_TOPIC)
    private Topic syncTopic;


    /**
     * pushes ano integers to a JMS topic
     *
     * @return void
     */
    public boolean enqueueNumber(int number) {
        LOGGER.debug("enqueueing number {}", number);
        context.createProducer().send(syncTopic, number);
        LOGGER.info("enqueued number {}", number);
        return true;
    }
}
