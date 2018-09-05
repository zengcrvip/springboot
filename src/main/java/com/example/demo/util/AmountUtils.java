package com.example.demo.util;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 17:17 2018/9/4 .
 */
public class AmountUtils {
    public static final BigDecimal li = new BigDecimal(1000);
    public static final BigDecimal fen = new BigDecimal(100);

    public AmountUtils()
    {
    }

    public static BigDecimal convertYuanToFen(BigDecimal from)
    {
        return from.multiply(fen);
    }

    public static BigDecimal convertYuanToLi(BigDecimal from)
    {
        return from.multiply(li);
    }

    public static BigDecimal convertLiToYuan(BigDecimal from)
    {
        return from.divide(li);
    }


}
