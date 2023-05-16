package com.suning.epp.eppscbp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.dto.req.OrderCalculateDetailDto;
import com.suning.epp.eppscbp.dto.req.OrderCalculateDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDetailDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.dto.res.BatchPaymentQueryResDto;

@RunWith(MockitoJUnitRunner.class)
public class BatchPaymentServiceImplTest {
    @Mock
    private GeneralRsfService<Map<String, String>> generalRsfService;
    @InjectMocks
    private BatchPaymentServiceImpl batchPaymentServiceImpl;

    @Test
    public void testOrdersAudit() throws Exception {

        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.ordersAudit("a", "b", new ArrayList<OrdersAuditRequestDetailDto>());
        
        response.put("responseCode", "000000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.ordersAudit("a", "b", new ArrayList<OrdersAuditRequestDetailDto>());
        
    }

    @Test
    public void testOrdersQuery() throws Exception {
        List<BatchPaymentQueryResDto> dto = new ArrayList<BatchPaymentQueryResDto>();
        
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        response.put("context", JSON.toJSONString(dto));
        response.put("batchPaymentFlag", "0");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.ordersQuery("a", "b");
        
        response.put("responseCode", "000000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.ordersQuery("a", "b");
    }

    @Test
    public void testResultQuery() throws Exception {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "00000");
        response.put("batchPaymentFlag", "0");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.resultQuery("a", "b");
        
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.resultQuery("a", "b");
    }
    
    @Test
    public void testOrdersCalculate() throws Exception {      
        OrderCalculateDto ordersAuditRequestDto = new OrderCalculateDto();
        List<OrderCalculateDetailDto> list = new ArrayList<OrderCalculateDetailDto>();
        OrderCalculateDetailDto dto1 = new OrderCalculateDetailDto();
        dto1.setAmount("1");
        dto1.setNumber("1");
        OrderCalculateDetailDto dto2 = new OrderCalculateDetailDto();
        dto2.setAmount("2");
        dto2.setNumber("2");
        list.add(dto1);
        list.add(dto2);
        ordersAuditRequestDto.setDetails(list);
        ordersAuditRequestDto.setPrePayAmount("3");
        batchPaymentServiceImpl.ordersCalculate(ordersAuditRequestDto);
    }
    
    @Test
    public void bpOrdersParseAndCalculateTest() {
    	
    	batchPaymentServiceImpl.bpOrdersParseAndCalculate("", "", "", "", "", "");
    }
    
    @Test
    public void bpOrdersParseAndCalculateTest1() {
    	Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
    	batchPaymentServiceImpl.bpOrdersParseAndCalculate("", "", "", "", "", "");
    }
    
    @Test
    public void bpOrdersParseAndCalculateTest2() {
    	Map<String, String> response = new HashMap<String, String>();
    	List<OrderCalculateDetailDto> details = new ArrayList<OrderCalculateDetailDto>();
    	OrderCalculateDetailDto dto = new OrderCalculateDetailDto();
    	dto.setAmount("333");
    	details.add(dto);
    	response.put("context", JSON.toJSONString(details));
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
    	batchPaymentServiceImpl.bpOrdersParseAndCalculate("", "33334", "", "","3444", "");
    }


    @Test
    public void batchPaymentUploadTest() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseMsg", "成功");
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.batchPaymentUpload("123","123","dedddd","cbp");
    }

    @Test
    public void batchPaymentUploadTest1() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseMsg", "失败");
        response.put("responseCode", "9999");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(response);
        batchPaymentServiceImpl.batchPaymentUpload("123","123","dedddd","cbp");
    }

}
