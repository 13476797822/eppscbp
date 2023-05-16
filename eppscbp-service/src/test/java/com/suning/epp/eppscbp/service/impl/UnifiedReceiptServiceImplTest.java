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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderExRateRefreshDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.rsf.service.impl.UnifiedReceiptServiceImpl;
import com.suning.rsf.consumer.ServiceLocator;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @param <T>
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ GeneralRsfService.class })
@SuppressStaticInitializationFor("com.suning.rsf.consumer.ServiceLocator")
public class UnifiedReceiptServiceImplTest<T> {
    @InjectMocks
    UnifiedReceiptServiceImpl unifiedReceiptServiceImpl;

    @Mock
    GeneralRsfService<Map<String, String>> generalRsfService;

    @BeforeClass
    public static void setUp() throws Exception {
        PowerMockito.mockStatic(ServiceLocator.class);
    }

    @Test
    public void submiteSettleOrder() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        OrderlAcquireResponseDto orderlAcquireResponseDto = new OrderlAcquireResponseDto();
        orderlAcquireResponseDto.setRateExpiredTime(new Date());

        response.put("context", JSON.toJSONString(orderlAcquireResponseDto));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        OrderApplyAcquireDto orderApplyAcquireDto = new OrderApplyAcquireDto();
        unifiedReceiptServiceImpl.submiteSettleOrder(orderApplyAcquireDto);
        response.put("responseCode", "000000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        unifiedReceiptServiceImpl.submiteSettleOrder(orderApplyAcquireDto);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        unifiedReceiptServiceImpl.submiteSettleOrder(orderApplyAcquireDto);
    }

    @Test
    public void doOrderOperation() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        OrderOperationDto orderOperationDto = new OrderOperationDto();
        orderOperationDto.setBusinessNo("1243");
        orderOperationDto.setPayerAccount("124");
        unifiedReceiptServiceImpl.doOrderOperation(orderOperationDto, "123");
        response.put("responseCode", "000000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        unifiedReceiptServiceImpl.doOrderOperation(orderOperationDto, "123");

        Map<String, String> response1 = new HashMap<String, String>();
        response1.put("responseCode", "E001");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response1);
        unifiedReceiptServiceImpl.doOrderOperation(orderOperationDto, "123");
    }

    @Test
    public void refreshRate() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        OrderlAcquireResponseDto orderlAcquireResponseDto = new OrderlAcquireResponseDto();
        orderlAcquireResponseDto.setRateExpiredTime(new Date());
        response.put("context", JSON.toJSONString(orderlAcquireResponseDto));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        OrderExRateRefreshDto orderExRateRefreshDto = new OrderExRateRefreshDto();
        unifiedReceiptServiceImpl.refreshRate(orderExRateRefreshDto);
        response.put("responseCode", "000000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        unifiedReceiptServiceImpl.refreshRate(orderExRateRefreshDto);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        unifiedReceiptServiceImpl.refreshRate(orderExRateRefreshDto);
    }

    @Test
    public void batchSubmiteOrder() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        OrderlAcquireResponseDto orderlAcquireResponseDto = new OrderlAcquireResponseDto();
        orderlAcquireResponseDto.setRateExpiredTime(new Date());
        response.put("context", JSON.toJSONString(orderlAcquireResponseDto));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        OrderApplyAcquireDto orderApplyAcquireDto = new OrderApplyAcquireDto();
        unifiedReceiptServiceImpl.batchSubmiteOrder(orderApplyAcquireDto);
        response.put("responseCode", "000000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        unifiedReceiptServiceImpl.batchSubmiteOrder(orderApplyAcquireDto);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        unifiedReceiptServiceImpl.batchSubmiteOrder(orderApplyAcquireDto);
    }

    @Test
    public void test1() {
        CollAndSettleApplyDto req = new CollAndSettleApplyDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        unifiedReceiptServiceImpl.submitCollSettleOrder(req);
    }

    @Test
    public void test2() {
        CollAndSettleApplyDto req = new CollAndSettleApplyDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "9999");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        unifiedReceiptServiceImpl.submitCollSettleOrder(req);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        unifiedReceiptServiceImpl.submitCollSettleOrder(req);
    }

    @Test
    public void test3() {
        String payerAccount = "";
        String orderNo = "";
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "9999");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        unifiedReceiptServiceImpl.closeCollSettOrder(payerAccount, orderNo);

    }

    @Test
    public void test4() {
        String payerAccount = "";
        String orderNo = "";
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        unifiedReceiptServiceImpl.closeCollSettOrder(payerAccount, orderNo);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        unifiedReceiptServiceImpl.closeCollSettOrder(payerAccount, orderNo);
    }
    
    @Test
    public void testQueryDetailFlag() {
        OrderOperationDto orderOperationDto = new OrderOperationDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        unifiedReceiptServiceImpl.queryDetailFlag(orderOperationDto);
    }
}
