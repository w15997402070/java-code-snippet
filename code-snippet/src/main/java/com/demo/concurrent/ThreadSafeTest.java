package com.demo.concurrent;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadSafeTest {
}

/**
 * 线程不安全的类
 *  ++count 包含三个独立的操作: 读取count的值,将值加1,然后将计算结果写入count.
 *  读取 - 修改 -写入
 */
//class UnsafCountingFactorizer implements Servlet{
//
//    private long count = 0;
//
//    @Override
//    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
//
//       BigInteger i = extractFromRequest(req);
//       BigInteger [] factors = factor(i);
//       ++count;
//       encodeIntoResponse(resp,factors);
//    }
//}

/**
 * 线程安全
 */
//class UnsafCountingFactorizer implements Servlet{
//
//    private final AtomicLong count = new AtomicLong(0);
//
//    private long getCount(){
//        return count.get();
//    }
//    @Override
//    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
//
//       BigInteger i = extractFromRequest(req);
//       BigInteger [] factors = factor(i);
//       count.incrementAndGet();
//       encodeIntoResponse(resp,factors);
//    }
//}


/**
 * 线程不安全
 */
//class LazyInitRace {
//    private ExpensiveObject instance = null;
//
//    public ExpensiveObject getInstance() {
//        if (instance == null){
//            instance = new ExpensiveObject();
//        }
//        return instance;
//    }
//}