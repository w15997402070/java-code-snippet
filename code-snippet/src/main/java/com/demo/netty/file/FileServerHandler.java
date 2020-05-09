package com.demo.netty.file;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.File;
import java.io.RandomAccessFile;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String line_separator = System.getProperty("line.separator");
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        File file = new File(msg);
        if (file.exists()){
            if (!file.isFile()){
                ctx.writeAndFlush("Not a file : "+file+line_separator);
                return;
            }
            ctx.write(file + " "+ file.length() + line_separator);
            RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");
            DefaultFileRegion region = new DefaultFileRegion(
                    randomAccessFile.getChannel(), 0, randomAccessFile.length());
            ctx.write(region);
            ctx.writeAndFlush(line_separator);
            randomAccessFile.close();
        }else {
            ctx.writeAndFlush("Not a file : "+file+line_separator);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
