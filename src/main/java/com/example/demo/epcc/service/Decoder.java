package com.example.demo.epcc.service;

import com.example.demo.epcc.model.AppBizException;
import com.example.demo.epcc.model.EpccTxn;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 13:48 2018/9/4 .
 */
public interface Decoder {
    void decode(EpccTxn epcctxn) throws AppBizException;
}
