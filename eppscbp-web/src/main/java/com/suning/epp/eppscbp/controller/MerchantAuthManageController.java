/*
 * Copyright (C), 2002-2021, 苏宁易购电子商务有限公司
 * FileName: MerchantAuthManageController.java
 * Author:   17033387
 * Date:     2021年7月26日 下午2:23:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.MerchantAuthEnum;
import com.suning.epp.eppscbp.dto.res.CtMerchantAuth;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.ids.client.util.AuthUtils;

/**
 * 〈权限管理〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/authManage/authManage/")
public class MerchantAuthManageController {

    /**
     * 已授权
     */
    private static final String AUTHRIZED = "Y";

    /**
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantAuthManageController.class);

    // 页面
    private static final String INIT = "screen/authManage/authManage";

    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 功能描述: <br>
     * 〈页面初始化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init(HttpServletRequest request) {
        LOGGER.info("权限管理初始化开始");
        ModelAndView mav = new ModelAndView(INIT);
        // 获取户头号和登录名
        String userNo = request.getRemoteUser();
        String userName = AuthUtils.getCurrentUserAttribute(request, "username");
        List<String> operators = merchantInfoService.queryOperator(userNo);
        mav.addObject(EPPSCBPConstants.RESULT, userName);
        mav.addObject(EPPSCBPConstants.RESULT_LIST, operators);
        LOGGER.info("权限管理初始化结束");
        return mav;
    }

    @RequestMapping("queryAuth")
    @ResponseBody
    public String queryAuth(HttpServletRequest request, String operatorCode) {
        try {
            String userNo = request.getRemoteUser();
            LOGGER.info("查询操作员权限:{}#{}", userNo, operatorCode);
            if (StringUtil.isEmpty(operatorCode)) {
                Map<String, String> response = merchantInfoService.queryMerchantAuth(userNo, EPPSCBPConstants.OPERATOR_CODE_MAIN);
                String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
                if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                    return handleQueryAuthResult(response);
                }
            } else {
                Map<String, String> responseOperator = merchantInfoService.queryMerchantAuth(userNo, operatorCode);
                String responseCodeOperator = MapUtils.getString(responseOperator, CommonConstant.RESPONSE_CODE);
                Map<String, String> response = merchantInfoService.queryMerchantAuth(userNo, EPPSCBPConstants.OPERATOR_CODE_MAIN);
                String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
                LOGGER.info("responseCode: {}",responseCode);
                LOGGER.info("responseCodeOperator: {}",responseCodeOperator);
                if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                    if (!CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCodeOperator)) {
                        // 未配置操作员权限，展示主账号所有权限
                        return handleQueryAuthResult(response);
                    } else {
                        return handleQueryAuthResult(response, responseOperator);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("查询操作员权限出错：{}", ExceptionUtils.getStackTrace(e));
        }
        return "";
    }

    private String handleQueryAuthResult(Map<String, String> response) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
        CtMerchantAuth merchantAuth = JSON.parseObject(MapUtils.getString(response, CommonConstant.CONTEXT), CtMerchantAuth.class);
        Map<String, List<Map<String, Object>>> authMap = MerchantAuthEnum.getAuthMap();
        for (Map.Entry<String, List<Map<String, Object>>> entry : authMap.entrySet()) {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> value : entry.getValue()) {
                Field field = CtMerchantAuth.class.getDeclaredField(value.get("code").toString());
                field.setAccessible(true);
                String status = field.get(merchantAuth).toString();
                if (status.equals(AUTHRIZED)) {
                    result.add(value);
                }
            }
            authMap.put(entry.getKey(), result);
        }
        LOGGER.info("authMap: {}" ,authMap);
        return JSON.toJSONString(authMap);
    }

    private String handleQueryAuthResult(Map<String, String> response, Map<String, String> responseOperator) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
        CtMerchantAuth merchantAuth = JSON.parseObject(MapUtils.getString(response, CommonConstant.CONTEXT), CtMerchantAuth.class);
        CtMerchantAuth operatorAuth = JSON.parseObject(MapUtils.getString(responseOperator, CommonConstant.CONTEXT), CtMerchantAuth.class);
        Map<String, List<Map<String, Object>>> authMap = MerchantAuthEnum.getAuthMap();
        for (Map.Entry<String, List<Map<String, Object>>> entry : authMap.entrySet()) {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> value : entry.getValue()) {
                Field field = CtMerchantAuth.class.getDeclaredField(value.get("code").toString());
                field.setAccessible(true);
                String status = field.get(merchantAuth).toString();
                if (status.equals(AUTHRIZED)) {
                    String operatorStatus = field.get(operatorAuth).toString();
                    value.put("status", operatorStatus);
                    result.add(value);
                }
            }
            entry.setValue(result);

        }
        LOGGER.info("authMap: {}" ,authMap);
        return JSON.toJSONString(authMap);
    }
    
    @RequestMapping("setAuth")
    @ResponseBody
    public String setAuth(HttpServletRequest request, @RequestBody CtMerchantAuth authority) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ischecked", true);
        try {
            String userNo = request.getRemoteUser();
            authority.setPayerAccount(userNo);
            String operatorCode = authority.getOperatorCode();
            LOGGER.info("新增/修改操作员权限:{}#{}", userNo, operatorCode);
            if (!StringUtil.isEmpty(operatorCode)) {
                Map<String, String> responseOperator = merchantInfoService.queryMerchantAuth(userNo, operatorCode);
                String responseCodeOperator = MapUtils.getString(responseOperator, CommonConstant.RESPONSE_CODE);
                if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCodeOperator)) {
                    CtMerchantAuth operatorAuth = JSON.parseObject(MapUtils.getString(responseOperator, CommonConstant.CONTEXT), CtMerchantAuth.class);
                    if(operatorAuth.equals(authority)) {
                        result.put("ischecked", false);
                        return JSON.toJSONString(result);
                    }
                }   
                result.put("isSuccess", merchantInfoService.setOperatorAuth(authority));
                return JSON.toJSONString(result);
            }
        } catch (Exception e) {
            LOGGER.error("新增/修改操作员权限出错：{}", ExceptionUtils.getStackTrace(e));
        }
        return "";
    }
}
