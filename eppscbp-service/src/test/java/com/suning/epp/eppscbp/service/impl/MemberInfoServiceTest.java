package com.suning.epp.eppscbp.service.impl;

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

import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.rsf.service.impl.MemberInfoServiceImpl;
import com.suning.rsf.consumer.ServiceLocator;
@RunWith(PowerMockRunner.class)
@PrepareForTest({GeneralRsfService.class})
@SuppressStaticInitializationFor("com.suning.rsf.consumer.ServiceLocator")
public class MemberInfoServiceTest {
    
    @InjectMocks
    MemberInfoServiceImpl memberInfoServiceImpl;

    @Mock
    GeneralRsfService<Map<String, Object>> generalRsfService;


    @BeforeClass
    public static void setUp() throws Exception {
        PowerMockito.mockStatic(ServiceLocator.class);
    }

    @Test
    public void validatePaymentPassword() { 
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rspCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        memberInfoServiceImpl.validatePaymentPassword("123","123");      
        
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        memberInfoServiceImpl.validatePaymentPassword("123","123"); 
    }
    
    @Test
    public void eppSecretKeyRsfServer() { 
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rspCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        memberInfoServiceImpl.eppSecretKeyRsfServer();      
        
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        memberInfoServiceImpl.eppSecretKeyRsfServer(); 
    }
}
