package com.demo.netty.grpc;

import com.demo.netty.grpc.generate.StudentResponseList;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class StudentServer {

    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(8888)
                .addService(new StudentServiceImpl())
                .build()
                .start();

    }

    public void stop(){
        if (null != this.server){
            this.server.shutdown();
        }
    }

    public void awaitTermination() throws InterruptedException {
        if (null != this.server){
            this.server.awaitTermination();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        StudentServer studentServer = new StudentServer();
        studentServer.start();
        studentServer.awaitTermination();


    }
}
