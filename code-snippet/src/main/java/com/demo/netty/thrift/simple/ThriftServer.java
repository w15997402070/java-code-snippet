package com.demo.netty.thrift.simple;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {

    public static void main(String[] args) {
        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
            THsHaServer.Args args1 = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(5);
            PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

            args1.protocolFactory(new TCompactProtocol.Factory())  //协议层
                 .transportFactory(new TFramedTransport.Factory()) //传输层
                 .processorFactory(new TProcessorFactory(processor));
            //THsHaServer -> 半同步半异步
            THsHaServer server = new THsHaServer(args1);
            System.out.println("Thrift Server Started!");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void single(){
        try {
            TServerTransport serverTransport = new TServerSocket(8899);
            TServer.Args args = new TServer.Args(serverTransport);
            PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
            args.processor(processor);
            TServer tServer = new TSimpleServer(args);
            System.out.println("Thrift Server Started!");
            tServer.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
