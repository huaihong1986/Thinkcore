package com.thinkcore.utils;

/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.SparseArray;

/**
 * @字符串操作工具包
 */
public class TStringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        return str;
    }

    /**
     * 字符串截取
     *
     * @param str
     * @param length
     * @return
     * @throws Exception
     */
    public static String subString(String str, int length) throws Exception {

        byte[] bytes = str.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++) {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1) {
                n++; // 在UCS2第二个字节时n加1
            } else {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0) {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1) {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
                // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }
        return new String(bytes, 0, i, "Unicode");
    }

    /**
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 计算微博内容的长度 1个汉字 == 两个英文字母所占的长度 标点符号区分英文和中文
     *
     * @param c 所要统计的字符序列
     * @return 返回字符序列计算的长度
     */
    public static long calculateWeiboLength(CharSequence c) {

        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int temp = (int) c.charAt(i);
            if (temp > 0 && temp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * 分割字符串
     *
     * @param str       String 原始字符串
     * @param splitsign String 分隔符
     * @return String[] 分割后的字符串数组
     */
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null)
            return null;
        ArrayList<String> al = new ArrayList<String>();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[]) al.toArray(new String[0]);
    }

    /**
     * 替换字符串
     *
     * @param from   String 原始字符串
     * @param to     String 目标字符串
     * @param source String 母字符串
     * @return String 替换后的字符串
     */
    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1) {
            bf.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        bf.append(source);
        return bf.toString();
    }

    /**
     * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlencode(String str) {
        if (str == null) {
            return null;
        }

        return replace("\"", "&quot;", replace("<", "&lt;", str));
    }

    /**
     * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
     *
     * @param str String
     * @return String
     */
    public static String htmldecode(String str) {
        if (str == null) {
            return null;
        }

        return replace("&quot;", "\"", replace("&lt;", "<", str));
    }

    private static final String _BR = "<br/>";

    /**
     * 在页面上直接显示文本内容，替换小于号，空格，回车，TAB
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlshow(String str) {
        if (str == null) {
            return null;
        }

        str = replace("<", "&lt;", str);
        str = replace(" ", "&nbsp;", str);
        str = replace("\r\n", _BR, str);
        str = replace("\n", _BR, str);
        str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
        return str;
    }

    /**
     * 返回指定字节长度的字符串
     *
     * @param str    String 字符串
     * @param length int 指定长度
     * @return String 返回的字符串
     */
    public static String toLength(String str, int length) {
        if (str == null) {
            return null;
        }
        if (length <= 0) {
            return "";
        }
        try {
            if (str.getBytes("GBK").length <= length) {
                return str;
            }
        } catch (Exception ex) {
        }
        StringBuffer buff = new StringBuffer();

        int index = 0;
        char c;
        length -= 3;
        while (length > 0) {
            c = str.charAt(index);
            if (c < 128) {
                length--;
            } else {
                length--;
                length--;
            }
            buff.append(c);
            index++;
        }
        buff.append("...");
        return buff.toString();
    }

    /**
     * 获取url的后缀名
     *
     * @param str
     * @return
     */
    public static String getUrlFileName(String urlString) {
        String fileName = urlString.substring(urlString.lastIndexOf("/"));
        fileName = fileName.substring(1, fileName.length());
        if (fileName.equalsIgnoreCase("")) {
            Calendar c = Calendar.getInstance();
            fileName = c.get(Calendar.YEAR) + "" + c.get(Calendar.MONTH) + ""
                    + c.get(Calendar.DAY_OF_MONTH) + ""
                    + c.get(Calendar.MINUTE);

        }
        return fileName;
    }

    public static String replaceSomeString(String str) {
        String dest = "";
        try {
            if (str != null) {
                str = str.replaceAll("\r", "");
                str = str.replaceAll("&gt;", ">");
                str = str.replaceAll("&ldquo;", "“");
                str = str.replaceAll("&rdquo;", "”");
                str = str.replaceAll("&#39;", "'");
                str = str.replaceAll("&nbsp;", "");
                str = str.replaceAll("<br\\s*/>", "\n");
                str = str.replaceAll("&quot;", "\"");
                str = str.replaceAll("&lt;", "<");
                str = str.replaceAll("&lsquo;", "《");
                str = str.replaceAll("&rsquo;", "》");
                str = str.replaceAll("&middot;", "·");
                str = str.replace("&mdash;", "—");
                str = str.replace("&hellip;", "…");
                str = str.replace("&amp;", "×");
                str = str.replaceAll("\\s*", "");
                str = str.trim();
                str = str.replaceAll("<p>", "\n      ");
                str = str.replaceAll("</p>", "");
                str = str.replaceAll("<div.*?>", "\n      ");
                str = str.replaceAll("</div>", "");
                dest = str;
            }
        } catch (Exception e) {
        }

        return dest;
    }

    /**
     * 清除文本里面的HTML标签
     *
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        Log.v("htmlStr", htmlStr);
        try {
            Pattern p_script = Pattern.compile(regEx_script,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            Pattern p_style = Pattern.compile(regEx_style,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            Pattern p_html = Pattern.compile(regEx_html,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
        } catch (Exception e) {
        }

        return htmlStr; // 返回文本字符串
    }

    public static String delSpace(String str) {
        if (str != null) {
            str = str.replaceAll("\r", "");
            str = str.replaceAll("\n", "");
            str = str.replace(" ", "");
        }
        return str;
    }

    /**
     * 检查字符串是否存在值，如果为true,
     *
     * @param str 待检验的字符串
     * @return 当 str 不为 null 或 "" 就返回 true
     */
    public static boolean isNotNull(String str) {
        return (str != null && !"".equalsIgnoreCase(str.trim()));
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static String trimmy(String str) {
        String dest = "";
        if (str != null) {
            str = str.replaceAll("-", "");
            str = str.replaceAll("\\+", "");
            dest = str;
        }
        return dest;
    }

    // 去除空格
    public static String replaceBlank(String str) {

        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\r");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 对象转Double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static Double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    public static Float toFloat(String obj) {
        try {
            return Float.parseFloat(obj);
        } catch (Exception e) {
        }
        return 0F;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断是不是合法手机 handset 手机号码
     */
    public static boolean isHandset(String handset) {
        try {
            if (!handset.substring(0, 1).equals("1")) {
                return false;
            }
            if (handset == null || handset.length() != 11) {
                return false;
            }
            String check = "^[0123456789]+$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(handset);
            boolean isMatched = matcher.matches();
            if (isMatched) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 判断输入的字符串是否为纯汉字
     *
     * @param str 传入的字符窜
     * @return 如果是纯汉字返回true, 否则返回false
     */
    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }

    // 同上
    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    /**
     * 判断不能含有中文
     *
     * @param str 传入的字符窜
     */
    public static boolean isChontainsChinese(String s) {
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i)))
                return true;
        }

        return false;
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str 传入的字符串
     * @return
     */
    public static String replaceBlankOther(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }

    /**
     * 过滤数字以外的字符
     *
     * @param str 传入的字符串
     */
    public static String filterUnNumber(String str) {
        String regEx = "[^0-9]"; // 只允数字
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim(); // 替换与模式匹配的所有字符（即非数字的字符将被""替换）
    }

    /**
     * 返回标准的电话格式
     *
     * @param str 传入的字符串
     */
    public static String getPhoneNumberStandardization(String phoneNumber) {
        String standardization = phoneNumber.trim();
        standardization = standardization.replace("+86", "");
        standardization = standardization.replace(" ", "");
        standardization = TStringUtils.filterUnNumber(standardization);
        return standardization;
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     * @return 是浮点数返回true, 否则返回false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否为空白,包括null和""
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断是否是指定长度的字符串
     *
     * @param text   字符串
     * @param lenght 自定的长度
     * @return
     */
    public static boolean isLenghtStrLentht(String text, int lenght) {
        if (text.length() <= lenght)
            return true;
        else
            return false;
    }

    /**
     * 是否是短信的长度
     *
     * @param text
     * @return
     */
    public static boolean isSMSStrLentht(String text) {
        if (text.length() <= 70)
            return true;
        else
            return false;
    }

    /**
     * 判断手机号码是否正确
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        phoneNumber = trimmy(phoneNumber);
        TMobileFormatUtils mobile = new TMobileFormatUtils(phoneNumber);
        return mobile.isLawful();
    }

    // 判断是否为url
    public static boolean checkEmail(String email) {

        Pattern pattern = Pattern
                .compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    // 判断是否为url
    public static boolean checkWebUrl(String url) {
        Pattern pattern = Pattern
                .compile("^(http|https|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$");
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    // 判断微博分享是否为是否为120个
    public static boolean isShareStrLentht(String text, int lenght) {
        if (text.length() <= 120)
            return true;
        else
            return false;
    }

    public static String getFileNameFromUrl(String url) {

        // 名字不能只用这个
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        String extName = "";
        String filename;
        int index = url.lastIndexOf('?');
        if (index > 1) {
            extName = url.substring(url.lastIndexOf('.') + 1, index);
        } else {
            extName = url.substring(url.lastIndexOf('.') + 1);
        }
        filename = hashKeyForDisk(url) + "." + extName;
        return filename;
        /*
         * int index = url.lastIndexOf('?'); String filename; if (index > 1) {
		 * filename = url.substring(url.lastIndexOf('/') + 1, index); } else {
		 * filename = url.substring(url.lastIndexOf('/') + 1); }
		 * 
		 * if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
		 * filename = UUID.randomUUID() + ".apk";// 默认取一个文件名 } return filename;
		 */
    }

    /**
     * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 时间转数字
     *
     * @param String sourceTime
     * @param String
     * @return long
     */
    public static long string2long(String sourceTime,
                                   SimpleDateFormat dateFormat) {
        long longTime = 0L;
        Date d = null;
        try {
            d = dateFormat.parse(sourceTime);
            longTime = d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        d = null;
        return longTime;
    }

    /**
     * 数字转时间
     *
     * @param long   longTime
     * @param String dataFormat
     * @return String
     */
    public static String long2String(long longTime, SimpleDateFormat dateFormat) {
        String result = "";
        Date date = null;
        try {
            date = new Date(longTime);
            result = dateFormat.format(date);
        } catch (Exception e) {
        }
        date = null;
        return result;
    }

    /**
     * 把单个英文字母或者字符串转换成数字ASCII码
     *
     * @param String dataFormat
     * @return int
     */
    public static int character2ASCII(String input) { //
        char[] temp = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char each : temp) {
            builder.append((int) each);
        }
        String result = builder.toString();
        return Integer.parseInt(result);
    }

    /**
     * 字节码转换（宽字节-》多字节）
     */
    public static char ascii2Char(int ASCII) {
        return (char) ASCII;
    }

    public static int char2ASCII(char c) {
        return (int) c;
    }

    public static String ascii2String(int[] ASCIIs) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ASCIIs.length; i++) {
                sb.append((char) ascii2Char(ASCIIs[i]));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return "";
    }

    public static String ascii2String(String ASCII) {
        try {
            String[] ASCIIss = ASCII.split(",");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ASCIIss.length; i++) {
                sb.append((char) ascii2Char(Integer.parseInt(ASCIIss[i])));
            }
            return sb.toString();
        } catch (Exception e) {
            return ASCII;
        }
    }

    public static String string2ASCIIString(String s) {// 字符串转换为ASCII码
        try {
            return getIntArrayString(string2ASCII(s));
        } catch (Exception e) {
            return s;
        }
    }

    public static int[] string2ASCII(String s) {// 字符串转换为ASCII码
        if (s == null || "".equals(s)) {
            return null;
        }

        char[] chars = s.toCharArray();
        int[] asciiArray = new int[chars.length];

        for (int i = 0; i < chars.length; i++) {
            asciiArray[i] = char2ASCII(chars[i]);
        }
        return asciiArray;
    }

    public static String[] HexArray(int[] intArray) {
        String[] hexArray = new String[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            hexArray[i] = Integer.toHexString(intArray[i]).toUpperCase();
        }
        return hexArray;
    }

    public static String getIntArrayString(int[] intArray) {
        return getIntArrayString(intArray, ",");
    }

    public static String getIntArrayString(int[] intArray, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intArray.length; i++) {
            sb.append(intArray[i]);
            if (i < intArray.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static String getASCII(int begin, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = begin; i < end; i++) {
            sb.append(i).append(":").append((char) i).append("/t");
            if (i % 10 == 0) {
                sb.append("/n");
            }
        }
        return sb.toString();
    }

    public static String getCHASCII(int begin, int end) {
        return getASCII(19968, 40869);
    }

    /**
     * Utility method used for debugging purposes, writes the content of a
     * SparseArray to a String.
     *
     * @param sparseArray
     * @return "{ key1 => value1, key2 => value2, ...}"
     */
    public static String sparseArrayToString(SparseArray<?> sparseArray) {
        StringBuilder result = new StringBuilder();
        if (sparseArray == null) {
            return "null";
        }

        result.append('{');
        for (int i = 0; i < sparseArray.size(); i++) {
            result.append(sparseArray.keyAt(i));
            result.append(" => ");
            if (sparseArray.valueAt(i) == null) {
                result.append("null");
            } else {
                result.append(sparseArray.valueAt(i).toString());
            }
            if (i < sparseArray.size() - 1) {
                result.append(", ");
            }
        }
        result.append('}');
        return result.toString();
    }

    public static String stripTrailingSlash(String s) {
        if (s.endsWith("/") && s.length() > 1)
            return s.substring(0, s.length() - 1);
        return s;
    }

    static boolean StartsWith(String[] array, String text) {
        for (String item : array)
            if (text.startsWith(item))
                return true;
        return false;
    }

    /**
     * Convert time to a string
     *
     * @param millis e.g.time/length from file
     * @return formated string (hh:)mm:ss
     */
    public static String millisToString(long millis) {
        return millisToString(millis, false);
    }

    /**
     * Convert time to a string
     *
     * @param millis e.g.time/length from file
     * @return formated string "[hh]h[mm]min" / "[mm]min[s]s"
     */
    public static String millisToText(long millis) {
        return millisToString(millis, true);
    }

    static String millisToString(long millis, boolean text) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);

        millis /= 1000;
        int sec = (int) (millis % 60);
        millis /= 60;
        int min = (int) (millis % 60);
        millis /= 60;
        int hours = (int) millis;

        String time;
        DecimalFormat format = (DecimalFormat) NumberFormat
                .getInstance(Locale.US);
        format.applyPattern("00");
        if (text) {
            if (millis > 0)
                time = (negative ? "-" : "") + hours + "h" + format.format(min)
                        + "min";
            else if (min > 0)
                time = (negative ? "-" : "") + min + "min";
            else
                time = (negative ? "-" : "") + sec + "s";
        } else {
            if (millis > 0)
                time = (negative ? "-" : "") + hours + ":" + format.format(min)
                        + ":" + format.format(sec);
            else
                time = (negative ? "-" : "") + min + ":" + format.format(sec);
        }
        return time;
    }

    /**
     * Get the formatted current playback speed in the form of 1.00x
     */
    public static String formatRateString(float rate) {
        return String.format(Locale.US, "%.2fx", rate);
    }

    public static String makeHtmlStr(String text, String color) {
        return "<font color=\"" + color + "\">" + text + "</font>";
    }

    public static String makeHtmlStr(String text, int color) {
        String strColor = String.valueOf(color);
        return makeHtmlStr(text, strColor);
    }

    public static Spanned getColorfulString(String color, String content,
                                            String rightContent, String leftContent) {
        return Html.fromHtml(rightContent + "<font color=\"" + color + "\">"
                + content + "</font>" + leftContent);
    }

    private static String delEmpty(String IP) {// 去掉IP字符串前后所有的空格
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }

    public static boolean isIp(String IP) {// 判断是否是一个IP
        boolean b = false;
        IP = delEmpty(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String s[] = IP.split("\\.");
            if (Integer.parseInt(s[0]) < 255)
                if (Integer.parseInt(s[1]) < 255)
                    if (Integer.parseInt(s[2]) < 255)
                        if (Integer.parseInt(s[3]) < 255)
                            b = true;
        }
        return b;
    }

    /*
     * 手机号验证
     *
     * @param str
     *
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isLandline(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)"); // 验证固定电话
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    //身份证
    public static boolean isPersion(String str) {
        Pattern p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //非法字符
    public static boolean isString(String str) {
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;'\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’？]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    //非法字符
    public static boolean isName(String str) {
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;'\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’？-]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    //非法字符
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }
}