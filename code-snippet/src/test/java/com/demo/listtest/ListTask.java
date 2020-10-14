package com.demo.listtest;

import com.demo.graph.In;
import com.demo.vo.Student;

import java.util.List;

/**
 * @author wang
 * @since 2020/8/22
 */
public class ListTask implements Runnable {

    private List<Student> list;
    public ListTask(List<Student> list) {
        this.list = list;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Student student = list.get(i);
            student.setId((long) i);
        }
    }
}
