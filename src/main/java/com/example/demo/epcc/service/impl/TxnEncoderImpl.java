package com.example.demo.epcc.service.impl;

import com.example.demo.epcc.model.AppBizException;
import com.example.demo.epcc.model.EpccTxn;
import com.example.demo.epcc.protocol.SecurityService;
import com.example.demo.epcc.service.Encoder;
import com.example.demo.epcc.service.XmlEncoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:编码器
 * @Author: changrong.zeng
 * @Date: Created in 14:02 2018/9/4 .
 */
@Service
public class TxnEncoderImpl implements Encoder {
    public static final Logger logger = LogManager.getLogger(TxnEncoderImpl.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private XmlEncoder xmlEncoder;

    @Override
    public void encode(EpccTxn epccTxn) throws AppBizException {
        String reqMsg = null;
        try {
            //编码，生成报文
            reqMsg = xmlEncoder.encode(epccTxn);
            if (StringUtils.isNotEmpty(reqMsg)) {
                //对报文加签
                reqMsg = securityService.signRSA(reqMsg);
                logger.info("The epccTxn reqMsg xml=" + reqMsg);
                //设置请求报文数据
                epccTxn.setSignedReqMessage(reqMsg);
            } else {
                logger.error("encode(epccTxn) result is empty!");
            }
        } catch (Exception e) {
            logger.error("encode error", e);
            throw new AppBizException("B.SYS.0001", "xmlEncoder.encode is error" + e);
        }

    }
}
