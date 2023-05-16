package com.suning.epp.eppscbp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.suning.epp.eppscbp.dto.req.BatchOrderQueryDto;
import com.suning.epp.eppscbp.dto.req.BatchOrderRemitQueryDto;
import com.suning.epp.eppscbp.dto.req.CollAndSettleQueryDto;
import com.suning.epp.eppscbp.dto.req.SingleOrderQueryDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.rsf.service.impl.OrderQueryServiceImpl;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.MerchantInfoService;

public class OrderQueryServiceImplTest {

    @InjectMocks
    OrderQueryServiceImpl orderQueryServiceImpl;

    @Mock
    private MerchantInfoService merchantInfoService;

    @Mock
    GeneralRsfService<Map<String, String>> generalRsfService;

    @Mock
    private ApplyFileUploadService applyFileUploadService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void singleOrderQuery() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        SingleOrderQueryDto requestParam = new SingleOrderQueryDto();
        // 户头号
        requestParam.setPayerAccount("1243124");
        // 业务单号
        requestParam.setBusinessNo("1243124");
        // 业务类型
        requestParam.setBizType("留学教育");
        // 产品类型
        requestParam.setProductType("单笔付汇");
        // 状态
        requestParam.setStatus("支付失败");
        // 监管状态
        requestParam.setSupStatus("上传失败");
        // 创建开始时间
        requestParam.setCreatOrderStartTime("1243124");
        // 创建结束时间
        requestParam.setCreatOrderEndTime("1243124");
        // 收款方商户号
        requestParam.setPayeeMerchantCode("1243124");
        // 收款方商户名称
        requestParam.setPayeeMerchantName("1243124");
        // 当前页
        requestParam.setCurrentPage("1243124");
        orderQueryServiceImpl.singleOrderQuery(requestParam);
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        SingleOrderQueryDto requestParam1 = new SingleOrderQueryDto();
        orderQueryServiceImpl.singleOrderQuery(requestParam1);

        Map<String, String> response1 = new HashMap<String, String>();
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response1);
        orderQueryServiceImpl.singleOrderQuery(requestParam1);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        orderQueryServiceImpl.singleOrderQuery(requestParam1);

    }

    @Test
    public void batchOrderQuery() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        BatchOrderQueryDto requestParam = new BatchOrderQueryDto();
        // 户头号
        requestParam.setPayerAccount("1243124");
        // 业务单号
        requestParam.setBusinessNo("1243124");
        // 状态
        requestParam.setStatus("支付失败");
        // 监管状态
        requestParam.setSupStatus("上传失败");
        // 创建开始时间
        requestParam.setCreatOrderStartTime("1243124");
        // 创建结束时间
        requestParam.setCreatOrderEndTime("1243124");
        // 当前页
        requestParam.setCurrentPage("1243124");
        orderQueryServiceImpl.batchOrderQuery(requestParam);
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        BatchOrderQueryDto requestParam1 = new BatchOrderQueryDto();
        orderQueryServiceImpl.batchOrderQuery(requestParam1);

        Map<String, String> response1 = new HashMap<String, String>();
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response1);
        orderQueryServiceImpl.batchOrderQuery(requestParam1);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        orderQueryServiceImpl.batchOrderQuery(requestParam1);

    }

    @Test
    public void batchOrderRemitQuery() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        BatchOrderRemitQueryDto requestParam = new BatchOrderRemitQueryDto();
        // 户头号
        requestParam.setPayerAccount("1243124");
        // 业务单号
        requestParam.setBusinessNo("1243124");
        // 业务子单号
        requestParam.setSubBusinessNo("12414");
        // 状态
        requestParam.setStatus("支付失败");
        // 收款方商户号
        requestParam.setPayeeMerchantCode("1243124");
        // 收款方商户名称
        requestParam.setPayeeMerchantName("1243124");
        // 创建开始时间
        requestParam.setCreatOrderStartTime("1243124");
        // 创建结束时间
        requestParam.setCreatOrderEndTime("1243124");
        // 当前页
        requestParam.setCurrentPage("1243124");
        orderQueryServiceImpl.batchOrderRemitQuery(requestParam);
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        BatchOrderRemitQueryDto requestParam1 = new BatchOrderRemitQueryDto();
        orderQueryServiceImpl.batchOrderRemitQuery(requestParam1);

        Map<String, String> response1 = new HashMap<String, String>();
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response1);
        orderQueryServiceImpl.batchOrderRemitQuery(requestParam1);

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        orderQueryServiceImpl.batchOrderRemitQuery(requestParam1);
    }

    @Test
    public void detailInfoQuery() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        orderQueryServiceImpl.detailInfoQuery("1234", "1211234");

        Map<String, String> response1 = new HashMap<String, String>();
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response1);
        orderQueryServiceImpl.detailInfoQuery("1234", "1211234");

        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        orderQueryServiceImpl.detailInfoQuery("1234", "1211234");
    }

    @Test
    public void tradeSuperviseUpload() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        orderQueryServiceImpl.tradeSuperviseUpload("1234", "1211234", "123", "");
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        orderQueryServiceImpl.tradeSuperviseUpload("123", "123", "123", "");
    }

    @Test
    public void studySuperviseUpload() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        orderQueryServiceImpl.studySuperviseUpload("1234", "1211234");
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        orderQueryServiceImpl.studySuperviseUpload("123", "123");
    }

    @Test
    public void parseFile() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(response);
        orderQueryServiceImpl.studySuperviseUpload("1234", "1211234");
        Mockito.doThrow(new RuntimeException()).when(generalRsfService).invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject());
        orderQueryServiceImpl.studySuperviseUpload("123", "123");
    }

    @Test
    public void collAndSettleQuery() {
        CollAndSettleQueryDto reqDto = new CollAndSettleQueryDto();
        reqDto.setPayerAccount("1234567890");
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        orderQueryServiceImpl.collAndSettleQuery(reqDto);

        Map<String, String> outputParam1 = new HashMap<String, String>();
        outputParam1.put("responseCode", "9999");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam1);
        orderQueryServiceImpl.collAndSettleQuery(reqDto);
    }

    @Test
    public void collAndSettleDetailInfoQuery() {

        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        orderQueryServiceImpl.collAndSettleDetailInfoQuery("1", "1");

        Map<String, String> outputParam1 = new HashMap<String, String>();
        outputParam1.put("responseCode", "9999");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam1);
        orderQueryServiceImpl.collAndSettleDetailInfoQuery("1", "1");

    }

    @Test
    public void uploadFileTest() throws FileNotFoundException, IOException, ExcelForamatException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
        Workbook workbook = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile targetFile = new MockMultipartFile("trade_supervise.xls", "trade_supervise.xls", "trade_supervise.xls", is);
        Map<String, String> map = Maps.newHashMap();
        Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
        orderQueryServiceImpl.uploadFile(targetFile, "");
    }

    @Test
    public void uploadFileTest_1() throws FileNotFoundException, IOException, ExcelForamatException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
        Workbook workbook = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile targetFile = new MockMultipartFile("trade_supervise.xls", "trade_supervise.xls", "trade_supervise.xls", is);
        Map<String, String> map = Maps.newHashMap();
        map.put("responseCode", "0000");
        map.put("context", "{'superviseCombineTypeAuth':'N'}");
        Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
        orderQueryServiceImpl.uploadFile(targetFile, "");
    }

    @Test
    public void uploadFileTest2() throws FileNotFoundException, IOException, ExcelForamatException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise.xls"));
        Workbook workbook = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile targetFile = new MockMultipartFile("trade_supervise.xls", "trade_supervise.xls", "trade_supervise.xls", is);
        Map<String, String> map = Maps.newHashMap();
        Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
        orderQueryServiceImpl.uploadFile(targetFile, "");
    }

    @Test
    public void uploadFileTest1() throws FileNotFoundException, IOException, ExcelForamatException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("src/test/resources/trade_supervise (4).xls"));
        Workbook workbook = new HSSFWorkbook(fs);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        MultipartFile targetFile = new MockMultipartFile("trade_supervise (4).xls", "trade_supervise (4).xls", "trade_supervise (4).xls", is);
        orderQueryServiceImpl.uploadFile(targetFile, "");
    }


    @Test
    public void testUploadFile() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook("src/test/resources/170707364.xlsx");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile targetFile = new MockMultipartFile("170707364.xlsx", "170707364.xlsx", "170707364.xlsx", is);
            Map<String, String> map = Maps.newHashMap();
            Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
            orderQueryServiceImpl.uploadFile(targetFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUploadFile3() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook("src/test/resources/170707364.xlsx");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile targetFile = new MockMultipartFile("170707364.xlsx", "170707364.xlsx", "170707364.xlsx", is);
            Map<String, String> map = Maps.newHashMap();
            map.put("responseCode", "0000");
            map.put("context", "{'superviseCombineTypeAuth':'N'}");
            Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
            orderQueryServiceImpl.uploadFile(targetFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUploadFile3_1() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook("src/test/resources/1707073641.xlsx");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile targetFile = new MockMultipartFile("1707073641.xlsx", "1707073641.xlsx", "1707073641.xlsx", is);
            Map<String, String> map = Maps.newHashMap();
            map.put("responseCode", "0000");
            map.put("context", "{'superviseCombineTypeAuth':'N'}");
            Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
            orderQueryServiceImpl.uploadFile(targetFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUploadFile1() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook("src/test/resources/170707364.xlsx");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile targetFile = new MockMultipartFile("170707364.xlsx", "170707364.xlsx", "170707364.xlsx", (byte[]) null);
            Map<String, String> map = Maps.newHashMap();
            Mockito.when(merchantInfoService.queryMerchantAuth(Mockito.anyString(), Mockito.anyString())).thenReturn(map);
            orderQueryServiceImpl.uploadFile(targetFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }
    }

}
