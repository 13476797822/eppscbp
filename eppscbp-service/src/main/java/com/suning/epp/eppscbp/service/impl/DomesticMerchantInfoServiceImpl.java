/**
 * 
 */
package com.suning.epp.eppscbp.service.impl;

import static com.suning.epp.eppscbp.util.ExcelUtil.getCellValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monitorjbl.xlsx.StreamingReader;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.IndexConstant;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.UploadDomesticMerchantFileCheck;
import com.suning.epp.eppscbp.dto.req.DomesticMerchantInfoReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.DomesticMerchantInfoResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.DomesticMerchantInfoService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
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
@Service("domesticMerchantInfoService")
public class DomesticMerchantInfoServiceImpl implements DomesticMerchantInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomesticMerchantInfoServiceImpl.class);

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    // 商户分页查询方法
    private static final String DOME_MERCHANTINFO_QUERY = "batchQueryDomesticPayee";
    // 境内商户详情
    private static final String MERCHANT_INFO_DETAIL = "detailsDomesticPayee";
    // 境内商户新增修改
    private static final String MERCHANT_INFO_MANAGE = "domesticPayeeInfoManage";
    // 批量新增境内商户
    private static final String BATCH_ADD_MERCHANTINFO = "batchAddDomesticPayeeInfo";
    // 境内银行编码
    private static final String BANK_LIST_QUERY = "getBankList";
    // 检查企业客户的报备状态
    private static final String CHECK_REGIST_STATUS = "checkRegistStatus";
    // 检查企业客户苏宁反洗钱审核状态
    private static final String CHECK_SN_ANTI_MONEY_STATUS = "checkSnAntiMoneyStatus";

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.DomesticMerchantInfoService#
     * merchantInfoQuery(com.suning.epp.eppscbp.dto.req. DomesticMerchantInfoReqDto)
     */
    @Override
    public ApiResDto<List<DomesticMerchantInfoResDto>> merchantInfoQuery(DomesticMerchantInfoReqDto reqDto) {
        ApiResDto<List<DomesticMerchantInfoResDto>> apiResDto = new ApiResDto<List<DomesticMerchantInfoResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行境内商户分页查询");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.DOME_MERCHANT_MANAGE, DOME_MERCHANTINFO_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:********");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                List<DomesticMerchantInfoResDto> resultList = JSONObject.parseArray(outputParam.get("context"), DomesticMerchantInfoResDto.class);
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

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.DomesticMerchantInfoService# merchantInfoDetail(java.util.Map)
     */
    @Override
    public ApiResDto<DomesticMerchantInfoResDto> merchantInfoDetail(Map<String, Object> param) {
        ApiResDto<DomesticMerchantInfoResDto> apiResDto = new ApiResDto<DomesticMerchantInfoResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始单笔商户详情查询请求参数{}", param);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.DOME_MERCHANT_MANAGE, MERCHANT_INFO_DETAIL, new Object[] { param });
            LOGGER.info("查询返回参数,outputParam:******");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                final String context = MapUtils.getString(outputParam, CommonConstant.CONTEXT);
                DomesticMerchantInfoResDto detailDto = JSON.parseObject(context, DomesticMerchantInfoResDto.class);
                if (StringUtil.isEmpty(detailDto.getMobilePhone())) {
                    detailDto.setMobilePhone("");
                }
                apiResDto.setResult(detailDto);
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.DomesticMerchantInfoService#
     * merchantInfoManage(com.suning.epp.eppscbp.dto.req. DomesticMerchantInfoReqDto)
     */
    @Override
    public ApiResDto<String> merchantInfoManage(DomesticMerchantInfoReqDto reqDto) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始单笔新增修改商户");
            if ("N".equals(reqDto.getOperationType())) { // 若新增, 则塞入收款方名称 为 银行开户名
                reqDto.setPayeeMerchantName(reqDto.getBankAccountName());
            }
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("新增商户入参,reqDto:{}", reqDto);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.DOME_MERCHANT_MANAGE, MERCHANT_INFO_MANAGE, new Object[] { inputParam });
            LOGGER.info("新增商户返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, "responseCode");
            final String responseMessage = MapUtils.getString(outputParam, "responseMsg");
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.DomesticMerchantInfoService#batchAdd(java. lang.String, java.lang.String)
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
            LOGGER.info("批量新增境内商户或提现账号请求跨境入参:{}", requestMap);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.DOME_MERCHANT_MANAGE, BATCH_ADD_MERCHANTINFO, new Object[] { requestMap });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("批量新增境内商户或提现账号请求跨境返回参数:{}", responseStr);
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

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.DomesticMerchantInfoService#uploadFile(org
     * .springframework.web.multipart.MultipartFile, java.lang.String)
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
            	 LOGGER.info("开始解析文件");  
            } else {
                LOGGER.info("不支持的文件类型");
                res.fail(WebErrorCode.FILE_TYPE_MISMATCH.getCode(), WebErrorCode.FILE_TYPE_MISMATCH.getDescription());
                return res;
            }
            return checkFile(workbook, targetFile, user);
            
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

    private FileUploadResDto checkFile(Workbook workbook, MultipartFile targetFile, String user) throws IOException, ExcelForamatException {
        LOGGER.info("开始批量境内收款方解析");
    	FileUploadResDto res = new FileUploadResDto();
        // 解析文件
        StringBuilder error = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 总行数
        int totalRow = sheet.getLastRowNum();
        
        LOGGER.info("不含表头，明细记录：{}", totalRow);
        if (totalRow  == IndexConstant.ZERO) {
            error.append("境内收款方内容为空").append(";");
        }
        
        int rowNum = 0;
        // 遍历每一行取值
        for (Row row : sheet) {
        	rowNum++;
        	if (rowNum == 1) {
                continue;
            }
            UploadDomesticMerchantFileCheck detail = new UploadDomesticMerchantFileCheck();
            detail.setAccountCharacter(getCellValue(row.getCell(0)));
            detail.setCompanyOrPersonCode(getCellValue(row.getCell(1)));
            detail.setBankAccountName(getCellValue(row.getCell(2)));
            detail.setBankAccountNo(getCellValue(row.getCell(3)));
            detail.setBank(getCellValue(row.getCell(4)));
            detail.setBankCode(getCellValue(row.getCell(5)));
            detail.setBankProvince(getCellValue(row.getCell(6)));
            detail.setBankCity(getCellValue(row.getCell(7)));
            detail.setMobilePhone(getCellValue(row.getCell(8)));
            detail.setBankNo(getCellValue(row.getCell(9)));
            detail.setBizType(getCellValue(row.getCell(10)));
            detail.setPayeeMerchantName(detail.getBankAccountName());

            // 整行为空则判定为最后一行,退出校验
            if (StringUtil.allEmpty(detail.getPayeeMerchantName(), detail.getAccountCharacter(), detail.getCompanyOrPersonCode(), detail.getBankAccountName(), detail.getBankAccountNo(), detail.getBank(), detail.getBankCode(), detail.getBankProvince(), detail.getBankCity(), detail.getMobilePhone(), detail.getBankNo())) {
                if (rowNum - 1 == IndexConstant.ZERO) {
                    error.append("明细文件内容为空").append(";");
                }
                if (rowNum - 1 > IndexConstant.ONE_HUNDRED) {
                    error.append("批量新增境内商户:").append(rowNum - 1).append("条,超过100条;").append("<br>");
                }
                break;
            }
            Boolean flag = true;
            // 基础参数校验
            String validateRes = ValidateBaseHibernateUtil.validate(detail);
            // 商户名和开户行名一致判断
            String sameValue = "";
            if (!detail.getPayeeMerchantName().equals(detail.getBankAccountName())) {
                sameValue = "名称和银行开户名不一致";
            }

            if (!StringUtil.isEmpty(validateRes) || !StringUtil.isEmpty(sameValue)) {
                flag = false;
                error.append("第").append(rowNum).append("行商户数据").append(", 失败原因：").append(StringUtil.isEmpty(validateRes) ? "" : validateRes).append(sameValue);
            }

            if (flag == false && !StringUtil.isEmpty(error.toString())) {
                error.append("<br>");
            }

            // 非空最后一行校验 总条数
            if (rowNum == totalRow + 1) {
                if (totalRow > IndexConstant.ONE_HUNDRED) {
                    error.append("批量新增境内商户:").append(rowNum).append("条,超过100条;").append("<br>");
                }
            }

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
        LOGGER.info("批量新增境内商户上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        res.setFileAddress(fileId);
        return res;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.DomesticMerchantInfoService#getBankList()
     */
    @Override
    public ApiResDto<JSONArray> getBankList() {
        ApiResDto<JSONArray> apiResDto = new ApiResDto<JSONArray>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            LOGGER.info("请求查询银行编码信息开始");
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.DOME_MERCHANT_MANAGE, BANK_LIST_QUERY, new Object[] {});
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("查询银行编码信息的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("调用查询银行编码信息返回成功");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                JSONArray jsonArray = JSON.parseObject(context, JSONArray.class);
                apiResDto.setResult(jsonArray);
                apiResDto.setResponseMsg("");
            } else {
                LOGGER.error("查询银行编码信息结果未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("调用银行编码信息失败");
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<String> checkRegistStatusByCompanyCode(Map<String, String> param) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始根据组织机构代码查询企业客户是否报备,入参param:{}", param);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.DOME_MERCHANT_MANAGE, CHECK_REGIST_STATUS, new Object[] { param });
            LOGGER.info("根据组织机构代码查询企业客户是否报备返回参数,outputParam:{}", outputParam);
            String responseCode = MapUtils.getString(outputParam, "responseCode");
            String responseMessage = MapUtils.getString(outputParam, "responseMsg");
            String context = MapUtils.getString(outputParam, CommonConstant.CONTEXT);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                apiResDto.setResult(context);
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<String> checkSnAntiMoneyStatusByCompanyCode(Map<String, String> param) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始根据组织机构代码查询企业客户是否通过苏宁反洗钱,入参param:{}", param);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.DOME_MERCHANT_MANAGE, CHECK_SN_ANTI_MONEY_STATUS, new Object[] { param });
            LOGGER.info("根据组织机构代码查询企业客户是否通过苏宁反洗钱,返回参数outputParam:{}", outputParam);
            String responseCode = MapUtils.getString(outputParam, "responseCode");
            String responseMessage = MapUtils.getString(outputParam, "responseMsg");
            String context = MapUtils.getString(outputParam, CommonConstant.CONTEXT);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                apiResDto.setResult(context);
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }

        } catch (Exception e) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(e));
        }
        return apiResDto;
    }
}
