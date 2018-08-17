package com.example.demo.controller;

import com.example.demo.processor.AppBizException;
import com.example.demo.processor.ProcessorChain;
import com.example.demo.processor.model.ProcessContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:  动作链处理器入口，调用processor包下的逻辑
 * @Author: changrong.zeng
 * @Date: Created in 20:04 2018/8/16 .
 */
@RestController
public class ProcessorController {

    @Autowired
    private ProcessorChain<ProcessContext> processorChain;

    @RequestMapping("/processor")
    public String processor(){
        ProcessContext context = new ProcessContext();
        try {
            processorChain.process(context);
        } catch (AppBizException e) {
            e.printStackTrace();
        }

        return "sucess";

    }
}
