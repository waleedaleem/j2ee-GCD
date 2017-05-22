package com.walid.gcd.jms;

import javax.jms.JMSDestinationDefinition;

/**
 * A JMS topic configured on JBoss AS to host numbers
 *
 * @author waleedaleem@hotmail.com
 */

@JMSDestinationDefinition(
        name = NumbersTopicDefinition.NUMBERS_TOPIC,
        interfaceName = "javax.jms.Topic"
)

public class NumbersTopicDefinition {
    public static final String NUMBERS_TOPIC = "java:jboss/jms/topic/numbersTopic";
}
