package com.example.demo.controller;

import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 14:16 2018/8/14 .
 */
@RestController
public class HelloController {

    @Autowired
    @Qualifier("helloService")
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(){
        return helloService.hello();
    }
}
