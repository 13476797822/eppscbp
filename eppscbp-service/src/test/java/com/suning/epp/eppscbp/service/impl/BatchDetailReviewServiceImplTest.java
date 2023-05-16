package com.suning.epp.eppscbp.service.impl;

import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewDetailDto;
import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewQueryReqDto;
import com.suning.epp.eppscbp.rsf.service.impl.BatchDetailReviewServiceImpl;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈功能详细描述〉
 *
 * @author 88450663
 * @title: BatchDetailReviewServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class BatchDetailReviewServiceImplTest {

    @InjectMocks
    BatchDetailReviewServiceImpl batchDetailReviewService;

    @Mock
    private GeneralRsfService<Map<String, String>> generalRsfService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void ordersReviewQueryTest() {
        BatchPaymentReviewQueryReqDto dto = new BatchPaymentReviewQueryReqDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode","0000");
        outputParam.put("responseMsg","成功");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.ordersReviewQuery(dto);
        outputParam.put("responseCode","9999");
        outputParam.put("responseMsg","失败");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.ordersReviewQuery(dto);
    }

    @Test
    public void ordersReviewDetailTest() {
        BatchPaymentReviewDetailDto dto = new BatchPaymentReviewDetailDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode","0000");
        outputParam.put("responseMsg","成功");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.ordersReviewDetail(dto);
        outputParam.put("responseCode","9999");
        outputParam.put("responseMsg","失败");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.ordersReviewDetail(dto);
    }

    @Test
    public void confirmBatchPaymentTest() {
        BatchPaymentReviewDetailDto dto = new BatchPaymentReviewDetailDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode","0000");
        outputParam.put("responseMsg","成功");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.confirmBatchPayment(dto);
        outputParam.put("responseCode","9999");
        outputParam.put("responseMsg","失败");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.confirmBatchPayment(dto);
    }

    @Test
    public void batchPaymentValidateTest() {
        BatchPaymentReviewDetailDto dto = new BatchPaymentReviewDetailDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode","0000");
        outputParam.put("responseMsg","成功");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.batchPaymentValidate(dto);
        outputParam.put("responseCode","9999");
        outputParam.put("responseMsg","失败");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.batchPaymentValidate(dto);
    }

    @Test
    public void batchPaymentNotPassTest() {
        BatchPaymentReviewDetailDto dto = new BatchPaymentReviewDetailDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode","0000");
        outputParam.put("responseMsg","成功");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.batchPaymentNotPass(dto);
        outputParam.put("responseCode","9999");
        outputParam.put("responseMsg","失败");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.anyObject())).thenReturn(outputParam);
        batchDetailReviewService.batchPaymentNotPass(dto);
    }
}
