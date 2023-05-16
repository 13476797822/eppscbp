/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: CommonResDto.java
 * Author:   17033387
 * Date:     2018年4月11日 下午3:35:55
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.res;

import java.io.Serializable;

/**
 * 〈标准结果类〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CommonResDto implements Serializable {

    /**
     */
    private static final long serialVersionUID = 5706911466637834964L;

    /** 默认成功 */
    protected boolean success = true;

    /** 错误代码 */
    protected String errorCode = "";

    /** 错误信息 */
    protected String message = "";

    /**
     * 复制
     * 
     * @param CommonResDto 
     * @return CommonResDto
     */
    public CommonResDto copy(CommonResDto CommonResDto) {
        if (CommonResDto != null) {
            this.success = CommonResDto.success;
            this.errorCode = CommonResDto.errorCode;
            this.message = CommonResDto.message;
        }

        return this;
    }

    /**
     * 置为失败
     */
    public void fail() {
        this.success = false;
    }

    /**
     * 置为失败,添加错误码
     * 
     * @param errorCode 添加错误码
     */
    public void fail(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    /**
     * 置为失败,添加错误码，错误信息
     * 
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public void fail(String errorCode, String message) {
        this.success = false;
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 判断是否成功
     * 
     * @return 成功与否
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
