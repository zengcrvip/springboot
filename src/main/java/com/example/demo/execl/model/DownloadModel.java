package com.example.demo.execl.model;


import com.example.demo.execl.download.DownloadExtend;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: zegncr
 * @description:
 * @date: 2019-01-30 10:46
 */
@Data
@Builder
public class DownloadModel {
    /**
     * 模版路径
     */
    private String templatePath;
    /**
     * 下载文件名
     */
    private String fileName;
    /**
     * 列名字
     */
    private List<List<String>> columnName;
    /**
     * 表格数据,支持多sheet
     */
    private List<List<Map<String, Object>>> data;
    /**
     * 写入数据的行数
     */
    private Integer writeIndex;
    /**
     * 用户扩展下载功能
     */
    private DownloadExtend downloadExtend;
}
