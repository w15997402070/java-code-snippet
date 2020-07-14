package com.demo.executor.rejecthandler;

/**
 * Created on 2020/7/13
 *
 * @author wang
 */
public class TestMain {

    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("Main: Starting... ");
        for (int i = 0; i < 100 ; i++) {
            Task task = new Task("Task" + i);
            server.executeTask(task);
        }
        System.out.println("Main: Shutting down the Executor.");
        server.endServer();

        System.out.println("Main: Sending another Task.");
        Task rejected_task = new Task("Rejected task");
        server.executeTask(rejected_task);
        System.out.println("Main: End!");
    }
}
