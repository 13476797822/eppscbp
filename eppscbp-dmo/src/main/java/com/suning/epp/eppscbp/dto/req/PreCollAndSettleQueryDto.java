/**
 * 
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * @author 17080704
 *
 */
public class PreCollAndSettleQueryDto {

    /**
     * 商户户头号
     */
    private String payerAccount;
    /**
     * 文件状态
     */
    private String fileStatus;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 创建起始时间
     */
    private String creatFileStartTime;
    /**
     * 创建终止时间
     */
    private String creatFileEndTime;
    /**
     * 当前页
     */
    private String currentPage;

    private Integer pageSize;


    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getCreatFileStartTime() {
        return creatFileStartTime;
    }

    public void setCreatFileStartTime(String creatFileStartTime) {
        this.creatFileStartTime = creatFileStartTime == null ? null : creatFileStartTime.trim();
    }

    public String getCreatFileEndTime() {
        return creatFileEndTime;
    }

    public void setCreatFileEndTime(String creatFileEndTime) {
        this.creatFileEndTime = creatFileEndTime == null ? null : creatFileEndTime.trim();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PreCollAndSettleQueryDto{");
        sb.append("payerAccount='").append(payerAccount).append('\'');
        sb.append(", fileStatus='").append(fileStatus).append('\'');
        sb.append(", bizType='").append(bizType).append('\'');
        sb.append(", creatFileStartTime='").append(creatFileStartTime).append('\'');
        sb.append(", creatFileEndTime='").append(creatFileEndTime).append('\'');
        sb.append(", pageSize='").append(pageSize).append('\'');
        sb.append(", currentPage='").append(currentPage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
