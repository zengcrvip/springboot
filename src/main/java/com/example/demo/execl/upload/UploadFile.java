package com.example.demo.execl.upload;

import com.example.demo.execl.model.UploadModel;

import java.util.List;
import java.util.Map;

/**
 * @author: zegncr
 * @description:
 * @date: 2019-01-26 12:55
 */
public interface UploadFile {
    /**
     * 上传处理
     * @param model
     * @return
     */
    List<Map<String, Object>> execute(UploadModel model);
}
