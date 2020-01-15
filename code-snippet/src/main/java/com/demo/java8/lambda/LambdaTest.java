package com.demo.java8.lambda;

import java.io.File;
import java.io.FileFilter;

public class LambdaTest {

    public static void execute(Runnable runnable) {
        runnable.run();
    }

    public static void main(String[] args) {
        //Java8֮ǰ
//        execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("run");
//            }
//        });

        //ʹ��Lambda���ʽ
        // execute(() -> System.out.println("run2"));
        filteFile();
    }

    public static void filteFile() {
        File[] file = new File("D:\\gitRepository\\git").listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {

                return pathname.isHidden();
            }
        });
        File[] hiddenFile = new File("D:\\\\gitRepository\\\\git").listFiles(File::isHidden);
        for (int i = 0; i < hiddenFile.length; i++) {
            System.out.println(hiddenFile[i].getName());
        }

    }
}
