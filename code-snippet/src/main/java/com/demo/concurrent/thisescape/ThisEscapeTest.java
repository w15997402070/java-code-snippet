package com.demo.concurrent.thisescape;

public class ThisEscapeTest {
    public static void main(String[] args) {
        EventSource<EventListener> source = new EventSource<>();
        ListenerRunnable listenerRunnable = new ListenerRunnable(source);
        Thread thread = new Thread(listenerRunnable);
        thread.start();
//        ThisEscape escape = new ThisEscape(source);
    }
}
