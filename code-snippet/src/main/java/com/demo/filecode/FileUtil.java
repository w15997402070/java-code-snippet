package com.demo.filecode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * 操作文件类
 */
@Slf4j
public class FileUtil {

    /**
     * 列举文件夹所有文件
     * @param rootDir
     */
    public void listFiles(String rootDir){
        File dir = new File(rootDir);

        List<File> files = (List<File>)FileUtils.listFiles(dir,
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            System.out.println("file : "+ file.getAbsolutePath());
        }
    }
    /**
     * java8一次读取所有的文件里面的内容
     */

    public void readAllContent(){
        String file = "D:\\data\\javaTest\\github\\java-code-snippet\\README.md";
        try(Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach(s -> log.info(s));
        }catch (IOException e){
            e.printStackTrace();
        }
        readAllContent(file);
    }

    /**
     * commons-io 包的工具类读取文件所有内容
     * @param file
     */
    public void readAllContent(String file){
        try {
            String s = FileUtils.readFileToString(new File(file), "UTF-8");
            log.info(s);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main (String [] args){
        new FileUtil().readAllContent();
    }

}
