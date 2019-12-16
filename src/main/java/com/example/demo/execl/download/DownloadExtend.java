package com.example.demo.execl.download;

import org.apache.poi.ss.usermodel.Workbook;

public interface DownloadExtend {
	/**
	 * 扩展下载功能，填充数据之前的操作
	 * @param workbook
	 */
	void doBefore(Workbook workbook);
	/**
	 * 扩展下载功能，填充数据以后的操作
	 * @param workbook
	 */
	void doAfter(Workbook workbook);
}