package com.walid.gcd;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by walid on 22/05/2017.
 */
public class GCDServiceIMplTest {

    private GCDServiceIMpl service;

    @Before
    public void setUp() throws Exception {
        service = new GCDServiceIMpl();
    }

    @Test
    public void getGCD() throws Exception {
        assertThat(service.getGCD(20, 16), equalTo(4));
    }
}