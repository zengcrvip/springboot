package com.example.demo.processor.Impl;

import com.example.demo.processor.AppBizException;
import com.example.demo.processor.PciProcessorUnit;
import com.example.demo.processor.model.ProcessContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:59 2018/8/16 .
 */

@Order(2)
@Service
public class CommonCheckerPCIProcessor extends PciProcessorUnit {
    private static final Logger log = LogManager.getLogger(CommonCheckerPCIProcessor.class);

    @Override
    protected boolean doProcess(ProcessContext ctx) throws AppBizException {
        log.info("this is CommonCheckerPCIProcessor");
        return true;
    }
}
