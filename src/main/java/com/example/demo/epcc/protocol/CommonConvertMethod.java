package com.example.demo.epcc.protocol;

import com.example.demo.epcc.model.EpccTxn;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:公共的转换方法（用于请求头以及部分可以公用的请求体）
 * @Author: changrong.zeng
 * @Date: Created in 16:58 2018/9/4 .
 */
@Component
public class CommonConvertMethod implements ConvertMethod{
    private static Logger logger = LogManager.getLogger(CommonConvertMethod.class);

    /**
     * 本地公私钥加解密工具类
     */
    @Autowired
    protected SecurityHelper securityHelper;

    /**
     * 加解密服务
     */
    @Autowired
    protected SecurityService securityService;

    /**
     * 获取交易日期
     * @param epccTxn
     * @return
     */
    public String getTrxDtTm(EpccTxn epccTxn) {
        String strFormat = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        return dateFormat.format(new Date());
    }

    /**
     * 获取发送日期
     * @param epccTxn
     * @return
     */
    public String getSndDt(EpccTxn epccTxn) {
        String strFormat = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        return dateFormat.format(new Date());
    }

    /**
     * 报文类型，即报文编号
     * @param epccTxn
     * @return
     */
    public String getMsgTp(EpccTxn epccTxn) {
        return epccTxn.getMsgTp();
    }

    /**
     * 本地机构标识
     * @param epccTxn
     * @return
     */
    public String getIssrId(EpccTxn epccTxn) {
        return "Z2003731000018";
    }

    /**
     * 报文方向，11-支付机构到平台
     */
    public String getDrctn(EpccTxn epccTxn) {
        return "11";
    }

    public String getSignSN(EpccTxn epccTxn) {
        return securityHelper.getSignSN();
    }

    public String getNcrptnSN(EpccTxn epccTxn) {
        return securityHelper.getNcrptnSN();
    }

    /**
     * 数字信封 内容为:加密方式|对称密钥明文，再以RSA方式加签密后转BASE64
     * @return
     */
    public String getDgtlEnvlp(EpccTxn epccTxn) {
        // 工单表id，唯一
        byte[] keyInfo = securityService.getAESKey(epccTxn.getDebitTxnId());
        byte[] head = "01|".getBytes();
        byte[] result = new byte[head.length + keyInfo.length];
        System.arraycopy(head, 0, result, 0, head.length);
        System.arraycopy(keyInfo, 0, result, head.length, keyInfo.length);
        return securityService.encryptRSA(result);
    }

    /**
     * 获取交易序列号，并将其回写到epccTxn对象中
     * @param epccTxn
     * @return
     */
    public String getTrxId(EpccTxn epccTxn) {
        logger.info("begin to getTrxId...");
        StringBuilder trxIdStrBuilder = new StringBuilder(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        String debitTxnId = epccTxn.getDebitTxnId();
        logger.info("debitTxnId=" + debitTxnId);
        trxIdStrBuilder.append(String.format("%016d",
                debitTxnId.length() < 16 ? Long.parseLong(debitTxnId)
                        : Long.parseLong(debitTxnId.substring(debitTxnId.length() - 16, debitTxnId.length()))));
        trxIdStrBuilder.append("0");
        logger.info("trxIdStrBuilder=" + trxIdStrBuilder.toString());
        // 控制位
        String[] info = new String[]{"123456"};
        logger.info("get cache from redis=" + ArrayUtils.toString(info));
        String ctrl = info[(int) (Math.random() * info.length)];
        logger.info("ctrl=" + ctrl);
        trxIdStrBuilder.append(ctrl);
        epccTxn.setIdc(ctrl.substring(0, 2));
        logger.info("epccTxn set idc=" + epccTxn.getIdc());
        String trxId = trxIdStrBuilder.toString();
        epccTxn.setTrxId(trxId);
        logger.info("epccTxn set trxId=" + trxId);
        logger.info("end getTrxId");
        return trxId;
    }

    /**
     * 公用的加密方式
     * @param content
     * @return
     */
    public String encrypt(String content, EpccTxn epccTxn) {
        if (StringUtils.isEmpty(content)) {
            //只有非空的时候才加密，防止上送问题
            return "";
        } else {
            return securityService.encryptAES(content, epccTxn.getDebitTxnId());
        }
    }
}
