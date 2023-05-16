package com.suning.epp.eppscbp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.dto.req.MerchantInfoQueryDto;
import com.suning.epp.eppscbp.dto.res.MerchantInfoQueryResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.rsf.consumer.ServiceLocator;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GeneralRsfService.class })
@SuppressStaticInitializationFor("com.suning.rsf.consumer.ServiceLocator")
public class MerchantInfoServiceImplTest {

    @InjectMocks
    MerchantInfoServiceImpl merchantInfoServiceImpl;

    @Mock
    GeneralRsfService<Map<String, String>> generalRsfService;

    @BeforeClass
    public static void setUp() throws Exception {
        PowerMockito.mockStatic(ServiceLocator.class);
    }

    @Test
    public void queryMerchantAuth() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        merchantInfoServiceImpl.queryMerchantAuth("123", "0");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(null);
        merchantInfoServiceImpl.queryMerchantAuth("123", "0");
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        merchantInfoServiceImpl.queryMerchantAuth("123", "0");
    }

    @Test
    public void queryMerchantMeg() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        merchantInfoServiceImpl.queryMerchantMeg("123", "123", "123");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(null);
        merchantInfoServiceImpl.queryMerchantMeg("123", "123", "123");
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        merchantInfoServiceImpl.queryMerchantMeg("123", "123", "123");
    }

    @Test
    public void merchantInfoQuery() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        List<MerchantInfoQueryResDto> resultList = new ArrayList<MerchantInfoQueryResDto>();
        MerchantInfoQueryResDto dto = new MerchantInfoQueryResDto();
        dto.setBizType("001");
        dto.setCurrency("USD");
        dto.setPayeeCountry("USA");
        resultList.add(dto);
        response.put("context", JSON.toJSONString(resultList));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        MerchantInfoQueryDto reqDto = new MerchantInfoQueryDto();
        merchantInfoServiceImpl.merchantInfoQuery(reqDto);
    }

    @Test
    public void merchantInfoManage() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        MerchantInfoQueryDto reqDto = new MerchantInfoQueryDto();
        merchantInfoServiceImpl.merchantInfoManage(reqDto);
    }

    @Test
    public void detailsMerchantInfo() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        Map<String, Object> resquest = new HashMap<String, Object>();
        merchantInfoServiceImpl.detailsMerchantInfo(resquest);
    }

    @Test
    public void batchAdd() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        merchantInfoServiceImpl.batchAdd("123", "123");
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        merchantInfoServiceImpl.batchAdd("123", "123");
    }

    @Test
    public void uploadFile() throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
        Workbook workbook = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile mulFile = new MockMultipartFile("trade_supervise.xls","trade_supervise.xls","trade_supervise.xls", is);
        merchantInfoServiceImpl.uploadFile(mulFile, "");
    }
}
