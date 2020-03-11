package com.demo.classLoader;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author wang
 */
public class MyClassLoader extends ClassLoader {

    public static final String TARGET_DIR = "D:\\data\\javaTest\\github\\java-code-snippet\\code-snippet\\target\\classes\\";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String  fileName = name.replaceAll("\\.","/");
        fileName = TARGET_DIR+fileName+".class";

        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File(fileName));
            return defineClass(name,bytes,0,bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
