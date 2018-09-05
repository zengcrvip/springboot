package com.example.demo.epcc.service;

import com.example.demo.epcc.model.EpccTxn;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 14:04 2018/9/4 .
 */
public interface XmlEncoder {
    public abstract String encode(EpccTxn epcctxn) throws Exception;
}
