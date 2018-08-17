package com.example.demo.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 12:11 2018/8/17 .
 */
public class DateUtil {

    private DateUtil()
    {
    }

    public static String formatDateTime(String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String now = sdf.format(new Date());
        return now;
    }

    public static String formatDateTime(String pattern, Date date)
    {
        String strDate = null;
        String strFormat = pattern;
        SimpleDateFormat dateFormat = null;
        if(date == null)
        {
            return "";
        } else
        {
            dateFormat = new SimpleDateFormat(strFormat);
            strDate = dateFormat.format(date);
            return strDate;
        }
    }

    public static String formatDateTime(String pattern, Date date, Locale locale)
    {
        String strDate = null;
        String strFormat = pattern;
        SimpleDateFormat dateFormat = null;
        if(date == null)
        {
            return "";
        } else
        {
            dateFormat = new SimpleDateFormat(strFormat, locale);
            strDate = dateFormat.format(date);
            return strDate;
        }
    }

    public static Date parse(String pattern, String strDateTime)
    {
        Date date = null;
        if(strDateTime == null || pattern == null){
            return null;
        }
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            date = formatter.parse(strDateTime);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static Date composeDate(Date date, Date time)
    {
        if(date == null || time == null)
        {
            return null;
        } else
        {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(time);
            Calendar c3 = Calendar.getInstance();
            c3.set(c1.get(1), c1.get(2), c1.get(5), c2.get(11), c2.get(12), c2.get(13));
            return c3.getTime();
        }
    }

    public static Date getTheDate(Date date)
    {
        if(date == null)
        {
            return null;
        } else
        {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            Calendar c2 = Calendar.getInstance();
            c2.set(c1.get(1), c1.get(2), c1.get(5), 0, 0, 0);
            long millis = c2.getTimeInMillis();
            millis -= millis % 1000L;
            c2.setTimeInMillis(millis);
            return c2.getTime();
        }
    }

    public static Date skipDateTime(Date d, int skipDay)
    {
        if(d == null)
        {
            return null;
        } else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(5, skipDay);
            return calendar.getTime();
        }
    }

    public static String skipDateTime(String timeStr, int skipDay)
    {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date d = parse(pattern, timeStr);
        Date date = skipDateTime(d, skipDay);
        return formatDateTime(pattern, date);
    }

    public static String skipDate(String dateStr, int skipDay)
    {
        String pattern = "yyyy-MM-dd";
        Date d = parse(pattern, dateStr);
        Date date = skipDateTime(d, skipDay);
        return formatDateTime(pattern, date);
    }

    public static String getTime(String timeStr, int skipDay, int skipHour, int skipMinute)
    {
        if(null == timeStr)
        {
            return null;
        } else
        {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(parse("yyyy-MM-dd HH:mm:ss", timeStr));
            cal.add(5, skipDay);
            cal.add(11, skipHour);
            cal.add(12, skipMinute);
            cal.get(8);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(cal.getTime());
        }
    }

    public static boolean dateCompare(String str)
    {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
        String isDate = sdf_d.format(new Date());
        try
        {
            Date date1 = sdf_d.parse(str);
            Date date0 = sdf_d.parse(isDate);
            if(date0.after(date1)){
                bea = true;
            }

        }
        catch(ParseException e)
        {
            bea = false;
        }
        return bea;
    }

    public static boolean monthCompare(String str)
    {
        boolean bea = false;
        SimpleDateFormat sdf_m = new SimpleDateFormat("yyyy-MM");
        String isMonth = sdf_m.format(new Date());
        try
        {
            Date date1 = sdf_m.parse(str);
            Date date0 = sdf_m.parse(isMonth);
            if(date0.after(date1)){
                bea = true;
            }

        }
        catch(ParseException e)
        {
            bea = false;
        }
        return bea;
    }

    public static boolean secondCompare(String str)
    {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String isDate = sdf_d.format(new Date());
        try
        {
            Date date1 = sdf_d.parse(str);
            Date date0 = sdf_d.parse(isDate);
            if(date0.after(date1)){
                bea = true;
            }

        }
        catch(ParseException e)
        {
            bea = false;
        }
        return bea;
    }

    public static boolean secondCompare(String str1, String str2)
    {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date date1 = sdf_d.parse(str1);
            Date date0 = sdf_d.parse(str2);
            if(date0.after(date1)){
                bea = true;
            }

        }
        catch(ParseException e)
        {
            bea = false;
        }
        return bea;
    }

    public static String dateAdd(String type, int i)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatDateTime("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        if(type.equals("s"))
        {
            int s = c.get(13);
            s += i;
            c.set(13, s);
            str = df.format(c.getTime());
        } else
        if(type.equals("d"))
        {
            int d = c.get(5);
            d += i;
            c.set(5, d);
            str = df.format(c.getTime());
        }
        return str;
    }

    public static String getTime(int skipDay)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(5, skipDay);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(cal.getTime());
    }

    public static long subtraction(Date minuend, Date subtrahend)
    {
        long daterange = minuend.getTime() - subtrahend.getTime();
        long time = 86400000L;
        return daterange % time != 0L ? daterange / time + 1L : daterange / time;
    }

    public static long getM(Date date)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return (long)cal.get(7);
    }

    public static String getLastDate(String temp)
    {
        if(temp == null || temp.equals(""))
            temp = "1";
        int i = Integer.parseInt(temp);
        DateFormat dateFormat = DateFormat.getDateInstance(2);
        Calendar grc = Calendar.getInstance();
        grc.add(5, -i);
        return dateFormat.format(grc.getTime());
    }

    public static String getLastYearDate()
    {
        Calendar c = Calendar.getInstance();
        int y = c.get(1);
        String year = String.valueOf(y - 1);
        return year;
    }

    public static String getLastMonthDate()
    {
        Calendar c = Calendar.getInstance();
        int y = c.get(1);
        int m = c.get(2) + 1;
        String month = null;
        String year = String.valueOf(y);
        if(m > 1)
        {
            if(m > 10){
                month = String.valueOf(m - 1);
            }

            else{
                month = (new StringBuilder()).append("0").append(String.valueOf(m - 1)).toString();
            }

        } else
        {
            year = String.valueOf(y - 1);
            month = String.valueOf(12);
        }
        return (new StringBuilder()).append(year).append("-").append(month).toString();
    }

    public static String getLastDayDate()
    {
        Calendar c = Calendar.getInstance();
        int y = c.get(1);
        int m = c.get(2) + 1;
        int d = c.get(5);
        int days = 0;
        if(m > 1){
            days = getMonthsDays(m - 1, y);
        }else {
            days = 31;
        }

        String day = null;
        String month = null;
        String year = String.valueOf(y);
        if(d > 1)
        {
            day = String.valueOf(d - 1);
            if(m > 9){
                month = String.valueOf(m);
            }
            else{
                month = (new StringBuilder()).append("0").append(String.valueOf(m)).toString();
            }
        } else
        if(d < 2 && m < 2)
        {
            day = String.valueOf(31);
            month = String.valueOf(12);
            year = String.valueOf(y - 1);
        } else
        if(d < 2 && m > 2)
        {
            day = String.valueOf(days);
            if(m > 10){
                month = String.valueOf(m - 1);
            }
            else{
                month = (new StringBuilder()).append("0").append(String.valueOf(m - 1)).toString();
            }
        }
        return (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
    }

    public static boolean isLeapYear(int year)
    {
        return year % 4 == 0 && year % 100 != 0 || year % 4 == 0 && year % 400 == 0;
    }

    public static int getMonthsDays(int month, int year)
    {
        if(isLeapYear(year) && month == 2){
            return 29;
        }

        if(!isLeapYear(year) && month == 2){
            return 28;
        }

        return month != 1 && month != 3 && month != 5 && month != 7 && month != 8 && month != 10 && month != 12 ? 30 : 31;
    }

    public static String getWeekDay()
    {
        DateFormatSymbols symboles = new DateFormatSymbols(Locale.getDefault());
        symboles.setShortWeekdays(new String[] {
                "", "7", "1", "2", "3", "4", "5", "6"
        });
        SimpleDateFormat date = new SimpleDateFormat("E", symboles);
        return date.format(new Date());
    }

    public static int getYear()
    {
        Calendar c = Calendar.getInstance();
        return c.get(1);
    }

    public static int getMonth()
    {
        Calendar c = Calendar.getInstance();
        return c.get(2);
    }

    public static int getDay()
    {
        Calendar c = Calendar.getInstance();
        return c.get(5);
    }

    public static String getLastMonthDay(int lastmonths)
    {
        int month = getMonth() + 1;
        if(month - lastmonths > 0){
            return (new StringBuilder()).append(String.valueOf(getYear())).append("-").append(String.valueOf(month - lastmonths)).append("-1").toString();
        }
        else{
            return (new StringBuilder()).append(String.valueOf(getYear() - 1)).append("-").append(String.valueOf((12 + month) - lastmonths)).append("-1").toString();
        }

    }
}
