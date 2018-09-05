package com.example.demo.epcc.service.impl;

import com.example.demo.epcc.connector.Connector;
import com.example.demo.epcc.model.EpccPaymentRequestDto;
import com.example.demo.epcc.model.EpccTxn;
import com.example.demo.epcc.service.Decoder;
import com.example.demo.epcc.service.Encoder;
import com.example.demo.epcc.service.EpccSingleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 13:36 2018/9/4 .
 */
@Service
public class EpccSingleServiceImpl implements EpccSingleService {
    private static final Logger logger = LogManager.getLogger(EpccSingleServiceImpl.class);

    @Autowired
    Encoder encoder;

    @Autowired
    Decoder decoder;

    @Autowired
    private Connector connector;

    @Override
    public void doDebitSingle(EpccPaymentRequestDto paymentRequestDto) {
        long beginTime = System.currentTimeMillis();
        try {
            EpccTxn epccTxn = new EpccTxn();
            // 协议单笔支付
            epccTxn.setMsgTp("epcc.201.001.01");
            epccTxn.setMessageBody(paymentRequestDto);
            epccTxn.setDebitTxnId(paymentRequestDto.getOrdrId());
            logger.info("doDebitSingle request param,Bgw receive Message from DDP=" + epccTxn.toString());
            // 加签
            logger.info("doDebitSingle begin to sign...orderId=" + epccTxn.getDebitTxnId());
            encoder.encode(epccTxn);
            logger.info("doDebitSingle sign complete.orderId=" + epccTxn.getDebitTxnId());
            // 发送请求
            connector.connectSend(epccTxn);
            if (null != epccTxn.getRspMessageBody()) {
                epccTxn.setRspMsgTp("epcc.206.001.01");
                //验签
                this.decoder.decode(epccTxn);
                if ("00000000".equals(epccTxn.getSysErrorCode())) {
                    //反盘通知responseQueue
//                    this.backProcess(epccTxn, paymentRequestDto);
//                    this.sender.send(epccTxn);
                } else {
                    logger.warn("doDebitSingle epcc return fail! sysRtnCd=" + epccTxn.getSysErrorCode() + " sysRtnDesc=" + epccTxn.getSysErrorMessage());
                }
            } else {
                // 未返回正确的http状态。
                logger.error("doDebitSingle rspData is empty." + epccTxn.toString());
            }

        } catch (Exception e) {
            logger.error("doDebitSingle error. ", e);
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("EpccSingleService.doDebitSingle() end. cost " + (endTime - beginTime) + "ms");
        }

    }
}
