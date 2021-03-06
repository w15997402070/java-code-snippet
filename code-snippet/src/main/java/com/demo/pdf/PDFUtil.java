package com.demo.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class PDFUtil {

    public void readPDF() {
        String path = "D:\\tmp\\how-tomcat-works.pdf";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            //1.创建解析器  不知道哪个文档需要转换时可以用这个类
            AutoDetectParser parser = new AutoDetectParser();
            //2.创建handler 参数设置-1 通常Tika会对文件进行限制,要求它至多包含100000个字符,使用-1取消这个限制
            BodyContentHandler handler = new BodyContentHandler(-1);
            //3.创建MetaData对象
            Metadata metadata = new Metadata();
            //4. 调用解析器对象的parser()方法,并传入参数
            parser.parse(inputStream, handler, metadata, new ParseContext());
            //5. 调用handler的toString()方法提取正文文本
            log.info(handler.toString());
        } catch (SAXException | TikaException | IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new PDFUtil().readPDF();
    }
}
