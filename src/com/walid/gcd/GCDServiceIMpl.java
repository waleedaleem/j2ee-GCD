package com.walid.gcd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;


@WebService(targetNamespace = "http://www.walid.com/", serviceName = "gcdWebService")
public class GCDServiceIMpl implements GCDService, Serializable {

	private Logger LOGGER = LoggerFactory.getLogger(GCDServiceIMpl.class);

	private static int getGCD(int a, int b) {
		BigInteger b1 = BigInteger.valueOf(a);
		BigInteger b2 = BigInteger.valueOf(b);
		BigInteger gcd = b1.gcd(b2);
		return gcd.intValue();
	}

	@Override
	public int gcd() {
		// consume the top 2 integers from the topic
		//final List<Integer> integers = new NumberSubscriber().synchConsume(2);
		final List<Integer> integers = Arrays.asList(1, 2);

		// calculate GCD
		int gcdNumber = getGCD(integers.get(0), integers.get(1));

		// persist gcd result to database

		return gcdNumber;
	}
}
