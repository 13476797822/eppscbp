package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.suning.epp.eppscbp.dto.req.TradeInfoReqDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;

@RunWith(MockitoJUnitRunner.class)
public class TradeInfoAggregationServiceImplTest {
	@InjectMocks
	private TradeInfoAggregationServiceImpl tradeInfoAggregationServiceImpl;
	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Test
	public void queryCollSettTradeInfoTest() {
		TradeInfoReqDto reqDto = new TradeInfoReqDto();
		reqDto.setMerchantAccount("1111");
		Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		tradeInfoAggregationServiceImpl.queryCollSettTradeInfo(reqDto);
	}
	
	@Test
	public void queryCollSettTradeInfoTest1() {
		TradeInfoReqDto reqDto = new TradeInfoReqDto();
		reqDto.setMerchantAccount("1111");
		Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		tradeInfoAggregationServiceImpl.queryCollSettTradeInfo(reqDto);
	}
	
	@Test
	public void purchasePaymentTradeInfoTest() {
		TradeInfoReqDto reqDto = new TradeInfoReqDto();
		reqDto.setMerchantAccount("1111");
		Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		tradeInfoAggregationServiceImpl.purchasePaymentTradeInfo(reqDto);
	}
	
	@Test
	public void purchasePaymentTradeInfoTest1() {
		TradeInfoReqDto reqDto = new TradeInfoReqDto();
		reqDto.setMerchantAccount("1111");
		Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		tradeInfoAggregationServiceImpl.purchasePaymentTradeInfo(reqDto);
	}
	
	@Test
	public void getFileAddrePDFTest() {
		TradeInfoReqDto reqDto = new TradeInfoReqDto();
		reqDto.setMerchantAccount("1111");
		Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		tradeInfoAggregationServiceImpl.getFileAddrePDF("","");
	}
	
	@Test
	public void getFileAddrePDFTest1() {
		TradeInfoReqDto reqDto = new TradeInfoReqDto();
		reqDto.setMerchantAccount("1111");
		Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		tradeInfoAggregationServiceImpl.getFileAddrePDF("","");
	}
	
	@Test
	public void queryPDFAddressByCondition1() {
		TradeInfoReqDto reqDto = new TradeInfoReqDto();
		reqDto.setMerchantAccount("1111");
		Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "111");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		tradeInfoAggregationServiceImpl.queryPDFAddressByCondition(reqDto);
	}
	

}
