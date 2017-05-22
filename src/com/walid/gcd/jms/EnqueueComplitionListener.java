package com.walid.gcd.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.CompletionListener;
import javax.jms.Message;

/**
 * A JMS publisher completion listener
 *
 * @author waleedaleem@hotmail.com
 */
public class EnqueueComplitionListener implements CompletionListener {

    private Logger LOGGER = LoggerFactory.getLogger(EnqueueComplitionListener.class);

    @Override
    public void onCompletion(Message message) {
        LOGGER.debug("Number was successfully enqueued");
    }

    @Override
    public void onException(Message message, Exception e) {
        LOGGER.debug("Error enqueueing Number", e);
    }
}
