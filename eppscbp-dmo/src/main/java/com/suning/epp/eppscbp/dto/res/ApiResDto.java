/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: ApiRe.java
 * Author:   17061545
 * Date:     2018年4月18日 下午2:15:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.res;

import java.io.Serializable;

import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.CommonConstant;

/**
 * 〈接口返回结果类〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ApiResDto<T> implements Serializable {

    /**
     */
    private static final long serialVersionUID = 4361175168127086733L;
    
    /** 默认代码 */
    protected String responseCode = CommonConstant.RESPONSE_SUCCESS_CODE;

    /** 错误信息 */
    protected String responseMsg = "";

    /** 结果实体类 */
    protected T result;
    
    /** 结果实体类 */
    protected DalPage page;
    
    /** 标记 */
    protected Boolean flag;
    
    /**
     * @return the page
     */
    public DalPage getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(DalPage page) {
        this.page = page;
    }

    /**
     * 判断是否成功
     * 
     * @return 成功与否
     */
    public boolean isSuccess() {
        return CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode);
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseMsg
     */
    public String getResponseMsg() {
        return responseMsg;
    }

    /**
     * @param responseMsg the responseMsg to set
     */
    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    /**
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(T result) {
        this.result = result;
    } 

    /**
     * @return the flag
     */
    public Boolean getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ApiResDto [responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", result=" + result + ", page=" + page + ", flag=" + flag + "]";
    }

}
