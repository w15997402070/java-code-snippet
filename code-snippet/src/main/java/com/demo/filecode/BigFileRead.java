package com.demo.filecode;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Created by Administrator on 2020/6/3.
 */
public class BigFileRead {

    public static void main(String [] args) throws Exception {
         testExecuteTime();
    }

    public static void testExecuteTime() throws IOException {
        LocalDateTime start = LocalDateTime.now();
        doSomething();
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);
        System.out.println("耗时 : "+between.toMillis());
    }
    //48.5 MB (50,916,518 字节)
    static String path = "E:\\tmp\\Java从入门到精通.pdf";
    private static void doSomething() throws IOException {
        testMemoryMapReadFile();

    }

    /**
     *  @see org.apache.commons.io.FileUtils
     *  耗时 : 5522
     * @throws IOException
     */
    public static void testCommonFileUtils() throws IOException {
        String s = FileUtils.readFileToString(new File(path), Charset.defaultCharset());
    }

    /**
     * @see BufferedReader 读文件
     * 耗时 : 5271
     */
    public static void testBufferReadFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String content;
            StringBuilder sb = new StringBuilder();
            while ((content = bufferedReader.readLine()) != null){
                sb.append(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see Scanner 读文件
     * @throws FileNotFoundException
     */
    public static void testScanner() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        try (Scanner scanner = new Scanner(fileInputStream)) {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()){
                sb.append(scanner.nextLine());
            }
            System.out.println(sb.toString());
        }

    }
    //246 MB (258,533,228 字节)
    static String outMemoryFilePath = "E:\\tmp\\JavaScript入门.pdf";
    /**
     * 对大文件建立NIO的FileChannel,每次调用read方法时会将文件数据读取到已经分配的ByteBuffer中,从中读取数据
     *
     * 耗时 : 2431
     * @throws IOException
     */
    public static void testNioFileChannel() throws IOException {
//        FileInputStream fileInputStream = new FileInputStream(path);//耗时 : 2431
        FileInputStream fileInputStream = new FileInputStream(outMemoryFilePath);//耗时 : 10765
        ByteBuffer byteBuffer = ByteBuffer.allocate(65535);
        FileChannel channel = fileInputStream.getChannel();
        int bytes = -1;
        do {
            bytes = channel.read(byteBuffer);
            if (bytes != -1){
                byte[] array = new byte[bytes];
                byteBuffer.flip();
                byteBuffer.get(array);
                byteBuffer.clear();
                //这里可以用array处理逻辑
                String s = new String(array);
//                System.out.println(s.length());
            }
        } while (bytes > 0);
        byteBuffer.clear();
        channel.close();
        fileInputStream.close();
    }

    /**
     * 内存文件映射,就是把文件内容映射到虚拟内存的一块区域中,
     * 从而可以直接操作内存当中的数据而无需每次都通过I/O去物理硬盘读取文件
     * @throws IOException
     */
    public static void testMemoryMapReadFile() throws IOException {
//        FileInputStream fileInputStream = new FileInputStream(path);//耗时 : 2915
        FileInputStream fileInputStream = new FileInputStream(outMemoryFilePath);//耗时 : 8573
        FileChannel channel = fileInputStream.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        boolean end = false;
        do {
            int limit = mappedByteBuffer.limit();
            int position = mappedByteBuffer.position();
            if (position >= limit){
                end = true;
            }
            int maxSize = 2048;
            if (limit - position < maxSize){
                maxSize = limit - position;
            }
            byte[] array = new byte[maxSize];
            mappedByteBuffer.get(array);
            //这里可以用array处理逻辑
            String s = new String(array);
//            System.out.println(s);
        }while (!end);
        mappedByteBuffer.clear();
        channel.close();
        fileInputStream.close();
    }

    /**
     * 通过randomAccessFile的seek方法进行分块读写操作
     */
    public static void testRandomAccessFileRead(){

    }
}
