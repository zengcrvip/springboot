package com.example.demo.execl.download;

import lombok.Builder;
import lombok.Data;

/**
 * @author: jerry
 * @description:
 * @date: 2019-04-22 10:46
 */
@Data
@Builder
public class CellWriteModel {

    private int beginRow;
    private int beginCell;
    private Object cellValue;
}
