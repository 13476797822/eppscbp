/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: OSSDownloadParams.java
 * Author:   17033387
 * Date:     2018年3月27日 下午8:01:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.util.oss;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OSSDownloadParams {
    // bucket 名称
    private String bucketName;
    // 文件ID
    private String objectId;
    // 路径名
    private String pathName;
    
    /**
     * @param bucketName
     * @param objectId
     * @param pathName
     */
    public OSSDownloadParams(String bucketName, String objectId) {
        super();
        this.bucketName = bucketName;
        this.objectId = objectId;
    }
     
    /**
     * @param bucketName
     * @param objectId
     * @param pathName
     */
    public OSSDownloadParams(String bucketName, String objectId, String pathName) {
        super();
        this.bucketName = bucketName;
        this.objectId = objectId;
        this.pathName = pathName;
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
     * @return the objectId
     */
    public String getObjectId() {
        return objectId;
    }
    /**
     * @param objectId the objectId to set
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OSSDownloadParams [bucketName=" + bucketName + ", objectId=" + objectId + ", pathName=" + pathName + "]";
    }
    
}
