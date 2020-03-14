package com.demo.netty.grpc;

import com.demo.netty.grpc.generate.MyRequest;
import com.demo.netty.grpc.generate.MyResponse;
import com.demo.netty.grpc.generate.StreamRequest;
import com.demo.netty.grpc.generate.StreamResponse;
import com.demo.netty.grpc.generate.StudentRequest;
import com.demo.netty.grpc.generate.StudentResponseList;
import com.demo.netty.grpc.generate.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentClient {

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8888";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();


        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        MyResponse testStudent = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("testStudent").build());
        log.info(testStudent.getRealname());

        //==========================================
//        StreamObserver<StudentResponseList> listStreamObserver = new StreamObserver<StudentResponseList>() {
//            @Override
//            public void onNext(StudentResponseList studentResponseList) {
//                studentResponseList.getStudentResponseList().forEach(studentResponse -> {
//                    System.out.println("name : "+studentResponse.getName());
//                    System.out.println("age : "+studentResponse.getAge());
//                    System.out.println("city : "+studentResponse.getCity());
//                    System.out.println("**********");
//                });
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//            }
//        };
//        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(channel);
//        //1
//        StreamObserver<StudentRequest> studentWrapperByAge = stub.getStudentWrapperByAge(listStreamObserver);
//        //2
//        studentWrapperByAge.onNext(StudentRequest.newBuilder().setAge(13).build());
//        studentWrapperByAge.onNext(StudentRequest.newBuilder().setAge(14).build());
//        studentWrapperByAge.onNext(StudentRequest.newBuilder().setAge(15).build());
//        studentWrapperByAge.onNext(StudentRequest.newBuilder().setAge(16).build());
//        studentWrapperByAge.onCompleted();

        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(channel);
        StreamObserver<StreamRequest> requestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse streamResponse) {
                System.out.println("Client : "+streamResponse.getResponseInfo());

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });

        requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo("From Client msg1").build());
        requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo("From Client msg2").build());
        requestStreamObserver.onCompleted();
        //-----------注意------------
        //StudentServiceGrpc.newStub(channel) 方法是创建一个异步的stub,线程执行 1 下面那一行之后没有执行2下面的几行
        Thread.sleep(5000);




    }
}
