package com.example.demo.execl.download;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.execl.model.DownloadModel;
import com.example.demo.util.MathUtil;
import com.example.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author: zegncr
 * @description: 基于模版的通用excel下载，如需扩展，继承该类，重写buildExcelDocument
 * @date: 2019-01-02 16:20
 */
@Slf4j
public class SimpleExcelView extends AbstractXlsxView {
	
    protected final static String DOWNLOAD_MODEL = "_downloadModel";

    /**
     * 流输出，清理磁盘缓存
     *
     * @param workbook
     * @param response
     * @throws IOException
     */
    @Override
    protected void renderWorkbook(Workbook workbook, HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        // Dispose of temporary files in case of streaming variant...
        ((SXSSFWorkbook) workbook).dispose();
    }

    /**
     * 创建文档
     *
     * @param model
     * @param request
     * @return
     */
    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        //根据模版创建Excel
        //使用SXSSFWorkbook支持大文件导出，不会出现OOM(自动写入硬盘)
        SXSSFWorkbook workbook = null;
        DownloadModel downloadModel = (DownloadModel) model.get(DOWNLOAD_MODEL);
        String templatePath = downloadModel.getTemplatePath();
        if (null == downloadModel || StringUtil.isEmpty(templatePath)){
            return null;
        }

        try (InputStream is = this.getClass().getResourceAsStream(templatePath)) {
            workbook = new SXSSFWorkbook(new XSSFWorkbook(is));
        } catch (IOException e) {
            log.error("加载excel模版失败 model{}", JSONObject.toJSONString(model), e);
        }
        return workbook;
    }


    /**
     * 构建文档
     *
     */
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DownloadModel downloadModel = (DownloadModel) model.get(DOWNLOAD_MODEL);
        String fileName = downloadModel.getFileName();
        List<List<Map<String, Object>>> sheetDataList = downloadModel.getData();
        List<List<String>> columnNameList = downloadModel.getColumnName();
        if (StringUtil.isEmpty(fileName) || CollectionUtils.isEmpty(sheetDataList) || CollectionUtils.isEmpty(columnNameList)
                || sheetDataList.size() != columnNameList.size()){
            return;
        }
        if(downloadModel.getDownloadExtend()!=null) {
        	downloadModel.getDownloadExtend().doBefore(workbook);
        }
        CellStyle decimalStyle = BuildExcel.getDecimalStyle(workbook);
        CellStyle boldStyle = BuildExcel.getBoldStyle(workbook);
        //创建Excel文件输出流对象
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        int sheetNum = 0;
        for (List<Map<String, Object>> sheetData: sheetDataList) {
            SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(sheetNum);
            List<String> columnName = columnNameList.get(sheetNum);
            int rowNum = downloadModel.getWriteIndex()==null?sheet.getLastRowNum() + 1:downloadModel.getWriteIndex();
            //将结果集渲染到当前sheet当中
            for (Map<String, Object> map : sheetData) {
                Row row = sheet.createRow(rowNum);
                for (int i = 0; i < columnName.size(); i++) {
                    Object object = map.get(columnName.get(i));
                    String val = (null == object) ? "" : String.valueOf(object);
                    Cell cell = row.createCell(i);
                    if (!StringUtil.isEmpty(val) && MathUtil.isDouble(val)){
                        Double doubleValue = MathUtil.getStr2Double(val);
                        cell.setCellValue(doubleValue);
                        cell.setCellStyle(decimalStyle);
                    }else {
                        cell.setCellValue(val);
                    }
                }
                rowNum++;
            }
            sheetNum++;
        }
        if(downloadModel.getDownloadExtend()!=null) {
        	downloadModel.getDownloadExtend().doAfter(workbook);
        }
    }

}
