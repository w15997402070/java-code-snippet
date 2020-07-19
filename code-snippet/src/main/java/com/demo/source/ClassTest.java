package com.demo.source;

/**
 * Created on 2020/7/9
 *
 * @author wang
 */
public class ClassTest {

    public static void main(String[] args) {
        testClass();
    }

    public static void testClass(){
        Class<String> stringClass = String.class;
        try {
            String s = stringClass.newInstance();
            System.out.println(s);
            String[] enumConstants = stringClass.getEnumConstants();
            System.out.println();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
