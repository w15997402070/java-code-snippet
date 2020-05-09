package com.demo.concurrent.thisescape;

import java.util.Arrays;
import java.util.List;

public class ListenerRunnable implements Runnable {

    private EventSource<EventListener> source;

    public ListenerRunnable(EventSource<EventListener> source) {
        this.source = source;
    }
    @Override
    public void run() {
        List<EventListener> listeners = null;
        try {
            listeners = this.source.retrieveListeners();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (EventListener listener : listeners) {
            String [] str = new String[2];
            str[0] = "aaaaaa";
            listener.onEvent(str);
            System.out.println(Arrays.toString(str));
        }
    }
}
