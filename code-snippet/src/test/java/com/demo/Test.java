package com.demo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String path = "D:\\data\\javaTest\\github\\java-code-snippet\\code-snippet\\file\\demo2.txt";
        try {
            List<String> list = FileUtils.readLines(new File(path), "UTF-8");
            BigDecimal bigDecimal = new BigDecimal(0);
            for (String s : list) {
               bigDecimal =  bigDecimal.add(new BigDecimal(s)) ;
            }
            System.out.println(bigDecimal.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
