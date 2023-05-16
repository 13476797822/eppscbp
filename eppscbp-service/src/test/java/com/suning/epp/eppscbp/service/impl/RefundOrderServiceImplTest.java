package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.suning.epp.eppscbp.dto.req.RefundOrderQueryReqDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

public class RefundOrderServiceImplTest {
	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
    @InjectMocks
    private RefundOrderServiceImpl refundOrderServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void queryRefundOrder() { 
    	RefundOrderQueryReqDto requestDto = new RefundOrderQueryReqDto();
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        refundOrderServiceImpl.queryRefundOrder(requestDto);
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        refundOrderServiceImpl.queryRefundOrder(requestDto);
    }
    
    @Test
    public void refundSubmit() { 
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        refundOrderServiceImpl.refundSubmit("","","","");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        refundOrderServiceImpl.refundSubmit("","","","");
    }
    
    @Test
    public void queryDetailInfo() { 
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        refundOrderServiceImpl.queryDetailInfo("","");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        refundOrderServiceImpl.queryDetailInfo("","");
    }
}
