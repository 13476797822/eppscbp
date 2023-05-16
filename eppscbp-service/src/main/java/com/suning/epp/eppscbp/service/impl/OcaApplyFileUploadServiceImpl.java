package com.suning.epp.eppscbp.service.impl;

import static com.suning.epp.eppscbp.util.ExcelUtil.getCellValue;
import static com.suning.epp.eppscbp.util.ExcelUtil.getCellValueDate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.IndexConstant;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.OcaLogisticsInfoFileCheck;
import com.suning.epp.eppscbp.dto.OcaLogisticsInfoFileCheckFail;
import com.suning.epp.eppscbp.dto.OcaLogisticsInfoFileCheckSucc;
import com.suning.epp.eppscbp.dto.res.FileUploadLogisticsInfoResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.OcaApplyFileUploadService;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSMimeType;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;
import com.suning.epp.eppscbp.util.validator.ValidateBaseHibernateUtil;

import org.springframework.web.multipart.MultipartFile;

@Service("ocaApplyFileUploadService")
public class OcaApplyFileUploadServiceImpl implements OcaApplyFileUploadService {
	/**
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OcaApplyFileUploadServiceImpl.class);
    private static final String FILE_NAME_FAIL = "logistics_fail_template.xlsx";
    private static final String FILE_NAME_SUCC = "logistics_succ_template.xlsx";
    
    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;
    
    /**
	 * 上传申诉材料文件 
	 */
	@Override
	public FileUploadResDto uploadFile(MultipartFile targetFile, String remoteUser) {
		FileUploadResDto res = new FileUploadResDto();
        
        if (targetFile.isEmpty()) {
            LOGGER.info("文件为空或文件不存在");
            res.fail(WebErrorCode.FILE_NOT_EXIST.getCode(), WebErrorCode.FILE_NOT_EXIST.getDescription());
            return res;
        }
        
        try {
			return uploadAppealFile(targetFile, remoteUser);
		} catch (IOException e) {
			LOGGER.error("文件解析错误:{}", ExceptionUtils.getStackTrace(e));
			return res;
		}
		
	}

	protected FileUploadResDto uploadAppealFile(MultipartFile targetFile, String user)  throws IOException{
		FileUploadResDto res = new FileUploadResDto();
        String fileName = targetFile.getOriginalFilename();
        //String mimeType = MimeUtil.getMimeTypes(fileName).toString();
        OSSUploadParams params = new OSSUploadParams(OSSBucket.OCA, targetFile);
        // 上传路径
        params.setRemotePath(OSSBucket.REJECT_APPEAL_FILE + user.replaceFirst("0+", "") + DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSS)+ "_"+ fileName); 
        LOGGER.info("申诉材料上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStreamOriginalFilename(params);
        LOGGER.info("申诉材料上传结果:{}", fileId);
        res.setFileAddress(fileId);
        return res;
	}

	/**
	 * 上传物流信息文件 
	 */
	@Override
	public FileUploadLogisticsInfoResDto uploadFileLogisticsInfo(MultipartFile targetFile, String remoteUser) throws ExcelForamatException, ParseException, IOException{
		FileUploadLogisticsInfoResDto res = new FileUploadLogisticsInfoResDto();
        
        if (targetFile.isEmpty()) {
            LOGGER.info("文件为空或文件不存在");
            res.fail(WebErrorCode.FILE_NOT_EXIST.getCode(), WebErrorCode.FILE_NOT_EXIST.getDescription());
            return res;
        }
        
        return parseFile(targetFile, remoteUser);
	}

	private FileUploadLogisticsInfoResDto parseFile(MultipartFile targetFile, String user) throws ExcelForamatException, ParseException, IOException {
		FileUploadLogisticsInfoResDto res = new FileUploadLogisticsInfoResDto(); 
		// 获取文件对象
        LOGGER.info("明细文件导入操作开始 fileName:{}", targetFile.getOriginalFilename());
        Workbook workbook = null;
        String fileName = targetFile.getOriginalFilename();
        try {
            if (fileName.endsWith(CommonConstant.XLS)) {
                workbook = new HSSFWorkbook(targetFile.getInputStream());
            } else if (fileName.endsWith(CommonConstant.XLSX)) {
                workbook = new XSSFWorkbook(targetFile.getInputStream());
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
                    workbook.close();
                }
            } catch (IOException e) {
                LOGGER.error("文件关闭错误:{}", ExceptionUtils.getStackTrace(e));
            }
        }
		return checkLogisticInfoFile(workbook, targetFile, user);
	}

	private FileUploadLogisticsInfoResDto checkLogisticInfoFile(Workbook workbook, MultipartFile targetFile, String user) throws ExcelForamatException, ParseException, IOException {
		LOGGER.info("解析物流信息文件开始");
		FileUploadLogisticsInfoResDto res = new FileUploadLogisticsInfoResDto();
		List<OcaLogisticsInfoFileCheckFail> failList = new ArrayList<OcaLogisticsInfoFileCheckFail>();
		List<OcaLogisticsInfoFileCheckSucc> succList = new ArrayList<OcaLogisticsInfoFileCheckSucc>();
        Sheet sheet = workbook.getSheetAt(IndexConstant.ZERO);
        // 总行数
        int totalRow = sheet.getPhysicalNumberOfRows();
        // 笔数超出阀值10000
        if(totalRow-1 > 10000) {
        	res.fail(WebErrorCode.QUANTITY_OVERSTEP_10000.getCode(), WebErrorCode.QUANTITY_OVERSTEP_10000.getDescription());
        	return res;
        }
        int failCount = 0;
        LOGGER.info("获取到总行数:{}", totalRow);
        // 遍历每一行取值
        for (int i = 1; i < totalRow; i++) {
            Row row = sheet.getRow(i);
            // 物流信息基本字段校验dto
            OcaLogisticsInfoFileCheck fileCheck = new OcaLogisticsInfoFileCheck();
            // 物流信息校验 成功dto
            OcaLogisticsInfoFileCheckSucc fileCheckSucc = new OcaLogisticsInfoFileCheckSucc();
            // 物流信息校验 失败dto
            OcaLogisticsInfoFileCheckFail fileCheckFail = new OcaLogisticsInfoFileCheckFail();
            // 商户订单号
            String merchantOrderNo = getCellValue(row.getCell(0));
            fileCheck.setMerchantOrderNo(merchantOrderNo);
            // 发货日期
            String shippingDate = getCellValueDate(row.getCell(1));
            fileCheck.setShippingDate(shippingDate);
            // 物流公司名称
            String logisticsCompName = getCellValue(row.getCell(2));
            fileCheck.setLogisticsCompName(logisticsCompName);
            // 物流单号
            String logisticsNo = getCellValue(row.getCell(3));
            fileCheck.setLogisticsNo(logisticsNo);
            
            // 整行为空则判定为最后一行,退出校验
            if (StringUtil.allEmpty(merchantOrderNo, shippingDate, logisticsCompName, logisticsNo)) {
            	res.fail(WebErrorCode.ROW_NULL.getCode(), WebErrorCode.ROW_NULL.getDescription());
            	return res;
            }
            StringBuilder validateRes = new StringBuilder(ValidateBaseHibernateUtil.validate(fileCheck));
            //基础信息校验失败，保存到文件校验失败dto
            if (!StringUtil.isEmpty(validateRes.toString())) {
            	failCount = buildLogisticsFailFile(failList, failCount, fileCheckFail, fileCheck, validateRes);
            	continue;
            }
            
            //判断商户订单号是否存在，以及物流状态是否为审核中或者审核通过
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("merchantOrderNo", merchantOrderNo);
            inputParam.put("payerAccount", user);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.LOGISTICS_INFO, "isOrNotUpload", new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            
            String responseCode = outputParam.get("responseCode");
            String responseMsg = outputParam.get("responseMsg");
            
            //商户订单号存在且状态不为审核中或者审核通过
            if(WebErrorCode.SUCCESS.getCode().equals(responseCode)) {
            	getLogisticsSuccFile(succList, fileCheckSucc, fileCheck);
            }else{
            	//判断1.商户订单号是否不存在，2.物流状态是否为非审核中 或者审核通过，以上符合一个条件则为错误数据，入错误文件
            	StringBuilder validateRes2 = new StringBuilder();
            	validateRes2.append(responseMsg);
            	failCount = buildLogisticsFailFile(failList, failCount, fileCheckFail, fileCheck, validateRes2);
            }
        }
        
        // 成功笔数
        int succCount = (totalRow-1)-failCount;
        
        // 上传成功文件返回路径
        String filePath = uploadLogisticsInfoFileSucc(succList, targetFile.getOriginalFilename(), user);
        
        // 上传失败笔数文件，返回路径
        String filePathFail = uploadLogisticsInfoFileFail(failList, targetFile.getOriginalFilename(), user);
        
        // 组装返回数据
        res.setLogisticsCountFail(failCount);
        res.setLogisticsCount(totalRow-1);
        res.setLogisticsCountSucess(succCount);
        res.setFilePathSuccess(filePath);
        res.setFilePathFail(filePathFail);
        return res;
	}

	private void getLogisticsSuccFile(List<OcaLogisticsInfoFileCheckSucc> succList,	OcaLogisticsInfoFileCheckSucc fileCheckSucc, OcaLogisticsInfoFileCheck fileCheck) {
		fileCheckSucc.setMerchantOrderNo(fileCheck.getMerchantOrderNo());
		String shippingDate = fileCheck.getShippingDate();
		fileCheckSucc.setShippingDate(shippingDate);
		fileCheckSucc.setLogisticsCompName(fileCheck.getLogisticsCompName());
		fileCheckSucc.setLogisticsNo(fileCheck.getLogisticsNo());
		succList.add(fileCheckSucc);
	}

	/**
	 * 文件中明细数据校验失败的，放到失败的文件中
	 * @param failList
	 * @param failCount
	 * @param fileCheckFail
	 * @param fileCheck
	 * @param validateRes
	 * @return
	 */
	private int buildLogisticsFailFile(List<OcaLogisticsInfoFileCheckFail> failList, int failCount,	OcaLogisticsInfoFileCheckFail fileCheckFail, OcaLogisticsInfoFileCheck fileCheck, StringBuilder validateRes) {
		fileCheckFail.setMerchantOrderNo(fileCheck.getMerchantOrderNo());
		String shippingDate = fileCheck.getShippingDate();
		fileCheckFail.setShippingDate(shippingDate);
		fileCheckFail.setLogisticsCompName(fileCheck.getLogisticsCompName());
		fileCheckFail.setLogisticsNo(fileCheck.getLogisticsNo());
		fileCheckFail.setFailReason(validateRes.toString());
		//统计失败笔数
		failCount = failCount+1;
		failList.add(fileCheckFail);
		return failCount;
	}
	
	public String uploadLogisticsInfoFileFail(List<OcaLogisticsInfoFileCheckFail> detailList, String fileName, String user) throws IOException {
       
        LOGGER.info("开始创建失败物流信息");
        if(CollectionUtils.isEmpty(detailList)) {
        	LOGGER.info("没有失败的的物流信息");
        	return "";
        }
        // 获取模版文件
        Workbook webBook = readExcel(FILE_NAME_FAIL);
        createCellStyle(webBook);

        Sheet sheet1 = webBook.getSheetAt(0);
        for (int rows = 1; rows < detailList.size() + 1; rows++) {
            Row row1 = sheet1.createRow(rows);
            OcaLogisticsInfoFileCheckFail fileCheckFail = detailList.get(rows - 1);
            row1.createCell(0).setCellValue(fileCheckFail.getMerchantOrderNo());
            row1.createCell(1).setCellValue(fileCheckFail.getShippingDate());
            row1.createCell(2).setCellValue(fileCheckFail.getLogisticsCompName());
            row1.createCell(3).setCellValue(fileCheckFail.getLogisticsNo());
            row1.createCell(4).setCellValue(fileCheckFail.getFailReason());
        }
        fileName = OSSBucket.LOGISTICS_FILE_FAIL + user.replaceFirst("0+", "") + new Date().getTime() + "." + fileName.split("\\.")[1];
        return writeExcel(webBook, fileName);
    }
	
	public String uploadLogisticsInfoFileSucc(List<OcaLogisticsInfoFileCheckSucc> detailList, String fileName, String user) throws IOException {
        
        LOGGER.info("开始创建成功物流信息");
        if(CollectionUtils.isEmpty(detailList)) {
        	LOGGER.info("没有成功的物流信息");
        	return "";
        }
        // 获取模版文件
        Workbook webBook = readExcel(FILE_NAME_SUCC);
        createCellStyle(webBook);

        Sheet sheet1 = webBook.getSheetAt(0);
        for (int rows = 1; rows < detailList.size() + 1; rows++) {
            Row row1 = sheet1.createRow(rows);

            OcaLogisticsInfoFileCheckSucc fileCheckSucc = detailList.get(rows-1);
            row1.createCell(0).setCellValue(fileCheckSucc.getMerchantOrderNo());
            row1.createCell(1).setCellValue(fileCheckSucc.getShippingDate());
            row1.createCell(2).setCellValue(fileCheckSucc.getLogisticsCompName());
            row1.createCell(3).setCellValue(fileCheckSucc.getLogisticsNo());
        }
        fileName = OSSBucket.LOGISTICS_FILE_SUCC + user.replaceFirst("0+", "") + new Date().getTime() + "." + fileName.split("\\.")[1];
       return writeExcel(webBook, fileName);
    }
	
	/**
     * 
     * 功能描述: 读取模版<br>
     * 〈功能详细描述〉
     *
     * @param filePath
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private Workbook readExcel(String fileName) {
        InputStream in = null;
        Workbook work = null;
        try {
            in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            XSSFWorkbook xWork = new XSSFWorkbook(in);
            work = new SXSSFWorkbook(xWork);
        } catch (FileNotFoundException e) {
            LOGGER.error("文件路径错误:{}", ExceptionUtils.getFullStackTrace(e));
        } catch (IOException e) {
            LOGGER.error("文件读写错误:{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("输出流错误:{}", ExceptionUtils.getFullStackTrace(e));
                }
            }
        }
        return work;
    }

    /**
     * 
     * 功能描述: 生成excel<br>
     * 〈功能详细描述〉
     *
     * @param work
     * @param fileName
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private String  writeExcel(Workbook work, String fileName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            work.write(baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            OSSUploadParams params = new OSSUploadParams(OSSBucket.UNDERLINE_FILE_BUCKET, bais, OSSMimeType.XLSX);
            params.setPathName(fileName);
            String filePath = OSSClientUtil.uploadInputStream(params);
            LOGGER.info("数据导出生成文件完毕.{}", filePath);
            return filePath;
        } catch (IOException e) {
            LOGGER.error("输出流错误:{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            baos.close();
        }
        return null;
    }

    /**
     * 
     * 功能描述: 设置单元格格式<br>
     * 〈功能详细描述〉
     *
     * @param wb
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private XSSFCellStyle createCellStyle(Workbook wb) {
        XSSFCellStyle cellstyle = (XSSFCellStyle) wb.createCellStyle();
        cellstyle.setAlignment(HorizontalAlignment.CENTER);
        cellstyle.setBorderBottom(BorderStyle.THIN);
        cellstyle.setBorderLeft(BorderStyle.THIN);
        cellstyle.setBorderRight(BorderStyle.THIN);
        cellstyle.setBorderTop(BorderStyle.THIN);
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellstyle;
    }

}
