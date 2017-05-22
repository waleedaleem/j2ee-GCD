package com.walid.gcd.client;

import com.walid.gcd.GCDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A SOAP JAX-WS web service test client
 *
 * @author waleedaleem@hotmail.com
 */

public class GCDTestClient {

    private static Logger LOGGER = LoggerFactory.getLogger(GCDTestClient.class);

    public static void main(String[] argv) {
        GCDService service = new GCDServiceIMplService().getPort(GCDService.class);
        //test gcd() business method
        LOGGER.info("Retrieved gcd from SOAP service = {}", service.gcd());

        //test listGCDs() business method
        LOGGER.info("Retrieved gcd from SOAP service = {}", service.listGCDs());

        //test sumGCDs() business method
        LOGGER.info("Retrieved gcd from SOAP service = {}", service.sumGCDs());
    }
}
