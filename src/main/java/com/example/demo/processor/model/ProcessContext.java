package com.example.demo.processor.model;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:处理器上下文
 * @Author: changrong.zeng
 * @Date: Created in 19:29 2018/8/16 .
 */
public class ProcessContext implements Serializable {
    private static final long serialVersionUID = 340795647623963556L;

    private Map<String, Object> ctx = new HashMap<String, Object>();

    /**
     * 当前工作状态
     */
    private String currentWork = TaisConstants.WORK_START.getCode();

    /**
     * 处理结果
     */
    private String processResult = TaisConstants.PROCESS_CONTINUE.getCode();

    public Object get(String key) {
        return ctx.get(key);
    }

    public void set(String key, Object obj) {
        ctx.put(key, obj);
    }

    public String getCurrentWork() {
        return currentWork;
    }

    public void setCurrentWork(String currentWork) {
        this.currentWork = currentWork;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }
}
