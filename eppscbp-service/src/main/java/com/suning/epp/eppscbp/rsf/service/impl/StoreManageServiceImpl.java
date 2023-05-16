/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: ShopManageServiceImpl.java
 * Author:   88412423
 * Date:     2019年5月8日 14:32:49
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.StoreCurEnum;
import com.suning.epp.eppscbp.common.enums.StoreSiteEnum;
import com.suning.epp.eppscbp.common.enums.StoreStatusEnum;
import com.suning.epp.eppscbp.common.errorcode.CoreErrorCode;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.AddStoreDto;
import com.suning.epp.eppscbp.dto.req.StoreManageDto;
import com.suning.epp.eppscbp.dto.req.StoreWithDrawAndPaymentDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.StoreResDto;
import com.suning.epp.eppscbp.dto.res.StoreSiteResDto;
import com.suning.epp.eppscbp.rsf.service.StoreManageService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.pu.common.aop.lang.CommonResult;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈店铺管理service〉
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("shopManageService")
public class StoreManageServiceImpl implements StoreManageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreManageServiceImpl.class);

    // 店铺管理查询方法
    private static final String STORE_MANAGE = "queryBalance";

    // 批量查询店铺信息
    public static final String STORE_MANAGE_QUERY = "batchQuery";

    //新增或更新
    private static final String STORE_INSERT_OR_QUERY = "insertOrUpdate";

    // 删除店铺
    public static final String STORE_MANAGE_DELETE = "deleteCpStoreInfo";

    //提现
    private static final String STORE_CASH_WITHDRAW = "apply";

    //查询店铺站点
    private static final String STORE_SITES = "getStoreSites";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;


    @Override
    public ApiResDto<StoreWithDrawAndPaymentDto> getStoreInfos(StoreManageDto storeManageDto) {
        ApiResDto<StoreWithDrawAndPaymentDto> resDto = new ApiResDto<StoreWithDrawAndPaymentDto>();
        resDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        resDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(storeManageDto);
            LOGGER.info("查询店铺管理入参,inputParam:{}", inputParam);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_MANAGE, STORE_MANAGE, new Object[]{inputParam});
            LOGGER.info("查询店铺管理返回参数,outputParam:{}", response);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            final String context = MapUtils.getString(response, CommonConstant.CONTEXT);

            resDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("店铺管理信息返回信息成功");
                resDto.setResponseMsg("");
                StoreWithDrawAndPaymentDto shop = JSON.parseObject(context, StoreWithDrawAndPaymentDto.class);
                resDto.setResult(shop);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
                resDto.setResponseMsg(responseMessage);
            }
        } catch (Exception e) {
            LOGGER.info("店铺管理异常,inputParam:{}", ExceptionUtils.getStackTrace(e));
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(e));
        }
        return resDto;
    }

    @Override
    public ApiResDto<List<StoreResDto>> getStoreManages(StoreManageDto storeManageDto) {
        ApiResDto<List<StoreResDto>> apiResDto = new ApiResDto<List<StoreResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        LOGGER.info("开始调用跨境结算进行批量交易单查询");
        Map<String, Object> inputParam = BeanConverterUtil.beanToMap(storeManageDto);
        Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_MANAGE, STORE_MANAGE_QUERY, new Object[]{inputParam});
        LOGGER.info("查询返回参数,outputParam:{}", response);
        final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
        final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);

        apiResDto.setResponseCode(responseCode);
        if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
            // 成功
            List<StoreResDto> storeResList = new ArrayList<StoreResDto>();
            List<StoreResDto> resultList = JSONObject.parseArray(response.get(EPPSCBPConstants.CONTEXT), StoreResDto.class);
            for (StoreResDto result : resultList) {
                result.setSite(StoreSiteEnum.getDescriptionFromCode(result.getSite()));
                result.setCur(StoreCurEnum.getDescriptionFromCode(result.getCur()));
                result.setStatus(StoreStatusEnum.getDescriptionFromCode(result.getStatus()));
                result.setWithdrawBal(StringUtil.formatMoney(Long.parseLong(result.getWithdrawBal())));
                storeResList.add(result);
            }
            DalPage pageInfo = JSONObject.parseObject(response.get(EPPSCBPConstants.PAGE), DalPage.class);
            apiResDto.setResponseMsg("");
            apiResDto.setResult(storeResList);
            apiResDto.setPage(pageInfo);
        } else {
            // 失败
            apiResDto.setResponseMsg(responseMessage);
        }

        return apiResDto;
    }

    @Override
    public ApiResDto<String> cashWithdraw(StoreManageDto storeManageDto) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(storeManageDto);
            LOGGER.info("店铺提现请求入参{}", inputParam);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_CASH_WITHDRAW, STORE_CASH_WITHDRAW, new Object[]{inputParam});
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("店铺提现返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            apiResDto.setResponseMsg(responseMessage);
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }


    @Override
    public ApiResDto<String> addStore(AddStoreDto storeManageDto) {
        ApiResDto<String> resDto = new ApiResDto<String>();
        resDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        resDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        Map<String, Object> request = BeanConverterUtil.beanToMap(storeManageDto);
        LOGGER.info("调用新增店铺rsf接口开始,inputParam:{}", storeManageDto);
        Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_MANAGE, STORE_INSERT_OR_QUERY, new Object[]{request});
        final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
        final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
        LOGGER.info("新增店铺管理返回参数,outputParam:{}", response);
        resDto.setResponseCode(responseCode);
        resDto.setResponseMsg(responseMessage);
        return resDto;
    }

    @Override
    public CommonResult deleteStore(StoreManageDto storeManageDto) {
        CommonResult result = new CommonResult();

        Map<String, Object> request = BeanConverterUtil.beanToMap(storeManageDto);
        LOGGER.info("调用删除店铺rsf接口开始,inputParam:{}", storeManageDto);
        Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_MANAGE, STORE_MANAGE_DELETE, new Object[]{request});
        LOGGER.info("删除店铺返回参数,outputParam:{}", response);
        final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
        if (!CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
            result.fail(CoreErrorCode.DELETE_RESULT_FAILURE, CoreErrorCode.DELETE_RESULT_FAILURE_CN);
        }
        return result;
    }

    @Override
    public ApiResDto<StoreSiteResDto> getStoreSites(StoreManageDto storeManageDto) {
        ApiResDto<StoreSiteResDto> resDto = new ApiResDto<StoreSiteResDto>();
        resDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        resDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(storeManageDto);
            LOGGER.info("查询店铺站点入参,inputParam:{}", inputParam);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_MANAGE, STORE_SITES, new Object[]{inputParam});
            LOGGER.info("查询店铺站点返回参数,outputParam:{}", response);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            final String context = MapUtils.getString(response, CommonConstant.CONTEXT);

            resDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("店铺管理信息返回信息成功");
                resDto.setResponseMsg("");
                StoreSiteResDto shop = JSON.parseObject(context, StoreSiteResDto.class);
                resDto.setResult(shop);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
                resDto.setResponseMsg(responseMessage);
            }
        } catch (Exception e) {
            LOGGER.info("店铺管理异常,inputParam:{}", e.fillInStackTrace());
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(e));
        }
        return resDto;
    }
}
