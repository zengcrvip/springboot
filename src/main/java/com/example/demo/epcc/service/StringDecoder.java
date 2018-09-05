package com.example.demo.epcc.service;

import com.example.demo.epcc.model.EpccTxn;
import java.util.Map;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 15:17 2018/9/4 .
 */
public interface StringDecoder {

    public abstract Map decode(String s, EpccTxn epcctxn) throws Exception;
}
