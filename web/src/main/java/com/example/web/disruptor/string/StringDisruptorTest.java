package com.example.web.disruptor.string;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.val;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class StringDisruptorTest {

    public static void main(String[] args) {
        int ringBufferSize = 8;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        Disruptor<StringEvent> disruptor = new Disruptor<>(StringEvent::new, ringBufferSize, threadFactory);

        //串行,先执行first,再执行third,third中获取的值是first中改变后的值
//        disruptor.handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3());

//        disruptor.handleEventsWith(( (event, sequence, endOfBatch) -> {
//            System.out.println("first");
//            Thread.sleep(100);
//            System.out.println("first : "+event.getValue());
//            event.setValue("first"+event.getValue());
//        } )).handleEventsWith(((event, sequence, endOfBatch) -> {
//            System.out.println("third");
//            Thread.sleep(100);
//            System.out.println("third : "+event.getValue());
//        }));

        //并行,second中的值没有被改变
//        disruptor.handleEventsWith(new Hanlder1(),new Handler2(),new Handler3());
        //下面三行等同于上面一行
//        disruptor.handleEventsWith(new Hanlder1());
//        disruptor.handleEventsWith(new Hanlder2());
//        disruptor.handleEventsWith(new Hanlder3());
        disruptor.handleEventsWith(( (event, sequence, endOfBatch) -> {
            System.out.println("first");
            Thread.sleep(100);
            System.out.println("first : "+event.getValue());
            event.setValue("first"+event.getValue());
        } ));
        disruptor.handleEventsWith(((event, sequence, endOfBatch) -> {
            System.out.println("second");
            Thread.sleep(100);
            System.out.println(event.getValue());
        }));

        /**
         * 通过并行串行可以实现多边形操作
         *     -> B1
         *  A        ->C
         *     -> B2
         *  A执行完之后,B1,B2并行执行,再执行C
         */

        disruptor.start();

        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();

        for (int i = 0; i < 8 ; i++) {
            final String val = ""+i;
            ringBuffer.publishEvent(((event, sequence, arg0) -> {
                event.setValue(val);
            }));
        }

        disruptor.shutdown();
    }
}
