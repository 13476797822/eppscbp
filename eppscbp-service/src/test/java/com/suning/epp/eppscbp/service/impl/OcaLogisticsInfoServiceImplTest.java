package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.suning.epp.eppscbp.dto.req.LogisticsInfoQueryReqDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

public class OcaLogisticsInfoServiceImplTest {
	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
    @InjectMocks
    private OcaLogisticsInfoServiceImpl ocaLogisticsInfoServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void queryRefundOrder() { 
    	LogisticsInfoQueryReqDto requestDto = new LogisticsInfoQueryReqDto();
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ocaLogisticsInfoServiceImpl.logisticsInfoQuery(requestDto);
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ocaLogisticsInfoServiceImpl.logisticsInfoQuery(requestDto);
    }
    
    @Test
    public void logisticsInfoSubmit() { 
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ocaLogisticsInfoServiceImpl.logisticsInfoSubmit("","");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ocaLogisticsInfoServiceImpl.logisticsInfoSubmit("","");
    }
}
