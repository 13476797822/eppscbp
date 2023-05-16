package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.suning.epp.eppscbp.dto.req.SaleOrderQueryReqDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

public class SaleOrderServiceImplTest {
	
	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
    @InjectMocks
    private SaleOrderServiceImpl saleOrderServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void saleOrderQuery() { 
    	SaleOrderQueryReqDto requestDto = new SaleOrderQueryReqDto();
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        saleOrderServiceImpl.saleOrderQuery(requestDto);
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        saleOrderServiceImpl.saleOrderQuery(requestDto);
        
    }
    
    @Test
    public void queryDetailInfo() { 
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        saleOrderServiceImpl.queryDetailInfo("111","111112");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        saleOrderServiceImpl.queryDetailInfo("111","111112");
        
    }

    @Test
    public void batchQueryChannel() {
        saleOrderServiceImpl.batchQueryChannel("",null);
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        saleOrderServiceImpl.batchQueryChannel("","232323232323,2323232");

        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        saleOrderServiceImpl.batchQueryChannel("","232323232323,2323232");
    }
    
}
