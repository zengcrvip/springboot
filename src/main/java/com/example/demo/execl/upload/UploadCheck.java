package com.example.demo.execl.upload;

import java.util.Map;

@FunctionalInterface
public interface UploadCheck {
	/**
	 * 用于校验上传excel格式
	 * @param cols 封装好的行数据类型Map<String,Object>
	 * @param index 当前是第几列数据
	 */
	void check(Map<String, Object> cols, int index);
}