package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.suning.epp.eppscbp.dto.req.CpBatchPaymentDetailReqDto;
import com.suning.epp.eppscbp.dto.req.CpTranInOutDetailReqDto;
import com.suning.epp.eppscbp.dto.req.CpWithdrawDetailReqDto;
import com.suning.epp.eppscbp.rsf.service.impl.CpBatchDetailQueryServiceImpl;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

@RunWith(MockitoJUnitRunner.class)
public class CpBatchDetailQueryServiceImplTest {
	@InjectMocks
    private CpBatchDetailQueryServiceImpl cpBatchDetailQueryServiceImpl;

	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Test
	public void batchPaymentDetailQueryTest() {
		CpBatchPaymentDetailReqDto reqDto = new CpBatchPaymentDetailReqDto();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.any(CpBatchPaymentDetailReqDto.class))).thenReturn(new HashMap<String, String>());
		cpBatchDetailQueryServiceImpl.batchPaymentDetailQuery(reqDto);
	}
	
	@Test
	public void tranInOutDetailQueryTest() {
		CpTranInOutDetailReqDto reqDto = new CpTranInOutDetailReqDto();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.any(CpTranInOutDetailReqDto.class))).thenReturn(new HashMap<String, String>());
		cpBatchDetailQueryServiceImpl.tranInOutDetailQuery(reqDto);
	}
	
	@Test
	public void withdrawDetailQueryTest() {
		CpWithdrawDetailReqDto reqDto = new CpWithdrawDetailReqDto();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(),Mockito.anyString(),Mockito.any(CpWithdrawDetailReqDto.class))).thenReturn(new HashMap<String, String>());
		cpBatchDetailQueryServiceImpl.withdrawDetailQuery(reqDto);
	}
}
