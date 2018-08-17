package com.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Map;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 16:35 2018/8/17 .
 */
public class StringUtil {

    private StringUtil()
    {
    }

    public static boolean isNull(Object object)
    {
        if(object instanceof String){
            return isEmpty(object.toString());
        }
        else{
            return object == null;
        }

    }

    public static boolean isEmpty(String value)
    {
        return value == null || value.trim().length() == 0 || "null".endsWith(value);
    }

    public static String null2String(Object obj)
    {
        return obj != null ? obj.toString() : "";
    }

    public static String null2String(String str)
    {
        return str != null ? str : "";
    }

    public static String iso2Gb(String gbString)
    {
        if(gbString == null){
            return null;
        }

        String outString = "";
        try
        {
            byte temp[] = null;
            temp = gbString.getBytes("ISO8859-1");
            outString = new String(temp, "GB2312");
        }
        catch(UnsupportedEncodingException e) { }
        return outString;
    }

    public static String iso2Utf(String isoString)
    {
        if(isoString == null){
            return null;
        }

        String outString = "";
        try
        {
            byte temp[] = null;
            temp = isoString.getBytes("ISO8859-1");
            outString = new String(temp, "UTF-8");
        }
        catch(UnsupportedEncodingException e) { }
        return outString;
    }

    public static String str2Gb(String inString)
    {
        if(inString == null){
            return null;
        }

        String outString = "";
        try
        {
            byte temp[] = null;
            temp = inString.getBytes();
            outString = new String(temp, "GB2312");
        }
        catch(UnsupportedEncodingException e) { }
        return outString;
    }

    public static String fillZero(String dealCode)
    {
        String zero = "";
        if(dealCode.length() < 3)
        {
            for(int i = 0; i < 3 - dealCode.length(); i++){
                zero = (new StringBuilder()).append(zero).append("0").toString();
            }


        }
        return (new StringBuilder()).append(zero).append(dealCode).toString();
    }

    public static String fillZero(String value, int len)
    {
        if(isNull(value) || len <= 0){
            throw new IllegalArgumentException("\u53C2\u6570\u4E0D\u6B63\u786E");
        }

        String zero = "";
        int paramLen = value.trim().length();
        if(paramLen < len)
        {
            for(int i = 0; i < len - paramLen; i++){
                zero = (new StringBuilder()).append(zero).append("0").toString();
            }


        }
        return (new StringBuilder()).append(zero).append(value).toString();
    }

    public static String convertAmount(String amount)
    {
        String str = String.valueOf(Double.parseDouble(amount));
        int pos = str.indexOf(".");
        int len = str.length();
        if(len - pos < 3){
            return (new StringBuilder()).append(str.substring(0, pos + 2)).append("0").toString();
        }

        else{
            return str.substring(0, pos + 3);
        }

    }

    public static String to10(String opStr)
    {
        String zm = "#123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int lenOfOp = opStr.length();
        long result = 0L;
        for(int i = 0; i < lenOfOp; i++)
        {
            String indexOfOp = opStr.substring(i, i + 1);
            int js = zm.indexOf(indexOfOp);
            result = result * 36L + (long)js;
        }

        String jg = String.valueOf(result);
        int bc = 12 - jg.length();
        String jgq = "";
        for(int j = 0; j < bc; j++){
            jgq = (new StringBuilder()).append(jgq).append("0").toString();
        }


        return (new StringBuilder()).append(jgq).append(jg).toString();
    }

    public static String to36(String originalStr)
    {
        long oVal = Long.parseLong(originalStr);
        String result = "";
        String zm = "#123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i = 1; i < 9; i++)
        {
            long shang = oVal / 36L;
            int yushu = (int)(oVal % 36L);
            result = (new StringBuilder()).append(zm.substring(yushu, yushu + 1)).append(result).toString();
            oVal = shang;
        }

        return result;
    }

    public static String to36shortly(String originalStr)
    {
        long oVal = Long.parseLong(originalStr);
        String result = "";
        String zm = "123456789ACDEFGHJKLMNPRTUVWXY";
        for(int i = 1; i < 9; i++)
        {
            long shang = oVal / 36L;
            int yushu = (int)(oVal % 29L);
            result = (new StringBuilder()).append(zm.substring(yushu, yushu + 1)).append(result).toString();
            oVal = shang;
        }

        return result;
    }

    public static String encDealId(String dealid)
    {
        if(dealid.length() != 23)
        {
            return "notval";
        } else
        {
            String ny = dealid.substring(5, 7);
            String sq = dealid.substring(11, 21);
            return to36((new StringBuilder()).append(ny).append(sq).toString());
        }
    }

    public static String decDealId(String opStr)
    {
        return to10(opStr);
    }

    public static String[] numToWords(String money)
    {
        int j = money.length();
        String str[] = new String[j];
        for(int i = 0; i < j; i++)
            switch(money.charAt(i))
            {
                case 48: // '0'
                    str[i] = "\u96F6";
                    break;

                case 49: // '1'
                    str[i] = "\u58F9";
                    break;

                case 50: // '2'
                    str[i] = "\u8D30";
                    break;

                case 51: // '3'
                    str[i] = "\u53C1";
                    break;

                case 52: // '4'
                    str[i] = "\u8086";
                    break;

                case 53: // '5'
                    str[i] = "\u4F0D";
                    break;

                case 54: // '6'
                    str[i] = "\u9646";
                    break;

                case 55: // '7'
                    str[i] = "\u67D2";
                    break;

                case 56: // '8'
                    str[i] = "\u634C";
                    break;

                case 57: // '9'
                    str[i] = "\u7396";
                    break;

                case 46: // '.'
                    str[i] = "\u70B9";
                    break;
            }

        return str;
    }

    public static String money2BigFormat(String money)
    {
        String bigNumber[] = numToWords(money);
        int len = bigNumber.length;
        if(len > 11){
            return "\u6570\u989D\u8FC7\u9AD8";
        }

        StringBuffer sb = new StringBuffer();
        if(len >= 7)
        {
            if(len == 11)
            {
                sb.append((new StringBuilder()).append(bigNumber[0]).append("\u4EDF").toString());
                sb.append((new StringBuilder()).append(bigNumber[1]).append("\u4F70").append(bigNumber[2]).append("\u62FE").append(bigNumber[3]).append("\u4E07").toString());
            }
            if(len == 10){
                sb.append((new StringBuilder()).append(bigNumber[0]).append("\u4F70").append(bigNumber[1]).append("\u62FE").append(bigNumber[2]).append("\u4E07").toString());
            }

            if(len == 9){
                sb.append((new StringBuilder()).append(bigNumber[0]).append("\u62FE").append(bigNumber[1]).append("\u4E07").toString());
            }

            if(len == 8){
                sb.append((new StringBuilder()).append(bigNumber[0]).append("\u4E07").toString());
            }

            sb.append((new StringBuilder()).append(bigNumber[len - 7]).append("\u4EDF").append(bigNumber[len - 6]).append("\u4F70").append(bigNumber[len - 5]).append("\u62FE").toString());
        }
        if(len == 6){
            sb.append((new StringBuilder()).append(bigNumber[0]).append("\u4F70").append(bigNumber[1]).append("\u62FE").toString());
        }

        if(len == 5){
            sb.append((new StringBuilder()).append(bigNumber[0]).append("\u62FE").toString());
        }

        sb.append((new StringBuilder()).append(bigNumber[len - 4]).append("\u5143").append(bigNumber[len - 2]).append("\u89D2").append(bigNumber[len - 1]).append("\u5206\u6574").toString());
        return sb.toString();
    }

    public static String formatCurrecy(String currency)
    {
        if(null == currency || "".equals(currency) || "null".equals(currency)){
            return "";
        }

        NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        try
        {
            return usFormat.format(Double.parseDouble(currency));
        }
        catch(Exception e)
        {
            return "";
        }
    }

    public static String formatCurrecy(String currency, String currencyCode)
    {
        try
        {
            if(null == currency || "".equals(currency) || "null".equals(currency)){
                return "";
            }

        }
        catch(Exception e)
        {
            return "";
        }
        if(currencyCode.equalsIgnoreCase("1"))
        {
            NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
            return usFormat.format(Double.parseDouble(currency));
        }
        return (new StringBuilder()).append(currency).append("\u70B9").toString();
    }

    public static String[] split(String str)
    {
        return split(str, null, -1);
    }

    public static String[] split(String text, String separator)
    {
        return split(text, separator, -1);
    }

    public static String[] split(String str, String separator, int max)
    {
        StringTokenizer tok = null;
        if(separator == null){
            tok = new StringTokenizer(str);
        }

        else{
            tok = new StringTokenizer(str, separator);
        }

        int listSize = tok.countTokens();
        if(max > 0 && listSize > max){
            listSize = max;
        }

        String list[] = new String[listSize];
        int i = 0;
        int lastTokenBegin = 0;
        int lastTokenEnd = 0;
        do
        {
            if(!tok.hasMoreTokens()){
                break;
            }

            if(max > 0 && i == listSize - 1)
            {
                String endToken = tok.nextToken();
                lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
                list[i] = str.substring(lastTokenBegin);
                break;
            }
            list[i] = tok.nextToken();
            lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
            lastTokenEnd = lastTokenBegin + list[i].length();
            i++;
        } while(true);
        return list;
    }

    public static String replace(String text, String repl, String with)
    {
        return replace(text, repl, with, -1);
    }

    private static String replace(String text, String repl, String with, int max)
    {
        if(text == null){
            return null;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = text.indexOf(repl, start);
        do
        {
            if(end == -1){
                break;
            }

            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();
            if(--max == 0){
                break;
            }

            end = text.indexOf(repl, start);
        } while(true);
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String replaceVariable(String src, Map value)
    {
        int len = src.length();
        StringBuffer buf = new StringBuffer(len);
        for(int i = 0; i < len; i++)
        {
            char c = src.charAt(i);
            if(c == '$')
            {
                i++;
                StringBuffer key = new StringBuffer();
                for(char temp = src.charAt(i); temp != '}'; temp = src.charAt(i))
                {
                    if(temp != '{'){
                        key.append(temp);
                    }

                    i++;
                }

                String variable = (String)value.get(key.toString());
                if(null == variable){
                    buf.append("");
                }

                else{
                    buf.append(variable);
                }

            } else
            {
                buf.append(c);
            }
        }

        return buf.toString();
    }

    public static String utfToGBK(byte srcByte[])
            throws Exception
    {
        StringBuffer str = new StringBuffer();
        int len = srcByte.length;
        int count = 0;
        do
        {
            if(count >= len){
                break;
            }

            int char1 = srcByte[count] & 255;
            switch(char1 >> 4)
            {
                case 0: // '\0'
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                case 4: // '\004'
                case 5: // '\005'
                case 6: // '\006'
                case 7: // '\007'
                {
                    count++;
                    str.append((char)char1);
                    break;
                }

                case 12: // '\f'
                case 13: // '\r'
                {
                    if((count += 2) > len){
                        throw new Exception();
                    }

                    int char2 = srcByte[count - 1];
                    if((char2 & 192) != 128){
                        throw new Exception();
                    }

                    str.append((char)((char1 & 31) << 6 | char2 & 63));
                    break;
                }

                case 14: // '\016'
                {
                    if((count += 3) > len){
                        throw new Exception();
                    }

                    int char2 = srcByte[count - 2];
                    int char3 = srcByte[count - 1];
                    if((char2 & 192) != 128 || (char3 & 192) != 128){
                        throw new Exception();
                    }

                    str.append((char)((char1 & 15) << 12 | (char2 & 63) << 6 | (char3 & 63) << 0));
                    break;
                }

                case 8: // '\b'
                case 9: // '\t'
                case 10: // '\n'
                case 11: // '\013'
                default:
                {
                    throw new Exception();
                }
            }
        } while(true);
        return str.toString();
    }

    public static byte[] getUTFBytes(String s, String charset)
    {
        try
        {
            int pos = 0;
            if(charset != null){
                s = new String(s.getBytes(), charset);
            }

            char cc[] = s.toCharArray();
            byte buf[] = new byte[cc.length * 3];
            for(int i = 0; i < cc.length; i++)
            {
                char c = cc[i];
                if(c <= '\177' && c != 0)
                {
                    buf[pos++] = (byte)c;
                    continue;
                }
                if(c > '\u07FF')
                {
                    buf[pos + 2] = (byte)(128 | c >> 0 & 63);
                    buf[pos + 1] = (byte)(128 | c >> 6 & 63);
                    buf[pos + 0] = (byte)(224 | c >> 12 & 15);
                    pos += 3;
                } else
                {
                    buf[pos + 1] = (byte)(128 | c >> 0 & 63);
                    buf[pos + 0] = (byte)(192 | c >> 6 & 31);
                    pos += 2;
                }
            }

            byte tmp[] = new byte[pos];
            for(int i = 0; i < pos; i++){
                tmp[i] = buf[i];
            }


            return tmp;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public static int utfLength(String value)
    {
        int utflen = 0;
        char val[] = value.toCharArray();
        for(int i = 0; i < value.length(); i++)
        {
            int c = val[i];
            if(c >= 1 && c <= 127)
            {
                utflen++;
                continue;
            }
            if(c > 2047){
                utflen += 3;
            }

            else{
                utflen += 2;
            }

        }

        return utflen;
    }

    public static String couponIdGenerator(String src)
    {
        String srcTemp = null;
        String dst = null;
        int iSrc = 0;
        if(src == null)
            srcTemp = "        ";
        else
        if(src.equals("")){
            srcTemp = "        ";
        }

        else{
            srcTemp = src;
        }

        for(int i = 0; i < srcTemp.length(); i++){
            iSrc += (249 - i) * srcTemp.charAt(i);
        }


        dst = (new StringBuilder()).append(iSrc).append("").toString();
        return dst;
    }

    public static String getAliasName(String name)
    {
        StringBuilder sb = new StringBuilder("SonicJMSRASubcontext/Sonic_");
        String token;
        for(StringTokenizer st = new StringTokenizer(name, "."); st.hasMoreTokens(); sb.append(first2Upper(token))){
            token = st.nextToken();
        }


        return sb.toString();
    }

    public static String first2Upper(String str)
    {
        return (new StringBuilder()).append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
    }

    public static final String bytesToHexStr(byte bcd[])
    {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        for(int i = 0; i < bcd.length; i++)
        {
            s.append(bcdLookup[bcd[i] >>> 4 & 15]);
            s.append(bcdLookup[bcd[i] & 15]);
        }

        return s.toString();
    }

    public static final byte[] hexStrToBytes(String s)
    {
        byte bytes[] = new byte[s.length() / 2];
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);

        return bytes;
    }

    public static String hexString(byte b[])
    {
        StringBuffer d = new StringBuffer(b.length * 2);
        for(int i = 0; i < b.length; i++)
        {
            char hi = Character.forDigit(b[i] >> 4 & 15, 16);
            char lo = Character.forDigit(b[i] & 15, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }

        return d.toString();
    }

    public static byte[] hex2byte(byte b[], int offset, int len)
    {
        byte d[] = new byte[len];
        for(int i = 0; i < len * 2; i++)
        {
            int shift = i % 2 != 1 ? 4 : 0;
            d[i >> 1] |= Character.digit((char)b[offset + i], 16) << shift;
        }

        return d;
    }

    public static byte[] hex2byte(String s)
    {
        return hex2byte(s.getBytes(), 0, s.length() >> 1);
    }

    public static String arrayToDelimitedString(Object arr[], String delim)
    {
        if(arr == null || arr.length == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < arr.length; i++)
        {
            if(i > 0)
                sb.append(delim);
            sb.append('\'');
            sb.append(arr[i]);
            sb.append('\'');
        }

        return sb.toString();
    }

    public static String arrayToStr(Object arr[], char split)
    {
        if(arr == null || arr.length == 0)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < arr.length; i++)
        {
            if(i > 0)
                sb.append(split);
            sb.append(arr[i]);
        }

        return sb.toString();
    }

    private static final char bcdLookup[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };
}
