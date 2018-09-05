package com.example.demo.epcc.service.impl;

import com.example.demo.epcc.model.AppBizException;
import com.example.demo.epcc.model.EpccTxn;
import com.example.demo.epcc.protocol.SecurityService;
import com.example.demo.epcc.service.Decoder;
import com.example.demo.epcc.service.StringDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 15:16 2018/9/4 .
 */
@Service
public class TxnDecoderImpl implements Decoder {
    private static final Logger logger = LogManager.getLogger(TxnDecoderImpl.class);

    @Autowired
    private StringDecoder stringDecoder;

    /** 加验签服务 */
    @Autowired
    private SecurityService securityService;

    @Override
    public void decode(EpccTxn epccTxn) throws AppBizException {
        String respInfo = (String) epccTxn.getRspMessageBody();
        // 解析xml
        Map<String, String> respMsg = null;
        try {
            String xml = securityService.substringBystr(respInfo, "<root", "</root>", true);
            respMsg = stringDecoder.decode(xml, epccTxn);
            logger.info("the decoded map content="+respMsg);
        } catch (Exception e) {
            logger.info("decode error", e);
            return;
        }
        // 系统返回码
        String sysRet = respMsg.get("sysRtnCd");
        // 业务返回码
        String bizRet = respMsg.get("bizStsCd");
        // 系统返回说明
        String sysDesc = respMsg.get("sysRtnDesc");
        // 业务返回说明
        String bizDesc = respMsg.get("bizStsDesc");
        // 交易状态
        String trxStatus = respMsg.get("trxStatus");
        try {
            // 验签
            if (!securityService.verifyRSA(respInfo)) {
                logger.info("verifyRSA fail");
                return;
            }
        } catch (Exception e) {
            logger.info("verifyRSA error", e);
            return;
        }
        //设置返回信息
        epccTxn.setSysErrorCode(sysRet);
        epccTxn.setSysErrorMessage(sysDesc);
        epccTxn.setBizStsCd(bizRet);
        epccTxn.setBizStsDesc(bizDesc);
        epccTxn.setTrxStatus(trxStatus);
        epccTxn.setResultMap(respMsg);

    }
}
