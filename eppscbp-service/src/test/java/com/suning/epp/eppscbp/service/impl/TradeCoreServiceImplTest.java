package com.suning.epp.eppscbp.service.impl;

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

import com.suning.epp.eppscbp.rsf.service.impl.TradeCoreServiceImpl;
import com.suning.fab.model.common.AbstractDatagram;
import com.suning.fab.model.common.IFabRsfService;
import com.suning.fab.model.domain.protocal.ExitBusinessCommon;
import com.suning.rsf.RSFException;
import com.suning.rsf.consumer.ExceedActiveLimitException;
import com.suning.rsf.consumer.ExceedRetryLimitException;
import com.suning.rsf.consumer.ServiceLocator;
import com.suning.rsf.consumer.TimeoutException;
@RunWith(PowerMockRunner.class)
@PrepareForTest({IFabRsfService.class})
@SuppressStaticInitializationFor("com.suning.rsf.consumer.ServiceLocator")
public class TradeCoreServiceImplTest {
    
    @InjectMocks
    TradeCoreServiceImpl tradeCoreServiceImpl;

    @Mock
    IFabRsfService queryBalanceService;


    @BeforeClass
    public static void setUp() throws Exception {
        PowerMockito.mockStatic(ServiceLocator.class);
    }

    @Test
    public void queryBalance() { 
        ExitBusinessCommon businessCommon =new ExitBusinessCommon();
        businessCommon.setRspCode("000000");
        Mockito.when(queryBalanceService.execute((AbstractDatagram) Mockito.anyObject())).thenReturn(businessCommon);
        tradeCoreServiceImpl.queryBalance("1","2","3");   
        businessCommon.setRspCode("0000");
        Mockito.when(queryBalanceService.execute((AbstractDatagram) Mockito.anyObject())).thenReturn(businessCommon);
        tradeCoreServiceImpl.queryBalance("1","2","3");  
   
        Mockito.when(queryBalanceService.execute((AbstractDatagram) Mockito.anyObject())).thenThrow(new ExceedActiveLimitException());
        tradeCoreServiceImpl.queryBalance("1","2","3");  
     
    }
    @Test
    public void queryBalance1() { 
        Mockito.when(queryBalanceService.execute((AbstractDatagram) Mockito.anyObject())).thenThrow(new ExceedRetryLimitException());
        tradeCoreServiceImpl.queryBalance("1","2","3");  
     
    }
    @Test
    public void queryBalance2() { 
        Mockito.when(queryBalanceService.execute((AbstractDatagram) Mockito.anyObject())).thenThrow(new TimeoutException());
        tradeCoreServiceImpl.queryBalance("1","2","3");  
     
    }
    @Test
    public void queryBalance3() { 
        Mockito.when(queryBalanceService.execute((AbstractDatagram) Mockito.anyObject())).thenThrow(new RSFException());
        tradeCoreServiceImpl.queryBalance("1","2","3");  
     
    }
}
