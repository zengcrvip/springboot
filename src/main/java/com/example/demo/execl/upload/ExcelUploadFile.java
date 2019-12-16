package com.example.demo.execl.upload;

import com.example.demo.exception.ServiceException;
import com.example.demo.execl.model.UploadModel;
import com.example.demo.util.Assert;
import enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

/**
 * @author: zegncr
 * @description: Excel上传实现
 * @date: 2019-01-24 10:24
 */
@Component
@Slf4j
public class ExcelUploadFile extends AbstractUploadFile {

//    @Value("${ledger.totalUpload}")
    private Integer totalupload =1;


    @Override
    protected List<Map<String, Object>> analysis2Collection(UploadModel model) {
        List<Map<String, Object>> rows = new ArrayList<>();
        String originalFilename = model.getMultipartFile().getOriginalFilename();
        List<String> columnKeys = model.getColumnKeys();
        try (InputStream is = model.getInputStream()) {
            Workbook workbook = null;
            //2007以前的版本
            if (originalFilename.endsWith(EXCEL_XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (originalFilename.endsWith(EXCEL_XLSX)) {
                workbook = new XSSFWorkbook(is);
            }

            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> checkedList = null;
            Set<Integer> checkColumn = null;
            if (model.isRepeatCheck()) {
                Assert.notEmpty(model.getRepeatCheckNameList(), ErrorEnum.REPEAT_COLUMN_NAME);
                checkedList = new HashMap<>();
                checkColumn = new HashSet<>();
            }
            for (int i = model.getTitleIndex(); i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (null == row) {
                    continue;
                }
                Map<String, Object> cols = new HashMap<>();
                int j = 0;
                for (String column : columnKeys) {
                    Cell c = row.getCell(j);
                    String value = "";
                    if (c != null) {
                        c.setCellType(Cell.CELL_TYPE_STRING);
                        value = c.getStringCellValue();
                    }
                    cols.put(column, value);
                    if (model.isRepeatCheck()) {
                        if (model.getRepeatCheckNameList().contains(column)) {
                            checkColumn.add(j + 1);
                        }
                    }
                    j++;
                }
                if (model.getUploadCheck() != null) {
                    model.getUploadCheck().check(cols, i + 1);
                }
                if (model.isRepeatCheck()) {
                    StringBuilder key = new StringBuilder();
                    for (String s : model.getRepeatCheckNameList()) {
                        key.append(cols.get(s));
                    }
                    if (checkedList.get(key.toString()) == null) {
                        checkedList.put(key.toString(), i);
                    } else {
                        int repeatedColumn = checkedList.get(key.toString());
                        StringBuilder errorMsg = new StringBuilder("第" + i + "行和第" + repeatedColumn + "行数据有重复,第");
                        for (Integer column : checkColumn) {
                            errorMsg.append(column + " ");
                        }
                        errorMsg.append("列数据不允许重复");
                        throw new ServiceException(ErrorEnum.EXCEL_ANALYSIS_ERROR.getCode(), errorMsg.toString());
                    }
                }
                rows.add(cols);
                Assert.isFalse(totalupload != null && rows.size() > totalupload, ErrorEnum.EXCEL_ANALYSIS_ERROR, "超出单次上传最大限制 【" + totalupload + "】条数据，请分批上传");
            }
        } catch (Exception e) {
            log.error("解析excel为集合异常", e);
            throw new ServiceException(ErrorEnum.EXCEL_ANALYSIS_ERROR);
        }
        return rows;
    }
}
