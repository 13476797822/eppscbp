package com.suning.epp.eppscbp.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;

public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    private ExcelUtil() {
    }

    // 默认日期正则表达式
    public static String DATEEXPR = "{\"[0-9]{4}[0-9]{2}[0-9]{2} [0-9]{1}:[0-9]{2}:[0-9]{2}\":\"yyyyMMdd H:mm:ss\",\"[0-9]{4}[0-9]{2}[0-9]{2} [0-9]{1}:[0-9]{2}\":\"yyyyMMdd H:mm\",\"[0-9]{4}\\\\.[0-9]{2}\\\\.[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyy.MM.dd HH:mm:ss\",\"[0-9]{4}\\\\.[0-9]{2}\\\\.[0-9]{2} [0-9]{1}:[0-9]{2}:[0-9]{2}\":\"yyyy.MM.dd H:mm:ss\", \"[0-9]{4}\\\\.[0-9]{1}\\\\.[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyy.M.dd HH:mm:ss\",\"[0-9]{4}\\\\.[0-9]{1}\\\\.[0-9]{1} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyy.M.d HH:mm:ss\",\"[0-9]{4}[0-9]{2}[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyyMMdd HH:mm:ss\",\"[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyy-MM-dd HH:mm:ss\" }";

    /**
     * 解析单元格
     *
     * @param cell
     * @return
     * @see [相关类/方法))(可选)
     * @since [产品/模块版本))(可选)
     */
    public static String getCellValue(Cell cell) throws ExcelForamatException {
        if (cell == null) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date d = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                return sdf.format(d);
            } else {
                CellStyle style = cell.getCellStyle();
                DecimalFormat format = new DecimalFormat();
                format.applyPattern("#.##");
                return format.format(cell.getNumericCellValue());
            }
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            if (cell.getBooleanCellValue()) {
                return "1";
            }
            return "0";
        } else if (cell.getCellTypeEnum() == CellType.BLANK) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            throw new ExcelForamatException("第" + Integer.toString(cell.getRowIndex()) + "条数据第" + Integer.toString(cell.getColumnIndex() + 1) + "列数据格式不正确, 请检查格式后重新上传");
        } else {
            throw new ExcelForamatException("第" + Integer.toString(cell.getRowIndex()) + "条数据第" + Integer.toString(cell.getColumnIndex() + 1) + "列数据格式不正确, 请检查格式后重新上传");
        }
    }

    /**
     * 解析STRING/NUMERIC日期格式单元格。 STRING日期需要匹配正则表达 如2019.05.05 9:00:00/2019.5.05 09:00:00/20190505 9:00:00/20190505 9:00等
     * 
     *
     *
     * @param cell
     * @return 格式化为：yyyyMMdd hh:mm:ss
     * @throws ParseException
     * @see [相关类/方法))(可选)
     * @since [产品/模块版本))(可选)
     */
    @SuppressWarnings("unchecked")
    public static String getCellValueDate(Cell cell) throws ParseException {
        SimpleDateFormat sdf2 = new SimpleDateFormat(DateConstant.DATEFORMATE_YYYYMMDD_HHMMSS);

        if (cell == null) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.STRING) {
            String cellStr = cell.getStringCellValue();

            // regStr = "{\"[0-9]{4}[0-9]{2}[0-9]{2} [0-9]{1}:[0-9]{2}:[0-9]{2}\":\"yyyyMMdd H:mm:ss\",\"[0-9]{4}[0-9]{2}[0-9]{2} [0-9]{1}:[0-9]{2}\":\"yyyyMMdd H:mm\",\"[0-9]{4}.[0-9]{2}.[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyy.MM.dd HH:mm:ss\",\"[0-9]{4}.[0-9]{2}.[0-9]{2} [0-9]{1}:[0-9]{2}:[0-9]{2}\":\"yyyy.MM.dd H:mm:ss\", \"[0-9]{4}.[0-9]{1}.[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyy.M.dd HH:mm:ss\",\"[0-9]{4}.[0-9]{1}.[0-9]{1} [0-9]{2}:[0-9]{2}:[0-9]{2}\":\"yyyy.M.d HH:mm:ss\" }";
            // 匹配string格式的日期的正则表达式
            Map<String, String> mapType = JSON.parseObject(DATEEXPR, Map.class);
            for (Object map : mapType.entrySet()) {
                if (cellStr.matches((String) ((Map.Entry) map).getKey())) {
                    SimpleDateFormat sdf1 = new SimpleDateFormat((String) ((Map.Entry) map).getValue());
                    return sdf2.format(sdf1.parse(cellStr));
                }
            }
            return "0";

        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            // 日期格式
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date d = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                SimpleDateFormat sdf = new SimpleDateFormat(DateConstant.DATEFORMATE_YYYYMMDD_HHMMSS);
                String str = sdf.format(d);
                return str;
            }
            LOGGER.info("CellType.NUMERIC下,格式有误");
            return "0";
        } else {
            LOGGER.info("格式有误.");
            return "0";
        }
    }

    /**
     * 此方法以后不再使用，仅为兼容购付汇收单明细解析
     *
     * @param cell
     * @return
     * @see [相关类/方法))(可选)
     * @since [产品/模块版本))(可选)
     */
    public static String getCellValue(Cell cell, String dateFormat) throws ExcelForamatException {
        if (cell == null) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date d = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                if (StringUtil.isEmpty(dateFormat)) {
                    sdf = new SimpleDateFormat(dateFormat);
                }
                return sdf.format(d);
            } else {
                CellStyle style = cell.getCellStyle();
                DecimalFormat format = new DecimalFormat();
                // 单元格设置成常规
                if (style.getDataFormatString().equals("General")) {
                    format.applyPattern("#");
                }
                return format.format(cell.getNumericCellValue());
            }
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            if (cell.getBooleanCellValue()) {
                return "1";
            }
            return "0";
        } else if (cell.getCellTypeEnum() == CellType.BLANK) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            throw new ExcelForamatException("第" + Integer.toString(cell.getRowIndex()) + "条数据第" + Integer.toString(cell.getColumnIndex() + 1) + "列数据格式不正确, 请检查格式后重新上传");
        } else {
            throw new ExcelForamatException("第" + Integer.toString(cell.getRowIndex()) + "条数据第" + Integer.toString(cell.getColumnIndex() + 1) + "列数据格式不正确, 请检查格式后重新上传");
        }
    }

    public static void setDateFormat(String expr) {
        DATEEXPR = expr;
    }

}
