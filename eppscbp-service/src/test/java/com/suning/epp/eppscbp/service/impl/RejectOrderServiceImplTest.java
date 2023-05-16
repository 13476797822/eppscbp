package com.suning.epp.eppscbp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.suning.epp.eppscbp.dto.res.RefundOrderQueryRespDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.suning.epp.eppscbp.dto.req.RejectOrderQueryReqDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

public class RejectOrderServiceImplTest {
	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
    @InjectMocks
    private RejectOrderServiceImpl rejectOrderServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void rejectOrderQuery() { 
    	RejectOrderQueryReqDto requestDto = new RejectOrderQueryReqDto();
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.rejectOrderQuery(requestDto);
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.rejectOrderQuery(requestDto);
        
    }
    
    @Test
    public void rejectDetailInfoQuery() { 
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.rejectDetailInfoQuery("111","111112");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.rejectDetailInfoQuery("111","111112");
        
    }
    
    @Test
    public void accept() { 
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.accept("111","111112","");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.accept("111","111112","");
        
    }
    
    @Test
    public void appeal() { 
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.appeal("111","111112","","");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.appeal("111","111112","","");
        
    }

    @Test
    public void rejectMessageBoxQueryTest() {
        rejectOrderServiceImpl.rejectMessageBoxQuery("12345","23456");
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        List<RefundOrderQueryRespDto> refundOrderQueryRespDtos = new ArrayList<RefundOrderQueryRespDto>();
        RefundOrderQueryRespDto dto = new RefundOrderQueryRespDto();
        dto.setAmt(1000l);
        dto.setAmtCny(7000l);
        dto.setRefundOrderNo("12345");
        refundOrderQueryRespDtos.add(dto);
        String s = JSON.toJSONString(refundOrderQueryRespDtos);
        response.put("context",s);
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rejectOrderServiceImpl.rejectMessageBoxQuery("12345","23456");
    }
}
