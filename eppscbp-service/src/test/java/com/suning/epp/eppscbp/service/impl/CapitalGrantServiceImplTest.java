package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.suning.epp.eppscbp.dto.req.CapitalGrantDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDto;
import com.suning.epp.eppscbp.rsf.service.impl.CapitalGrantServiceImpl;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

@RunWith(MockitoJUnitRunner.class)
public class CapitalGrantServiceImplTest {
	@InjectMocks
    private CapitalGrantServiceImpl capitalGrantServiceImpl;

	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Test
	public void getCapitalGrantTest() {
		CapitalGrantDto capitalGrantDto = new CapitalGrantDto();
		Map<String, String> response = new HashMap<String, String>();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.getCapitalGrant(capitalGrantDto);
	}
	
	@Test
	public void getCapitalGrantTest1() {
		CapitalGrantDto capitalGrantDto = new CapitalGrantDto();
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "0000");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.getCapitalGrant(capitalGrantDto);
	}
	
	@Test
	public void pendingPaymentQueryTest0() {
		CapitalGrantDto capitalGrantDto = new CapitalGrantDto();
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "0000");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.pendingPaymentQuery(capitalGrantDto);
	}
	
	@Test
	public void pendingPaymentQueryTest1() {
		CapitalGrantDto capitalGrantDto = new CapitalGrantDto();
		Map<String, String> response = new HashMap<String, String>();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.pendingPaymentQuery(capitalGrantDto);
	}
	
	@Test
	public void batchPaymentTest0() {
		OrdersAuditRequestDto requestDto= new OrdersAuditRequestDto();
		Map<String, String> response = new HashMap<String, String>();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.batchPayment(requestDto);
	}
	
	@Test
	public void batchPaymentTest1() {
		OrdersAuditRequestDto requestDto= new OrdersAuditRequestDto();
		Map<String, String> response = new HashMap<String, String>();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.batchPayment(requestDto);
	}
	
	@Test
	public void importBatchPaymentDetailsTest0() {
		CapitalGrantDto capitalGrantDto= new CapitalGrantDto();
		Map<String, String> response = new HashMap<String, String>();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.importBatchPaymentDetails(capitalGrantDto);
	}
	
	@Test
	public void importBatchPaymentDetailsTest1() {
		CapitalGrantDto capitalGrantDto= new CapitalGrantDto();
		Map<String, String> response = new HashMap<String, String>();
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		capitalGrantServiceImpl.importBatchPaymentDetails(capitalGrantDto);
	}
}
