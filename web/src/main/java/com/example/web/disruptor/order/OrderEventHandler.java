package com.example.web.disruptor.order;

import com.lmax.disruptor.EventHandler;

public class OrderEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println(orderEvent.getValue());
//        try {
            if (orderEvent.getValue() == 3){
                int a = 2/0;
            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        System.out.println(orderEvent.getValue());
        Thread.sleep(1000);
    }
}
