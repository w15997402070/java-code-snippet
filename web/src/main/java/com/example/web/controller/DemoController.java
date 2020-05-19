package com.example.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * Created by Administrator on 2020/5/11.
 */
@RestController
@RequestMapping("/test")
public class DemoController {

    @RequestMapping("/hello/{id}")
    public String hello(@PathParam("id") String id, int start, int size){

        return start+"----"+size;
    }
}
