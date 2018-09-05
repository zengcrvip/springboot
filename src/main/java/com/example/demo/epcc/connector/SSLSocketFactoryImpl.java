package com.example.demo.epcc.connector;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 17:22 2018/9/5 .
 */
@Component
public class SSLSocketFactoryImpl {

    SSLContext context = null;

    public SSLContext getSSLContext() throws HttpException {
        try {
            X509TrustManager trustManager = new X509TrustManager(){
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                               String paramString) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                               String paramString) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            context = SSLContext.getInstance("TLSV1.2");
            context.init(null, new TrustManager[] { trustManager }, null);
            return context;
        }catch (Exception ex) {
            throw new HttpException(ex.toString());
        }
    }

    public CloseableHttpClient createSSLClient() throws HttpException {
        if(context == null) {
            context = getSSLContext();
        }
        return HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(context, new HostnameVerifier(){
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        })).build();
    }
}
