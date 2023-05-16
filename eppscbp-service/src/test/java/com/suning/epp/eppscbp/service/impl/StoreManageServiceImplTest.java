package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.suning.epp.eppscbp.dto.req.AddStoreDto;
import com.suning.epp.eppscbp.dto.req.StoreManageDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.rsf.service.impl.StoreManageServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class StoreManageServiceImplTest {
	@InjectMocks
    private StoreManageServiceImpl storeManageServiceImpl;

	@Mock
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Test
	public void getStoreInfosTest() {
		StoreManageDto dto = new StoreManageDto();
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "0000");
		response.put("context", "0000");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		storeManageServiceImpl.getStoreInfos(dto);
	}
	
	@Test
	public void getStoreInfosTest1() {
		StoreManageDto dto = new StoreManageDto();
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "222");
		response.put("context", "0000");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		storeManageServiceImpl.getStoreInfos(dto);
	}
	
	@Test
	public void getStoreManagesTest1() {
		StoreManageDto storeManageDto = new StoreManageDto();
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseCode", "2222");
		Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
		storeManageServiceImpl.getStoreManages(storeManageDto);
	}
	
	@Test
	public void cashWithdrawTest() {
		StoreManageDto storeManageDto = new StoreManageDto();
		storeManageServiceImpl.cashWithdraw(storeManageDto);
	}
	
	@Test
	public void addStoreTest() {
		AddStoreDto addStoreDto = new AddStoreDto();
		storeManageServiceImpl.addStore(addStoreDto);
	}  
	
	@Test
	public void deleteStoreTest() {
		StoreManageDto storeManageDto = new StoreManageDto();
		storeManageServiceImpl.deleteStore(storeManageDto);
	}
	
	@Test
	public void getStoreSitesTest() {
		StoreManageDto storeManageDto = new StoreManageDto();
		storeManageServiceImpl.getStoreSites(storeManageDto);
	}

}
