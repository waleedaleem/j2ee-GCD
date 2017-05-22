package com.walid.gcd;

import com.walid.gcd.jms.NumberPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A RESTful-Style JAX-WS web service implementing the Provider interface
 *
 * @author waleedaleem@hotmail.com
 */

@WebServiceProvider
@ServiceMode(value = Service.Mode.MESSAGE)
// HTTP versus SOAP binding
@BindingType(value = HTTPBinding.HTTP_BINDING)

public class RESTfulQueueService implements Provider<Source> {

    @Resource
    protected WebServiceContext wsContext;
    private Logger LOGGER = LoggerFactory.getLogger(RESTfulQueueService.class);
    // queue of received numbers
    private List<Integer> numberList = Collections.synchronizedList(new LinkedList());

    @Override
    public Source invoke(Source T) {
        MessageContext msg_cxt = wsContext.getMessageContext();
        String httpMethod = (String) msg_cxt.get(MessageContext.HTTP_REQUEST_METHOD);
        String httpPath = (String) msg_cxt.get(MessageContext.PATH_INFO);

        LOGGER.info("received httpMethod = [{}], httpPath = [{}]", httpMethod, httpPath);

        if (httpMethod.equalsIgnoreCase("POST") && httpPath.matches("/push/\\d+/\\d+")) {
            String[] segments = httpPath.split("/");
            return pushToQueue(Integer.valueOf(segments[2]), Integer.valueOf(segments[3]));
        } else if (httpMethod.equalsIgnoreCase("GET") && httpPath.startsWith("/list")) {
            return listQueue();
        }
        return new StreamSource(new StringReader("<false/>"));
    }

    /**
     * Push two integers into a JMS queue
     *
     * @return "true" or "false" as status
     */
    private Source pushToQueue(int i1, int i2) {

        String status;

        //if (numberList.add(i1) && numberList.add(i2)) {
        NumberPublisher jmsPublisher = new NumberPublisher();
        if (jmsPublisher.enqueueNumber(i1) && jmsPublisher.enqueueNumber(i2)) {
            status = "<true/>";
        } else {
            status = "<false/>";
        }
        return new StreamSource(new StringReader(status));
    }

    /**
     * list JMS queue number contents
     *
     * @return list of numbers in JSON format
     */
    private Source listQueue() {
        String jsonList = "[1, 2, 3]";
        return new StreamSource(new StringReader(
                String.format("<jsonlist>%s</jsonlist>", jsonList)
        ));
    }
}