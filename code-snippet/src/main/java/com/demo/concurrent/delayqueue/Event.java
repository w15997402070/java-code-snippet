package com.demo.concurrent.delayqueue;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020/7/19
 *
 * @author wang
 */
public class Event implements Delayed {

    private final Date startDate;

    public Event(Date startDate){
        this.startDate = startDate;
    }
    @Override
    public long getDelay(TimeUnit unit) {
        Date now = new Date();
        long diff = startDate.getTime() - now.getTime();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        long result = this.getDelay(TimeUnit.NANOSECONDS)-delayed.getDelay(TimeUnit.NANOSECONDS);
        if (result < 0){
            return -1;
        }else if (result > 0){
            return 1;
        }
        return 0;
    }
}
