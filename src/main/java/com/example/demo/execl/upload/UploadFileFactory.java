package com.example.demo.execl.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: zegncr
 * @description: 上传简单工厂方法
 * @date: 2019-01-26 16:59
 */
@Component
public class UploadFileFactory {
    @Autowired
    Map<String, UploadFile> uploadFileMap;

    public static final String TEXT = "textUploadFile";
    public static final String EXCEL = "excelUploadFile";

    public UploadFile getInstance(String type) {
        return uploadFileMap.get(type);
    }

}
