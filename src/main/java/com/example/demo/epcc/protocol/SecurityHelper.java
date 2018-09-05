package com.example.demo.epcc.protocol;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

/**
 * RSAHelper -对RSA 签名&验签/分段加密&分段解密的包装签名算法: "SHA1withRSA", 私钥进行签名; 公钥进行验签. 加密算法: "RSA/ECB/PKCS1Padding", 使用对方公钥进行加密;
 * 使用自己私钥进行解密. [localPrivKey]是自己的私钥, 自己的公钥给通信对方. [peerPubKey]是对方的公钥, 对方的私钥在对方那边. 为了方便, 这里假定双方的密钥长度一致, 签名和加密的规则也一致.
 * 以`Base64Str`结尾的参数表示内容是Base64编码的字符串, 其他情况都是raw字符串.
 */
@Component
public class SecurityHelper {
    private static Logger logger = LogManager.getLogger(SecurityHelper.class);

    private static final String KEY_ALGORITHM = "RSA";

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    // 加密block需要预留11字节
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    private static final int KEYBIT = 2048;

    private static final int RESERVEBYTES = 11;

    /**
     * 本地私钥
     */
    @Value("${privateKeyPath}")
    private String privateKeyPath;

    /**
     * 本地公钥
     */
    @Value("${billPublicKeyPath}")
    private String billPublicKeyPath;

    /**
     * 银行公钥
     */
    @Value("${publicKeyPath}")
    private String publicKeyPath;


    private PrivateKey privateKey;


    private PublicKey publicKey;

    /** 加密证书序列号 */
    private String ncrptnSN;

    /** 签名证书序列号 */
    private String signSN;

    /**
     * 获得AES密钥
     * 
     * @param keyInfo 密钥源信息
     * @return
     */
    public SecretKey getSecretKey(String keyInfo) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(keyInfo.getBytes());
            kgen.init(256, random);
            return kgen.generateKey();
        }catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 加载私钥证书
     */
    private PrivateKey getPrivateKey() {
        InputStream is = null;
        if(null == privateKey) {
            try {
                is = getClass().getResourceAsStream(privateKeyPath);
                privateKey = getPrivateKey(is);
            }catch (FileNotFoundException e) {
                logger.error("initPriKey get privateKeyPath error file not found !!" + e.getMessage());
            }catch (Exception e) {
                logger.error("initPriKey get privKey error!!" + e.getMessage());
            }finally {
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return privateKey;
    }

    /**
     * 从文件中加载私钥
     * keyFileName 私钥文件名
     * @param
     * @return是否成功
     * @throws Exception
     */
    private RSAPrivateKey getPrivateKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if(readLine.charAt(0) == '-') {
                    continue;
                }else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPrivateKey(sb.toString());
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }finally {
            try {
                if(br != null) {
                    br.close();
                }
            }catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            try {
                if(in != null) {
                    in.close();
                }
            }catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     * 从字符串中加载私钥
     * 
     * @param privateKeyStr 公钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    private RSAPrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        }catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e.getMessage());
        }catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException(e.getMessage());
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * 加载公钥证书
     */
    private PublicKey getPublicKey() {
        InputStream is = null;
        try {
            if(publicKey != null) {
                return publicKey;
            }
//            is = new FileInputStream(publicKeyPath);
            is = getClass().getResourceAsStream(publicKeyPath);
            // cer文件获取
            if(publicKeyPath.toLowerCase().contains(".cer")) {
                CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
                X509Certificate Cert = (X509Certificate) certificatefactory.generateCertificate(is);
                publicKey = Cert.getPublicKey();
            }else {
                // pem文件获取
                publicKey = getPublicKey(is);
            }
        }catch (FileNotFoundException e) {
            logger.error("initPubKey get publicKeyPath={} error file not found !!", publicKeyPath, e);
        }catch (Exception e) {
            logger.error("initPubKey get pubKey publicKeyPath={} error!!", publicKeyPath, e);
        }finally {
            if(is != null) {
                try {
                    is.close();
                }catch (IOException e) {
                    logger.error("close file={} error!!", publicKeyPath);
                }
            }
        }
        return publicKey;
    }

    /**
     * 从文件中输入流中加载公钥
     * 
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    private RSAPublicKey getPublicKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if(readLine.charAt(0) == '-') {
                    continue;
                }else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPublicKey(sb.toString());
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }finally {
            try {
                if(br != null) {
                    br.close();
                }
            }catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            try {
                if(in != null) {
                    in.close();
                }
            }catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     * 从字符串中加载公钥
     * 
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    private RSAPublicKey getPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return publicKey;
        }catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e.getMessage());
        }catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException(e.getMessage());
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * RAS加密
     * 
     *  peerPubKey 公钥
     *  data 待加密信息
     * @return byte[]
     * @throws Exception
     */
    public String encryptRSA(byte[] plainBytes, String charset) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段加密的block数 (向上取整)
        int nBlock = (plainBytes.length / encryptBlock);
        if((plainBytes.length % encryptBlock) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }
        // 输出buffer, 大小为nBlock个decryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlock);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        // 分段加密
        for (int offset = 0; offset < plainBytes.length; offset += encryptBlock) {
            // block大小: encryptBlock 或剩余字节数
            int inputLen = (plainBytes.length - offset);
            if(inputLen > encryptBlock) {
                inputLen = encryptBlock;
            }
            // 得到分段加密结果
            byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(encryptedBlock);
        }
        // 如果是Base64编码，则返回Base64编码后的数组
        return Base64.encodeBase64String(outbuf.toByteArray()).replaceAll("\r|\n", "");
    }

    /**
     * RSA解密
     * 
     * @param cryptedBytes
     *  useBase64Code
     * @param charset
     * @return
     * @throws Exception
     */
    public byte[] decryptRSA(byte[] cryptedBytes, String charset) throws Exception {
        byte[] data = null;
        // 如果是Base64编码的话，则要Base64解码
        data = Base64.decodeBase64(new String(cryptedBytes, charset));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段解密的block数 (理论上应该能整除)
        int nBlock = (data.length / decryptBlock);
        // 输出buffer, , 大小为nBlock个encryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * encryptBlock);
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        // 分段解密
        for (int offset = 0; offset < data.length; offset += decryptBlock) {
            // block大小: decryptBlock 或剩余字节数
            int inputLen = (data.length - offset);
            if(inputLen > decryptBlock) {
                inputLen = decryptBlock;
            }
            // 得到分段解密结果
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(decryptedBlock);
        }
        outbuf.flush();
        outbuf.close();
        return outbuf.toByteArray();
    }

    /**
     * RSA签名
     * 
     *  localPrivKey 私钥
     *   plaintext 需要签名的信息
     * @return byte[]
     * @throws Exception
     */
    public String signRSA(byte[] plainBytes) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(getPrivateKey());
        signature.update(plainBytes);
        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        return Base64.encodeBase64String(signature.sign()).replaceAll("\r|\n", "");
    }

    /**
     * RSA验签操作
     * 
     * @param plainBytes 需要验签的信息
     * @param signBytes 签名信息
     * @return boolean
     */
    public boolean verifyRSA(byte[] plainBytes, byte[] signBytes, String charset) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(getPublicKey());
        signature.update(plainBytes);
        return signature.verify(Base64.decodeBase64(new String(signBytes, charset)));
    }

    // 256位AES加密
    public String encryptAES(String content, String keyInfo) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(keyInfo));
        byte[] cryptograph = cipher.doFinal(content.getBytes("utf-8"));
        return Base64.encodeBase64String(cryptograph).replaceAll("\r|\n", "");
    }

    // AES解密
    public String decryptAES(byte[] cryptograph, String keyInfo) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keyInfo));
        byte[] content = cipher.doFinal(Base64.decodeBase64(cryptograph));
        return new String(content);
    }

    // 获取对称密钥
    public byte[] getKey(String keyInfo) {
        return getSecretKey(keyInfo).getEncoded();
    }

    // 获取网联公钥证书系列号
    public String getNcrptnSN() {
        if(ncrptnSN != null) {
            return ncrptnSN;
        }
        ncrptnSN = getPublicKeySN(publicKeyPath);
        return ncrptnSN;
    }

    // 获取我方公钥证书系列号
    public String getSignSN() {
        if(signSN != null) {
            return signSN;
        }
        signSN = getPublicKeySN(billPublicKeyPath);
        return signSN;
    }

    private String getPublicKeySN(String pukPath) {
        if(pukPath == null || !pukPath.toLowerCase().endsWith(".cer")) {
            logger.error("public key path={} must be endsWith cer", pukPath);
            return null;
        }
        CertificateFactory certificatefactory;
        InputStream is = null;
        String sn = null;
        try {
            certificatefactory = CertificateFactory.getInstance("X.509");
            X509Certificate Cert;
//            is = new FileInputStream(pukPath);
            is = getClass().getResourceAsStream(pukPath);
            Cert = (X509Certificate) certificatefactory.generateCertificate(is);
            sn = Cert.getSerialNumber().toString(16);
        }catch (CertificateException e) {
            sn = StringUtils.EMPTY;
            logger.error("get public key sn with load file={} error!!", pukPath, e);
        }catch (Exception e) {
            sn = StringUtils.EMPTY;
            logger.error("get public key sn with load file={} error!!", pukPath, e);
        }finally {
            if(is != null) {
                try {
                    is.close();
                }catch (IOException e) {
                    logger.warn("close file={} error!!", pukPath, e);
                }
            }
        }
        return sn;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
        privateKey = null;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
        publicKey = null;
        if(ncrptnSN != null) {
            ncrptnSN = null;
        }
    }

    public void setBillPublicKeyPath(String billPublicKeyPath) {
        this.billPublicKeyPath = billPublicKeyPath;
        if(signSN != null) {
            signSN = null;
        }
    }
}