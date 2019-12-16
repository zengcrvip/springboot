package com.example.demo.execl.upload;

import com.example.demo.exception.ServiceException;
import com.example.demo.execl.model.UploadModel;
import com.example.demo.util.Assert;
import enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zegncr
 * @description: Text下载实现
 * @date: 2019-01-24 10:23
 */
@Component
@Slf4j
public class TextUploadFile extends AbstractUploadFile {

    @Override
    protected List<Map<String, Object>> analysis2Collection(UploadModel model) {
        List<String> columnKeys = model.getColumnKeys();
        List<Map<String, Object>> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(model.getInputStream()))) {
            String tempString;
            while ((tempString = br.readLine()) != null) {
                String[] columnValues = StringUtils.tokenizeToStringArray(tempString, model.getSeparator());
                Assert.isTrue(columnKeys.size() == columnValues.length, ErrorEnum.TEXT_COLUMN_KEY_ERROR);
                Map col = new HashMap();
                for (int i = 0; i < columnKeys.size(); i++) {
                    col.put(columnKeys.get(i), columnValues[i]);
                }
                rows.add(col);
            }
        } catch (IOException e) {
            log.error("解析Text为集合异常", e);
            throw new ServiceException(ErrorEnum.TEXT_ANALYSIS_ERROR);
        }
        return rows;
    }

    @Override
    protected void check(UploadModel model) {
        super.check(model);
        Assert.notNull(model.getSeparator(), ErrorEnum.NOT_NULL_ERROR);
    }

}
