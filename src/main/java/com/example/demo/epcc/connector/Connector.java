package com.example.demo.epcc.connector;

import com.example.demo.epcc.model.EpccTxn;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 17:20 2018/9/5 .
 */
public interface Connector {

    public abstract void connectSend(EpccTxn epcctxn);

    public abstract void checkConnections();
}
