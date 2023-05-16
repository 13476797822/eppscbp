/**
 *
 */
package com.suning.epp.eppscbp.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.suning.epp.eppscbp.dto.req.DomesticMerchantInfoReqDto;
import com.suning.epp.eppscbp.dto.res.DomesticMerchantInfoResDto;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 17080704
 */
public class DomesticMerchantInfoServiceImplTest {

    @InjectMocks
    DomesticMerchantInfoServiceImpl domesticMerchantInfoServiceImpl;

    @Mock
    UnifiedReceiptService unifiedReceiptService;

    @Mock
    GeneralRsfService<Map<String, String>> generalRsfService;

    @Mock
    OSSClientUtil ossClientUtil;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {
        DomesticMerchantInfoReqDto req = new DomesticMerchantInfoReqDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.merchantInfoQuery(req);
    }

    @Test
    public void test2() {
        DomesticMerchantInfoReqDto req = new DomesticMerchantInfoReqDto();
        DomesticMerchantInfoResDto res = new DomesticMerchantInfoResDto();
        List<DomesticMerchantInfoResDto> resultList = new ArrayList<DomesticMerchantInfoResDto>();
        resultList.add(res);
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        outputParam.put("context", JSON.toJSONString(resultList));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.merchantInfoQuery(req);
    }

    @Test
    public void test3() {
        Map<String, Object> param = new HashMap<String, Object>();
        DomesticMerchantInfoResDto result = new DomesticMerchantInfoResDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        outputParam.put("context", JSON.toJSONString(result));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.merchantInfoDetail(param);
    }

    @Test
    public void test4() {
        Map<String, Object> param = new HashMap<String, Object>();
        DomesticMerchantInfoResDto result = new DomesticMerchantInfoResDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "9999");
        outputParam.put("context", JSON.toJSONString(result));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.merchantInfoDetail(param);
    }

    @Test
    public void test5() {
        DomesticMerchantInfoReqDto req = new DomesticMerchantInfoReqDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("errorCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.merchantInfoManage(req);
    }

    @Test
    public void test6() {
        DomesticMerchantInfoReqDto req = new DomesticMerchantInfoReqDto();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("errorCode", "9999");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.merchantInfoManage(req);
    }

    @Test
    public void test7() {
        String fileAddress = "";
        String payerAccount = "";
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.batchAdd(fileAddress, payerAccount);
    }

    @Test
    public void test8() {
        String fileAddress = "";
        String payerAccount = "";
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "9999");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.batchAdd(fileAddress, payerAccount);
    }

    @Test
    public void test9() {
        JSONArray arr = new JSONArray();
        Map<String, String> outputParam = new HashMap<String, String>();
        outputParam.put("responseCode", "0000");
        outputParam.put("context", JSON.toJSONString(arr));
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.getBankList();

        outputParam.put("responseCode", "000000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(outputParam);
        domesticMerchantInfoServiceImpl.getBankList();
    }

    @Test
    public void checkRegistStatusByCompanyCodeTest() {
        Map<String, String> map = new HashMap<String, String>();

        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(null);
        domesticMerchantInfoServiceImpl.checkRegistStatusByCompanyCode(map);
    }

    @Test
    public void checkRegistStatusByCompanyCodeTest1() {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(null);
        domesticMerchantInfoServiceImpl.checkRegistStatusByCompanyCode(map);
    }

    @Test
    public void checkSnAntiMoneyStatusByCompanyCodeTest() {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("responseCode", "0000");
        Mockito.when(generalRsfService.invoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(resultMap);
        domesticMerchantInfoServiceImpl.checkSnAntiMoneyStatusByCompanyCode(map);
    }

        @Test
        public void uploadFileTest() {
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream("src/test/resources/batch_domestic_merchant.xls"));
            Workbook workbook = new HSSFWorkbook(fs);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            /*XSSFWorkbook sheets = new XSSFWorkbook();
            XSSFSheet sheet = sheets.createSheet();
            sheet.createRow(0).createCell(1).setCellValue("322332");
            Workbook workbook = new XSSFWorkbook("src/test/resources/batch_domestic_merchant.xls");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();*/
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile multipartFile = new MockMultipartFile("batch_domestic_merchant.xls", "batch_domestic_merchant.xls", "batch_domestic_merchant.xls",is);
            domesticMerchantInfoServiceImpl.uploadFile(multipartFile,"70078888");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }

        }


    @Test
    public void uploadFileTest1() {
        try {
            Workbook workbook = new XSSFWorkbook("src/test/resources/170707363.xlsx");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile multipartFile = new MockMultipartFile("170707363.xlsx", "170707363.xlsx", "170707363.xlsx", (byte[]) null);
            domesticMerchantInfoServiceImpl.uploadFile(multipartFile,"70078888");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void uploadFileTest2() {
        try {
            Workbook workbook = new XSSFWorkbook("src/test/resources/170707363.xlsx");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile multipartFile = new MockMultipartFile("170707363.xlsx", "170707363.xlsx", "170707363.xlsx", is);
            domesticMerchantInfoServiceImpl.uploadFile(multipartFile,"70078888");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void uploadFileTest3() {
        try {
            Workbook workbook = new XSSFWorkbook("src/test/resources/170707365.xlsx");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            MultipartFile multipartFile = new MockMultipartFile("170707365.xlsx", "170707365.xlsx", "170707365.xlsx", is);
            domesticMerchantInfoServiceImpl.uploadFile(multipartFile,"70078888");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcelForamatException e) {
            e.printStackTrace();
        }

    }
}
