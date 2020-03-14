package com.demo.netty.grpc;

import com.demo.netty.grpc.generate.GreeterGrpc;
import com.demo.netty.grpc.generate.HelloRequest;
import com.demo.netty.grpc.generate.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldClient {
    public static void main(String[] args) {
        String target = "localhost:8899";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);

        HelloRequest helloRequest = HelloRequest.newBuilder().setName("test").build();
        HelloResponse response = blockingStub.sayHello(helloRequest);
        log.info(response.getMessage());
    }
}
