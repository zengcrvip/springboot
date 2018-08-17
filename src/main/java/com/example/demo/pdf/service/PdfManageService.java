package com.example.demo.pdf.service;

import java.util.Date;

/**
 * @Description: pdf生成服务
 * @Author: changrong.zeng
 * @Date: Created in 11:15 2018/8/17 .
 */
public interface PdfManageService {

   void pdfHandle_async(String appCode,Date date) throws Exception;
}
