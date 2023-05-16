/**
 * 
 */
package com.suning.epp.eppscbp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.DomesticMerchantInfoReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.DomesticMerchantInfoResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;

/**
 * @author 17080704
 *
 */
public interface DomesticMerchantInfoService {

    // 境内商户分页查询
    public ApiResDto<List<DomesticMerchantInfoResDto>> merchantInfoQuery(DomesticMerchantInfoReqDto reqDto);

    // 单笔查询商户方法
    public ApiResDto<DomesticMerchantInfoResDto> merchantInfoDetail(Map<String, Object> param);

    // 单笔新增修改商户信息
    public ApiResDto<String> merchantInfoManage(DomesticMerchantInfoReqDto reqDto);

    /**
     * 
     * 功能描述:<批量上传境内商户校验> 〈功能详细描述〉
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
     * 功能描述:<批量新增境内商户> 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<String> batchAdd(String fileAddress, String payerAccount);
    
    /**
     * 
     * 功能描述: 获取境内银行编码<br>
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<JSONArray> getBankList();

    /**
     * 根据组织机构代码查询企业客户是否报备
     * @param param
     * @return
     */
    public ApiResDto<String> checkRegistStatusByCompanyCode(Map<String, String> param);
    
    /**
     * 
     * 功能描述:  查询企业客户苏宁反洗钱审核状态 By 组织结构代码<br>
     * 〈功能详细描述〉
     *
     * @param param
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<String> checkSnAntiMoneyStatusByCompanyCode(Map<String, String> param);
}
