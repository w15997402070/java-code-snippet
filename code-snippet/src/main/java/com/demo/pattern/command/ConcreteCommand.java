package com.demo.pattern.command;

public class ConcreteCommand implements Command {

    private Receiver receiver;

    public ConcreteCommand() {
        receiver = new Receiver();
    }

    @Override
    public void execute() {
        receiver.action();
    }

}
