/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: NotEmptyPatternValidator.java
 * Author:   17033387
 * Date:     2018年4月16日 上午11:33:15
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OptionValidator implements ConstraintValidator<Option, String> {

    private String[] valuelist;

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(Option constraintAnnotation) {
        this.valuelist = constraintAnnotation.value().getCodes();
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        for (String option : valuelist) {
            if(option.equals(value)){
                return true;
            }
        }
        return false;
    }

}
