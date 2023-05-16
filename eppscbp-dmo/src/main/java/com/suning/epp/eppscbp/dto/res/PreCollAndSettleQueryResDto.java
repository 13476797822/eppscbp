/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.*;

/**
 * @author 17080704
 *
 */
public class PreCollAndSettleQueryResDto {
    /**
     * 文件号
     */
    private String fileNo;
    /**
     * 文件上传时间
     */
    private String fileDate;
    /**
     * 文件状态
     */
    private String fileStatus;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 文件地址
     */
    private String fileAddress;

    /**
     * 异常信息文件地址
     */
    private String failFileAddress;


    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileStatusStr() {
        return PreOrderFileStatus.getDescriptionFromCode(Integer.valueOf(fileStatus));
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizTypeStr() {
        return ChargeBizType.getDescriptionFromCode(bizType);
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getFailFileAddress() {
        return failFileAddress;
    }

    public void setFailFileAddress(String failFileAddress) {
        this.failFileAddress = failFileAddress;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PreCollAndSettleQueryResDto{");
        sb.append("fileNo='").append(fileNo).append('\'');
        sb.append(", fileDate='").append(fileDate).append('\'');
        sb.append(", fileStatus='").append(fileStatus).append('\'');
        sb.append(", bizType='").append(bizType).append('\'');
        sb.append(", fileAddress='").append(fileAddress).append('\'');
        sb.append(", failFileAddress='").append(failFileAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
