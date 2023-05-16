/*
 * Copyright (C), 2002-2012, 苏宁易购电子商务有限公司
 * FileName: PayCoreSessionBean.java
 * Author:   Administrator
 * Date:     2012-12-12 上午11:54:53
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

    private static final String MONEY_NUMBER_PATTERN = "#0.00";

    private static final BigDecimal BIGDECIMAL_HUNDRED = new BigDecimal(100);

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    private StringUtil() {

    }

    /**
     * 
     * 功能描述: 敏感信息隐藏<br>
     * 〈功能详细描述〉
     * 
     * @param str 字符串
     * @param begin
     * @param end
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String hidingSensitiveBit(String value, int begin, int end) {

        // 空不隐位
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        // 字段长度小于不需要隐位的长度则不隐位
        value = value.trim();
        int stringLength = value.length();
        if (stringLength < (begin + end)) {
            return value;
        }

        // 用*替换隐藏的字符串
        String hiddenString = "";
        for (int i = begin; i < stringLength - end; i++) {
            hiddenString += '*';
        }

        String displayStringStrat = value.substring(0, begin);
        String displayStringEnd = value.substring(stringLength - end, stringLength);

        return displayStringStrat + hiddenString + displayStringEnd;
    }

    /**
     * 不为空验证 为空返回false 不为空返回true
     * 
     * @param obj
     * @return 为空返回false，不为空返回true。
     */
    public static boolean isNotNull(Object obj) {
        return (null == obj || "".equals(String.valueOf(obj).trim())) ? false : true;
    }

    /**
     * 为空验证 只要有一个为空就返回true 都不为空则返回false
     * 
     * @param objs
     * @return 只要有一个为空就返回true，都不为空则返回false。
     */
    public static boolean isNull(Object... objs) {
        if (null == objs) {
            return true;
        }
        for (Object obj : objs) {
            if (null == obj || "".equals(String.valueOf(obj).trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述: <br>
     * 〈是否为null〉
     * 
     * @param strings
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isNull(String... strings) {
        if (strings == null) {
            return true;
        }

        for (String one : strings) {
            if (one == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述: <br>
     * 〈是否为空〉
     * 
     * @param strings
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isEmpty(String... strings) {
        if (strings == null) {
            return true;
        }

        for (String one : strings) {
            if (one == null || "".equals(one.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述: <br>
     * 〈去除空格〉
     * 
     * @param value
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String trim(String value) {
        if (value == null) {
            return "";
        } else {
            return value.trim();
        }
    }

    /**
     * 功能描述: <br>
     * 〈判断是否空,并校验长度〉
     * 
     * @param value
     * @param len
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean checkNullAndLengthOver(String value, int len) {
        if (value == null) {
            return false;
        } else {
            return value.trim().length() <= len && value.trim().length() > 0 ? true : false;
        }
    }

    /**
     * 功能描述: <br>
     * 〈判断身份证号码是否空,并校验长度是否为18位〉
     * 
     * @param value
     * @param len
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean checkNullAndLengthOfCardId(String value, int len) {
        if (value == null) {
            return false;
        } else {
            return value.trim().length() == len ? true : false;
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 〈获取文件,截取后缀名(输入参数test.txt,返回test)〉
     * 
     * @param fileName
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getFileName(String fileName) {

        if (!isEmpty(fileName) && fileName.contains(".")) {

            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }

        return fileName;
    }

    /**
     * 根据要求字符串长度，数字左补零
     * 
     * @author YuanBing
     * @param data
     * @param length
     * @return
     */
    public static String leftFillZero(String s, int length) {
        if (StringUtil.isEmpty(s) || length <= 0) {
            return "";
        }
        if (s.length() == length) {
            return s;
        }
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < length) {
            sb.append("0");
            i++;
        }
        sb.append(s);
        return sb.substring(sb.length() - length);
    }

    /**
     * 格式化钱（如：2000L--20.00）
     * 
     * @param money
     * @return
     */
    public static String formatMoney(Long money) {
        if (money == null || "".equals(String.valueOf(money).trim())) {
            return "0.00";
        }
        BigDecimal bMoney = new BigDecimal(money);
        BigDecimal result = bMoney.divide(BIGDECIMAL_HUNDRED);
        DecimalFormat formatter = new DecimalFormat(MONEY_NUMBER_PATTERN);
        return formatter.format(result);
    }

    /**
     * 将string 格式的钱转换为long（如："20.00"--2000L） 输入格式必须为"20.00"
     * 
     * @param money
     * @return
     */
    public static Long moneyToLong(String money) {
        if (isEmpty(money)) {
            return null;
        }
        BigDecimal moneyYuan = new BigDecimal(money);
        BigDecimal moneyF = moneyYuan.multiply(BIGDECIMAL_HUNDRED);
        return moneyF.longValue();
    }

    /**
     * 功能描述: <br>
     * 〈验证字符串长度〉
     * 
     * <pre>
     *    value='222',maxlength=3;return true.
     *    value='222',maxlength=2;return false.
     *    value= null,maxlength=3;return true.
     * </pre>
     * 
     * @param value
     * @param maxLength
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean validateStrLength(String strParameter, int limitLength) {
        return isEmpty(strParameter) || strParameter.length() <= limitLength;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈查看某个日期字符串是否满足格式〉
     * 
     * @param str
     * @param pattern
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isFormatMatching(String dateString, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 功能描述: <br>
     * 〈转换成String〉
     * 
     * @param value
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String toString(Object value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    public static Boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String regEx = "^([1-9]\\d*|0)(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 功能描述: <br>
     * 〈是否全部为空〉
     * 
     * @param strings
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean allEmpty(String... strings) {
        if (strings == null) {
            return true;
        }

        for (String one : strings) {
            if (one != null && !"".equals(one.trim())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 是否为中文
     * @param name
     * @return
     */
    public static boolean isChinese(String str)
    {
        int n = 0;
        for(int i = 0; i < str.length(); i++) {
            n = (int)str.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断身份证格式是否符合
     */
    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression =
                "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9X]$)|"
                        + "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        boolean matches = IDNumber.matches(regularExpression);
        // 判断第18位校验值
        if (matches) {
            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    // 前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    // 这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase()
                            .equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        LOGGER.error("身份证最后一位:{}错误,正确的应该是:{}," ,String.valueOf(idCardLast).toUpperCase(), idCardY[idCardMod].toUpperCase());
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase()
                                + "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        }
        return matches;
    }

    public static List<String> StringTOList(String str) {
        if (StringUtil.isEmpty(str)) {
            return Collections.emptyList();
        }
        String[] array = StringUtils.split(str, ",");
        List<String> result = new ArrayList<String>(array.length);
        Collections.addAll(result, array);
        return result;
    }

    /**
     *
     * 功能描述: 判断字符长度 中文占两个字符<br>
     * 〈功能详细描述〉
     *
     * @param value
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
