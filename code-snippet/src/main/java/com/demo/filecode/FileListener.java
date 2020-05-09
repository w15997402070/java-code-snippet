package com.demo.filecode;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FileListener extends FileAlterationListenerAdaptor {

    String SEPERATOR = ".";
    String FlAC = "flac";
    String MP3 = "mp3";
    String DEST_PATH = "D:\\Apache24\\htdocs\\music\\";
    @Override
    public void onFileCreate(File file) {
        long length = file.length();
        //获取文件路径
        String path = file.getPath();
        System.out.println("path: "+path+" filename : "+file.getName()+" length : "+length);
        String[] split = StringUtils.split(path,SEPERATOR);
        //将文件替换成音乐文件
        String flacFile = split[0]+SEPERATOR+FlAC;
        File file1 = new File(flacFile);
        if (!file1.exists()){
          String  mp3File = split[0]+SEPERATOR+MP3;
            file1 = new File(mp3File);
            if (!file1.exists()){
                return;
            }
        }
        try {
            FileUtils.copyFileToDirectory(file1,new File(DEST_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        String listenerPath = "D:\\wang\\Music";

        long interval = TimeUnit.SECONDS.toMillis(1);
        // 创建过滤器
        IOFileFilter directories = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        //文件过滤器,以 flac 结尾的文件
        IOFileFilter files       = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".flac"));
        //文件过滤器,以 MP3 结尾的文件
        IOFileFilter mp3       = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".mp3"));
        //文件过滤器,以 lrc 结尾的歌词文件
        IOFileFilter lrcFile       = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".lrc"));
        //监控歌词文件,因为歌曲文件创建时还没有完全下载,会缺失内容
        IOFileFilter filter = FileFilterUtils.or(directories, lrcFile);
        // 使用过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(listenerPath), filter);
        //不使用过滤器
        //FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir));
        observer.addListener(new FileListener());
        //创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        monitor.start();
    }
}