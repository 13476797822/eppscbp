package com.suning.epp.eppscbp.util;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.CoreErrorCode;
import com.suning.epp.pu.common.aop.exception.FrameException;


/**
 * 〈一句话功能简述〉<br> 
 * 〈时间操作工具类〉  
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 14070519
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    
    private static final int MILLIS_PER_DAY = DateTimeConstants.MILLIS_PER_DAY - 1;

    static final DateFormat YYYYMMDDHHMMSS_FORMAT_LOST = new java.text.SimpleDateFormat(DateConstant.DATEFORMATE_YYYYMMDDHHMMSS);

    static final DateTimeFormatter YYYYMMDDHHMMSS_FORMAT_FAST = DateTimeFormat.forPattern(DateConstant.DATEFORMATEyyyyMMddHHmmss);

    static final DateFormat YYYYMMDD_FORMAT = new java.text.SimpleDateFormat(DateConstant.DATEFORMATE_YYYYMMDD);

    /**
     * 线程安全
     */
    static final DateTimeFormatter YYYYMMDD_FORMAT_FAST = DateTimeFormat.forPattern(DateConstant.DATEFORMATE_YYYYMMDD);

    static final DateTimeFormatter YYYY_MM_DD_HHMMSSSSS_FORMAT_FAST = DateTimeFormat.forPattern(DateConstant.DATEFORMATEyyyyMMddHHmmss);

    private DateUtil() {

    }

    /**
     * 
     * 功能描述: <br>
     * 〈把yyyyMMdd格式字符串转换成java.util.Date〉
     * 
     * @param datestr
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static java.util.Date getUtilDateyyyyMMddFast(String datestr) {
        DateTime dateTime = YYYYMMDD_FORMAT_FAST.parseDateTime(datestr);
        return dateTime.toDate();
    }

    /**
     * 日期相减
     * 
     * @param date 日期
     * @param day 天数
     * @return 返回相加后的日期
     */
    public static java.util.Date reduceDate(java.util.Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) - ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 返回毫秒
     * 
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈把yyyyMMdd转换成日期〉
     * 
     * @param dateStr
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static java.util.Date getDateOfYYYYMMdd(String dateStr) {
        DateFormat df = new java.text.SimpleDateFormat("yyyyMMdd");
        java.util.Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (Exception e) {
            LOG.error("时间转换异常", e);
        }
        return da;
    }

    /**
     * 
     * 功能描述:根据指定格式，格式化日期 <br>
     * 〈功能详细描述〉
     * 
     * @param date
     * @param format
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formatDate(Date date, String format) {
        if (null == date) {
            return EPPSCBPConstants.EMPTY_STRING;
        } else {
            return new DateTime(date).toString(DateTimeFormat.forPattern(format));
        }
    }

    /**
     * 计算两个日期之间的间隔天数
     * 
     * @param beginDate
     * @param endDate
     * @return
     * @author 巩明(12120499)
     */
    public static int getIntervalDays(Date beginDate, Date endDate) {
        LocalDate beginLocalDate = new LocalDate(beginDate);
        LocalDate endLocalDate = new LocalDate(endDate);
        Days days = Days.daysBetween(beginLocalDate, endLocalDate);
        return days.getDays();
    }

    /**
     * 计算两个日期之间的间隔月
     *
     * @param beginDate
     * @param endDate
     * @return
     * @author 巩明(12120499)
     */
    public static int getIntervalMonths(Date beginDate, Date endDate) {
        LocalDate beginLocalDate = new LocalDate(beginDate);
        LocalDate endLocalDate = new LocalDate(endDate);
        Months months = Months.monthsBetween(beginLocalDate, endLocalDate);
        return months.getMonths();
    }

    /**
     * 功能描述: <br>
     * 根据Date和fromat返回任意类型的String格式
     * 
     * @param date
     * @param dateFormat
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDefinableTime(Date date, String dateFormat) {
        if (null == date || null == dateFormat || "".equals(dateFormat.trim())) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 功能描述: <br>
     * 〈根据指定格式的字符串转化为Date〉
     * 
     * @param timeStr
     * @param format
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getDateOfTimeStr(String timeStr, String format) {
        if (StringUtil.isEmpty(timeStr, format)) {
            return null;
        } else {
            return DateTimeFormat.forPattern(format).parseDateTime(timeStr).toDate();
        }
    }

    /**
     * 
     * 将yyyy-MM-dd格式的字符串转换为Date<br>
     * 
     * @param timeStr 时间字符串
     */
    public static Date getDateOfShortDateStr(String timeStr) {
        return getDateOfTimeStr(timeStr, DateConstant.DATE_FORMAT);
    }

    /**
     * 
     * 功能描述: <br>
     * 〈把yyyy-MM-dd转换成日期〉
     * 
     * @param dateStr
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static java.util.Date getDateOfStr(String dateStr) {
        DateFormat df = new java.text.SimpleDateFormat(DateConstant.DATE_FORMAT);
        java.util.Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (Exception e) {
            LOG.error("时间转换异常", e);
        }
        return da;
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss格式字符串转换成 java.util.Date 新版本
     * 
     * @param dateStr
     * @return
     */
    public static java.util.Date getUtilDateTimeByShortStrFast(String datestr) {
        DateTime dateTime = YYYYMMDDHHMMSS_FORMAT_FAST.parseDateTime(datestr);
        return dateTime.toDate();
    }

    /**
     * 把日期转换成 yyyyMMdd格式的字符串
     * 
     * @param date
     * @return
     */
    public synchronized static String getShortStrDate(java.util.Date date) {
        return YYYYMMDD_FORMAT.format(date);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param time
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getDateOfTime(long time) {
        return new DateTime(time).toDate();
    }

    /**
     * 获取当前时间
     * 
     * @return Date
     */
    public static Date getCurDate() {
        return new DateTime().toDate();
    }

    /**
     * 功能描述: <br>
     * 〈以yyyyy-MM-dd格式 获取当前时间〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCurrentDateString() {
        return getCurrentDateString(DateConstant.DATE_FORMAT);
    }

    /**
     * 功能描述: <br>
     * 〈以指定格式 获取当前时间〉
     * 
     * @param format
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCurrentDateString(String format) {
        return new DateTime().toString(format);
    }

    /**
     * 
     * 功能描述:yyyy-MM-dd HH:mm:ss格式化时间 <br>
     * 
     * 
     * @param date
     * @param format
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return EPPSCBPConstants.EMPTY_STRING;
        } else {
            return formatDate(date, DateConstant.DATEFORMATEyyyyMMddHHmmss);
        }
    }

    /**
     * 
     * 功能描述:yyyy-MM-dd格式化时间 <br>
     * 
     * 
     * @param date
     * @param format
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formatDateStr(Date date) {
        if (null == date) {
            return EPPSCBPConstants.EMPTY_STRING;
        } else {
            return formatDate(date, DateConstant.DATE_FORMAT);
        }
    }

    /**
     * 功能描述: <br>
     * 〈验证时间范围是否在[days]天之内〉
     * 
     * @param start
     * @param end
     * @param month
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean verificationDate(Date start, Date end, int days) {
        DateTime maxEndDateTime = new DateTime(start).plusDays(days).withMillisOfDay(MILLIS_PER_DAY);
        return maxEndDateTime.isAfter(new DateTime(end));
    }

    /**
     * 
     * 获取所传时间的开始时间 <br>
     * 例如：入参是2014-09-11 18:09:39，返回为2014-09-11 00:00:00
     * 
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getStartDate(Date date) {
        return new DateTime(date).withMillisOfDay(0).toDate();
    }

    /**
     * 
     * 获取所传时间的结束时间 <br>
     * 例如：入参是2014-09-11 18:09:39，返回为2014-09-11 23:59:59
     * 
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getEndDate(Date date) {
        return new DateTime(date).withMillisOfDay(MILLIS_PER_DAY).toDate();
    }

    /**
     * 
     * 获取 yyyyMMdd格式的字符串<br>
     * 〈功能详细描述〉
     * 
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getFormatDateStr(Date date) {
        return formatDate(date, DateConstant.DATE_FORMAT);
    }

    /**
     * 获取yyyyMMddHHmmss格式的字符串
     */
    public static String getFormatDateLongStr(Date date) {
        return formatDate(date, DateConstant.DATEFORMATE_YYYYMMDDHHMMSS);
    }

    /**
     * 获取yyyy-MM-dd HH:mm:ss.SSS格式的字符串
     */
    public static String getStdLongDetailDateStr(Date date) {
        return formatDate(date, DateConstant.DATEFORMATE_YYYY_MM_DD_HHMMSSSSS);
    }

    /**
     * 日期相加
     * 
     * @param date 日期
     * @param day 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        return new DateTime(date).plusDays(day).toDate();
    }

    /**
     * 日期添加指定分钟数
     * 
     * 例如：入参是(2014-12-20 11:10:30，20)，返回为2014-12-20 11:30:30
     * 
     * @param date 日期
     * @param minutes 分钟数
     * @return 返回相加后的日期
     */
    public static Date addMinutes(Date date, int minutes) {
        return new DateTime(date).plusMinutes(minutes).toDate();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈判断日期格式〉
     * 
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean validateDate(String date) {
        try {
            DateFormat format = new SimpleDateFormat(DateConstant.DATEFORMATEyyyyMMddHHmmss);
            Date formatDate = format.parse(date);
            String str = format.format(formatDate);
            if (str.equals(date)) {
                return true;
            } else {
                LOG.error("校验时间格式有误,请参考yyyy-MM-dd HH:mm:ss");
                return false;
            }

        } catch (ParseException e) {
            LOG.error("校验时间格式有误,请参考yyyy-MM-dd HH:mm:ss");
            return false;
        }

    }

    /**
     * 
     * 功能描述: <br>
     * 获取指定时间间隔天数的时间
     * 
     * @param startDate
     * @param days
     * @return
     * @author 张超
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getAddDate(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 
     * 把自定义格式的日期字符串转换成java.util.Date
     * 
     * @param dateStr 日期字符串，例如：2014-08-20 13:15:26
     * @param format dateStr对应的格式，例如：yyyy-MM-dd HH:mm:ss <br>
     *            日期格式有： yyyy-MM-dd HH:mm:ss yyyy年MM月dd日 HH时mm分ss秒 yyyy年MM月dd日 HH时mm分 yyyy-MM-dd HH:mm yyyyMMddHH:mm:ss
     *            yyyy-MM-dd yyyyMMdd HHmmss yyyy年MM月dd日 HH:mm:ss" HH时mm分ss秒 ......(很多)<br>
     *            注意：解析异常返回null
     * @return java.util.Date
     * @author 王子银 2014-8-20 下午08:49:59
     * @since [V20140917]
     */
    public static Date getDateFromStr(String dateStr, String format) {
        DateFormat df = new java.text.SimpleDateFormat(format);
        java.util.Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (ParseException e) {
            // Ignore
        }
        return da;
    }

    /**
     * 
     * 功能描述: 获取当天日期并设置时间为23：59：59<br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getTodayByEnd() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();

    }

    /**
     * 
     * 功能描述: 获取当天日期并设置时间为00：00：00<br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getTodayByStart() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈精确到天〉
     * 
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static java.util.Date getDate(java.util.Date date) {
        String datestr = DateUtil.formatDate(date, DateConstant.DATEFORMATE_YYYYMMDD);
        SimpleDateFormat df = new SimpleDateFormat(DateConstant.DATEFORMATE_YYYYMMDD);
        try {
            return df.parse(datestr);
        } catch (ParseException e) {
            throw new FrameException(CoreErrorCode.DATE_FORMAT_ERROR, CoreErrorCode.DATE_FORMAT_ERROR_CN);
        }
    }

    /**
     * 
     * 功能描述: 格式成带时间的日期，格式：yyyy-MM-dd HH:mm:ss<br>
     * 〈功能详细描述〉
     * 
     * @param date
     * @return
     * @author GONGMING
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formatDateWithTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 
     * 功能描述: <br>
     * 〈日期转换成yyyyMMddHHmmss格式〉
     * 
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DateConstant.DATEFORMATE_YYYYMMDDHHMMSS);
    }

    /**
     * 把yyyyMMdd HH:mm:ss格式字符串转换成 java.util.Date 新版本
     * 
     * @param dateStr
     * @return
     */
    public static java.util.Date getUtilDateTimeByStrFast(String datestr) {
        DateTime dateTime = YYYY_MM_DD_HHMMSSSSS_FORMAT_FAST.parseDateTime(datestr);
        return dateTime.toDate();
    }

    /**
     * 把yyyyMMddHHmmss格式字符串转换成 java.util.Date
     * 
     * @param dateStr
     * @return
     */
    public synchronized static java.util.Date getUtilDateTimeByShortStr(String datestr) {
        try {
            return YYYYMMDDHHMMSS_FORMAT_LOST.parse(datestr);
        } catch (ParseException e) {
            LOG.error(CoreErrorCode.DATE_FORMAT_ERROR_CN, e);
            throw new FrameException(CoreErrorCode.DATE_FORMAT_ERROR, CoreErrorCode.DATE_FORMAT_ERROR_CN);
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 〈判断日期格式〉
     * 
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean validateYyyymmdd(String date) {
        try {
            DateFormat format = new java.text.SimpleDateFormat(DateConstant.DATEFORMATE_YYYYMMDD);
            Date formatDate = format.parse(date);
            String str = format.format(formatDate);
            if (str.equals(date)) {
                return true;
            } else {
                LOG.error("校验时间格式有误,请参考yyyyMMdd");
                return false;
            }
        } catch (ParseException e) {
            LOG.error("校验时间格式有误,请参考yyyyMMdd");
            return false;
        }
    }

    /**
     * 将日期字符串转换成日期类型
     * 
     * @param dateStr
     * @return
     */
    public static Date buildDate(String dateStr) {
        return DateUtil.getDateFromStr(dateStr, DateConstant.DATEFORMATEyyyyMMddHHmmss);
    }
    
    /**
     * 是否为当天24h内
     * @param inputJudgeDate
     * @return
     */
    public static boolean isToday(Date inputJudgeDate) {
		boolean flag = false;
		//获取当前系统时间
		long longDate = System.currentTimeMillis();
		Date nowDate = new Date(longDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(nowDate);
		String subDate = format.substring(0, 10);
		//定义每天的24h时间范围
		String beginTime = subDate + " 00:00:00";
		String endTime = subDate + " 23:59:59";
		Date paseBeginTime = null;
		Date paseEndTime = null;
		try {
			paseBeginTime = dateFormat.parse(beginTime);
			paseEndTime = dateFormat.parse(endTime);
		} catch (ParseException e) {
			LOG.error(e.getMessage());

		}
		if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
			flag = true;
		}
		return flag;

	}
    
}
