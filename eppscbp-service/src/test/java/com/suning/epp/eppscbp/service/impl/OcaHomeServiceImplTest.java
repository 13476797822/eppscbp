package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.WebApplicationContext;

import com.suning.epp.eppscbp.rsf.service.TradeCoreService;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

public class OcaHomeServiceImplTest {
	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	@Mock
	private ScmInitStartListener scmInitStartListener;
	@Mock
	private TradeCoreService tradeCoreService;
	@Mock
	private WebApplicationContext webApplicationContext;
	
	@Mock 
	private MockHttpSession session;


	
    @InjectMocks
    private  OcaHomeServiceImpl  ocaHomeServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        session = new MockHttpSession(); //2.初始化

    }
    
    @Test
    public void queryRefundOrder() { 
        ExitQueryBalance balanceResult = new ExitQueryBalance();
        balanceResult.setRspCode("000000");
        Mockito.when(tradeCoreService.queryBalance(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn(balanceResult);
        Map<String, String> response1 = new HashMap<String, String>();
        response1.put("responseCode", "0000");
        response1.put("responseMsg","成功");
        response1.put("context","3223");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response1);
        ocaHomeServiceImpl.initHomeInfo("",  null);
        
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0004");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ExitQueryBalance balanceResult1 = new ExitQueryBalance();
        Mockito.when(tradeCoreService.queryBalance(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn(balanceResult1);
        ocaHomeServiceImpl.initHomeInfo("",  null);
        
        Map<String, String> response2 = new HashMap<String, String>();
        response.put("responseCode", "11111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response2);
        ExitQueryBalance balanceResult2 = new ExitQueryBalance();
        Mockito.when(tradeCoreService.queryBalance(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn(balanceResult2);
        ocaHomeServiceImpl.initHomeInfo("",  null);
    }


    @Test
    public void queryRefundOrder1() {
        Mockito.when(scmInitStartListener.getValue(Mockito.anyString())).thenReturn("dwedew|221212");

        ExitQueryBalance balanceResult = new ExitQueryBalance();
        balanceResult.setRspCode("000000");
        Mockito.when(tradeCoreService.queryBalance(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn(balanceResult);
        Map<String, String> response1 = new HashMap<String, String>();
        response1.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response1);
        ocaHomeServiceImpl.initHomeInfo("",  null);

        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0004");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ExitQueryBalance balanceResult1 = new ExitQueryBalance();
        Mockito.when(tradeCoreService.queryBalance(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn(balanceResult1);
        ocaHomeServiceImpl.initHomeInfo("",  null);

        Map<String, String> response2 = new HashMap<String, String>();
        response.put("responseCode", "11111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response2);
        ExitQueryBalance balanceResult2 = new ExitQueryBalance();
        Mockito.when(tradeCoreService.queryBalance(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn(balanceResult2);
        ocaHomeServiceImpl.initHomeInfo("",  null);
    }

    
    @Test
    public void queryHomeInfo() { 
    	Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        response.put("context", "{\"includeMarginAmount\":331228,\"refundSuccessAmount\":223500,\"refundSuccessCount\":14,\"rejectSuccessAmount\":34000,\"rejectSuccessCount\":2,\"releaseMarginAmount\":207877,\"saleSuccessAmount\":1123155,\"saleSuccessCount\":50,\"settledAmount\":8929515}");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ocaHomeServiceImpl.queryHomeInfo("","","");
        
        response.put("responseCode", "1111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        ocaHomeServiceImpl.queryHomeInfo("","","");
    }
    
    
}
