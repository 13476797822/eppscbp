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

import com.suning.epp.eppscbp.dto.req.BatchOrderQueryDto;
import com.suning.epp.eppscbp.dto.req.BatchOrderRemitQueryDto;
import com.suning.epp.eppscbp.dto.req.SingleOrderQueryDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.rsf.service.impl.ReferRateQueryServiceImpl;
import com.suning.rsf.consumer.ServiceLocator;
@RunWith(PowerMockRunner.class)
@PrepareForTest({GeneralRsfService.class})
@SuppressStaticInitializationFor("com.suning.rsf.consumer.ServiceLocator")
public class ReferRateQueryServiceImplTest {
    
    @InjectMocks
    ReferRateQueryServiceImpl rateQueryServiceImpl;

    @Mock
    GeneralRsfService<Map<String, Object>> generalRsfService;


    @BeforeClass
    public static void setUp() throws Exception {
        PowerMockito.mockStatic(ServiceLocator.class);
    }

    @Test
    public void referRateQuery() { 
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rspCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
        rateQueryServiceImpl.referRateQuery();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
        rateQueryServiceImpl.referRateQuery();

        Mockito.doThrow(new Exception());
        rateQueryServiceImpl.referRateQuery();
    }

    @Test
    public void referRateQueryZXorGD() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rspCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rateQueryServiceImpl.referRateQueryZXorGD("7");

        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        rateQueryServiceImpl.referRateQueryZXorGD("7");

        Mockito.doThrow(new Exception());
        rateQueryServiceImpl.referRateQueryZXorGD("7");
    }
    

       
}
