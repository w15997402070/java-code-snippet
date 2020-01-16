package com.demo;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testString(){
        String s1 = "hello";
        String he = "he";
        String llo = "llo";
        String s2 = he + llo;
        String s3 = "he"+"llo";
        log.info("" + (s1 == s2));
        log.info("" + (s1 == s3));

    }
}
