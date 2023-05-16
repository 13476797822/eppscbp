/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: AcquireErrorCodeEnum.java
 * Author:   17033387
 * Date:     2018年3月23日 上午9:43:17
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.errorcode;

import java.util.HashMap;
import java.util.Map;

import com.suning.epp.eppscbp.common.constant.CommonConstant;

/**
 * 〈收单错误码常亮类〉<br>
 * 1.0001-1999 定义接口校验错误 2.2000-2999 定义文件校验错误 3.3000-3999 定义查询相关错误〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum WebErrorCode {

    /* 接口校验错误 */
    FILE_TYPE_MISMATCH("0001", "只支持xlsx文件类型"),
    FILE_NOT_EXIST("0002", "文件为空或文件不存在"),
    FILE_NO_CONTENT("0003", "文件缺少内容"),
    DIFF_TEMPLATE("0004", "和模板不一样"),
    ORDER_SAME("0005", "业务明细单号重复"),
    CHECK_FAIL("0006", "校验错误"),
    FORMAT_ERROR("0007", "CELL格式错误"),
    QUANTITY_OVERSTEP_10000("0008", "总笔数不可超过10000笔"),
    ROW_NULL("0009", "文件中含有空行"),
    QUANTITY_OVERSTEP_50000("0008", "总笔数不可超过50000笔"),
    
    NO_RESULT("3008", "指定条件未查询到数据"),
    AUTH_NULL("3009", "未查到商户权限"),

    SUCCESS("0000", "成功"),
    SYSTEM_ERROR("9999", "系统异常, 请稍后重试");

    private String code;

    private String description;

    private WebErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 功能描述: <br>
     * 〈根据返回值取得正确回复〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Map<String, String> getSuccessResponse(String context) {
        Map<String, String> response = new HashMap<String, String>();
        response.put(CommonConstant.RESPONSE_CODE, SUCCESS.getCode());
        response.put(CommonConstant.RESPONSE_MESSAGE, SUCCESS.getDescription());
        response.put(CommonConstant.CONTEXT, context);
        return response;
    }

    /**
     * 功能描述: <br>
     * 〈根据编码取得对应的错误回复〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Map<String, String> getErrorResponse() {
        Map<String, String> response = new HashMap<String, String>();
        response.put(CommonConstant.RESPONSE_CODE, code);
        response.put(CommonConstant.RESPONSE_MESSAGE, description);
        return response;
    }
    
    public Map<String, String> getErrorResponse(String responseMsg) {
        Map<String, String> response = new HashMap<String, String>();
        response.put(CommonConstant.RESPONSE_CODE, code);
        response.put(CommonConstant.RESPONSE_MESSAGE, responseMsg);
        return response;
    }
}
