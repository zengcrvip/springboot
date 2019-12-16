package com.example.demo.execl.upload;

import com.example.demo.exception.ServiceException;
import com.example.demo.execl.model.UploadModel;
import com.example.demo.util.Assert;
import enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: zegncr
 * @description: 通用的文件上传抽象类，实现常用方法
 * @date: 2019-01-24 10:23
 */
@Slf4j
abstract class AbstractUploadFile implements UploadFile {
    protected final static String EXCEL_XLS = "xls";
    protected final static String EXCEL_XLSX = "xlsx";

    @Override
    public final List<Map<String, Object>> execute(UploadModel model){
        check(model);
        save2disk(model);
        return analysis2Collection(model);
    }

    /**
     * 检查必要参数
     *
     * @param model
     */
    protected void check(UploadModel model) {
        if (model.isSave()) {
            Assert.notNull(model.getPath(), ErrorEnum.NOT_NULL_ERROR);
            Assert.notNull(model.getFileName(), ErrorEnum.NOT_NULL_ERROR);
        }
        Assert.notNull(model.getMultipartFile(), ErrorEnum.NOT_NULL_ERROR);
        if(model.getMultipartFile().getSize()>model.getFileSizeLimit()) {
        	throw new ServiceException(ErrorEnum.PARAM_ILLEGAL_ERROR.getCode(),"文件大小超过限制，最大为："+model.getFileSizeLimit()+"byte");
        }
        Assert.notEmpty(model.getColumnKeys(), ErrorEnum.NOT_NULL_ERROR);
        String[] fileExtends = model.getFileExtend().split(",");
        boolean allowFile = false;
        String fileExtendName = model.getMultipartFile().getOriginalFilename().substring(model.getMultipartFile().getOriginalFilename().lastIndexOf('.')+1);
        for(String fileExtend:fileExtends) {
        	if(fileExtend.equals(fileExtendName)) {
        		allowFile = true;
        		break;
        	}
        }
        if(!allowFile) {
        	throw new ServiceException(ErrorEnum.PARAM_ILLEGAL_ERROR.getCode(),"文件格式不正确，支持的文件格式"+model.getFileExtend());
        }
    }

    /**
     * 保存本地磁盘
     *
     * @param model
     */
    protected void save2disk(UploadModel model) {
        MultipartFile multipartFile = model.getMultipartFile();
        try {
            model.setInputStream(multipartFile.getInputStream());
        } catch (IOException e) {
            log.error("getInputStream error ", e);
        }
        if (model.isSave()) {
            String fileName = model.getFileName();
            String path = model.getPath();
            log.info("save file start:{}", path + fileName);
            long startTime = System.currentTimeMillis();
            File dest = new File(path + fileName);
            try {
                multipartFile.transferTo(dest);
            } catch (IOException e) {
                log.error("save file error ", path + fileName, e);
            }
            log.info("save file end:{} 耗时:{}", path + fileName, System.currentTimeMillis() - startTime);
        }
    }

    /**
     * 解析为指定集合
     *
     * @param model
     * @return
     */
    protected abstract List<Map<String, Object>> analysis2Collection(UploadModel model);

}
