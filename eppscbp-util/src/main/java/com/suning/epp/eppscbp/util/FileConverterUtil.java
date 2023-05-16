package com.suning.epp.eppscbp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 〈转化附件成字节流，修复文件导出乱码问题〉<br>
 * 〈币种相关工具类〉
 * 
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FileConverterUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileConverterUtil.class);
	
    /**
     * 功能描述: <br>
     * 〈读取excel文件生成字节流〉
     * 
     * @param fileType
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static byte[] toByteExcel(File file) throws IOException {
    	
     	InputStream fileInput=new FileInputStream(file);
    	
    	HSSFWorkbook workbook=new HSSFWorkbook(fileInput);
    	
    	ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
    	try {
    		workbook.write(byteOutStream);
    	}catch(FileNotFoundException e){
    		LOG.error("excel模版文件不存在", e);    		
    	}catch(Exception e){ 
    		LOG.error("excel模版读取异常", e);
    		
    	}finally {
    		byteOutStream.close();
    	}
    	byte[] bytes = byteOutStream.toByteArray();   	
    	return bytes;  

    }  

}
