/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: MerchantInfoService.java
 * Author:   17061545
 * Date:     2018年3月20日 下午2:17:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.MerchantInfoQueryDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.ColAndSettleMultiInfoDto;
import com.suning.epp.eppscbp.dto.res.CtFullAmtDto;
import com.suning.epp.eppscbp.dto.res.CtMerchantAuth;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.MerchantInfoQueryResDto;

/**
 * 〈商户管理接口〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface MerchantInfoService {

    /**
     * 
     * 查询商户权限: <br>
     * 〈功能详细描述〉
     *
     * @param payerAccount 会员号
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Map<String, String> queryMerchantAuth(String payerAccount, String operatorCode);

    /**
     * 
     * 查询商户权限: <br>
     * 〈功能详细描述〉
     *
     * @param payerAccount 会员号
     * @param message 收款人信息
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<CtFullAmtDto> queryMerchantMeg(String payerAccount, String message, String productType);

    // 收款方商户分页查询
    public ApiResDto<List<MerchantInfoQueryResDto>> merchantInfoQuery(MerchantInfoQueryDto reqDto);

    // 单笔新增修改商户信息
    public ApiResDto<String> merchantInfoManage(MerchantInfoQueryDto reqDto);

    // 批量新增请求跨境
    public ApiResDto<String> batchAdd(String fileAddress, String payerAccount);

    // 单笔查询商户方法
    public ApiResDto<MerchantInfoQueryResDto> detailsMerchantInfo(Map<String, Object> param);

    /**
     * 
     * 功能描述:批量上传商户校验> 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @throws ExcelForamatException 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    FileUploadResDto uploadFile(MultipartFile targetFile, String user) throws IOException, ExcelForamatException;

    /**
     * 
     * 商户来账信息查询<br>
     * 〈功能详细描述〉
     *
     * @param currency 币种
     * @param payerAccount 商户户头号
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<ColAndSettleMultiInfoDto> arrivalQuery(String currency, String payerAccount);

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param accountCharacter
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<String> queryMerchantMegForSearch(String payerAccount, String accountCharacter, String productType);
    
    
    /**
     * 根据payeeMerchantCode和payerAccount查询商户
     * <br>
     * 〈功能详细描述〉
     *
     * @param payeeMerchantCode 
     * @param payerAccount 商户户头号
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<MerchantInfoQueryResDto> isOpenColl(String payeeMerchantCode, String payerAccount);
    
    /**
     * 根据payerAccount更新商户，收件人邮箱地址
     * <br>
     * 〈功能详细描述〉
     * @param payerAccount 商户户头号
     * @param emailAddre 收件人邮箱地址
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Map<String, String> updateRecipientEmailAddre(String payerAccount, String emailAddre);
    
    /**
     * 
     * 查询操作员: <br>
     * 〈功能详细描述〉
     *
     * @param payerAccount 会员号
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<String> queryOperator(String userNo);

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param authority
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    boolean setOperatorAuth(CtMerchantAuth authority);

}
