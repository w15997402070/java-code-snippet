package com.demo.pattern.command;

public class Test {

    public static void main(String[] args) {
        Command cmdCommand = new ConcreteCommand();
        Invoker invoker = new Invoker(cmdCommand);
        invoker.call();
    }
}
