package com.suning.epp.eppscbp.interceptor;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.HtmlUtils;

/**
 * 
 * 〈 html转义〉<br>
 * 〈功能详细描述〉
 * 
 * @author 14072597
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StringEscapeEditor extends PropertyEditorSupport {

    public StringEscapeEditor() {
        super();
    }

    @Override
    public void setAsText(String text) {

        String  value = HtmlUtils.htmlEscape(text);
        setValue(value);
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

}
