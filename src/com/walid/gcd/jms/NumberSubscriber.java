package com.walid.gcd.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A JMS subscriber that consumes numbers from topic on JBoss AS
 *
 * @author waleedaleem@hotmail.com
 */

public class NumberSubscriber {

    private Logger LOGGER = LoggerFactory.getLogger(NumberSubscriber.class);

    @Inject
    private JMSContext context;
    @Resource(mappedName = NumbersTopicDefinition.NUMBERS_TOPIC)
    private Topic syncTopic;

    /**
     * synchronously consumes an arbitrary number of integers from the JMS topic
     *
     * @return List<Integer> a list of consumed numbers from the JMS topic
     */
    public List<Integer> synchConsume(int numberCount) {
        List<Integer> numbers = new ArrayList<>(numberCount);

        LOGGER.debug("consuming {} numbers from topic", numberCount);

        for (int n = 0; n < numberCount; n++) {
            numbers.add(Integer.parseInt(context.createConsumer(syncTopic).receive().toString()));
        }

        LOGGER.info("gcd() consumed {} from JMS numbers topic",
                String.join(", ", numbers
                        .stream()
                        .map(n -> String.valueOf(n)).collect(Collectors.toList())));
        return numbers;
    }
}
