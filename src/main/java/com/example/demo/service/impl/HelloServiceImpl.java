package com.example.demo.service.impl;

import com.example.demo.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 15:34 2018/8/14 .
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService {
    private static final Logger log = LogManager.getLogger(HelloServiceImpl.class);

    @Override
    public String hello() {
        log.info("hello method is begin!!!");
        return "this is 中国新";
    }
}
