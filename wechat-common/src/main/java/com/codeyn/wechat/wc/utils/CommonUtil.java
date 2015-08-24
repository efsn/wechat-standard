package com.codeyn.wechat.wc.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class CommonUtil{

    public static final int DEFAULT_PAGE_NO = 1;

    public static final int DEFAULT_PAGE_SIZE = 10;

    // 短信默认时间
    public static final Integer DEFAULT_CODE_EXPTIME = 3;
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 时间格式化统一使用该处
     */
    public static String formatDate(Date date){
        return DateFormatUtils.format(date, DATE_PATTERN);
    }

    public static Date parseDate(String date, String... patterns) throws ParseException{
        if (patterns == null || patterns.length < 1) {
            patterns = new String[] {DATE_PATTERN};
        }
        return DateUtils.parseDate(date, patterns);
    }

    /**
     * 时分秒转long
     * 
     * @param time
     * @return
     */
    // HH:mm:ss
    public static long timeToLong(String time){
        String[] my = time.split(":");
        int hour = Integer.parseInt(my[0]);
        int min = Integer.parseInt(my[1]);
        int sec = my.length == 3 ? sec = Integer.parseInt(my[2]) : 0;

        return hour * 3600 + min * 60 + sec;
    }

    public static long timeToLong(){
        return timeToLong(DateFormatUtils.format(new Date(), TIME_PATTERN));
    }

    /**
     * 获取分页
     * 
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static int[] getPager(Integer pageNo, Integer pageSize){
        // 默认20条一页
        pageSize = (pageSize == null || pageSize <= 0) ? 20 : pageSize;

        // 默认页码
        pageNo = (pageNo == null || pageNo <= 0) ? 1 : pageNo;

        // 计算开始位置
        Integer start = (pageNo - 1) * pageSize;

        return new int[] {start, pageSize};
    }

    /**
     * 获取子列表
     */
    public static <T> List<T> getSubList(List<T> list, int start, int limit){
        if (list == null || list.isEmpty()) {
            return new ArrayList<T>(0);
        }

        int listLength = list.size();
        if (start > listLength - 1) {
            return new ArrayList<T>();
        }

        limit = start + limit > listLength ? listLength - start : limit;
        return list.subList(start, start + limit);
    }

    /**
     * 将字符串按分隔符分割并转成整型列表
     */
    public static List<Integer> str2IntList(String ids, String separator){
        List<Integer> idList = new ArrayList<Integer>();
        if (StringUtils.isBlank(ids)) {
            return idList;
        }

        String[] arrId = ids.split("\\" + separator);
        for (String id : arrId) {
            idList.add(Integer.parseInt(id));
        }
        return idList;
    }

    /**
     * sting 转int
     */
    public static Integer toInt(String value, Integer defaultValue){
        if (value == null || "".equals(value.trim())) return defaultValue;
        if (value.startsWith("N") || value.startsWith("n")) return -Integer.parseInt(value.substring(1));
        return Integer.parseInt(value);
    }

    public static String substring(String str, int toCount, String more) throws Exception{
        int reInt = 0;
        String reStr = "";
        if (str == null) return "";
        char[] tempChar = str.toCharArray();
        for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
            String s1 = String.valueOf(tempChar[kk]);
            byte[] b = s1.getBytes();
            reInt += b.length;
            reStr += tempChar[kk];
        }
        if (toCount == reInt || (toCount == reInt - 1)) reStr += more;
        return reStr;
    }

}
