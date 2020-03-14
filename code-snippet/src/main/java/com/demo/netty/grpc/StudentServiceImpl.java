package com.demo.netty.grpc;

import com.demo.netty.grpc.generate.MyRequest;
import com.demo.netty.grpc.generate.MyResponse;
import com.demo.netty.grpc.generate.StreamRequest;
import com.demo.netty.grpc.generate.StreamResponse;
import com.demo.netty.grpc.generate.StudentRequest;
import com.demo.netty.grpc.generate.StudentResponse;
import com.demo.netty.grpc.generate.StudentResponseList;
import com.demo.netty.grpc.generate.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {

        log.info("接收到信息 : "+request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("student1").build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAge(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest studentRequest) {
                System.out.println("onNext : "+studentRequest.getAge());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse1 = StudentResponse.newBuilder().setName("张三").setAge(13).setCity("湖北3").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setName("李四").setAge(14).setCity("湖北4").build();
                StudentResponse studentResponse3 = StudentResponse.newBuilder().setName("王五").setAge(15).setCity("湖北5").build();
                StudentResponseList studentResponseList = StudentResponseList.newBuilder()
                        .addStudentResponse(studentResponse1)
                        .addStudentResponse(studentResponse2)
                        .addStudentResponse(studentResponse3)
                        .build();
                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest streamRequest) {
                System.out.println("BiTalk onNext : "+streamRequest.getRequestInfo());
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
                responseObserver.onCompleted();
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
