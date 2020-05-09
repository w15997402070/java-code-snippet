package com.example.web.disruptor.order;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer byteBuffer) {
        // 1.获取一个可用的序列号
        long sequence = ringBuffer.next();
        try {
            //2.根据这个序号找到具体的OrderEvent,此时Event对象里面没有值
            OrderEvent orderEvent = ringBuffer.get(sequence);
//            System.out.println(byteBuffer.getLong(0));
            //3. 赋值
            orderEvent.setValue(byteBuffer.getLong(0));
        }finally {
            //4. 提交发布操作
            ringBuffer.publish(sequence);
        }
    }
}
