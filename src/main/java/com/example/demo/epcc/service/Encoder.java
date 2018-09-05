package com.example.demo.epcc.service;

import com.example.demo.epcc.model.AppBizException;
import com.example.demo.epcc.model.EpccTxn;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 13:46 2018/9/4 .
 */
public interface Encoder {
    void encode(EpccTxn epcctxn) throws AppBizException;
}
