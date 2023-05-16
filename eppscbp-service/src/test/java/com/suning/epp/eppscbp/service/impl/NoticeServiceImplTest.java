package com.suning.epp.eppscbp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.suning.epp.eppscbp.common.constant.ScmOnlineConfig;

@RunWith(MockitoJUnitRunner.class)
public class NoticeServiceImplTest {
    @Mock
    private ScmInitStartListener scmInitStartListener;
    @InjectMocks
    private NoticeServiceImpl noticeServiceImpl;

    @Test
    public void testNoticeQuery() throws Exception {
        Mockito.when(scmInitStartListener.getValue("a")).thenReturn("b");
        noticeServiceImpl.noticeQuery("a");
    }
    
    @Test
    public void testNoticeQuery1() throws Exception {
    	String a ="a|b";
        Mockito.when(scmInitStartListener.getValue(a)).thenReturn(a);
        noticeServiceImpl.noticeQuery(a);
    }

}
