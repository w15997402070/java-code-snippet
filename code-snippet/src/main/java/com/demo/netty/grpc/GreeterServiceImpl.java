package com.demo.netty.grpc;

import com.demo.netty.grpc.generate.GreeterGrpc;
import com.demo.netty.grpc.generate.HelloRequest;
import com.demo.netty.grpc.generate.HelloResponse;
import io.grpc.stub.StreamObserver;

public class GreeterServiceImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
