package com.suning.epp.eppscbp.service;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.res.FileUploadLogisticsInfoResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;

public interface OcaApplyFileUploadService {

	/**
	 * 上传申诉材料
	 * @param targetFile
	 * @param remoteUser
	 * @return
	 */
	FileUploadResDto uploadFile(MultipartFile targetFile, String remoteUser);

	/**
	 * 上传物流信息
	 * @param targetFile
	 * @param remoteUser
	 * @return
	 * @throws IOException 
	 */
	FileUploadLogisticsInfoResDto uploadFileLogisticsInfo(MultipartFile targetFile, String remoteUser) throws ExcelForamatException, ParseException, IOException;

}
