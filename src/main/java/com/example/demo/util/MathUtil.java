package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangjianjun
 * @date 2018年8月29日
 *
 */
@Slf4j
public class MathUtil {


	// 该正则表达式可以匹配所有的数字 包括负数
	private static final Pattern pattern;

	static {
		pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
	}


	public static BigDecimal multiply(BigDecimal dataOne, BigDecimal dataTwo, BigDecimal... dataOthers) {
		BigDecimal b1 = Objects.isNull(dataOne) ? BigDecimal.ZERO : dataOne;
		BigDecimal b2 = Objects.isNull(dataTwo) ? BigDecimal.ZERO : dataTwo;
		BigDecimal multiply = b1.multiply(b2);
		for (BigDecimal dataOther : dataOthers) {
			BigDecimal other = Objects.isNull(dataOne) ? BigDecimal.ZERO : dataOther;
			multiply = multiply.multiply(other);
		}

		return multiply;
	}

	//获取两个数相除的结果
	public static BigDecimal getDivide(BigDecimal arg1 , BigDecimal arg2) {

		if (arg2 == BigDecimal.ZERO) {
			return BigDecimal.ZERO;
		} else {
			return arg1.divide(arg2, 0, BigDecimal.ROUND_HALF_UP);
		}
	}



    /**
     * object类型数值转BigDecimal，且保留小数点后两位
     * @param amount
     * @return
     */
    public static BigDecimal getFormatDecimalParseObject(Object amount) {
    	BigDecimal decimal = BigDecimal.ZERO;
    	try {
    		decimal = new BigDecimal(String.valueOf(amount)).setScale(2,   BigDecimal.ROUND_HALF_UP);
    	}catch (Exception e) {
		}
    	return decimal;
    }



	/**
	 * 数据相乘
	 * @param dataOne
	 * @param dataTwo
	 * @param dataOthers
	 * @return
	 */
	public static long multi(long dataOne, long dataTwo, long... dataOthers) {
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal multiply = b1.multiply(b2);
		for (long dataOther : dataOthers) {
			BigDecimal other = new BigDecimal(dataOther);
			multiply = multiply.multiply(other);
		}

		return multiply.longValue();
	}

	public static long addLong(long dataOne, long dataTwo, long... dataOthers) {
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal sumLong = b1.add(b2);
		for (long dataOther : dataOthers) {
			BigDecimal other = new BigDecimal(dataOther);
			sumLong = sumLong.add(other);
		}

		return sumLong.longValue();
	}

	public static int addInt(int dataOne, int dataTwo, int... dataOthers) {

		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal sumInt = b1.add(b2);
		for (long dataOther : dataOthers) {
			BigDecimal other = new BigDecimal(dataOther);
			sumInt = sumInt.add(other);
		}

		return sumInt.intValue();
	}

	public static BigDecimal addBigDecimal(BigDecimal dataOne, BigDecimal dataTwo, BigDecimal... dataOthers) {

		dataOne = Objects.isNull(dataOne) ? BigDecimal.ZERO : dataOne;
		dataTwo = Objects.isNull(dataTwo) ? BigDecimal.ZERO : dataTwo;

		BigDecimal sum = dataOne.add(dataTwo);
		for (BigDecimal dataOther : dataOthers) {
			dataOther = Objects.isNull(dataOther) ? BigDecimal.ZERO : dataOther;
			sum = sum.add(dataOther);
		}

		return sum;
	}

	/**
	 * 处理两个大数据相乘
	 *
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static long multi(String dataOne, String dataTwo) {
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal multiply = b1.multiply(b2);
		return multiply.longValue();
	}


	/**
	 * long型进行相减。
	 *
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static long subLong(long dataOne, long dataTwo, long... dataOthers) {
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal subtract = b1.subtract(b2);
		for (long dataOther : dataOthers) {
			BigDecimal other = new BigDecimal(dataOther);
			subtract = subtract.subtract(other);
		}
		return subtract.longValue();
	}

	public static BigDecimal subBigDecimal(BigDecimal dataOne, BigDecimal dataTwo, BigDecimal... dataOthers) {
		dataOne = Objects.isNull(dataOne) ? BigDecimal.ZERO : dataOne;
		dataTwo = Objects.isNull(dataTwo) ? BigDecimal.ZERO : dataTwo;

		BigDecimal result = dataOne.subtract(dataTwo);
		for (BigDecimal dataOther : dataOthers) {
			dataOther = Objects.isNull(dataOther) ? BigDecimal.ZERO : dataOther;
			result = result.subtract(dataOther);
		}

		return result;
	}

	/**
	 * 除法精确小数点后两位
	 *
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double divDouble(String dataOne, String dataTwo) {
		return divDouble(dataOne, dataTwo, 2);

	}

	/**
	 * 除法精确小数点后n位
	 *
	 * @param dataOne
	 * @param dataTwo
	 * @param n
	 * @return
	 */
	public static double divDouble(String dataOne, String dataTwo, int n) {
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		return b1.divide(b2, n, BigDecimal.ROUND_HALF_DOWN).doubleValue();
	}

	/**
	 * 除法精确小数点后十位
	 *
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double divDoubleForNine(String dataOne, String dataTwo) {
		return divDouble(dataOne, dataTwo, 9);
	}

	/**
	 * 处理两个大数据相乘返回double类型数据
	 *
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double multiDouble(String dataOne, String dataTwo, String... dataOthers) {
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal multiply = b1.multiply(b2);
		for (String dataOther : dataOthers) {
			BigDecimal other = new BigDecimal(dataOther);
			multiply = multiply.multiply(other);
		}
		return multiply.doubleValue();
	}

	public static BigDecimal nullBigDecimalZero(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return BigDecimal.ZERO;
		}
		return bigDecimal;
	}

	public static BigDecimal nullBigDecimalZero(String dataStr) {
		if (StringUtils.isBlank(dataStr)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(dataStr);
	}

	public static Long nullDataZero(Long data) {
		if (data == null) {
			return BigDecimal.ZERO.longValue();
		}
		return data;
	}

	public static Integer nullDataZero(Integer data) {
		if (data == null) {
			return BigDecimal.ZERO.intValue();
		}
		return data;
	}


	public static Integer nullDataZero(String data) {

		if (data == null) {
			return BigDecimal.ZERO.intValue();
		}

		return Integer.parseInt(data);
	}

	/**
	 * 匹配是否为数字
	 * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
	 * @return
	 */
	public static boolean isNumeric(String str) {

		String bigStr;
		try {
			bigStr = new BigDecimal(str).toString();
		} catch (Exception e) {
			return false;//异常 说明包含非数字。
		}

		Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
		return isNum.matches();
	}

	public static String format(BigDecimal data) {
		DecimalFormat df1 = new DecimalFormat("0.00");
		if (Objects.isNull(data)) {
			data = BigDecimal.ZERO;
		}
		return df1.format(data);
	}

	/**
	 * 判断字符串是否为Double
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
        boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
        return isDouble;
    }

	/**
	 * String转double
	 * @param str
	 * @return
	 */
	public static double getStr2Double(String str) {
        double res = 0;
        try {
            res = Double.parseDouble(str);
        } catch (Exception e) {
            res = 0;
        }
        return res;
    }


	public static BigDecimal blowZero2Zero(BigDecimal data) {
		if (Objects.isNull(data)) {
			return BigDecimal.ZERO;
		}
		if (data.compareTo(BigDecimal.ZERO) < 0) {
			return BigDecimal.ZERO;
		}
		return data;
	}

	/**
	 * @Description BigDecimal 转double
	 * @Date 2019-09-30
	 * @Param
	 * @return
	 */
    public static Double getDoubleValue(BigDecimal amount) {
        if (amount == null) return (double) 0;
        return amount.doubleValue();
    }

    public static Double getDoubleValueMultiplyOne(BigDecimal amount) {
        if (amount == null) return (double) 0;
        return (BigDecimal.valueOf(-1).multiply(amount)).doubleValue();
    }

    //转化费率
    public static String getRate(BigDecimal rate) {
        if (rate == null) {
            return "0";
        }
        return (rate.multiply(BigDecimal.valueOf(100)).setScale(2)) + "%";
    }

}
