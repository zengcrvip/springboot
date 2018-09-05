package com.example.demo.epcc.connector;

import com.example.demo.epcc.model.EpccTxn;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 17:21 2018/9/5 .
 */
@Component
public class HttpsConnectionAdapterImpl implements Connector  {

    private static final Logger logger = LogManager.getLogger(HttpsConnectionAdapterImpl.class);

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String TYPE = "application/xml; charset=utf-8";

    /** 编码格式 */
    private final static String DEFAULT_CHARSET = "utf-8";

    private int soTimeout = 3000;

    private int connTimeout = 3000;

    private String url;

    @Autowired
    private SSLSocketFactoryImpl protocalSocketFactory;

    /**
     * 是否JUnit mock模式
     */
    public static boolean DEBUG_MODE = true;

    @Override
    public void connectSend(EpccTxn debitTxn) {
        logger.info("enter connectSend()...");
        String reqMsg = (String) debitTxn.getSignedReqMessage();
        if (DEBUG_MODE) {
            if (debitTxn.getIdc().equals("76")){
                url = "https://221.122.73.124:8443/preSvr";
            }else if (debitTxn.getIdc().equals("77")){
                url = "https://221.122.73.124:443/preSvr";
            }else{
                url = "https://221.122.73.124:8443/preSvr";
            }
        } else {
            url = "";
        }
        logger.info("request url=" + url);
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(soTimeout)
                .setConnectTimeout(connTimeout)
                .setConnectionRequestTimeout(connTimeout)
                .setExpectContinueEnabled(false)
                .build();
        post.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            post.setHeader(CONTENT_TYPE, TYPE);
            post.setHeader("MsgTp", debitTxn.getMsgTp());
            post.setHeader("OriIssrId", "Z2003731000018");
            StringEntity se = new StringEntity(reqMsg, DEFAULT_CHARSET);
            se.setContentType(TYPE);
            post.setEntity(se);
            httpClient = protocalSocketFactory.createSSLClient();
            response = httpClient.execute(post);
            logger.info("begin to post to epcc...");
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("the response statusCode=" + statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                String result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
                // 银行响应日志打印
                logger.info("epcc response=" + result);
                debitTxn.setRspMessageBody(result);
            } else {
                debitTxn.setRspMessageBody(null);
            }
        } catch (ConnectTimeoutException e) {
            logger.error("connect epcc timeout", e);
            debitTxn.setRspMessageBody(null);
        } catch (ConnectException e) {
            logger.error("connect epcc fail", e);
            debitTxn.setRspMessageBody(null);
        } catch (IOException e) {
            logger.error("connect epcc IOException", e);
            debitTxn.setRspMessageBody(null);
        } catch (HttpException e) {
            logger.error("connect epcc HttpException", e);
            debitTxn.setRspMessageBody(null);
        } catch (Exception e) {
            logger.error("connect epcc exception", e);
            debitTxn.setRspMessageBody(null);
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void checkConnections() {
    }
}
