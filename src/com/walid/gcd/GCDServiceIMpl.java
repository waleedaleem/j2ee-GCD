package com.walid.gcd;

import com.walid.gcd.jdbc.DbManager;
import com.walid.gcd.jms.JMSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import java.math.BigInteger;
import java.util.List;

/**
 * A SOAP JAX-WS web service implementation bean
 *
 * @author waleedaleem@hotmail.com
 */


@WebService(name = "GCDService")
public class GCDServiceIMpl implements GCDService {

    private static Logger LOGGER = LoggerFactory.getLogger(GCDServiceIMpl.class);

    /**
     * consumes two integers from a JMS queue
     * calculates the GCD
     * persists result to database
     *
     * @return the GCD integer result
     */
    int getGCD(int a, int b) {
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
        final List<Integer> integers = new JMSClient()
                .asSubscriber()
                .synchConsume(2);

        // calculate GCD
        int gcdNumber = getGCD(integers.get(0), integers.get(1));

        // persist gcd result to H2 database
        if (new DbManager().persistNumber(gcdNumber, DbManager.NumberTable.GCDS)) {
            LOGGER.info("persisted GCD number {} to database", gcdNumber);
        } else {
            LOGGER.info("error persisting GCD number {} to database", gcdNumber);
        }

        return gcdNumber;
    }

    /**
     * list GCD integers from database
     *
     * @return list of GDC numbers
     */
    @Override
    public List<Integer> listGCDs() {
        DbManager dbManager = new DbManager();
        return dbManager.retrieveNumbers(DbManager.NumberTable.GCDS);
    }

    /**
     * returns sum of GCD from database
     *
     * @return sum of GCD numbers from database
     */
    @Override
    public int sumGCDs() {
        return listGCDs().stream().reduce(0, (x, y) -> x + y);
    }
}
