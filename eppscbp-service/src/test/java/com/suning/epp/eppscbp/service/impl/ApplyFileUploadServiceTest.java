package com.suning.epp.eppscbp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.rsf.service.SafeRateQueryService;
import com.suning.epp.eppscbp.service.MerchantInfoService;

public class ApplyFileUploadServiceTest {

    @InjectMocks
    ApplyFileUploadServiceImpl applyFileUploadService;
    
    @Mock
    SafeRateQueryService safeRateQueryService;
    @Mock
    MerchantInfoService merchantInfoService;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test() throws FileNotFoundException, IOException, ExcelForamatException, ParseException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
        Workbook workbook = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile mulFile = new MockMultipartFile("trade_supervise.xls","trade_supervise.xls","trade_supervise.xls", is);
        Map<String, String> map = Maps.newHashMap();
        map.put("responseCode", "111");
        Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
        Mockito.when(safeRateQueryService.safeRateQuery(Mockito.anyString())).thenReturn(new BigDecimal(1));
        applyFileUploadService.uploadFile(mulFile, "", 1, 1.0d, "", "USD",null);
        applyFileUploadService.uploadBatchFile(mulFile, "", "");
    }
}
