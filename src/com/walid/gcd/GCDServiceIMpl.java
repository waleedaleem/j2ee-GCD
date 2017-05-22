package com.walid.gcd;

import com.walid.gcd.jms.NumberSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * A SOAP JAX-WS web service implementation bean
 *
 * @author waleedaleem@hotmail.com
 */


@WebService(targetNamespace = "http://www.walid.com/", serviceName = "gcdWebService")
public class GCDServiceIMpl implements GCDService, Serializable {

    private static Logger LOGGER = LoggerFactory.getLogger(GCDServiceIMpl.class);

    /**
     * consumes two integers from a JMS queue
     * calculates the GCD
     * persists result to database
     *
     * @return the GCD integer result
     */
    private static int getGCD(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);

        LOGGER.info("calculated GCD of {} and {} as {}", a, b, gcd);
        return gcd.intValue();
    }

    /**
     * consumes two integers from a JMS queue
     * calculates the GCD
     * persists result to database
     *
     * @return the GCD integer result
     */
    @Override
    public int gcd() {
        // consume the top 2 integers from the topic
        //final List<Integer> integers = Arrays.asList(1, 2);
        final List<Integer> integers = new NumberSubscriber().synchConsume(2);

        // calculate GCD
        int gcdNumber = getGCD(integers.get(0), integers.get(1));

        //TODO: persist gcd result to database

        return gcdNumber;
    }
}
