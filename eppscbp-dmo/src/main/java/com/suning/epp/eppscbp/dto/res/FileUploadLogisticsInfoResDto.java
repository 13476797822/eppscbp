package com.suning.epp.eppscbp.dto.res;

/**
 * 物流信息上传返回数据
 * @author 19043747
 *
 */
public class FileUploadLogisticsInfoResDto extends CommonResDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//成功物流信息路径
	private String filePathSuccess;
	//物流信息总笔数
	private int logisticsCount;
	//成功物流笔数
	private int logisticsCountSucess;
	//失败物流笔数
	private int logisticsCountFail;
	//失败物流信息路径
	private String filePathFail;
	
	public String getFilePathSuccess() {
		return filePathSuccess;
	}
	public void setFilePathSuccess(String filePathSuccess) {
		this.filePathSuccess = filePathSuccess;
	}
	public int getLogisticsCount() {
		return logisticsCount;
	}
	public void setLogisticsCount(int logisticsCount) {
		this.logisticsCount = logisticsCount;
	}
	public int getLogisticsCountSucess() {
		return logisticsCountSucess;
	}
	public void setLogisticsCountSucess(int logisticsCountSucess) {
		this.logisticsCountSucess = logisticsCountSucess;
	}
	public int getLogisticsCountFail() {
		return logisticsCountFail;
	}
	public void setLogisticsCountFail(int logisticsCountFail) {
		this.logisticsCountFail = logisticsCountFail;
	}
	public String getFilePathFail() {
		return filePathFail;
	}
	public void setFilePathFail(String filePathFail) {
		this.filePathFail = filePathFail;
	}
	
}
