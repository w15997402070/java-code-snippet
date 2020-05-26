package com.demo.filecode;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileTest {

    public static void main(String[] args) {
        String separator = File.separator;
        String destPath = "D:\\Apache24\\htdocs\\music\\";
        String path = "D:\\wang\\Music\\等你下课(with 杨瑞代) - 周杰伦.flac";
        try {
            FileUtils.copyFileToDirectory(new File(path),new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
