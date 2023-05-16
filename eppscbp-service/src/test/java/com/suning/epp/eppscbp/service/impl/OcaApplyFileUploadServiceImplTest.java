package com.suning.epp.eppscbp.service.impl;

import java.io.*;
import java.text.ParseException;
import java.util.Map;

import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
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

import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;

public class OcaApplyFileUploadServiceImplTest {
    @InjectMocks
    OcaApplyFileUploadServiceImpl ocaApplyFileUploadServiceImpl;

    @Mock
    private GeneralRsfService<Map<String, String>> generalRsfService;

    @Mock
    OSSClientUtil ossClientUtil;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void uploadFile() throws IOException {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
            Workbook workbook = new HSSFWorkbook(fs);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile targetFile = new MockMultipartFile("trade_supervise.xls", "trade_supervise.xls", "trade_supervise.xls", is);
            ocaApplyFileUploadServiceImpl.uploadFile(targetFile, "");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    @Test
    public void uploadFileLogisticsInfoTest() throws IOException, ExcelForamatException, ParseException {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
            Workbook workbook = new HSSFWorkbook(fs);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile targetFile = new MockMultipartFile("trade_supervise.xls", "trade_supervise.xls", "trade_supervise.xls", is);
            ocaApplyFileUploadServiceImpl.uploadFileLogisticsInfo(targetFile,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
