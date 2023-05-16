/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: MerchantInfoServiceImpl.java
 * Author:   17061545
 * Date:     2018年3月20日 下午2:17:29
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service.impl;

import static com.suning.epp.eppscbp.util.ExcelUtil.getCellValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import com.alibaba.fastjson.JSONObject;
import com.monitorjbl.xlsx.StreamingReader;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.IndexConstant;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CountryType;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.MerchantAccountCharacterEnum;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.UploadMerchantFileCheck;
import com.suning.epp.eppscbp.dto.req.MerchantInfoQueryDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.ColAndSettleMultiInfoDto;
import com.suning.epp.eppscbp.dto.res.CtFullAmtDto;
import com.suning.epp.eppscbp.dto.res.CtMerchantAuth;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.MerchantInfoQueryResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSMimeType;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;
import com.suning.epp.eppscbp.util.validator.ValidateBaseHibernateUtil;

/**
 * 〈商户管理接口〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("merchantInfoService")
public class MerchantInfoServiceImpl implements MerchantInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantInfoServiceImpl.class);

    // 商户权限查询
    private static final String QUERY_MERCHANT_AUTH = "queryMerchantAuth";
    // 商户信息和全额到账费用查询
    private static final String QUERY_AMT = "queryAmt";
    // 商户分页查询方法
    private static final String BATCH_QUERY_MERCHANTINFO = "batchQueryMerchantInfo";
    // 批量新增商户方法
    private static final String BATCH_ADD_MERCHANTINFO = "batchAddMerchantInfo";
    // 单笔新增修改商户方法
    private static final String MERCHANTINFO_MANAGE = "merchantInfoManage";
    // 单笔查询商户方法
    private static final String DETAIL_MERCHANTINFO = "detailsMerchantInfo";
    // 收结汇申请
    public static final String ARRIVAL_QUERY = "multipleInfoQuery";
    // 模糊查询数据获取
    public static final String MERCHANTINFO_SEARCH = "queryMerchantInfoForSearch";
    // 境外商户号查询
    public static final String MERCHANTINFO_IS_OPEN_COLL = "merchantInfoIsOpenColl";
    // 更新收件人邮箱地址
    private static final String UPDATE_RECIPIENT_EMAIL_ADDRE = "updateRecipientEmailAddre";
    // 查询操作员
    private static final String EXECUTOR = "executor";
    // 查询操作员
    private static final String SET_OPERATOR_AUTH = "setOperatorAuth";
    // 反洗钱命中返回码
    public static final String REVIEW_FIAL_CODE = "0027";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;
    
    @Autowired
    private GeneralRsfService<String> generalRsfServiceStr;

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.MerchantInfoService#queryMerchantInfo(java.lang.String)
     */
    @Override
    public Map<String, String> queryMerchantAuth(String payerAccount, String operatorCode) {
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            request.put(EPPSCBPConstants.OPERATORCODE, operatorCode);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询商户权限的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.QUERY_MERCHANT_AUTH, QUERY_MERCHANT_AUTH, request);
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("查询商户权限的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            if (response == null || !CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                LOGGER.error("查询商户权限结果未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("调用查询商户权限失败");
            } else {
                LOGGER.info("调用查询商户权限返回成功");
            }
            return response;
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.MerchantInfoService#queryMerchantMeg(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<CtFullAmtDto> queryMerchantMeg(String payerAccount, String message, String productType) {
        ApiResDto<CtFullAmtDto> apiResDto = new ApiResDto<CtFullAmtDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            request.put(EPPSCBPConstants.MESSAGE, message);
            request.put(EPPSCBPConstants.PRODUCT_TYPE, productType);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询商户信息的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.QUERY_AMT, QUERY_AMT, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("查询商户信息的返回参数:******");
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("调用查询商户信息返回成功");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                CtFullAmtDto ctfullamt = JSON.parseObject(context, CtFullAmtDto.class);
                apiResDto.setResult(ctfullamt);
                apiResDto.setResponseMsg("");
            } else {
                LOGGER.error("查询商户信息结果未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("调用查询商户信息失败");
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.MerchantInfoService#queryMerchantMeg(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> queryMerchantMegForSearch(String payerAccount, String accountCharacter, String productType) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            request.put(EPPSCBPConstants.ACCOUNT_CHARACTER, accountCharacter);
            request.put(EPPSCBPConstants.PRODUCT_TYPE, productType);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询商户信息的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.MERCHANT_MANAGE, MERCHANTINFO_SEARCH, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("查询商户信息的返回参数:*****");
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("调用查询商户信息返回成功");
                String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                apiResDto.setResult(context.replaceAll("\\'", "\\\\'"));
                apiResDto.setResponseMsg("");
            } else {
                LOGGER.error("查询商户信息结果未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("调用查询商户信息失败");
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /**
     * 分页查询商户
     */
    @Override
    public ApiResDto<List<MerchantInfoQueryResDto>> merchantInfoQuery(MerchantInfoQueryDto reqDto) {
        ApiResDto<List<MerchantInfoQueryResDto>> apiResDto = new ApiResDto<List<MerchantInfoQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行商户分页查询");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.MERCHANT_MANAGE, BATCH_QUERY_MERCHANTINFO, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:*****");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                List<MerchantInfoQueryResDto> resultList = JSONObject.parseArray(outputParam.get("context"), MerchantInfoQueryResDto.class);
                for (MerchantInfoQueryResDto merchantInfoQueryResDto : resultList) {
                    // 币种转名称
                    merchantInfoQueryResDto.setCurrency(CurType.getDescriptionFromCode(merchantInfoQueryResDto.getCurrency()));
                    // 国家码转名称
                    merchantInfoQueryResDto.setPayeeCountry(CountryType.getDescriptionFromCode(merchantInfoQueryResDto.getPayeeCountry()));
                    // 业务类型
                    merchantInfoQueryResDto.setBizType(ChargeBizType.getDescriptionFromCode(merchantInfoQueryResDto.getBizType()));
                }
                DalPage pageInfo = JSONObject.parseObject(outputParam.get("page"), DalPage.class);
                apiResDto.setResult(resultList);
                apiResDto.setPage(pageInfo);
                apiResDto.setResponseMsg("");
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /**
     * 单笔新增修改商户
     */
    @Override
    public ApiResDto<String> merchantInfoManage(MerchantInfoQueryDto reqDto) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始单笔新增修改商户");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("查询入参,reqDto:{}", reqDto);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.MERCHANT_MANAGE, MERCHANTINFO_MANAGE, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:*****");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            // 修改页面，修改了信息后，点击"保存",返回显示"修改申请已提交，待审核"
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode) && (responseMessage.equals(CommonConstant.ACCEPT_SUCCESS) || responseMessage.equals(CommonConstant.SUCCESS))) {
                apiResDto.setResponseMsg("");
            } else if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode) && responseMessage.equals(CommonConstant.MODIFY_SUCCESS)) {
                // 修改页面，未修改任何信息，点击"保存",返回显示"修改成功"
                apiResDto.setResponseMsg(responseMessage);
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    // 单笔查询商户方法
    @Override
    public ApiResDto<MerchantInfoQueryResDto> detailsMerchantInfo(Map<String, Object> param) {
        ApiResDto<MerchantInfoQueryResDto> apiResDto = new ApiResDto<MerchantInfoQueryResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始单笔查询请求参数{}", param);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.MERCHANT_MANAGE, DETAIL_MERCHANTINFO, new Object[] { param });
            LOGGER.info("查询返回参数,outputParam:*****");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                final String context = MapUtils.getString(outputParam, CommonConstant.CONTEXT);
                MerchantInfoQueryResDto detailDto = JSON.parseObject(context, MerchantInfoQueryResDto.class);
                apiResDto.setResult(detailDto);
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /**
     * 批量新增商户触发跨境
     */
    @Override
    public ApiResDto<String> batchAdd(String fileAddress, String payerAccount) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> requestMap = new HashMap<String, Object>();
            requestMap.put("fileAddress", fileAddress);
            requestMap.put("payerAccount", payerAccount);
            LOGGER.info("批量新增商户请求跨境入参{}", requestMap);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.MERCHANT_MANAGE, BATCH_ADD_MERCHANTINFO, new Object[] { requestMap });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("新增商户请求跨境返回参数:{}", responseStr);
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

    /**
     * 批量上传商户校验
     * 
     * @throws ExcelForamatException
     */
    @Override
    public FileUploadResDto uploadFile(MultipartFile targetFile, String user) throws IOException, ExcelForamatException {
        return parseFile(targetFile, user);
    }

    // 解析校验商户
    private FileUploadResDto parseFile(MultipartFile targetFile, String user) throws IOException, ExcelForamatException {
        FileUploadResDto res = new FileUploadResDto();
        if (targetFile.isEmpty()) {
            LOGGER.info("文件为空或文件不存在");
            res.fail(WebErrorCode.FILE_NOT_EXIST.getCode(), WebErrorCode.FILE_NOT_EXIST.getDescription());
            return res;
        }

        LOGGER.info("批量商户校验操作开始 fileName:{}", targetFile.getOriginalFilename());
        Workbook workbook = null;
        String fileName = targetFile.getOriginalFilename();
        InputStream is = targetFile.getInputStream();
        try {
            if (fileName.endsWith(CommonConstant.XLSX)) {
            	workbook = StreamingReader.builder().rowCacheSize(10) // 缓存到内存中的行数，默认是10
                        .bufferSize(4096) // 读取资源时，缓存到内存的字节大小，默认是1024
                        .open(is);
            } else {
                LOGGER.info("不支持的文件类型");
                res.fail(WebErrorCode.FILE_TYPE_MISMATCH.getCode(), WebErrorCode.FILE_TYPE_MISMATCH.getDescription());
                return res;
            }
            return checkFile(workbook, targetFile, user);
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

    // 校验批量商户
    private FileUploadResDto checkFile(Workbook workbook, MultipartFile targetFile, String user) throws IOException, ExcelForamatException {
        FileUploadResDto res = new FileUploadResDto();
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 总行数
        int totalRow = sheet.getLastRowNum();
        // 实际总行数
        int realTotalRow = 0;
        LOGGER.info("不含表头，明细总数：{}", totalRow);
        if (totalRow == IndexConstant.ZERO) {
            error.append("明细文件内容为空").append(";");
        }

        Set<String> set = new HashSet<String>();
        // 遍历每一行取值
        int rowNum = 0;
        for (Row row : sheet) {
            // 读文件
            rowNum++;
            // 标题不读
            if (rowNum == 1) {
                continue;
            }
            UploadMerchantFileCheck detail = new UploadMerchantFileCheck();
            detail.setPayeeMerchantName(getCellValue(row.getCell(0)));
            detail.setAccountCharacter(getCellValue(row.getCell(1)));
            detail.setCurrency(getCellValue(row.getCell(2)));
            detail.setPayeeAccount(getCellValue(row.getCell(3)));
            detail.setPayeeName(getCellValue(row.getCell(4)));
            detail.setPayeeBank(getCellValue(row.getCell(5)));
            detail.setPayeeBankAdd(getCellValue(row.getCell(6)));
            detail.setMobilePhone(getCellValue(row.getCell(7)));
            detail.setPayeeCountry(getCellValue(row.getCell(8)));
            detail.setPayeeBankSwiftCode(getCellValue(row.getCell(9)));
            detail.setPayeeAddress(getCellValue(row.getCell(10)));
            detail.setBizType(getCellValue(row.getCell(11)));

            // 整行为空则判定为最后一行,退出校验
            if (StringUtil.allEmpty(detail.getPayeeMerchantName(), detail.getAccountCharacter(), detail.getCurrency(), detail.getPayeeAccount(), detail.getPayeeName(), detail.getPayeeBank(), detail.getPayeeBankAdd(), detail.getMobilePhone(), detail.getPayeeCountry(), detail.getPayeeBankSwiftCode(), detail.getPayeeAddress(), detail.getBizType())) {
                realTotalRow = rowNum - 1;
                if (rowNum - 1 == IndexConstant.ZERO) {
                    error.append("明细文件内容为空").append(";");
                }
                if (rowNum - 1 > IndexConstant.ONE_HUNDRED) {
                    error.append("批量新增商户:").append(rowNum - 1).append("条,超过100条;").append("<br>");
                }
                break;
            }

            // 校验参数
            String validateRes = ValidateBaseHibernateUtil.validate(detail);

            if (!StringUtil.isEmpty(validateRes)) {
                error.append("第").append(rowNum).append("行商户数据").append(", 失败原因：").append(validateRes).append("<br>");
            }

            if (MerchantAccountCharacterEnum.PAYEE_MERCHANT_CODE.getCode().equals(detail.getAccountCharacter()) && StringUtil.isEmpty(detail.getBizType())) {
                error.append("第").append(rowNum).append("行商户数据").append(", 失败原因：境外收款账号的业务类型为空").append("<br>");
            }

            String checkUnique = detail.getCurrency() + detail.getPayeeAccount();
            if (!set.contains(checkUnique)) {
                set.add(checkUnique);
            }

            // 非空最后一行校验 总条数
            if (rowNum == totalRow + 1) {
                realTotalRow = rowNum-1;
                if (totalRow> IndexConstant.ONE_HUNDRED) {
                    error.append("批量新增商户:").append(rowNum-1).append("条,超过100条;").append("<br>");
                }
            }

        }
        // 判断是否存在相同付款方商户号+币种+收款账号
        if (set.size() != realTotalRow) {
            error.append("文档中存在相同付款方商户号+币种+收款账号").append("<br>");
        }

        if (StringUtil.isEmpty(error.toString())) {
            return upload(targetFile, user);
        } else {
            res.fail(WebErrorCode.CHECK_FAIL.getCode(), error.toString());
            return res;
        }
    }

    // oss上传商户文件
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
        OSSUploadParams params = new OSSUploadParams(OSSBucket.MERCHANT_FILE, targetFile, mimeType);
        // 上传路径
        params.setRemotePath(user.replaceFirst("0+", "") + new Date().getTime() + "." + fileType);
        LOGGER.info("批量新增商户上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        res.setFileAddress(fileId);
        return res;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.MerchantInfoService#arrivalQuery(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<ColAndSettleMultiInfoDto> arrivalQuery(String currency, String payerAccount) {
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("currency", currency);
            request.put("platformCode", CommonConstant.CBP_CODE);
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询商户来账信息的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.COLL_SETTLE_APPLY, ARRIVAL_QUERY, new Object[] { request });

            ApiResDto<ColAndSettleMultiInfoDto> apiResDto = new ApiResDto<ColAndSettleMultiInfoDto>();
            apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
            apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);

            String responseStr = JSON.toJSONString(response);

            LOGGER.info("查询商户来账信息的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseCode(CommonConstant.RESPONSE_SUCCESS_CODE);
                apiResDto.setResponseMsg("");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                ColAndSettleMultiInfoDto detailDto = JSON.parseObject(context, ColAndSettleMultiInfoDto.class);
                apiResDto.setResult(detailDto);
            } else {
                apiResDto.setResponseMsg("该币种未开通境外账号");
            }
            return apiResDto;

        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    @Override
    public ApiResDto<MerchantInfoQueryResDto> isOpenColl(String payeeMerchantCode, String payerAccount) {
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("payeeMerchantCode", payeeMerchantCode);
            request.put("platformCode", CommonConstant.CBP_CODE);
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询商户信息的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.MERCHANT_MANAGE, BATCH_QUERY_MERCHANTINFO, new Object[] { request });

            ApiResDto<MerchantInfoQueryResDto> apiResDto = new ApiResDto<MerchantInfoQueryResDto>();
            apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
            apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);

            String responseStr = JSON.toJSONString(response);

            LOGGER.info("查询商户信息返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseCode(CommonConstant.RESPONSE_SUCCESS_CODE);
                apiResDto.setResponseMsg("");
                List<MerchantInfoQueryResDto> resultList = JSONObject.parseArray(response.get("context"), MerchantInfoQueryResDto.class);
                MerchantInfoQueryResDto detailDto = resultList.get(0);
                apiResDto.setResult(detailDto);
            } else {
                apiResDto.setResponseMsg("该账号未开通收汇功能");
            }
            return apiResDto;

        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }
    
    @Override
    public Map<String, String> updateRecipientEmailAddre(String payerAccount, String emailAddre) {
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            request.put(EPPSCBPConstants.EMAIL_ADDRE, emailAddre);
            String requestStr = JSON.toJSONString(request);
            LOGGER.debug("请求更新收件人邮箱地址的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.QUERY_MERCHANT_AUTH, UPDATE_RECIPIENT_EMAIL_ADDRE, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("请求更新收件人邮箱地址返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            if (response == null || !CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                LOGGER.error("请求更新收件人邮箱地址未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.debug("请求更新收件人邮箱地址失败");
            } else {
                LOGGER.debug("请求更新收件人邮箱地址返回成功");
            }
            return response;
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            return null;
        }       
    }

    @Override
    public List<String> queryOperator(String userNo) {
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put(EPPSCBPConstants.USE_NO, userNo);
            request.put(EPPSCBPConstants.SERIAL_NO, UUID.randomUUID().toString().replace("-", ""));
            request.put(EPPSCBPConstants.SYSNAME, EPPSCBPConstants.SYSNAME_CODE);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询操作员的参数:{}", requestStr);
            String response = generalRsfServiceStr.invoke(ApiCodeConstant.QUERY_OPERATOR, EXECUTOR, requestStr);
            LOGGER.info("查询操作员的返回参数:{}", response);
            // 结果list
            List<String> result = new ArrayList<String>();
            JSONObject parseObject = JSON.parseObject(response);
            // 判断是否成功
            String responseCode = MapUtils.getString(parseObject, CommonConstant.RESPONSE_CODE);
            if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                // 获取list
                String listText = MapUtils.getString(parseObject, "operatorList");
                List<JSONObject> parseArray = JSON.parseArray(listText, JSONObject.class);
                // 获取操作员编号
                for (JSONObject object : parseArray) {
                    result.add(MapUtils.getString(object, "operatorCode"));
                }
            } 
            return result;
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            return new ArrayList<String>();
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.MerchantInfoService#queryMerchantInfo(java.lang.String)
     */
    @Override
    public boolean setOperatorAuth(CtMerchantAuth authority) {
        try {
            // 调用参数
            String requestStr = JSON.toJSONString(authority);
            LOGGER.info("新增/修改操作员权限的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.QUERY_MERCHANT_AUTH, SET_OPERATOR_AUTH, requestStr);
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("新增/修改操作员权限的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            if (response == null || !CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                LOGGER.error("新增/修改操作员权限未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("新增/修改操作员权限失败");
                return false;
            } else {
                LOGGER.info("新增/修改操作员权限返回成功");
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            return false;
        }
    }

}
