/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: GeneralRsfService.java
 * Author:   17061545
 * Date:     2018年4月18日 上午9:48:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.MemberInfoService;
import com.suning.epp.eppscbp.rsf.service.TradeCoreService;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @param <T>
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */ 
public class GoodsRMBPayServiceImplTest {
    @InjectMocks
    GoodsRMBPayServiceImpl goodsRMBPayServiceImpl;

    @Mock
    UnifiedReceiptService unifiedReceiptService;
    
    @Mock
    TradeCoreService tradeCoreService;
    
    @Mock
    MemberInfoService memberInfoService;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void submiteSettleOrder() {
        ApiResDto<OrderlAcquireResponseDto> apiResDto=new ApiResDto<OrderlAcquireResponseDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        Mockito.when(unifiedReceiptService.submiteSettleOrder(Mockito.any(OrderApplyAcquireDto.class))).thenReturn(apiResDto);
        OrderApplyAcquireDto orderApplyAcquireDto =new OrderApplyAcquireDto();
        orderApplyAcquireDto.setAmtType("01");
        goodsRMBPayServiceImpl.submiteSettleOrder(orderApplyAcquireDto);    
        Mockito.when(unifiedReceiptService.submiteSettleOrder(Mockito.any(OrderApplyAcquireDto.class))).thenReturn(apiResDto);
        orderApplyAcquireDto.setAmtType("00");
        goodsRMBPayServiceImpl.submiteSettleOrder(orderApplyAcquireDto);   
    }  
    
    @Test
    public void confirmPayment() {
        ApiResDto<String> apiResDto=new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        Mockito.when(unifiedReceiptService.doOrderOperation(Mockito.any(OrderOperationDto.class),Mockito.anyString())).thenReturn(apiResDto);
        OrderOperationDto orderOperationDto =new OrderOperationDto();
        goodsRMBPayServiceImpl.confirmPayment(orderOperationDto);  
    } 
    
    @Test
    public void queryBalance() {
        ExitQueryBalance balance=new ExitQueryBalance();
        Mockito.when(tradeCoreService.queryBalance(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(balance);
        goodsRMBPayServiceImpl.queryBalance("1","2","3");  
    } 
    @Test
    public void eppSecretKeyRsfServer() {
        ApiResDto<String> apiResDto=new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        Mockito.when(memberInfoService.eppSecretKeyRsfServer()).thenReturn(apiResDto);
        goodsRMBPayServiceImpl.eppSecretKeyRsfServer();  
    } 
    
    @Test
    public void validatePaymentPassword() {
        ApiResDto<String> apiResDto=new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        Mockito.when(memberInfoService.validatePaymentPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(apiResDto);
        goodsRMBPayServiceImpl.validatePaymentPassword("1","2");  
    } 
}
