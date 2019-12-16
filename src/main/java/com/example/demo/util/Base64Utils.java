package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Base64;

@Slf4j
public final class Base64Utils {
    private final static Base64.Decoder decoder = Base64.getDecoder();
    private final static Base64.Encoder encoder = Base64.getEncoder();
    private static final int CACHE_SIZE = 1024;

    private Base64Utils(){}

    public static byte[] decode(String base64) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64);
    }

    public static String encode(byte[] bytes) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    /**
     * @param filePath absolute file path
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    /**
     * decode string and write to <code>filePath</code>
     *
     * @param filePath absolute file path
     * @param base64   base64 encoded string
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    private static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file);
                 ByteArrayOutputStream out = new ByteArrayOutputStream(2048)){
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                }
                data = out.toByteArray();
            } catch (FileNotFoundException e) {
                log.error("FileNotFoundException:{}", e);
            }
        }
        return data;
    }

    private static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try (InputStream in = new ByteArrayInputStream(bytes);
             OutputStream out = new FileOutputStream(destFile)){
            if (destFile.createNewFile()){
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                }
            }
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException:{}", e);
        }
    }
}