package com.suning.epp.eppscbp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.suning.epp.eppscbp.rsf.service.impl.OrderQueryServiceImpl;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ OSSClientUtil.class })
public class OrderQueryServiceTest {

    @InjectMocks
    OrderQueryServiceImpl orderQueryServiceImpl;
    
    @Mock
    MerchantInfoService merchantInfoService;
    
    @Mock
    private ApplyFileUploadService applyFileUploadService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void uploadfile() throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Workbook workbook = wb;
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile mulFile = new MockMultipartFile("trade_supervise.xls", "trade_supervise.xls", "trade_supervise.xls", is);
        PowerMockito.mockStatic(OSSClientUtil.class);
        PowerMockito.when(OSSClientUtil.upload(Mockito.any(OSSUploadParams.class))).thenReturn("123");
        PowerMockito.when( merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        orderQueryServiceImpl.uploadFile(mulFile, "");
    }

}
