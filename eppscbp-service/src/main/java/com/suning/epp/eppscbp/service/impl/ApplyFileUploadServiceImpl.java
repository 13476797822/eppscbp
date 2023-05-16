/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: ApplyFileUploadServiceImpl.java
 * Author:   17033387
 * Date:     2018年4月8日 下午5:39:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service.impl;

import static com.suning.epp.eppscbp.util.ExcelUtil.getCellValue;
import static com.suning.epp.eppscbp.util.ExcelUtil.getCellValueDate;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.monitorjbl.xlsx.StreamingReader;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.IndexConstant;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.CustomType;
import com.suning.epp.eppscbp.common.enums.PayeeType;
import com.suning.epp.eppscbp.common.enums.TransType;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.CtCollSettleFileCheck;
import com.suning.epp.eppscbp.dto.CtUaBatchFileCheck;
import com.suning.epp.eppscbp.dto.CtUaFileCheck;
import com.suning.epp.eppscbp.dto.LogisticsFundsPayFileCheck;
import com.suning.epp.eppscbp.dto.UploadBatchPaymentCheck;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.rsf.service.SafeRateQueryService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.DomesticMerchantInfoService;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.Arith;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSMimeType;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;
import com.suning.epp.eppscbp.util.validator.ValidateBaseHibernateUtil;

/**
 * 〈交易申请上传文件服务类实现〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("applyFileUploadService")
public class ApplyFileUploadServiceImpl implements ApplyFileUploadService {

    /**
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyFileUploadServiceImpl.class);

    @Autowired
    private SafeRateQueryService safeRateQueryService;
    
    @Autowired
    private MerchantInfoService merchantInfoService;

    private static final BigDecimal CNY_DETAIL_MAX_20W = new BigDecimal(200000);

    @Autowired
    private DomesticMerchantInfoService domesticMerchantInfoService;
    

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.ApplyFileUploadService#uploadFile()
     */
    @Override
    public FileUploadResDto uploadFile(MultipartFile targetFile, String bizType, int detailAmount, double payAmt, String user, String curType, String exchangeType) throws IOException, ExcelForamatException, ParseException {
        return parseFile(targetFile, bizType, 1, detailAmount, payAmt, user, curType, null, 0.0, exchangeType);
    }

    @Override
    public FileUploadResDto uploadStoreFile(MultipartFile targetFile, String cur, String user) throws IOException, ExcelForamatException, ParseException {
        return parseFile(targetFile, null, 5, 0, 0.0, user, cur, null, 0.0, null);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.ApplyFileUploadService#uploadBatchFile()
     */
    @Override
    public FileUploadResDto uploadBatchFile(MultipartFile targetFile, String bizType, String user) throws IOException, ExcelForamatException, ParseException {
        return parseFile(targetFile, bizType, 2, 0, 0, user, null, null, 0.0, null);
    }

    @Override
    public FileUploadResDto uploadBatchFiles(MultipartFile targetFile, double stayAmount, String user) throws IOException, ExcelForamatException, ParseException {
        return parseFile(targetFile, null, 4, 0, stayAmount, user, null, null, stayAmount, null);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.ApplyFileUploadService#uploadForCollAndSettle(org.springframework.web.multipart.MultipartFile, double, java.math.BigDecimal)
     */
    @Override
    public FileUploadResDto uploadForCollAndSettle(MultipartFile targetFile, double payAmt, String user, String curType, String collAndSettleFlag) throws IOException, ExcelForamatException, ParseException {
        return parseFile(targetFile, null, 3, 0, payAmt, user, curType, collAndSettleFlag, 0.0, null);
    }
    
    /*
     * flag: 1-购付汇单笔文件,2-购付汇批量文件,3-收结汇文件导入模式,4-资金批付,5-店铺管理提现
     */
    private FileUploadResDto parseFile(MultipartFile targetFile, String bizType, int flag, int detailAmount, double payAmt, String user, String curType, String collAndSettleFlag, double stayAmount, String exchangeType) throws IOException, ExcelForamatException, ParseException {
        FileUploadResDto res = new FileUploadResDto();
        
        //判断商户真实性材料合并方式
        String superviseCombineTypeAuth = this.getSuperviseCombineTypeAuth(user);
    	if(StringUtil.isEmpty(superviseCombineTypeAuth)) {
    		res.fail(WebErrorCode.AUTH_NULL.getCode(), WebErrorCode.AUTH_NULL.getDescription());
            return res;
    	}
        
        if (targetFile.isEmpty()) {
            LOGGER.info("文件为空或文件不存在");
            res.fail(WebErrorCode.FILE_NOT_EXIST.getCode(), WebErrorCode.FILE_NOT_EXIST.getDescription());
            return res;
        }
        // 获取文件对象
        LOGGER.info("明细文件导入操作开始 fileName:{}", targetFile.getOriginalFilename());
        Workbook workbook = null;
        String fileName = targetFile.getOriginalFilename();
        InputStream is = targetFile.getInputStream();
        if(null == is) {
        	 LOGGER.error("没有获取到文件");
        	 res.fail(WebErrorCode.FILE_NOT_EXIST.getCode(), WebErrorCode.FILE_NOT_EXIST.getDescription());
        	 return res;
        }
        
        try {
             if (fileName.endsWith(CommonConstant.XLSX)) {
            	 LOGGER.info("文件后缀为xlsx"); 
            	 workbook = StreamingReader.builder().rowCacheSize(10) // 缓存到内存中的行数，默认是10
                         .bufferSize(4096) // 读取资源时，缓存到内存的字节大小，默认是1024
                         .open(is);
            	 
            	 LOGGER.info("开始明细解析"); 
            	 if (flag == 1) {
                     // 单笔购付汇
                     return checkFile(workbook, targetFile, bizType, detailAmount, payAmt, user, curType, superviseCombineTypeAuth, exchangeType);
                 } else if (flag == 2) {
                     // 批量购付汇
                     return checkBatchFile(workbook, targetFile, user, superviseCombineTypeAuth);
                 } else if (flag == 3) {
                     // 收结汇&&收款平台提现
                     return checkForCollAndSettle(workbook, targetFile, payAmt, user, curType, collAndSettleFlag, superviseCombineTypeAuth);
                 } else if (flag == 4) {
                     // 资金跨境/收款平台批付
                     return checkWithdrawFile(workbook, targetFile, stayAmount, user);
                 } else if (flag == 5) {
                     // 外拓商户提现
                     return checkStoreHandleFile(workbook, targetFile, user, curType, superviseCombineTypeAuth);
                 } else {
                     return null;
                 }
            	 
            } else {
                LOGGER.info("不支持的文件类型");
                res.fail(WebErrorCode.FILE_TYPE_MISMATCH.getCode(), WebErrorCode.FILE_TYPE_MISMATCH.getDescription());
                return res;
            }
        } catch (IOException e) {
            LOGGER.error("文件解析错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
            return res;
        } finally {
            try {
                if (null != workbook) {
                	is.close();
                    workbook.close();
                }
            } catch (IOException e) {
                LOGGER.error("文件关闭错误:{}", ExceptionUtils.getStackTrace(e));
            }
        }
        
    }

    /**
     * 
     * 功能描述: 外拓商户提现文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @throws ParseException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto checkStoreHandleFile(Workbook workbook, MultipartFile targetFile, String user, String cur, String superviseCombineTypeAuth) throws IOException, ExcelForamatException, ParseException {
        FileUploadResDto res = new FileUploadResDto();
        // 金额
        String revenue;
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 总行数
        int totalRow = sheet.getLastRowNum();
        LOGGER.info("获取到总明细数:{}", totalRow);
        if (totalRow == IndexConstant.ZERO) {
            error.append("明细文件为空;").append("<br>");
        }
        // 求金额总和
        double totalAmt = 0;
        // 50000美金换算
        BigDecimal referAmt5W = null;
        // 60美金换算
        BigDecimal referAmt60 = null;
        // 总笔数
        int applyTotalRow = 0;
        // 外管汇率
        BigDecimal safeRate = safeRateQueryService.safeRateQuery(cur);
        LOGGER.info("查询到外管汇率,safeRate:{}", safeRate);

        if (safeRate == null) {
            error.append("获取外管汇率异常,请稍后重试;").append("<br>");
        } else {
            referAmt5W = new BigDecimal(50000).divide(safeRate, 2, BigDecimal.ROUND_HALF_DOWN);
            referAmt60 = new BigDecimal(60).divide(safeRate, 2, BigDecimal.ROUND_HALF_DOWN);
            LOGGER.info("根据外管汇率换算等值5W美金参考金额,referAmt:{}", referAmt5W);
            LOGGER.info("根据外管汇率换算等值60美金参考金额,referAmt:{}", referAmt60);
        }

        Set<String> setList = new HashSet<String>();
        // 遍历每一行取值
        int rowNum = 0;
        for (Row row : sheet) {
            // 读文件
            rowNum++;
            // 标题不读
            if (rowNum == 1) {
                continue;
            }
            CtCollSettleFileCheck detail = new CtCollSettleFileCheck();
            detail.setOrderNo(getCellValue(row.getCell(0)));

            // 日期特殊处理
            detail.setOrderDate(getCellValueDate(row.getCell(1)));

            detail.setApplication(getCellValue(row.getCell(2)));
            //原订单编号
            detail.setOriginalOrderNo(getCellValue(row.getCell(3)));
            
            detail.setPayeeMerchantType(getCellValue(row.getCell(4)));
            detail.setBankAccountNo(getCellValue(row.getCell(5)));
            detail.setPayerName(getCellValue(row.getCell(6)));
            revenue = getCellValue(row.getCell(7));
            // 金额获取做特殊处理,支持数值和文本类型
            detail.setRevenue(StringUtil.isNumber(revenue) ? Double.parseDouble(revenue) : Double.valueOf("0"));
            detail.setCountryCode(getCellValue(row.getCell(8)));
            
            //商品名称
            detail.setProductName(getCellValue(row.getCell(9)));
            //商品数量
            detail.setProductNum(getCellValue(row.getCell(10)));
            //物流企业名称
            detail.setLogistcsCompName(getCellValue(row.getCell(11)));
            //物流单号
            detail.setLogistcsWoNo(getCellValue(row.getCell(12)));
            // 商品种类
            detail.setMerchandiseType(getCellValue(row.getCell(13)));
            
            // 校验参数
            String validateSecond = "";
            if (CommonConstant.REFUND_APPLICATION.equals(detail.getApplication())) {
            	//跨境电商模式下收汇用途为1:退款，原订单编号必填
            	validateSecond = ValidateBaseHibernateUtil.validateSecond(detail);
                totalAmt = Arith.sub(totalAmt, detail.getRevenue());
            } else {
            	//跨境电商模式下收汇用途为0:支付，原订单编号不用填
            	if(!StringUtil.isEmpty(detail.getOriginalOrderNo())) {
            		validateSecond = EPPSCBPConstants.APPLICATION_IS_ZERO;
            	}
                totalAmt = Arith.add(totalAmt, detail.getRevenue());
            }

            // 总笔数
            applyTotalRow++;
            
            StringBuilder validateRes = new StringBuilder(ValidateBaseHibernateUtil.validate(detail));

            //跨境电商模式下收汇用途为1:退款，原订单编号未填写时，页面提示
            if (!"".equals(validateSecond)) {
            	validateRes.append(validateSecond);
            }

            if (!StringUtil.isEmpty(detail.getOrderNo()) && StringUtil.length(detail.getOrderNo()) > 30) {
                validateRes.append("订单编号超过30个字符");
            }
            
            //判断商户真实性材料合并方式，如果是合并,校验物流名称以及物流单号必填
            this.checkLogisticsInfo(superviseCombineTypeAuth, detail, validateRes);
            
            // 根据币种校验金额格式
            if (CurType.JPY.getCode().equals(cur)) {
                if (!StringUtil.isEmpty(revenue) && !revenue.matches("\\d{1,13}")) {
                    validateRes.append("日元情况收入款金额必须为无小数点且不超过13位的整数;");
                }
            } else {
                if (!StringUtil.isEmpty(revenue) && !revenue.matches("\\d{1,13}.\\d{1,2}||\\d{1,13}")) {
                    validateRes.append("非日元情况收入款金额整数部分不超过13位,精确到2位小数;");
                }
            }

            // 根据外管汇率换算5W美金
            if (referAmt5W != null && BigDecimal.valueOf(detail.getRevenue()).compareTo(referAmt5W) > 0) {
                LOGGER.error("收入款金额大于等值50000美金.申请金额:{},参考金额:{}", detail.getRevenue(), referAmt5W);
                validateRes.append("收入款金额大于等值50000美金;");
            }

            // 整行为空则判定为最后一行,退出校验
            if (StringUtil.allEmpty(detail.getOrderNo(), detail.getOrderDate(), detail.getApplication(), detail.getPayeeMerchantType(), detail.getBankAccountNo(), detail.getPayerName(), revenue, detail.getCountryCode())) {
                if (rowNum - 1 == IndexConstant.ZERO) {
                    error.append("明细文件为空");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
                break;
            }

            if (!StringUtil.isEmpty(validateRes.toString())) {
                error.append("第").append(rowNum).append("行异常, ").append("失败原因：").append(validateRes).append("<br>");
            }

            // 非空最后一行校验 总条数
            if (rowNum == totalRow + 1) {
                if (totalRow  > IndexConstant.ONE_THOUSAND) {
                    error.append("明细笔数:").append(rowNum-1).append("条,超过1000条;").append("<br>");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
            }
            // 收款商户类型校验
            setList.add(detail.getPayeeMerchantType());
        }

        // 校验总金额是否大于60美金
        if (BigDecimal.valueOf(totalAmt).compareTo(referAmt60) < 0) {
            error.append("总金额必须大于60美金").append("<br>");
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
        }

        // 收款方类型唯一校验
        if (setList.size() != 1) {
            LOGGER.info("收款人类型不唯一,收款人类型数:{}",setList.size());
            error.append("文件明细中收款人类型不唯一,必须都为:D 或都为:C .");
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
        }

        if (StringUtil.isEmpty(error.toString())) {
            return uploadCollSett(targetFile, user, applyTotalRow);
        } else {
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        }
    }

    /**
     * 
     * 功能描述: 批付文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto checkWithdrawFile(Workbook workbook, MultipartFile targetFile, double stayAmount, String user) throws IOException, ExcelForamatException {
        FileUploadResDto res = new FileUploadResDto();
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 总行数
        int totalRow = sheet.getLastRowNum();
        LOGGER.info("获取到明细数:{}", totalRow);
        
        if (totalRow == IndexConstant.ZERO) {
            error.append("明细文件为空;").append("<br>");
        }
        // 求金额总和
        double allAmt = 0;

       

        // 遍历每一行取值
        int rowNum = 0;
        for (Row row : sheet) {
            // 读文件
            rowNum++;
            // 标题不读
            if (rowNum == 1) {
                continue;
            }
            UploadBatchPaymentCheck detail = new UploadBatchPaymentCheck();
            LOGGER.info("第:{}行明细,detail:{}", rowNum, detail);
            detail.setPayeeName(getCellValue(row.getCell(0)));
            detail.setAccountNature(getCellValue(row.getCell(1)));
            detail.setBankAccountNo(getCellValue(row.getCell(2)));
            // 金额获取做特殊处理,支持数值和文本类型
            String amtStr = getCellValue(row.getCell(3));
            Double totalAmt = StringUtil.isNumber(amtStr) ? Double.parseDouble(amtStr) : Double.valueOf("0");
            allAmt = Arith.add(allAmt, totalAmt);
            // 校验框架DecimalMin不支持小数
            if (totalAmt > 0 && totalAmt < 1) {
                detail.setPayAmount(1);
            } else {
                detail.setPayAmount(totalAmt);
            }
            detail.setOrderName(getCellValue(row.getCell(4)));

            // 校验参数
            StringBuilder validateRes = new StringBuilder(ValidateBaseHibernateUtil.validate(detail));

            // 整行为空则判定为最后一行,退出校验
            if (StringUtil.allEmpty(detail.getPayeeName(), detail.getAccountNature(), detail.getBankAccountNo(), amtStr)) {
                if (rowNum - 1 == IndexConstant.ZERO) {
                    error.append("明细文件为空");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
                if (rowNum - 1 > IndexConstant.ONE_THOUSAND) {
                    error.append("明细笔数:").append(rowNum - 1).append("条,超过1000条;").append("<br>");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
                break;
            }

            if (!StringUtil.isEmpty(validateRes.toString())) {
                error.append("第").append(rowNum).append("行异常").append(",失败原因：").append(validateRes).append("<br>");
            }

            // 非空最后一行校验 总条数
            if (rowNum == totalRow + 1) {
                if (totalRow> IndexConstant.ONE_THOUSAND) {
                    error.append("明细笔数:").append(rowNum-1).append("条,超过1000条;").append("<br>");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
            }
        }

        if (BigDecimal.valueOf(allAmt).compareTo(BigDecimal.valueOf(stayAmount)) > 0) {
            LOGGER.error("出款总金额大于待批付金额.出款总金额:{},待批付金额:{}", allAmt, stayAmount);
            error.append("出款总金额大于待批付金额,").append("出款总金额:").append(allAmt).append(",待批付金额:").append(stayAmount).append("<br>");
        }
        if (StringUtil.isEmpty(error.toString())) {
            return uploadBPFile(targetFile, user);
        } else {
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        }
    }
    
    /**
     * 
     * 功能描述: 收结汇文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @throws ParseException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
	private FileUploadResDto checkForCollAndSettle(Workbook workbook, MultipartFile targetFile, double payAmt, String user, String curType, String collAndSettleFlag, String superviseCombineTypeAuth) throws IOException, ExcelForamatException, ParseException {
		LOGGER.info("开始收结汇明细解析");
		FileUploadResDto res = new FileUploadResDto();
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 最后一行
        int totalRow = sheet.getLastRowNum();
        LOGGER.info("不含表头，获取到总明细:{}", totalRow);
        
        if (totalRow == IndexConstant.ZERO) {
            error.append("明细文件为空;").append("<br>");
        }
        // 实际申请总行数,不超过30000
        int applyTotalRow = 0;
        // 合并后实际总行数,不超过5000
        int mergeTotalRow = 0;

        // 求金额总和
        double totalAmt = 0;

        // 外管汇率
        BigDecimal safeRate = safeRateQueryService.safeRateQuery(curType);
        LOGGER.info("查询到外管汇率,safeRate:{}", safeRate);

        // 50000美金换算
        BigDecimal referAmt5W = null;
        // 500美金换算
        BigDecimal referAmt500 = null;
        if (safeRate == null) {
            error.append("获取外管汇率异常,请稍后重试;").append("<br>");
        } else {
            referAmt5W = new BigDecimal(50000).divide(safeRate, 2, BigDecimal.ROUND_HALF_DOWN);
            referAmt500 = new BigDecimal(500).divide(safeRate, 2, BigDecimal.ROUND_HALF_DOWN);
            LOGGER.info("根据外管汇率换算等值5W美金参考金额,referAmt:{}", referAmt5W);
            LOGGER.info("根据外管汇率换算等值500美金参考金额,referAmt:{}", referAmt500);
        }
        
        int rowNum = 0;
        // 判断货运类型
        Set<String> setList = new HashSet<String>();
        // 遍历每一行取值
        for (Row row : sheet) {
        	rowNum++;
        	if (rowNum == 1) {
                continue;
            }
            CtCollSettleFileCheck detail = new CtCollSettleFileCheck();
            // 金额
            String revenue = "";
            // 校验参数
            String validateRes = "";
            String validateFirst = "";
            String validateSecond = "";
            if ("1".equals(collAndSettleFlag)) {// 国际物流
                detail.setOrderNo(getCellValue(row.getCell(0)));

                // 日期特殊处理
                detail.setOrderDate(getCellValueDate(row.getCell(1)));

                // 物流款项,物流款项不支持退款
                detail.setApplication("0");
                detail.setPayeeMerchantType(getCellValue(row.getCell(2)));
                detail.setBankAccountNo(getCellValue(row.getCell(3)));
                detail.setPayerName(getCellValue(row.getCell(4)));

                revenue = getCellValue(row.getCell(5));
                detail.setRevenue(StringUtil.isNumber(revenue) ? Double.parseDouble(revenue) : Double.valueOf("0"));
                detail.setCountryCode(getCellValue(row.getCell(6)));
                detail.setFreightType(getCellValue(row.getCell(7)));// 货运类型
                //商品名称
                detail.setProductName(getCellValue(row.getCell(8)));
                //商品数量
                detail.setProductNum(getCellValue(row.getCell(9)));
                //物流企业名称
                detail.setLogistcsCompName(getCellValue(row.getCell(10)));
                //物流单号
                detail.setLogistcsWoNo(getCellValue(row.getCell(11)));
                // 商品种类
                detail.setMerchandiseType(getCellValue(row.getCell(12)));
                
                validateFirst = ValidateBaseHibernateUtil.validateFrist(detail);
                
                totalAmt = Arith.add(totalAmt, detail.getRevenue());
            } else {// 2019-01-29版本，目前仅货物贸易
                detail.setOrderNo(getCellValue(row.getCell(0)));
                // 日期格式特殊处理
                detail.setOrderDate(getCellValueDate(row.getCell(1)));

                //跨境电商模式下收汇用途,0:支付，1:退款
                String application = getCellValue(row.getCell(2));
                detail.setApplication(application);
                detail.setOriginalOrderNo(getCellValue(row.getCell(3)));
                detail.setPayeeMerchantType(getCellValue(row.getCell(4)));
                detail.setBankAccountNo(getCellValue(row.getCell(5)));
                detail.setPayerName(getCellValue(row.getCell(6)));
                // 金额
                revenue = getCellValue(row.getCell(7));
                detail.setRevenue(StringUtil.isNumber(revenue) ? Double.parseDouble(revenue) : Double.valueOf("0"));
                detail.setCountryCode(getCellValue(row.getCell(8)));
                //商品名称
                detail.setProductName(getCellValue(row.getCell(9)));
                //商品数量
                detail.setProductNum(getCellValue(row.getCell(10)));
                //物流企业名称
                detail.setLogistcsCompName(getCellValue(row.getCell(11)));
                //物流单号
                detail.setLogistcsWoNo(getCellValue(row.getCell(12)));
                // 商品种类
                detail.setMerchandiseType(getCellValue(row.getCell(13)));

                if (CommonConstant.REFUND_APPLICATION.equals(detail.getApplication())) {
                	//跨境电商模式下收汇用途为1:退款，原订单编号必填
                	validateSecond = ValidateBaseHibernateUtil.validateSecond(detail);
                    totalAmt = Arith.sub(totalAmt, detail.getRevenue());
                } else {
                	//跨境电商模式下收汇用途为0:支付，原订单编号不填写
                	if(!StringUtil.isEmpty(detail.getOriginalOrderNo())) {
                		validateSecond = EPPSCBPConstants.APPLICATION_IS_ZERO;
                	}
                    totalAmt = Arith.add(totalAmt, detail.getRevenue());
                    
                }
            }

            validateRes = ValidateBaseHibernateUtil.validate(detail);
            StringBuilder additionalCheck = new StringBuilder(validateRes);

            //国际物流收款时，收款人只能为企业
            if("1".equals(collAndSettleFlag) && !PayeeType.PUBLIC.getCode().equals(detail.getPayeeMerchantType())){
                additionalCheck.append("国际物流交易的收款人不是对公用户.");
            }

            if (!"".equals(validateFirst)) {
                additionalCheck.append(validateFirst);
            }

            if (!StringUtil.isEmpty(detail.getOrderNo()) && StringUtil.length(detail.getOrderNo()) > 30) {
                additionalCheck.append("订单编号不能超过30个字符");
            }
            
            //跨境电商模式下收汇用途为1:退款，原订单编号未填写时，页面提示
            if (!"".equals(validateSecond)) {
                additionalCheck.append(validateSecond);
            }
            
            //判断商户真实性材料合并方式，如果是合并,校验物流名称以及物流单号必填
            this.checkLogisticsInfo(superviseCombineTypeAuth, detail, additionalCheck);

            // 根据币种校验金额格式
            if (CurType.JPY.getCode().equals(curType)) {
                if (!StringUtil.isEmpty(revenue) && !revenue.matches("\\d{1,13}")) {
                    additionalCheck.append("日元情况收入款金额必须为无小数点且不超过13位的整数;");
                }
            } else {
                if (!StringUtil.isEmpty(revenue) && !revenue.matches("\\d{1,13}.\\d{1,2}||\\d{1,13}")) {
                    additionalCheck.append("非日元情况收入款金额整数部分不超过13位,精确到2位小数;");
                }
            }

            // 人民币收汇明细不得超过20W
            if (CurType.CNY.getCode().equals(curType)) {
                if (BigDecimal.valueOf(detail.getRevenue()).compareTo(CNY_DETAIL_MAX_20W) >= 0) {
                    LOGGER.error("明细金额{}元，不得等于或者超过20万元;", detail.getRevenue());
                    additionalCheck.append("明细金额").append(detail.getRevenue()).append("元，不得等于或者超过20万元;");
                }
            }

            // 根据外管汇率换算5W美金
            if (referAmt5W != null && BigDecimal.valueOf(detail.getRevenue()).compareTo(referAmt5W) > 0) {
                LOGGER.error("收入款金额大于等值50000美金.申请金额:{},参考金额:{}", detail.getRevenue(), referAmt5W);
                additionalCheck.append("收入款金额大于等值50000美金;");
            }

            // 整行为空则判定为最后一行,退出校验
            if (StringUtil.allEmpty(detail.getOrderNo(), detail.getOrderDate(), detail.getApplication(), detail.getPayeeMerchantType(), detail.getPayeeMerchantCode(), detail.getPayerName(), revenue, detail.getCountryCode())) {
                applyTotalRow = rowNum - 1;
                break;
            }

            // 明细合并逻辑,个人且小于等于500美金合并
            if (PayeeType.PRIVATE_CHINESE.getCode().equals(detail.getPayeeMerchantType()) && referAmt500 != null && BigDecimal.valueOf(detail.getRevenue()).compareTo(referAmt500) < 1) {
                mergeTotalRow = mergeTotalRow + 1;
            }

            if (!StringUtil.isEmpty(additionalCheck == null ? null : additionalCheck.toString())) {
                error.append("第").append(rowNum).append("笔, 订单号").append(detail.getOrderNo()).append(", 失败原因：").append(additionalCheck).append("<br>");
            }

            // 非空最后一行校验 总条数
            if (rowNum == totalRow + 1) {
                applyTotalRow = totalRow;
            }

            // 收款人类型唯一校验
            setList.add(detail.getPayeeMerchantType());
        }
        if (BigDecimal.valueOf(totalAmt).compareTo(BigDecimal.valueOf(payAmt)) != 0) {
            LOGGER.error("申请金额与明细金额之和不一致.申请金额:{},明细金额之和:{}", payAmt, totalAmt);
            error.append("申请金额与明细金额之和不一致,").append("申请金额:").append(payAmt).append(",明细金额之和:").append(BigDecimal.valueOf(totalAmt).toPlainString()).append("<br>");
        }

        mergeTotalRow = applyTotalRow - Math.max(mergeTotalRow - 1, 0);
        LOGGER.info("实际申请笔数:{},合并后笔数:{}", applyTotalRow, mergeTotalRow);

        if (applyTotalRow > 50000) {
            LOGGER.error("申请笔数超过50000条,申请笔数:{}", applyTotalRow);
            error.append("申请笔数超过50000条,申请笔数:").append(applyTotalRow).append("<br>");
        }

        if (setList.size() != 1) {
            LOGGER.error("申请明细文件中收款人类型不唯一, 收款人类型数:{}", setList.size());
            error.append("申请明细文件中收款人类型不唯一,收款人类型必须都为: D或者都为: C").append("<br>");
        }

        /*
         * if (mergeTotalRow > 5000) { LOGGER.error("个人收款方合并后笔数超过5000条,合并后笔数:{}", mergeTotalRow); error.append("个人收款方合并后笔数超过5000条,合并后笔数:").append(mergeTotalRow).append("<br>"); }
         */

        if (StringUtil.isEmpty(error.toString())) {
            return uploadCollSett(targetFile, user, applyTotalRow);
        } else {
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        }
    }

    private void checkLogisticsInfo(String superviseCombineTypeAuth, CtCollSettleFileCheck detail, StringBuilder additionalCheck) {
		String logistcsCompName = detail.getLogistcsCompName();
		String logistcsWoNo = detail.getLogistcsWoNo();
		if (CommonConstant.Y.equals(superviseCombineTypeAuth)) {
			 //判断商户真实性材料合并方式：如果是合并方式，则物流企业名称、物流单号必填，以及物流企业名称长度为256
			if(StringUtil.isEmpty(logistcsCompName) || logistcsCompName.length() > 256) {
				additionalCheck.append("物流企业名称不能为空,最大长度为256字符.");
			}
			
			if(StringUtil.isEmpty(logistcsWoNo)) {
				additionalCheck.append("物流单号不能为空.");
			}
			
		}
	}
    /**
     *获取权限
     * 
     * @param user
     * @param detail
     * @param additionalCheck
     */
    @Override
	public String getSuperviseCombineTypeAuth(String user) {
		Map<String, String>  result = merchantInfoService.queryMerchantAuth(user, EPPSCBPConstants.OPERATOR_CODE_MAIN);
		final String responseCode = MapUtils.getString(result, CommonConstant.RESPONSE_CODE);
		if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
			final String context = MapUtils.getString(result, CommonConstant.CONTEXT);
		    Map<String, String> ctMerchantAuth = JSON.parseObject(context, Map.class);
			String isRight = (String) ctMerchantAuth.get("superviseCombineTypeAuth");
			
			return isRight;
		} else {
			 LOGGER.info("该商户权限未查到");
			 return null;
		}
	}

    /**
     * 
     * 功能描述: 单笔购付汇文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto checkFile(Workbook workbook, MultipartFile targetFile, String bizType, int detailAmount, double payAmt, String user, String curType, String superviseCombineTypeAuth, String exchangeType) throws IOException, ExcelForamatException, ParseException {
        FileUploadResDto res = new FileUploadResDto();
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 总行数
        int totalRow = sheet.getLastRowNum();
        // 实际总行数
        int realTotalRow = 0;
        // 需合并的行数
        int mergeTotalRow = 0;

        // 外管汇率
        BigDecimal safeRate = safeRateQueryService.safeRateQuery(curType);
        LOGGER.info("查询到外管汇率,safeRate:{}", safeRate);

        // 500美金换算
        BigDecimal referAmt500 = null;
        if (safeRate == null) {
            error.append("获取外管汇率异常,请稍后重试;").append("<br>");
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        } else {
            referAmt500 = new BigDecimal(500).divide(safeRate, 2, BigDecimal.ROUND_HALF_DOWN);
            LOGGER.info("根据外管汇率换算等值500美金参考金额,referAmt:{}", referAmt500);
        }
        LOGGER.info("不含表头，获取到明细行数:{}", totalRow);
        if (totalRow == IndexConstant.ZERO) {
            error.append("明细文件为空;").append("<br>");
        }
        // 求金额总和
        double allAmt = 0;

        Set<String> orgIdSet = new HashSet<String>();
        //总行数
        int rowNum = 0;
        // 收款人类型集合
        Set<String> setList = new HashSet<String>();
        // 遍历每一行取值
        for (Row row : sheet) {
        	rowNum++;
        	if (rowNum == 1) {
                continue;
            }
        	// 校验参数
            String validateRes = "";
            String tradeNo = "";
            LOGGER.info("开始处理行数:{}", rowNum);
            //判断是否取到行数据
            if(null == row) {
            	 LOGGER.info("明细文件第{}行，没有获取到row", rowNum);
            	 error.append("该文件格式有误,请仔细检查后,重新上传").append("<br>");
            	 res.fail(WebErrorCode.DIFF_TEMPLATE.getCode(), error.toString());
            	 return res;
            }
           
            // 1.如果非物流款项付款明细模板校验;
            if (!ChargeBizType.TYPE_LOGISTICS.getCode().equals(bizType)) {
                CtUaFileCheck detail = new CtUaFileCheck();
                detail.setCustomType(getCellValue(row.getCell(6)));
                if (!StringUtil.isEmpty(detail.getCustomType())) {
                    setList.add(detail.getCustomType());
                }
                detail.setOrderType(getCellValue(row.getCell(1)));
                // 金额获取做特殊处理,支持数值和文本类型
                String amtStr = getCellValue(row.getCell(5));
                Double totalAmt = StringUtil.isNumber(amtStr) ? Double.parseDouble(amtStr) : Double.valueOf("0");
                if (CommonConstant.REFUND.equals(detail.getOrderType())) {
                    allAmt = Arith.sub(allAmt, totalAmt);
                } else {
                    allAmt = Arith.add(allAmt, totalAmt);
                }
                // 校验框架DecimalMin不支持小数
                if (totalAmt > 0 && totalAmt < 1) {
                    detail.setTotalAmt(1);
                } else {
                    detail.setTotalAmt(totalAmt);
                }
                detail.setTradeNo(getCellValue(row.getCell(0)));

                // 交易创建时间和交易支付时间处理
                detail.setCreateTime(getCellValueDate(row.getCell(2)));
                detail.setPayTime(getCellValueDate(row.getCell(3)));

                detail.setOldTradeNo(getCellValue(row.getCell(4)));
                detail.setPersonName(getCellValue(row.getCell(7)));
                detail.setPersonIdType(getCellValue(row.getCell(8)));
                detail.setPersonId(getCellValue(row.getCell(9)));
                detail.setOrgName(getCellValue(row.getCell(10)));
                detail.setOrgType(getCellValue(row.getCell(11)));
                detail.setOrgId(getCellValue(row.getCell(12)));
                if(!StringUtil.isEmpty(detail.getOrgId())){
                    orgIdSet.add(detail.getOrgId());
                }
                detail.setTransType(getCellValue(row.getCell(13)));
                detail.setAboardDuration(getCellValue(row.getCell(14)));
                detail.setProductName(getCellValue(row.getCell(15)));
                detail.setProductNum(getCellValue(row.getCell(16)));
                detail.setLogistcsCompName(getCellValue(row.getCell(17)));
                detail.setLogistcsWoNo(getCellValue(row.getCell(18)));
                detail.setMerchandiseType(getCellValue(row.getCell(19)));

                validateRes = ValidateBaseHibernateUtil.validate(detail);
                LOGGER.info("默认校验：{}", detail);

                StringBuilder additionalCheck = new StringBuilder(validateRes);
                //校验币种，指定外币购汇，如果是日元，明细申请金额不能为小数
                if("02".equals(exchangeType) && "JPY".equals(curType) && amtStr.matches("\\d+\\.\\d+$")){
                	additionalCheck.append("币种为日元时,明细申请金额必须为整数").append(";");	
                }
                
                // 根据客户类型校验参数
                if (CustomType.PERSON.getCode().equals(detail.getCustomType())) {
                    additionalCheck.append(ValidateBaseHibernateUtil.validateFrist(detail));
                    if (BigDecimal.valueOf(detail.getTotalAmt()).compareTo(referAmt500) < 1) {
                        mergeTotalRow++;
                    }
                } else if (CustomType.ORG.getCode().equals(detail.getCustomType())) {
                    additionalCheck.append(ValidateBaseHibernateUtil.validateSecond(detail));
                }
                // 根据业务类型校验参数
                if (ChargeBizType.TYPE_GOODS_TRADE.getCode().equals(bizType) && StringUtil.isEmpty(detail.getTransType())) {
                    // 货物贸易
                    additionalCheck.append("发货方式不能为空").append(";");
                } else if (ChargeBizType.TYPE_ABROAD_EDUCATION.getCode().equals(bizType)) {
                    // 留学教育
                    if (StringUtil.isEmpty(detail.getAboardDuration())) {
                        additionalCheck.append("留学者计划境外居留时间不能为空").append(";");
                    }
                    if (!StringUtil.isEmpty(detail.getOrderType()) && TransType.BACK_MONEY.getCode().equals(detail.getOrderType())) {
                        additionalCheck.append("留学缴费业务交易性质不能为退款").append(";");
                    }
                }
                // 校验退款
                if (CommonConstant.REFUND.equals(detail.getOrderType()) && StringUtil.isEmpty(detail.getOldTradeNo())) {
                    additionalCheck.append("退款原明细订单号不能为空").append(";");
                }

                //判断商户真实性材料合并方式，如果是合并,校验物流名称以及物流单号必填
                if (ChargeBizType.TYPE_GOODS_TRADE.getCode().equals(bizType) && CommonConstant.Y.equals(superviseCombineTypeAuth)) {
   	       			this.checklogistcsInfo(detail.getLogistcsCompName(), detail.getLogistcsWoNo(), additionalCheck);
          			
                }

                // 身份证格式算法校验 StringUtil.isIDNumber(String str)
                if (!StringUtil.isEmpty(detail.getPersonId()) && !StringUtil.isIDNumber(detail.getPersonId())) {
                    LOGGER.info("身份证格式不正确");
                    additionalCheck.append("业务明细单号:").append(detail.getTradeNo()).append("，身份证信息有误");
                }

                validateRes = additionalCheck.toString();
                tradeNo = detail.getTradeNo();
                // 整行为空则判定为最后一行,退出校验
                if (StringUtil.allEmpty(tradeNo, detail.getOrderType(), detail.getCreateTime(), detail.getPayTime(), detail.getOldTradeNo(), amtStr, detail.getCustomType(), detail.getPersonName(), detail.getPersonIdType(), detail.getPersonId(), detail.getOrgName(), detail.getOrderType(), detail.getOrgId(), detail.getTransType(), detail.getAboardDuration())) {
                    if (rowNum - 1 != detailAmount) {
                        LOGGER.error("申请笔数与实际上传笔数不一致,申请笔数:{},实际上传笔数:{}", detailAmount, rowNum - 1);
                        error.append("申请笔数与实际上传笔数不一致,申请笔数:").append(detailAmount).append(",实际上传笔数:").append(rowNum - 1).append("<br>");
                    }
                    if (rowNum - 1 > 20000) {
                        error.append("明细笔数:").append(rowNum - 1).append("条,超过20000条;").append("<br>");
                    }
                    realTotalRow = rowNum-1;
                    break;
                }

            } else {
                // 2.物流款项付款明细模板校验 ;
                LogisticsFundsPayFileCheck fileCheck = new LogisticsFundsPayFileCheck();
                fileCheck.setCustomType(getCellValue(row.getCell(4)));
                if (!StringUtil.isEmpty(fileCheck.getCustomType())) {
                    setList.add(fileCheck.getCustomType());
                }
                fileCheck.setOrderType("01");
                // 金额获取做特殊处理,支持数值和文本类型
                String amtStr = getCellValue(row.getCell(3));
                Double totalAmt = StringUtil.isNumber(amtStr) ? Double.parseDouble(amtStr) : Double.valueOf("0");
                allAmt = Arith.add(allAmt, totalAmt);
                // 校验框架DecimalMin不支持小数
                if (totalAmt > 0 && totalAmt < 1) {
                    fileCheck.setTotalAmt(1);
                } else {
                    fileCheck.setTotalAmt(totalAmt);
                }
                fileCheck.setTradeNo(getCellValue(row.getCell(0)));

                // 日期格式特殊处理
                fileCheck.setCreateTime(getCellValueDate(row.getCell(1)));
                fileCheck.setPayTime(getCellValueDate(row.getCell(2)));

                fileCheck.setPersonName(getCellValue(row.getCell(5)));
                fileCheck.setPersonIdType(getCellValue(row.getCell(6)));
                fileCheck.setPersonId(getCellValue(row.getCell(7)));
                fileCheck.setOrgName(getCellValue(row.getCell(8)));
                fileCheck.setOrgType(getCellValue(row.getCell(9)));
                fileCheck.setOrgId(getCellValue(row.getCell(10)));
                if(!StringUtil.isEmpty(fileCheck.getOrgId())){
                    orgIdSet.add(fileCheck.getOrgId());
                }
                //fileCheck.setTransType(getCellValue(row.getCell(11)));
                fileCheck.setFreightType(getCellValue(row.getCell(11)));
                fileCheck.setProductName(getCellValue(row.getCell(12)));
                fileCheck.setProductNum(getCellValue(row.getCell(13)));
                fileCheck.setLogistcsCompName(getCellValue(row.getCell(14)));
                fileCheck.setLogistcsWoNo(getCellValue(row.getCell(15)));
                fileCheck.setMerchandiseType(getCellValue(row.getCell(16)));

                validateRes = ValidateBaseHibernateUtil.validate(fileCheck);

                // 根据客户类型校验参数
                StringBuilder additionalCheck = new StringBuilder(validateRes);
                if (CustomType.PERSON.getCode().equals(fileCheck.getCustomType())) {
                    additionalCheck.append(ValidateBaseHibernateUtil.validateFrist(fileCheck));
                    if (BigDecimal.valueOf(fileCheck.getTotalAmt()).compareTo(referAmt500) < 1) {
                        mergeTotalRow++;
                    }
                } else if (CustomType.ORG.getCode().equals(fileCheck.getCustomType())) {
                    additionalCheck.append(ValidateBaseHibernateUtil.validateSecond(fileCheck));
                }

               //判断商户真实性材料合并方式：如果是合并方式，则物流企业名称、物流单号必填，以及物流企业名称长度为256
                if (CommonConstant.Y.equals(superviseCombineTypeAuth)) {
	       			this.checklogistcsInfo(fileCheck.getLogistcsCompName(), fileCheck.getLogistcsWoNo(), additionalCheck);
       		 	}

                // 身份证格式算法校验 StringUtil.isIDNumber(String str)
                if (!StringUtil.isEmpty(fileCheck.getPersonId()) && !StringUtil.isIDNumber(fileCheck.getPersonId())) {
                    LOGGER.info("身份证格式不正确");
                    additionalCheck.append("业务明细单号:").append(fileCheck.getTradeNo()).append("，身份证信息有误");
                }

                validateRes = additionalCheck.toString();
                tradeNo = fileCheck.getTradeNo();
                // 整行为空则判定为最后一行,退出校验
                if (StringUtil.allEmpty(tradeNo, fileCheck.getCreateTime(), fileCheck.getPayTime(), amtStr, fileCheck.getCustomType(), fileCheck.getPersonName(), fileCheck.getPersonIdType(), fileCheck.getPersonId(), fileCheck.getOrgName(), fileCheck.getOrderType(), fileCheck.getOrgId(), fileCheck.getFreightType())) {
                    if (rowNum - 1 != detailAmount) {
                        LOGGER.error("申请笔数与实际上传笔数不一致,申请笔数:{},实际上传笔数:{}", detailAmount, rowNum - 1);
                        error.append("申请笔数与实际上传笔数不一致,申请笔数:").append(detailAmount).append(",实际上传笔数:").append(rowNum - 1).append("<br>");
                    }
                    if (rowNum - 1 > 20000) {
                        error.append("明细笔数:").append(rowNum - 1).append("条,超过20000条;").append("<br>");
                    }
                    realTotalRow = rowNum - 1;
                    break;
                }
            }
            
            if (!StringUtil.isEmpty(validateRes)) {
                error.append("第").append(rowNum).append("条, 订单号").append(tradeNo).append(", 失败原因：").append(validateRes).append("<br>");
            }

            // 非空最后一行校验 总条数
            if (rowNum == totalRow + 1) {
                if (totalRow != detailAmount) {
                    LOGGER.error("申请笔数与实际上传笔数不一致,申请笔数:{},实际上传笔数:{}", detailAmount, rowNum-1);
                    error.append("申请笔数与实际上传笔数不一致,申请笔数:").append(detailAmount).append(",实际上传笔数:").append(rowNum-1).append("<br>");

                }
                if (totalRow> 20000) {
                    error.append("明细笔数:").append(rowNum-1).append("条,超过20000条;").append("<br>");
                }
                realTotalRow = rowNum - 1;
            }

        }

        //企业客户需要都报备完成
        if(CollectionUtils.isNotEmpty(orgIdSet)){
            StringBuilder failOrgIds = new StringBuilder();
            for(String orgId : orgIdSet){
                Map<String, String> param = new HashMap<String, String>();
                param.put("companyCode", orgId);
                ApiResDto<String> checkResult = domesticMerchantInfoService.checkRegistStatusByCompanyCode(param);
                if(checkResult.isSuccess()){
                    if("false".equals(checkResult.getResult())){
                        failOrgIds.append(orgId).append(",");
                    }
                }else{
                    error.append("企业客户查询异常");
                    break;
                }
            }
            if(!StringUtil.isEmpty(failOrgIds.toString())){
                error.append("企业客户未完成报备，机构证件号为:").append(failOrgIds.toString()).append("<br>");
            }
            // 苏宁反洗钱审核
            StringBuilder failOrgIds2 = new StringBuilder();
            for(String orgId : orgIdSet) {
                Map<String, String> param = new HashMap<String, String>();
                param.put("companyCode", orgId);
                ApiResDto<String> checkResult = domesticMerchantInfoService.checkSnAntiMoneyStatusByCompanyCode(param);
                if(checkResult.isSuccess()) {
                    if("false".equals(checkResult.getResult())) {
                        failOrgIds2.append(orgId).append(",");
                    }
                } else {
                    error.append("企业客户审核查询异常");
                    break;
                }
            }
            if(!StringUtil.isEmpty(failOrgIds2.toString())){
                error.append("企业客户审核未通过，机构证件号为:").append(failOrgIds2.toString()).append("<br>");
            }
        }

        // 收款人类型唯一校验
        if (setList.size() != 1) {
            LOGGER.info("收款人类型不唯一; 收款人类型种类数:{}",setList.size());
            error.append("明细文件中客户类型不唯一, 必须都为:01 或都为:02").append("<br>");
        }

        // 合并后总行数  20210127版本去掉合并笔数不超过5000笔限制
        /*int mergedTotalRow = realTotalRow - mergeTotalRow + 1;
        LOGGER.info("实际申请笔数:{},合并后笔数:{}", realTotalRow, mergedTotalRow);
        if (mergedTotalRow > 5000) {
            error.append("合并后笔数:").append(mergedTotalRow).append("条,超过5000条;").append("<br>");
        }*/
        if (BigDecimal.valueOf(allAmt).compareTo(BigDecimal.valueOf(payAmt)) != 0) {
            LOGGER.error("申请金额与明细金额之和不一致.申请金额:{},明细金额之和:{}", payAmt, allAmt);
            error.append("申请金额与明细金额之和不一致,").append("申请金额:").append(payAmt).append(",明细金额之和:").append(allAmt).append("<br>");
        }
        if (StringUtil.isEmpty(error.toString())) {
            return upload(targetFile, user);
        } else {
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        }
    }

	private void checklogistcsInfo(String logistcsCompName, String logistcsWoNo, StringBuilder additionalCheck) {
		if(StringUtil.isEmpty(logistcsCompName) || logistcsCompName.length() > 256) {
			additionalCheck.append("物流企业名称不能为空,最大长度为256字符.");
		}
		
		if(StringUtil.isEmpty(logistcsWoNo)) {
			additionalCheck.append("物流单号不能为空.");
		}
	}
    

    /**
     * 
     * 功能描述: 批量购付汇文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @throws ParseException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto checkBatchFile(Workbook workbook, MultipartFile targetFile, String user, String superviseCombineTypeAuth) throws IOException, ExcelForamatException, ParseException {
        FileUploadResDto res = new FileUploadResDto();
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 总行数
        int totalRow = sheet.getLastRowNum();
        LOGGER.info("不含表头，明细总数：{}", totalRow);
        // 实际总行数
        int realTotalRow = 0;
        if (totalRow == IndexConstant.ZERO) {
            error.append("明细文件为空");
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
        }
        Set<String> setList = new HashSet<String>();
        // 遍历每一行取值
        int rowNum = 0;
        for (Row row : sheet) {
            // 读文件
            rowNum++;
            // 标题不读
            if (rowNum == 1) {
                continue;
            }
            CtUaBatchFileCheck detail = new CtUaBatchFileCheck();
            detail.setBizDetailType(getCellValue(row.getCell(0)));
            detail.setTradeNo(getCellValue(row.getCell(1)));
            detail.setPayeeMerchantCode(getCellValue(row.getCell(2)));
            // 金额获取做特殊处理,支持数值和文本类型
            String amtStr = getCellValue(row.getCell(3));
            Double totalAmt = StringUtil.isNumber(amtStr) ? Double.parseDouble(amtStr) : Double.valueOf("0");

            // 校验框架DecimalMin不支持小数
            if (totalAmt > 0 && totalAmt < 1) {
                detail.setTotalAmt(1);
            } else {
                detail.setTotalAmt(totalAmt);
            }

            // 日期格式处理
            detail.setCreateTime(getCellValueDate(row.getCell(4)));
            detail.setPayTime(getCellValueDate(row.getCell(5)));

            detail.setCustomType(getCellValue(row.getCell(6)));
            detail.setPersonName(getCellValue(row.getCell(7)));
            detail.setPersonIdType(getCellValue(row.getCell(8)));
            detail.setPersonId(getCellValue(row.getCell(9)));
            detail.setOrgName(getCellValue(row.getCell(10)));
            detail.setOrgType(getCellValue(row.getCell(11)));
            detail.setOrgId(getCellValue(row.getCell(12)));
            detail.setAboardDuration(getCellValue(row.getCell(13)));
            detail.setAmtType(getCellValue(row.getCell(14)));
            detail.setPayeeBankSwift(getCellValue(row.getCell(15)));
            detail.setRemark(getCellValue(row.getCell(16)));
            detail.setExchangeType(getCellValue(row.getCell(17)));
            // 校验参数
            StringBuilder validateRes = new StringBuilder(ValidateBaseHibernateUtil.validate(detail));

            if (CustomType.PERSON.getCode().equals(detail.getCustomType())) {
                validateRes.append(ValidateBaseHibernateUtil.validateFrist(detail));
            } else if (CustomType.ORG.getCode().equals(detail.getCustomType())) {
                validateRes.append(ValidateBaseHibernateUtil.validateSecond(detail));
            }

            // 整行为空则判定为最后一行,退出校验
            if (StringUtil.allEmpty(detail.getBizDetailType(), detail.getTradeNo(), detail.getPayeeMerchantCode(), amtStr, detail.getCreateTime(), detail.getPayTime(), detail.getCustomType(), detail.getPersonName(), detail.getPersonIdType(), detail.getPersonId(), detail.getOrgName(), detail.getOrgType(), detail.getOrgId(), detail.getAboardDuration(), detail.getAmtType(), detail.getPayeeBankSwift(), detail.getRemark(), detail.getExchangeType())) {
                realTotalRow = rowNum - 1;
                if (rowNum - 1 == IndexConstant.ZERO) {
                    error.append("明细文件为空");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
                if (rowNum - 1 > IndexConstant.ONE_HUNDRED) {
                    error.append("明细笔数:").append(rowNum - 1).append("条,超过100条;").append("<br>");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
                break;
            }

            if (!StringUtil.isEmpty(validateRes.toString())) {
                error.append("第").append(rowNum).append("笔, 订单号").append(detail.getTradeNo()).append(", 失败原因：").append(validateRes).append("<br>");
            }
            setList.add(detail.getCustomType());
            // 非空最后一行校验 总条数
            if (rowNum == totalRow + 1) {
                realTotalRow = rowNum-1;
                if (totalRow > IndexConstant.ONE_HUNDRED) {
                    error.append("明细笔数:").append(rowNum-1).append("条,超过100条;").append("<br>");
                    res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
                }
            }
        }
        // 客户类型唯一校验
        if (setList.size() != 1) {
            LOGGER.info("明细文件中客户类型不唯一,客户类型种类数:{}",setList.size());
            error.append("明细文件中客户类型不唯一,必须都为:01 或都为:02.").append("<br>");
            res.fail(WebErrorCode.CHECK_FAIL.getCode(),error.toString());
        }

        if (StringUtil.isEmpty(error.toString())) {
            res = upload(targetFile, user);
            res.setDetailAmount(realTotalRow);
            return res;
        } else {
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        }
    }

    /**
     * 
     * 功能描述: 上传购付汇文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto upload(MultipartFile targetFile, String user) throws IOException {
        FileUploadResDto res = new FileUploadResDto();
        String mimeType = "";
        String fileType = "";
        String fileName = targetFile.getOriginalFilename();
        // 区分文件类型
        if (fileName.endsWith(CommonConstant.XLS)) {
            mimeType = OSSMimeType.XLS;
            fileType = CommonConstant.XLS;
        } else if (fileName.endsWith(CommonConstant.XLSX)) {
            mimeType = OSSMimeType.XLSX;
            fileType = CommonConstant.XLSX;
        }
        OSSUploadParams params = new OSSUploadParams(OSSBucket.DETAIL_FILE, targetFile, mimeType);
        // 上传路径
        params.setRemotePath(user.replaceFirst("0+", "") + new Date().getTime() + "." + fileType);
        LOGGER.info("明细文件上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        res.setFileAddress(fileId);
        return res;
    }

    /**
     *
     * 功能描述: 上传店铺管理文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto uploadStoreCollSett(MultipartFile targetFile, String user, int detailAmount) throws IOException {
        FileUploadResDto res = new FileUploadResDto();
        String mimeType = "";
        String fileType = "";
        String fileName = targetFile.getOriginalFilename();
        // 区分文件类型
        if (fileName.endsWith(CommonConstant.XLS)) {
            mimeType = OSSMimeType.XLS;
            fileType = CommonConstant.XLS;
        } else if (fileName.endsWith(CommonConstant.XLSX)) {
            mimeType = OSSMimeType.XLSX;
            fileType = CommonConstant.XLSX;
        }
        OSSUploadParams params = new OSSUploadParams(OSSBucket.STORE_HANDLE_FILE, targetFile, mimeType);
        // 上传路径
        params.setRemotePath(user.replaceFirst("0+", "") + new Date().getTime() + "." + fileType);
        LOGGER.info("明细文件上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        res.setFileAddress(fileId);
        res.setDetailAmount(detailAmount);
        return res;
    }

    /**
     * 
     * 功能描述: 上传收结汇文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto uploadCollSett(MultipartFile targetFile, String user, int detailAmount) throws IOException {
        FileUploadResDto res = new FileUploadResDto();
        String mimeType = "";
        String fileType = "";
        String fileName = targetFile.getOriginalFilename();
        // 区分文件类型
        if (fileName.endsWith(CommonConstant.XLS)) {
            mimeType = OSSMimeType.XLS;
            fileType = CommonConstant.XLS;
        } else if (fileName.endsWith(CommonConstant.XLSX)) {
            mimeType = OSSMimeType.XLSX;
            fileType = CommonConstant.XLSX;
        }
        OSSUploadParams params = new OSSUploadParams(OSSBucket.COLL_SETT_FILE, targetFile, mimeType);
        // 上传路径
        params.setRemotePath(user.replaceFirst("0+", "") + new Date().getTime() + "." + fileType);
        LOGGER.info("明细文件上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        res.setFileAddress(fileId);
        res.setDetailAmount(detailAmount);
        return res;
    }

    /**
     * 
     * 功能描述: 上传批付文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private FileUploadResDto uploadBPFile(MultipartFile targetFile, String user) throws IOException {
        FileUploadResDto res = new FileUploadResDto();
        String mimeType = "";
        String fileType = "";
        String fileName = targetFile.getOriginalFilename();
        // 区分文件类型
        if (fileName.endsWith(CommonConstant.XLS)) {
            mimeType = OSSMimeType.XLS;
            fileType = CommonConstant.XLS;
        } else if (fileName.endsWith(CommonConstant.XLSX)) {
            mimeType = OSSMimeType.XLSX;
            fileType = CommonConstant.XLSX;
        }
        OSSUploadParams params = new OSSUploadParams(OSSBucket.BATCH_PAYMENT_FILE, targetFile, mimeType);
        // 上传路径
        params.setRemotePath(user.replaceFirst("0+", "") + new Date().getTime() + "." + fileType);
        LOGGER.info("明细文件上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        res.setFileAddress(fileId);
        return res;
    }

}
