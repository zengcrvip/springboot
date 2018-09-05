package com.example.demo.epcc.protocol;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 加签 验签
 */
@Component
public class SecurityService {
    private static Logger logger = LogManager.getLogger(SecurityService.class);

    /**
     * 加解密帮助类
     */
    @Autowired
    private SecurityHelper securityHelper;

    /**
     * RSA加密
     */
    public String encryptRSA(String content) {
        try {
            return securityHelper.encryptRSA(content.getBytes("UTF-8"), "UTF-8");
        }catch (UnsupportedEncodingException e) {
            logger.error("encryptRSA error", e);
        }catch (Exception e) {
            logger.error("encryptRSA error", e);
        }
        return null;
    }

    /**
     * RSA加密
     */
    public String encryptRSA(byte[] content) {
        try {
            return securityHelper.encryptRSA(content,"UTF-8");
        }catch (UnsupportedEncodingException e) {
            logger.error("encryptRSA error", e);
        }catch (Exception e) {
            logger.error("encryptRSA error", e);
        }
        return null;
    }

    /**
     * RSA解密
     *
     * @throws Exception
     */
    public String decryptRSA(byte[] plainBytes) {
        try {
            return new String(securityHelper.decryptRSA(plainBytes, "UTF-8"), "UTF-8");
        }catch (UnsupportedEncodingException e) {
            logger.error("decryptRSA error", e);
        }catch (Exception e) {
            logger.error("decryptRSA error", e);
        }
        return null;
    }

    /**
     * RSA加签
     */
    public String signRSA(String content) {
        try {
            String messageBodyString = substringBystr(content, "<root", "</root>", true);
            return content.replaceAll("\\{S:", "\\{S:" + securityHelper.signRSA(messageBodyString.getBytes()));
        }catch (Exception e) {
            logger.error("encryptRSA error", e);
        }
        return null;
    }

    /**
     * RSA验签
     *
     *  plainBytes 加签源数据
     *  signBytes 加签字段
     * @throws Exception
     */
    public boolean verifyRSA(String content) {
        try {
            return securityHelper.verifyRSA(
                    substringBystr(content, "<root", "</root>", true).getBytes("UTF-8"),
                    substringBystr(content, "{S:", "}", false).getBytes("UTF-8"), "UTF-8");
        }catch (Exception e) {
            logger.error("verifyRSA error", e);
        }
        return false;
    }

    /**
     * AES加密
     *
     * @param content
     * @return
     */
    public String encryptAES(String content, String keyInfo) {
        try {
            return securityHelper.encryptAES(content, keyInfo);
        }catch (Exception e) {
            logger.error("encryptAES error", e);
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param cryptograph
     * @return
     */
    public String decrypt(byte[] cryptograph, String keyInfo) {
        try {
            return securityHelper.decryptAES(cryptograph, keyInfo);
        }catch (Exception e) {
            logger.error("decryptAES error", e);
        }
        return null;
    }

    /**
     * 获取AES对称密钥
     *
     * @return
     */
    public byte[] getAESKey(String keyInfo) {
        return securityHelper.getKey(keyInfo);
    }

    /**
     * 获取公钥证书系列号
     *
     * @return
     */
    public String getSignSN() {
        return securityHelper.getSignSN();
    }

    /**
     * 获取银行公钥证书系列号
     *
     * @return
     */
    public String getNcrptnSN() {
        return securityHelper.getNcrptnSN();
    }

    /**
     * 按照字段截取字符串
     *
     * @param content
     * @param begin
     * @param end
     * @param hasInclude 是否包含节点
     * @return
     */
    public String substringBystr(String content, String begin, String end, boolean hasInclude) {
        if(hasInclude) {
            return content.substring(content.lastIndexOf(begin), content.lastIndexOf(end)) + end;
        }
        return content.substring(content.lastIndexOf(begin) + begin.length(), content.lastIndexOf(end));
    }
}
