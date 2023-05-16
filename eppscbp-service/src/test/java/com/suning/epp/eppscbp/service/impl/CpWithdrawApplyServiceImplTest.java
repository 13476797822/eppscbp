package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.suning.epp.eppscbp.dto.req.CpWithdrawApplyDto;
import com.suning.epp.eppscbp.rsf.service.impl.CpWithdrawApplyServiceImpl;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

@RunWith(MockitoJUnitRunner.class)
public class CpWithdrawApplyServiceImplTest {

	@InjectMocks
    private CpWithdrawApplyServiceImpl cpWithdrawApplyServiceImpl;

	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Test
	public void batchQueryTest() {
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "111");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		cpWithdrawApplyServiceImpl.batchQuery("1", "3", "00001111");
	}
	
	@Test
	public void outBatchQueryTest() {
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "111");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		cpWithdrawApplyServiceImpl.outBatchQuery("1", "00001111");
	}
	
	@Test
	public void submitWithdrawApplyTest() {
		CpWithdrawApplyDto dto = new CpWithdrawApplyDto();
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "111");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		cpWithdrawApplyServiceImpl.submitWithdrawApply(dto);
	}
}
