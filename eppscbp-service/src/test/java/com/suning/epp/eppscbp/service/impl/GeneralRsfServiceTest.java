/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: GeneralRsfService.java
 * Author:   17061545
 * Date:     2018年4月18日 上午9:48:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.rsf.consumer.ServiceAgent;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @param <T>
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */ 
public class GeneralRsfServiceTest {
    @InjectMocks
    GeneralRsfService generalRsfService;
    
    @Mock
    private ServiceAgent serviceAgent;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Object[] obj = {""};
        Mockito.when(serviceAgent.invoke("querySecretKey", obj, new Class[]{String.class})).thenReturn(null);
        generalRsfService.invoke("validatePaymentPassword", "querySecretKey", obj);
    }  
}
