package com.example.web.service;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/9/29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExecTime {
    String value() default "";
}