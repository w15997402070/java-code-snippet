package com.demo.netty.thrift.simple;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftClient {
    public static void main(String[] args) {

        TFramedTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 1000);
        TCompactProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();

            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("====================");
            Person person2 = new Person();
            person2.setUsername("张三");
            person2.setAge(1);
            person2.setMarried(false);

            client.savePerson(person2);
            System.out.println("Client finished!");
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (DataException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
    }
}
