/**
 * 
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import static com.suning.epp.eppscbp.util.ExcelUtil.getCellValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.monitorjbl.xlsx.StreamingReader;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.IndexConstant;
import com.suning.epp.eppscbp.common.enums.BatchPayFlag;
import com.suning.epp.eppscbp.common.enums.CbpDetailFlag;
import com.suning.epp.eppscbp.common.enums.CbpProductType;
import com.suning.epp.eppscbp.common.enums.CbpStatus;
import com.suning.epp.eppscbp.common.enums.CbpSupStatus;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CtCollAndSettleOrderStatus;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.UploadTradeFileCheck;
import com.suning.epp.eppscbp.dto.req.BatchOrderQueryDto;
import com.suning.epp.eppscbp.dto.req.BatchOrderRemitQueryDto;
import com.suning.epp.eppscbp.dto.req.CollAndSettleQueryDto;
import com.suning.epp.eppscbp.dto.req.SingleOrderQueryDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchTradeOrderQueryResDto;
import com.suning.epp.eppscbp.dto.res.BatchTradeOrderRemitQueryResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleDetailResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleQueryResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.SingleTradeOrderQueryResDto;
import com.suning.epp.eppscbp.dto.res.TradeOrderDetailQueryResDto;
import com.suning.epp.eppscbp.rsf.service.OrderQueryService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.ExcelUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSMimeType;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;
import com.suning.epp.eppscbp.util.validator.ValidateBaseHibernateUtil;

/**
 * @author 17080704
 *
 */
@Service("orderQueryService")
public class OrderQueryServiceImpl implements OrderQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryServiceImpl.class);

    // 货物贸易监管上传方法名
    private static final String TRADE_SUPERVISE_UPLOAD = "tradeSuperviseUpload";
    // 留学教育监管上传方法名
    private static final String STUDY_SUPERVISE_UPLOAD = "studySuperviseUpload";
    // 收结汇监管上传方法名
    private static final String ECS_SUPERVISE_UPLOAD = "upload";

    // 单笔交易单查询方法
    private static final String SINGLE_ORDER_QUERY = "singleTradeOrderQuery";
    // 批量交易单查询方法
    private static final String BATCH_ORDER_QUERY = "batchTradeOrderQuery";
    // 批量交易单查询方法
    private static final String BATCH_ORDER_REMIT_QUERY = "batchTradeOrderDetailQuery";
    // 指定交易单详情查询方法
    private static final String ORDER_DETAIL_QUERY = "tradeOrderDetailQuery";
    // 收结汇交易单查询方法
    private static final String BATCH_COLL_SETT_ORDER_QUERY = "batchCollAndSettleOrderQuery";
    // 指定收结汇交易单查询方法
    private static final String SINGLE_COLL_SETT_ORDER_QUERY = "singleCollAndSettleOrderQuery";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    @Autowired
    private ApplyFileUploadService applyFileUploadService;

    /**
     * 单笔交易单查询
     */
    @Override
    public ApiResDto<List<SingleTradeOrderQueryResDto>> singleOrderQuery(SingleOrderQueryDto reqDto) {
        ApiResDto<List<SingleTradeOrderQueryResDto>> apiResDto = new ApiResDto<List<SingleTradeOrderQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            SingleOrderQueryDto requestParam = new SingleOrderQueryDto();
            // 接口入参预处理
            LOGGER.info("开始单笔交易单查询入参预处理");
            // 户头号
            requestParam.setPayerAccount(reqDto.getPayerAccount());
            // 业务单号
            requestParam.setBusinessNo(StringUtil.isEmpty(reqDto.getBusinessNo()) ? null : reqDto.getBusinessNo());
            // 业务类型
            requestParam.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(reqDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(reqDto.getBizType()));
            // 产品类型
            requestParam.setProductType(StringUtil.isEmpty(CbpProductType.getCodeFromDescription(reqDto.getProductType())) ? null : CbpProductType.getCodeFromDescription(reqDto.getProductType()));
            // 状态
            requestParam.setStatus(StringUtil.isEmpty(CbpStatus.getCodeFromDescription(reqDto.getStatus())) ? null : CbpStatus.getCodeFromDescription(reqDto.getStatus()));
            // 监管状态
            requestParam.setSupStatus(StringUtil.isEmpty(CbpSupStatus.getCodeFromDescription(reqDto.getSupStatus())) ? null : CbpSupStatus.getCodeFromDescription(reqDto.getSupStatus()));
            // 创建开始时间
            requestParam.setCreatOrderStartTime(StringUtil.isEmpty(reqDto.getCreatOrderStartTime()) ? null : reqDto.getCreatOrderStartTime());
            // 创建结束时间
            requestParam.setCreatOrderEndTime(StringUtil.isEmpty(reqDto.getCreatOrderEndTime()) ? null : reqDto.getCreatOrderEndTime());
            // 收款方商户号
            requestParam.setPayeeMerchantCode(StringUtil.isEmpty(reqDto.getPayeeMerchantCode()) ? null : reqDto.getPayeeMerchantCode());
            // 收款方商户名称
            requestParam.setPayeeMerchantName(StringUtil.isEmpty(reqDto.getPayeeMerchantName()) ? null : reqDto.getPayeeMerchantName());
            // 当前页
            requestParam.setCurrentPage(StringUtil.isEmpty(reqDto.getCurrentPage()) ? null : reqDto.getCurrentPage());
            // 明细校验状态
            requestParam.setDetailFlag(StringUtil.isEmpty(CbpDetailFlag.getCodeFromDescription(reqDto.getDetailFlag())) ? null : CbpDetailFlag.getCodeFromDescription(reqDto.getDetailFlag()));
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestParam);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.ORDER_QUERY, SINGLE_ORDER_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<SingleTradeOrderQueryResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), SingleTradeOrderQueryResDto.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setPage(pageInfo);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /**
     * 批量交易单查询
     */
    @Override
    public ApiResDto<List<BatchTradeOrderQueryResDto>> batchOrderQuery(BatchOrderQueryDto reqDto) {
        ApiResDto<List<BatchTradeOrderQueryResDto>> apiResDto = new ApiResDto<List<BatchTradeOrderQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行批量交易单查询");
            BatchOrderQueryDto requestParam = new BatchOrderQueryDto();
            // 接口入参预处理
            LOGGER.info("开始批量交易单查询入参预处理");
            // 户头号
            requestParam.setPayerAccount(reqDto.getPayerAccount());
            // 业务单号
            requestParam.setBusinessNo(StringUtil.isEmpty(reqDto.getBusinessNo()) ? null : reqDto.getBusinessNo());
            // 状态
            requestParam.setStatus(StringUtil.isEmpty(CbpStatus.getCodeFromDescription(reqDto.getStatus())) ? null : CbpStatus.getCodeFromDescription(reqDto.getStatus()));
            // 监管状态
            requestParam.setSupStatus(StringUtil.isEmpty(CbpSupStatus.getCodeFromDescription(reqDto.getSupStatus())) ? null : CbpSupStatus.getCodeFromDescription(reqDto.getSupStatus()));
            // 创建开始时间
            requestParam.setCreatOrderStartTime(StringUtil.isEmpty(reqDto.getCreatOrderStartTime()) ? null : reqDto.getCreatOrderStartTime());
            // 创建结束时间
            requestParam.setCreatOrderEndTime(StringUtil.isEmpty(reqDto.getCreatOrderEndTime()) ? null : reqDto.getCreatOrderEndTime());
            // 当前页
            requestParam.setCurrentPage(StringUtil.isEmpty(reqDto.getCurrentPage()) ? null : reqDto.getCurrentPage());
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestParam);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.ORDER_QUERY, BATCH_ORDER_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                // 成功
                List<BatchTradeOrderQueryResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), BatchTradeOrderQueryResDto.class);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setResponseMsg("");
                apiResDto.setResult(resultList);
                apiResDto.setPage(pageInfo);
            } else {
                // 失败
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /**
     * 批量明细汇出查询
     */
    @Override
    public ApiResDto<List<BatchTradeOrderRemitQueryResDto>> batchOrderRemitQuery(BatchOrderRemitQueryDto reqDto) {
        ApiResDto<List<BatchTradeOrderRemitQueryResDto>> apiResDto = new ApiResDto<List<BatchTradeOrderRemitQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行批量明细汇出查询");
            BatchOrderRemitQueryDto requestParam = new BatchOrderRemitQueryDto();
            // 接口入参预处理
            LOGGER.info("开始批量明细汇出查询入参预处理");
            // 户头号
            requestParam.setPayerAccount(reqDto.getPayerAccount());
            // 业务单号
            requestParam.setBusinessNo(StringUtil.isEmpty(reqDto.getBusinessNo()) ? null : reqDto.getBusinessNo());
            // 业务子单号
            requestParam.setSubBusinessNo(StringUtil.isEmpty(reqDto.getSubBusinessNo()) ? null : reqDto.getSubBusinessNo());
            // 状态
            requestParam.setStatus(StringUtil.isEmpty(CbpStatus.getCodeFromDescription(reqDto.getStatus())) ? null : CbpStatus.getCodeFromDescription(reqDto.getStatus()));
            // 收款方商户号
            requestParam.setPayeeMerchantCode(StringUtil.isEmpty(reqDto.getPayeeMerchantCode()) ? null : reqDto.getPayeeMerchantCode());
            // 收款方商户名称
            requestParam.setPayeeMerchantName(StringUtil.isEmpty(reqDto.getPayeeMerchantName()) ? null : reqDto.getPayeeMerchantName());
            // 创建开始时间
            requestParam.setCreatOrderStartTime(StringUtil.isEmpty(reqDto.getCreatOrderStartTime()) ? null : reqDto.getCreatOrderStartTime());
            // 创建结束时间
            requestParam.setCreatOrderEndTime(StringUtil.isEmpty(reqDto.getCreatOrderEndTime()) ? null : reqDto.getCreatOrderEndTime());
            // 当前页
            requestParam.setCurrentPage(StringUtil.isEmpty(reqDto.getCurrentPage()) ? null : reqDto.getCurrentPage());
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestParam);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.ORDER_QUERY, BATCH_ORDER_REMIT_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                // 成功
                List<BatchTradeOrderRemitQueryResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), BatchTradeOrderRemitQueryResDto.class);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setResponseMsg("");
                apiResDto.setResult(resultList);
                apiResDto.setPage(pageInfo);
            } else {
                // 失败
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * 指定交易单详情查询
     */
    @Override
    public ApiResDto<TradeOrderDetailQueryResDto> detailInfoQuery(String payerAccount, String subBusinessNo) {
        ApiResDto<TradeOrderDetailQueryResDto> apiResDto = new ApiResDto<TradeOrderDetailQueryResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行指定交易单详情查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            inputParam.put("subBusinessNo", subBusinessNo);
            // 门户写死平台标识
            inputParam.put("platformCode", "cbp");
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.ORDER_QUERY, ORDER_DETAIL_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                TradeOrderDetailQueryResDto result = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.CONTEXT), TradeOrderDetailQueryResDto.class);
                apiResDto.setResult(result);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    // 调用货物贸易监管上传
    @Override
    public ApiResDto<String> tradeSuperviseUpload(String businessNo, String fileAddress, String payerAccount, String type) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("payerAccount", payerAccount);
            request.put("businessNo", businessNo);
            request.put("fileAddress", fileAddress);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求跨境货物贸易监管上传的参数:{}", requestStr);
            Map<String, String> response = Maps.newHashMap();
            if (StringUtil.isEmpty(type)) {
            	request.put("platformCode", EPPSCBPConstants.ECS);
                response = generalRsfService.invoke(ApiCodeConstant.SUPERVISE_UPLOAD, TRADE_SUPERVISE_UPLOAD, new Object[] { request });
            } else {
                // 收结汇调用新接口，增加平台代码参数
                request.put("platformCode", type);
                response = generalRsfService.invoke(ApiCodeConstant.ECS_SUPERVISE_UPLOAD, ECS_SUPERVISE_UPLOAD, new Object[] { request });
            }
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("请求跨境货物贸易监管上传返回参数:{}", responseStr);
            // 返回码
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    // 触发留学教育监管上传
    @Override
    public ApiResDto<String> studySuperviseUpload(String businessNo, String payerAccount) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("businessNo", businessNo);
            request.put("payerAccount", payerAccount);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求跨境留学教育监管上传的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.SUPERVISE_UPLOAD, STUDY_SUPERVISE_UPLOAD, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("请求跨境留学教育监管上传返回参数:{}", responseStr);
            // 返回码
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMsg = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                // 成功
                apiResDto.setResponseMsg("");
            } else {
                // 失败
                apiResDto.setResponseMsg(responseMsg);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;

    }

    /**
     * 货物货物贸易监管文件校验上传
     * 
     * @throws ExcelForamatException
     */
    @Override
    public FileUploadResDto uploadFile(MultipartFile targetFile, String user) throws IOException, ExcelForamatException {
        return parseFile(targetFile, user);
    }

    // 解析校验货物贸易文件
    private FileUploadResDto parseFile(MultipartFile targetFile, String user) throws IOException, ExcelForamatException {
        FileUploadResDto res = new FileUploadResDto();
        if (targetFile.isEmpty()) {
            LOGGER.info("文件为空或文件不存在");
            res.fail(WebErrorCode.FILE_NOT_EXIST.getCode(), WebErrorCode.FILE_NOT_EXIST.getDescription());
            return res;
        }

        LOGGER.info("贸易文件校验操作开始 fileName:{}", targetFile.getOriginalFilename());
        Workbook workbook = null;
        String fileName = targetFile.getOriginalFilename();
        InputStream is = targetFile.getInputStream();
        try {
            if (fileName.endsWith(CommonConstant.XLSX)) {
            	 workbook = StreamingReader.builder().rowCacheSize(10) // 缓存到内存中的行数，默认是10
                         .bufferSize(4096) // 读取资源时，缓存到内存的字节大小，默认是1024
                         .open(is);
            	 return checkFile(workbook, targetFile, user);
            } else {
                LOGGER.info("不支持的文件类型");
                res.fail(WebErrorCode.FILE_TYPE_MISMATCH.getCode(), WebErrorCode.FILE_TYPE_MISMATCH.getDescription());
                return res;
            }
        } catch (Exception e) {
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

    // 校验监管文件
    private FileUploadResDto checkFile(Workbook workbook, MultipartFile targetFile, String user) throws IOException, ExcelForamatException {
    	//真实性材料合并权限
    	String superviseCombineTypeAuth = applyFileUploadService.getSuperviseCombineTypeAuth(user);
    	
    	FileUploadResDto res = new FileUploadResDto();
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        
        // 返回值 表示为n(从0开始)的当前工作表的最后一行的行号
        int totalRow = sheet.getLastRowNum();
        if (totalRow < IndexConstant.ONE) {
            error.append("货物贸易监管文件无内容");
            res.fail(WebErrorCode.FILE_NO_CONTENT.getCode(), error.toString());
        }
        
        Set<String> set = new HashSet<String>();
        
        int rowNum = 0;
        for (Row row : sheet) {
            // 读文件
            rowNum++;
            // 标题不读
            if (rowNum == 1) {
            	String column0 = ExcelUtil.getCellValue(row.getCell(0));
                String column1 = ExcelUtil.getCellValue(row.getCell(1));
                String column2 = ExcelUtil.getCellValue(row.getCell(2));
                if(!CommonConstant.ORDER_NO.equals(column0) || !CommonConstant.LOGISTCS_COMP_NAME.equals(column1) || !CommonConstant.LOGISTCS_WO_NO.equals(column2)) {
                	error.append("上传文件表头有误，与模板不一致");
                	res.fail(WebErrorCode.FILE_NO_CONTENT.getCode(), error.toString());
                	return res;
                }
                continue;
            }

            // 防止文件格式有误，导致空指针
            if (null == row) {
                LOGGER.info("监管文件第{}行，没有获取到row", rowNum);
                error.append("该文件格式有误,请仔细检查后,重新上传").append("<br>");
                res.fail(WebErrorCode.DIFF_TEMPLATE.getCode(), error.toString());
                return res;
            }

            UploadTradeFileCheck detail = new UploadTradeFileCheck();
            detail.setTradeNo(getCellValue(row.getCell(0)));
            detail.setLogistcsCompName(getCellValue(row.getCell(1)));
            detail.setLogistcsWoNo(getCellValue(row.getCell(2)));
            String validateFirstRes = "";
            // 校验参数
            String validateRes = ValidateBaseHibernateUtil.validate(detail);

            // 如果真实性材料为不合并 物流企业名称/物流单号为必填；否则非必填，如果填了，校验长度
            if ("N".equals(superviseCombineTypeAuth)) {
                // 校验真实性材料为不合并方式， 物流企业名称/物流单号为必填
                validateFirstRes = ValidateBaseHibernateUtil.validateFrist(detail);
            }

            if (!StringUtil.isEmpty(validateRes)) {
                error.append("第").append(rowNum).append("行数据").append(", 失败原因：").append(validateRes).append("<br>");
            }
            
            if (!StringUtil.isEmpty(validateFirstRes)) {
                error.append("第").append(rowNum).append("行数据").append(", 失败原因：").append(validateFirstRes).append("<br>");
            }
            
            String trade = detail.getTradeNo();
            if (set.contains(trade)) {
                error.append("存在相同业务明细单号" + detail.getTradeNo()).append("<br>");
            }
        }

        if (StringUtil.isEmpty(error.toString())) {
            return upload(targetFile, user);
        } else {
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        }
    }

    // oss上传货物贸易监管文件
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
        OSSUploadParams params = new OSSUploadParams(OSSBucket.TRADE_SUPERVISE, targetFile, mimeType);
        // 上传路径
        params.setRemotePath(user.replaceFirst("0+", "") + new Date().getTime() + "." + fileType);
        LOGGER.info("上传货物贸易监管文件参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        res.setFileAddress(fileId);
        return res;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.OrderQueryService#collAndSettleQuery(com.suning.epp.eppscbp.dto.req. CollAndSettleQueryDto)
     */
    @Override
    public ApiResDto<List<CollAndSettleQueryResDto>> collAndSettleQuery(CollAndSettleQueryDto reqDto) {
        ApiResDto<List<CollAndSettleQueryResDto>> apiResDto = new ApiResDto<List<CollAndSettleQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            CollAndSettleQueryDto requestParam = new CollAndSettleQueryDto();
            // 接口入参预处理
            LOGGER.info("开始收结汇订单查询入参预处理");
            // 户头号
            requestParam.setPayerAccount(reqDto.getPayerAccount());
            // 业务单号
            requestParam.setBusinessNo(StringUtil.isEmpty(reqDto.getBusinessNo()) ? null : reqDto.getBusinessNo());
            // 货币
            requestParam.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(reqDto.getCurrency())) ? null : CurType.getCodeFromDescription(reqDto.getCurrency()));
            // 订单金额
            requestParam.setOrderAmt(StringUtil.isEmpty(reqDto.getOrderAmt()) ? null : StringUtil.moneyToLong(reqDto.getOrderAmt().trim()).toString());
            // 订单状态
            requestParam.setOrderState(StringUtil.isEmpty(reqDto.getOrderState()) ? null : CtCollAndSettleOrderStatus.getCodeFromDescription(reqDto.getOrderState()));
            // 批付状态
            requestParam.setBatchPayFlag(BatchPayFlag.getCodeFromDescription(reqDto.getBatchPayFlag()));
            // 创建开始时间
            requestParam.setCreatOrderStartTime(StringUtil.isEmpty(reqDto.getCreatOrderStartTime()) ? null : reqDto.getCreatOrderStartTime());
            // 创建结束时间
            requestParam.setCreatOrderEndTime(StringUtil.isEmpty(reqDto.getCreatOrderEndTime()) ? null : reqDto.getCreatOrderEndTime());
            // 业务类型
            requestParam.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(reqDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(reqDto.getBizType()));
            // 当前页
            requestParam.setCurrentPage(StringUtil.isEmpty(reqDto.getCurrentPage()) ? null : reqDto.getCurrentPage());
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestParam);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.COLL_SETT_ORDER_QUERY, BATCH_COLL_SETT_ORDER_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<CollAndSettleQueryResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), CollAndSettleQueryResDto.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setPage(pageInfo);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("收结汇订单查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.OrderQueryService#collAndSettleDetailInfoQuery(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<CollAndSettleDetailResDto> collAndSettleDetailInfoQuery(String payerAccount, String orderNo) {
        ApiResDto<CollAndSettleDetailResDto> apiResDto = new ApiResDto<CollAndSettleDetailResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行指定收结汇订单查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            inputParam.put("orderNo", orderNo);
            inputParam.put("platformCode", CommonConstant.CBP_CODE);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.COLL_SETT_ORDER_QUERY, SINGLE_COLL_SETT_ORDER_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                CollAndSettleDetailResDto result = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.CONTEXT), CollAndSettleDetailResDto.class);
                apiResDto.setResult(result);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("指定收结汇订单未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

}
