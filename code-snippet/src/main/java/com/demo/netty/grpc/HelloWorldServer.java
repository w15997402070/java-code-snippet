package com.demo.netty.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HelloWorldServer {

    private Server server;

    public  void start() throws IOException {
        int port = 8899;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterServiceImpl())
                .build()
                .start();
        log.info("Server started,listening on : "+port);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                log.info("shutting down grpc server since JVM  is shutting down");
                try {
                    HelloWorldServer.this.stop();
                }catch (Exception e){
                    e.printStackTrace();
                }
                log.info("Server shut down");
            }
        });

    }

    private void stop() throws InterruptedException {
        if (server != null){
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldServer helloWorldServer = new HelloWorldServer();
        helloWorldServer.start();
        helloWorldServer.blockUntilShutdown();
    }

}
