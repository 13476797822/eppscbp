/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: OSSParams.java
 * Author:   17033387
 * Date:     2018年3月27日 下午5:30:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.util.oss;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OSSUploadParams {
    // bucket 名称
    private String bucketName;
    // 路径名(含文件名,e.g. eppcbs/a/b.txt)
    private String pathName;
    // 目标路径(含文件名,e.g. eppcbs/a/b.txt)
    private String remotePath;
    // 上传获取文件流
    private MultipartFile multipartFile;
    private InputStream inputStream;
    // 文件流类型（MIME类型）
    private String mimeType;
    // 有效期
    private Integer validDays;
    // 追加上传次数
    private Integer pos;

    /**
     * @param bucketName
     * @param fileName
     * @param pathName
     */
    public OSSUploadParams(String bucketName, String pathName) {
        super();
        this.bucketName = bucketName;
        this.pathName = pathName;
    }

    /**
     * @param bucketName
     * @param multipartFile
     * @param mimeType
     */
    public OSSUploadParams(String bucketName, MultipartFile multipartFile) {
        this.bucketName = bucketName;
        this.multipartFile = multipartFile;
        this.mimeType = multipartFile.getContentType();
    }

    /**
     * @param bucketName
     * @param inputStream
     * @param mimeType
     */
    public OSSUploadParams(String bucketName, MultipartFile multipartFile, String mimeType) {
        super();
        this.bucketName = bucketName;
        this.multipartFile = multipartFile;
        this.mimeType = mimeType;
    }
    
    /**
     * @param bucketName
     * @param inputStream
     * @param mimeType
     */
    public OSSUploadParams(String bucketName, InputStream inputStream, String mimeType) {
        super();
        this.bucketName = bucketName;
        this.inputStream = inputStream;
        this.mimeType = mimeType;
    }


    /**
     * @return the bucketName
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * @param bucketName the bucketName to set
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * @return the pathName
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * @param pathName the pathName to set
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * @return the remotePath
     */
    public String getRemotePath() {
        return remotePath;
    }

    /**
     * @param remotePath the remotePath to set
     */
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    /**
     * @return the inputStream
     */
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    /**
     * @param inputStream the inputStream to set
     */
    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the validDays
     */
    public Integer getValidDays() {
        return validDays;
    }

    /**
     * @param validDays the validDays to set
     */
    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    /**
     * @return the pos
     */
    public Integer getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(Integer pos) {
        this.pos = pos;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OSSUploadParams [bucketName=" + bucketName + ", pathName=" + pathName + ", remotePath=" + remotePath + ", multipartFile=" + multipartFile + ", mimeType=" + mimeType + ", validDays=" + validDays + ", pos=" + pos + "]";
    }

}
