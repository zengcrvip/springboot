package com.example.demo.execl.model;

import com.example.demo.execl.upload.UploadCheck;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author: zegncr
 * @description:
 * @date: 2019-01-24 14:59
 */
@Data
@Builder
public class UploadModel {
	
    /**
     * 是否需要持久化
     */
	@Builder.Default
    private boolean isSave = false;
    /**
     * 保存磁盘路径
     */
    private String path;
    /**
     * 本地保存文件名称
     */
    private String fileName;
    /**
     * 文件对象
     */
    private MultipartFile multipartFile;

    /**
     * excel或者文本，每一列的key值。
     */
    private List<String> columnKeys;

    /**
     * 如果是文本，需要传分隔符
     */
    private String separator;

    /**
     * 文件流用于解析
     */
    private InputStream inputStream;
    /**
     * excel读取时忽略表头（从titleIndex开始读取数据）
     */
    @Builder.Default
    private int titleIndex = 0;
    /**
     * 上传文件大小限制(byte)
     */
    @Builder.Default
    private int fileSizeLimit = 1024*1024*1024;
    /**
     * 上传文件扩展名校验
     */
    @Builder.Default
    private String fileExtend = "xls,xlsx";
    /**
     * 上传数据校验
     */
    private UploadCheck uploadCheck;
    /**
     * 是否重复校验
     */
    @Builder.Default
    private boolean repeatCheck = false;
    /**
     * 重复校验的字段
     */
    private List<String> repeatCheckNameList;
}