package com.walid.gcd;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * A SOAP JAX-WS web service interface
 *
 * @author waleedaleem@hotmail.com
 */

@WebService
public interface GCDService {

	/**
	 * consumes two integers from a JMS queue
	 * calculates the GCD
	 * persists result to database
	 *
	 * @return the GCD integer result
	 */
	@WebMethod
	int gcd();

	/**
	 * list GCD integers from database
	 *
	 * @return list of numbers in JSON format
	 */
	@WebMethod
	List<Integer> listGCDs();

	/**
	 * returns sum of GCD from database
	 *
	 * @return list of numbers in JSON format
	 */
	@WebMethod
	int sumGCDs();
}
