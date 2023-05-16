/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: ApplyFileUploadService.java
 * Author:   17033387
 * Date:     2018年4月8日 下午5:31:46
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;

/**
 * 〈交易申请上传文件服务类〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ApplyFileUploadService {

    /**
     * 
     * 功能描述: 上传单笔文件br> 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    FileUploadResDto uploadFile(MultipartFile targetFile, String bizType, int detailAmount, double payAmt, String user, String curType, String exchangeType) throws IOException, ExcelForamatException, ParseException;

    /**
     * 
     * 功能描述: 上传批量文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    FileUploadResDto uploadBatchFile(MultipartFile targetFile, String bizType, String user) throws IOException, ExcelForamatException, ParseException;

    /**
     *
     * 功能描述: 上传批量文件<br>
     * 〈资金批付上传批量明细〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    FileUploadResDto uploadBatchFiles(MultipartFile targetFile, double stayAmount, String user) throws IOException, ExcelForamatException, ParseException;

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
    FileUploadResDto uploadForCollAndSettle(MultipartFile targetFile, double payAmt, String user, String curType, String collAndSettleFlag) throws IOException, ExcelForamatException, ParseException;

    /**
     *  店铺管理提现明细校验
     * @param targetFile
     * @param user
     * @return
     * @throws IOException
     * @throws ExcelForamatException
     */
    FileUploadResDto uploadStoreFile(MultipartFile targetFile,String cur, String user) throws IOException, ExcelForamatException, ParseException;

    
    /**
     * 查询真实现材料合并权限
     * @param user
     * @return
     */
    String getSuperviseCombineTypeAuth(String user);
}
