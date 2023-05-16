/**
 * 
 */
package com.suning.epp.eppscbp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;

/**
 * @author 17080704
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ OSSClientUtil.class })
public class DomesticMerchantInfoServiceTest {

    @InjectMocks
    DomesticMerchantInfoServiceImpl domesticMerchantInfoServiceImpl;

    @Mock
    UnifiedReceiptService unifiedReceiptService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() throws FileNotFoundException, IOException, ExcelForamatException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/batch_domestic_merchant.xls"));
        Workbook workbook = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile mulFile = new MockMultipartFile("batch_domestic_merchant.xls", "batch_domestic_merchant.xls", "batch_domestic_merchant.xls", is);
        PowerMockito.mockStatic(OSSClientUtil.class);
        PowerMockito.when(OSSClientUtil.uploadStream(Mockito.any(OSSUploadParams.class))).thenReturn("123");
        domesticMerchantInfoServiceImpl.uploadFile(mulFile, "0000000000002042071");
    }

}
