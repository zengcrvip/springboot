package com.example.demo.execl.download;


import com.example.demo.util.MathUtil;
import com.example.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jerry
 * @description: 基于模版的通用excel下载，如需扩展，继承该类，重写buildExcelDocument
 * @date: 2019-04-22
 */
@Slf4j
public class BuildExcel {

    public static Workbook createWorkbook(String templatePath) {
        if (StringUtil.isEmpty(templatePath)) {
            return null;
        }
        log.info("=====createWorkbook with templatePath=========", templatePath);
        SXSSFWorkbook workbook = null;
        BuildExcel thisObj = new BuildExcel();
        try (InputStream is = thisObj.getClass().getResourceAsStream(templatePath)) {
            workbook = new SXSSFWorkbook(new XSSFWorkbook(is));
        } catch (IOException e) {
            log.error("加载excel模版失败:{}", e);
        }
        return workbook;
    }

    public static Workbook createXSSFWorkbook(String templatePath) {
        if (StringUtil.isEmpty(templatePath)) {
            return null;
        }
        log.info("=====createWorkbook with templatePath=========", templatePath);
        XSSFWorkbook workbook = null;
        BuildExcel thisObj = new BuildExcel();
        try (InputStream is = thisObj.getClass().getResourceAsStream(templatePath)) {
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            log.error("加载excel模版失败:{}", e);
        }
        return workbook;
    }


    public static CellStyle getDecimalStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,#0.00"));
        return style;
    }

    public static CellStyle getBoldStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //在样式用应用设置的字体;
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        style.setBorderLeft(CellStyle.BORDER_THIN);  // 左边边框
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边边框颜色
        style.setBorderRight(CellStyle.BORDER_THIN); // 右边边框
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());  // 右边边框颜色
        style.setBorderTop(CellStyle.BORDER_THIN); // 上边边框
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());  // 上边边框颜色
        return style;
    }

    private static CellStyle getBoldStyleCell(Workbook workbook, short styleAlignment) {
        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //在样式用应用设置的字体;
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        style.setBorderLeft(CellStyle.BORDER_THIN);  // 左边边框
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边边框颜色
        style.setBorderRight(CellStyle.BORDER_THIN); // 右边边框
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());  // 右边边框颜色
        style.setBorderTop(CellStyle.BORDER_THIN); // 上边边框
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());  // 上边边框颜色
        //2:居中 3：右边齐
        style.setAlignment(styleAlignment); // 居中
        return style;
    }

    private static CellStyle getBoldStyleCellNoBorderBottom(Workbook workbook, short styleAlignment) {
        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //在样式用应用设置的字体;
        style.setFont(font);
        //2:居中 3：右边齐
        style.setAlignment(styleAlignment); // 居中
        return style;
    }


    public static void buildExcelSheet(Workbook workbook, List<Map<String, Object>> sheetData, List<String> columnName,
                                       int sheetNum, int rowNum) throws Exception {
        if (null == workbook || CollectionUtils.isEmpty(sheetData)) {
            return;
        }
        CellStyle decimalStyle = getDecimalStyle(workbook);
        CellStyle boldStyle = getBoldStyle(workbook);
        SXSSFSheet sheet = (SXSSFSheet) workbook.getSheetAt(sheetNum);
        log.info("sheet:{}", sheet.getSheetName());
        //将结果集渲染到当前sheet当中
        for (Map<String, Object> map : sheetData) {
            Row row = sheet.createRow(rowNum);
            for (int i = 0; i < columnName.size(); i++) {
                Object object = map.get(columnName.get(i));
                String val = (null == object) ? "" : String.valueOf(object);
                Cell cell = row.createCell(i);
                BuildExcel.setStyleCellValue(decimalStyle, boldStyle, cell, val);
            }
            rowNum++;
        }
    }

    public static void buildXExcelSheet(Workbook workbook, List<Map<String, Object>> sheetData, List<String> columnName,
                                        int sheetNum, int rowNum) throws Exception {
        if (null == workbook || CollectionUtils.isEmpty(sheetData)) {
            return;
        }
        CellStyle decimalStyle = getDecimalStyle(workbook);
        CellStyle boldStyle = getBoldStyle(workbook);
        Sheet sheet = workbook.getSheetAt(sheetNum);
        log.info("sheet:{}", sheet.getSheetName());
        //将结果集渲染到当前sheet当中
        for (Map<String, Object> map : sheetData) {
            Row row = sheet.createRow(rowNum);
            for (int i = 0; i < columnName.size(); i++) {
                Object object = map.get(columnName.get(i));
//                String val = (null == object) ? "" : String.valueOf(object);
                Cell cell = row.createCell(i);
                BuildExcel.setStyleCellValue(decimalStyle, boldStyle, cell, object);
            }
            rowNum++;
        }
    }

    public static void setStyleCellValue(CellStyle decimalStyle, CellStyle soldStyle, Cell cell, Object object) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null == object) {
            cell.setCellValue("");
        } else if (object instanceof String) {
            cell.setCellValue(String.valueOf(object));
        } else if (object instanceof Date) {
            cell.setCellValue(sdf.format(object));
        } else {
            String val = String.valueOf(object);
            if (!StringUtil.isEmpty(val) && MathUtil.isDouble(val)) {
                Double doubleValue = MathUtil.getStr2Double(val);
                cell.setCellValue(doubleValue);
                cell.setCellStyle(decimalStyle);
            } else {
                cell.setCellValue(val);
            }
        }
    }

    public static File generateFile(Workbook workbook, String filePath) {
        File file = new File(filePath);
        if (workbook != null) {
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            } catch (IOException e) {
                log.error("异常:{}", e);
            }
        }
        return file;
    }


    public static File getFile(String localPath, String templatePath) {
        return null;
    }


    //给特定单元格设置数值
    public static void setCellValue(Workbook workbook, Object value, int sheetNum, int rowNum, int cellNum, int style) {
        if (null == workbook) {
            return;
        }
        Sheet sheet = workbook.getSheetAt(sheetNum);
        log.info("sheet:{}", sheet.getSheetName());
        sheet.setForceFormulaRecalculation(false);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(cellNum);
        if (cell == null) {
            cell = row.createCell(cellNum);
        }
        if (value == null || "null".equals(value)) {
            value = "";
        }
        if (style == 1) {
            //向右靠齐
            cell.setCellStyle(getBoldStyleCell(workbook, (short) 3));
        } else if (style == 2) {
            //居中有边框
            cell.setCellStyle(getBoldStyleCell(workbook, (short) 2));
        } else if (style == 3) {
            //向右靠齐
            cell.setCellStyle(getBoldStyleCellNoBorderBottom(workbook, (short) 3));
        }else if (style == 4) {
            //居中无边框
            cell.setCellStyle(getBoldStyleCellNoBorderBottom(workbook, (short) 2));
        }
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }

    }


    //给特定单元格设置数值
    public static void setCellValue(Workbook workbook, Object value, int sheetNum, int rowNum, int cellNum) {
        setCellValue(workbook, value, sheetNum, rowNum, cellNum, 0);
    }

    public static void setCellValue(Workbook workbook, Sheet sheet, Object value, int rowNum, int cellNum) {
        setCellValue(workbook, sheet, value, rowNum, cellNum, 1);
    }

    //传入sheet
    public static void setCellValue(Workbook workbook, Sheet sheet, Object value, int rowNum, int cellNum,int style) {
        if (null == sheet) {
            return;
        }
        log.info("sheet:{}", sheet.getSheetName());
        sheet.setForceFormulaRecalculation(false);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(cellNum);
        if (cell == null) {
            cell = row.createCell(cellNum);
        }
        if (style == 1) {
            //向右靠齐
            cell.setCellStyle(getBoldStyleCell(workbook, (short) 3));
        } else if (style == 2) {
            //向右靠齐
            cell.setCellStyle(getBoldStyleCell(workbook, (short) 2));
        } else if (style == 3) {
            //向右靠齐
            cell.setCellStyle(getBoldStyleCellNoBorderBottom(workbook, (short) 3));
        }
        if (value == null || "null".equals(value)) {
            value = "";
        }
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }

    }


    /**
     *
     * @param fromsheet
     * @param newSheet
     * @param firstrow
     * @param lastrow
     */
    public static void copySheet(Sheet fromsheet, Sheet newSheet, int firstrow, int lastrow) {
        // 复制一个单元格样式到新建单元格
        if ((firstrow == -1) || (lastrow == -1) || lastrow < firstrow) {
            return;
        }
        // 复制合并的单元格
        CellRangeAddress region = null;
        for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
            region = fromsheet.getMergedRegion(i);
            if ((region.getFirstRow() >= firstrow) && (region.getLastRow() <= lastrow)) {
                newSheet.addMergedRegion(region);
            }
        }
        Row fromRow = null;
        Row newRow = null;
        Cell newCell = null;
        Cell fromCell = null;
        // 设置列宽
        for (int i = firstrow; i < lastrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow != null) {
                for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    int colnum = fromsheet.getColumnWidth((short) j);
                    if (colnum > 100) {
                        newSheet.setColumnWidth((short) j, (short) colnum);
                    }
                    if (colnum == 0) {
                        newSheet.setColumnHidden((short) j, true);
                    } else {
                        newSheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }

        // 复制行并填充数据
        for (int i = 0; i < lastrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            newRow = newSheet.createRow(i - firstrow);
            newRow.setHeight(fromRow.getHeight());
            for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {
                fromCell = fromRow.getCell((short) j);
                if (fromCell == null) {
                    continue;
                }
                newCell = newRow.createCell((short) j);
                newCell.setCellStyle(fromCell.getCellStyle());
                int cType = fromCell.getCellType();
                newCell.setCellType(cType);
                switch (cType) {
                    case HSSFCell.CELL_TYPE_STRING:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        newCell.setCellValue(fromCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        newCell.setCellValue(fromCell.getCellFormula());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        newCell.setCellValue(fromCell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        newCell.setCellValue(fromCell.getErrorCellValue());
                        break;
                    default:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

}
