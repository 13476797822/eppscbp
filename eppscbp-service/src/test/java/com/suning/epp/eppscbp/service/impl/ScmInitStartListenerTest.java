package com.suning.epp.eppscbp.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.suning.csrdc.scm.cache.instance.Cache;
import com.suning.csrdc.scm.cache.pool.LocalCachePool;
import com.suning.epp.eppscbp.util.ExcelUtil;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.suning.epp.eppscbp.util.ExcelUtil")
@PowerMockIgnore({"javax.net.ssl.*","javax.management.*", "javax.security.*", "javax.crypto.*"})
public class ScmInitStartListenerTest {
	@InjectMocks
	ScmInitStartListener scmInitStartListener;
	
	@Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ExcelUtil.class);
        MockitoAnnotations.initMocks(this);
    }
    
    @After
    public void tearDown() throws Exception {
        
    }
    
    @Test
    public void initTest() {
    	Cache ca = Mockito.mock(LocalCachePool.getInstance().createCache("eppscbp.config").getClass());
    	Mockito.when(ca.getString(Mockito.anyString())).thenReturn("2019-12-09 09:08:07");
    	//Mockito.when(scmInitStartListener.getValue(Mockito.anyString())).thenReturn("2019-12-09 09:08:07");
    	scmInitStartListener.init();
    }
    
}
