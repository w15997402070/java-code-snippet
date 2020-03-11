package com.demo.classLoader;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;

/**
 * @author wang
 */
@Slf4j
public class ClassLoaderTest {

    public static void main(String[] args) {
//        getClassloader();
//        testLoadClass();
        testMyClassLoader();
    }

    private static void getClassloader() {
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        while (classLoader != null){
            String name = classLoader.getClass().getName();
            log.info(name);
            classLoader = classLoader.getParent();
        }
        String name = ClassLoaderTest.class.getSimpleName();
        log.info(name);
        log.info("String ClassLoader : "+ String.class.getClassLoader());
        log.info("System ClassLoader : "+ ClassLoader.getSystemClassLoader());
    }

    public static class Hello{
        static {
            System.out.println("hello");
        }
    }


    public static void testLoadClass(){
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        String name = ClassLoaderTest.class.getName()+"$Hello";
        try {
            //运行之后什么都没输出
//            Class<?> aClass = systemClassLoader.loadClass(name);

            //运行之后会打印出hello,说明执行了静态代码块
            Class<?> aClass = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void testMyClassLoader(){
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = null;
        try {
            aClass = myClassLoader.findClass(ClassLoaderTest.class.getName());
            log.info(aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}