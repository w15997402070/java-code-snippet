package com.demo;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void testAvailableProcessors(){
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }

    @Test
    public void listTest(){
//        Set<Integer> set = new HashSet<>(Arrays.asList(1,2,3,4,5,6));
//        set.stream().forEach(System.out :: println);


        MyDate myDate = new MyDate();
        myDate.setCreateDate(new Date());
        myDate.getCreateDate().setYear(2019);
        System.out.println(myDate.getCreateDate());


    }
}

class MyDate {
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
