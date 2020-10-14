package com.demo.listtest;

import com.demo.vo.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wang
 * @since 2020/8/22
 */
public class TestMultiThreadArrayList {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Student> studentList = getStudentList();
        executorService.submit(() -> {
            for (int i = 0; i < 100 ; i++) {
                Student student = studentList.get(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                student.setId((long)i);
            }
        });

        executorService.submit(() -> {
            for (int i = 0; i < 60 ; i++) {
                Student student = studentList.get(i);
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                student.setAge(i);
            }
        });
        executorService.submit(() -> {
            for (int i = 0; i < 40 ; i++) {
                Student student = studentList.get(i);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                student.setAddress("4testAddress"+i);
            }
        });
        executorService.submit(() -> {
            for (int i = 0; i < 60 ; i++) {
                Student student = studentList.get(i);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                student.setAddress("3testAddress"+i);
            }
        });


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        for (Student student : studentList) {
            System.out.println(student.toString());
        }
    }

    private static List<Student> getStudentList() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Student student = new Student();
            list.add(student);
        }
        return list;
    }
}
