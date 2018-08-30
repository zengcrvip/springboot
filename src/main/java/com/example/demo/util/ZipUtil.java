package com.example.demo.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 9:49 2018/8/20 .
 */
public class ZipUtil {

    public static final int BUFFER = 10240 ;//缓存大小
    private static Logger logger = LogManager.getLogger(ZipUtil.class);


    public static void main(String[] args) throws Exception {
        zipFile("D:\\springboot","nice.zip" );
    }
    /**
     * zip压缩功能.
     * 压缩baseDir(文件夹目录)下所有文件，包括子目录
     * @throws Exception
     */
    public static void zipFile(String baseDir,String fullZipFileName) throws Exception{
        ZipOutputStream zos= null;
        try {
            List<File> fileList=getSubFiles(new File(baseDir));
            zos=new ZipOutputStream(new FileOutputStream(fullZipFileName));
            zos.setEncoding("GB18030");
            ZipEntry ze=null;
            byte[] buf=new byte[BUFFER];
            int readLen=0;
            for(int i = 0; i <fileList.size(); i++) {
                File f=(File)fileList.get(i);
                ze=new ZipEntry(getAbsFileName(baseDir, f));
                if(f.isDirectory()){
                    ze.setUnixMode(755);
                }else if(f.isFile()){
                    ze.setUnixMode(644);
                }
                ze.setSize(f.length());
                ze.setTime(f.lastModified());
                zos.putNextEntry(ze);
                InputStream is= null;
                try {
                    is=new BufferedInputStream(new FileInputStream(f));
                    while ((readLen=is.read(buf, 0, BUFFER))!=-1) {
                        zos.write(buf, 0, readLen);
                    }

                } catch (Exception e) {
                    logger.error(e);
                    throw e;
                }  finally {
                    if ( is != null ) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            logger.error( "Closing InputStream occurs error!" );
                        }
                    }
                }
            }


        } catch (Exception e) {
            logger.error(e);
            throw e;
        }  finally {
            if ( zos != null ) {
                try {
                    zos.close();
                } catch (IOException e) {
                    logger.error( "Closing InputStream occurs error!" );
                }
            }
        }
    }

    /**
     * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
     * @param baseDir java.lang.String 根目录
     * @param realFileName java.io.File 实际的文件名
     * @return 相对文件名
     */
    private static String getAbsFileName(String baseDir, File realFileName){
        File real=realFileName;
        File base=new File(baseDir);
        String ret=real.getName();
        while (true) {
            real=real.getParentFile();
            if(real==null){
                break;
            }

            if(real.equals(base)){
                break;
            }

            else{
                ret=real.getName()+"/"+ret;
            }

        }
        return ret;
    }

    /**
     * 取得指定目录下的所有文件列表，包括子目录.
     * @param baseDir File 指定的目录
     * @return 包含java.io.File的List
     */
    private static List<File> getSubFiles(File baseDir){
        List<File> ret=new ArrayList<File>();
        File[] tmp=baseDir.listFiles();
        for (int i = 0; i <tmp.length; i++) {
            if(tmp[i].isFile()){
                ret.add(tmp[i]);
            }

            if(tmp[i].isDirectory()){
                ret.addAll(getSubFiles(tmp[i]));
            }

        }
        return ret;
    }
}
