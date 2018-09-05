package com.example.demo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 14:46 2018/9/4 .
 */
public class FileUtil {

    private static final Logger logger = LogManager.getLogger(FileUtil.class);

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static String readContent(String path, String charSet) {
        try {
            File file = new File(path);
            FileInputStream fileIn = new FileInputStream(file);
            return readContent(fileIn, charSet);
        }catch (Exception e) {
            logger.error("Read [{}] meet failed", path, e);
        }
        return null;
    }

    /**
     * 读取文件
     *
     * @param
     * @return
     */
    public static String readContent(InputStream input, String charSet) {
        ByteArrayOutputStream outStream = null;
        BufferedInputStream bis = null;
        try {
            outStream = new ByteArrayOutputStream();
            bis = new BufferedInputStream(input);
            StringBuilder buf = new StringBuilder();
            for (int size = 0; (size = bis.available()) > 0;) {
                byte temp[] = new byte[size];
                bis.read(temp);
                outStream.write(temp);
            }
            byte[] rtnByte = outStream.toByteArray();
            buf.append(new String(rtnByte, charSet));
            return buf.toString();
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally {
            try {
                if(outStream != null) {
                    outStream.close();
                }
                if(bis != null) {
                    bis.close();
                }
                if(input != null) {
                    input.close();
                }
            }catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }
}
