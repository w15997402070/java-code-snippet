package com.example.web.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/9/29.
 */
@Aspect
@Component
public class MyInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    /**
     * 这儿填写ExecTime.java的全路径名
     */
    @Pointcut("@annotation(com.example.wang.service.ExecTime)")
    public void annotationPointCut() {
    }

    /**
     * 统计方法执行的时长
     *
     * @param joinPoint the join point
     * @return object
     * @throws Throwable
     */
    @Around("annotationPointCut()")
    public Object wasteTime(ProceedingJoinPoint joinPoint) {

        System.out.println("========================");
        Object output = null;
        try {
            long start = System.currentTimeMillis();
            output = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;

            // 执行的真实类名称
            String className = joinPoint.getTarget().getClass().getSimpleName();
            logger.info(String.format("method [%s.%s()] execution time:%sms", className, joinPoint.getSignature().getName(), elapsedTime));
        } catch (Throwable throwable) {
            logger.error("aop record method exec time error", throwable);
        }
        return output;
    }

}
