package com.example.demo.pdf.service;

import java.util.List;
import java.util.Map;

/**
 * @Description:pdf生成服务
 * @Author: changrong.zeng
 * @Date: Created in 16:16 2018/8/17 .
 */
public interface PdfCreateService {

     void createVoucher(Map map, String tempPath, String basePath) throws Exception;

     int createBatchDetailVoucher(List<Map> details, String tempPath, String basePath, String  appCode) throws Exception;
}
