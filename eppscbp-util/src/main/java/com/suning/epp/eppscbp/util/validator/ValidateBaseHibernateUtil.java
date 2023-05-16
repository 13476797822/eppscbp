package com.suning.epp.eppscbp.util.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.annotation.Validated;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈基于hibernate注解校验工具类〉
 *
 * @author 17070736
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ValidateBaseHibernateUtil {

    private ValidateBaseHibernateUtil() {
    }

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 
     * 功能描述: <br>
     * 〈基于hibernate注解进行校验〉
     *
     * @param object
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String validate(@Validated Object object) {
        StringBuilder sb = new StringBuilder();
        if (object != null) {
            Set<ConstraintViolation<Object>> set = validator.validate(object, Default.class);
            if (CollectionUtils.isNotEmpty(set)) {
                for (ConstraintViolation<Object> cv : set) {
                    sb.append(cv.getMessage()).append(";");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈基于hibernate注解进行校验〉
     *
     * @param object
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String validateFrist(@Validated({ First.class }) Object object) {
        StringBuilder sb = new StringBuilder();
        if (object != null) {
            Set<ConstraintViolation<Object>> set = validator.validate(object, First.class);
            if (CollectionUtils.isNotEmpty(set)) {
                for (ConstraintViolation<Object> cv : set) {
                    sb.append(cv.getMessage()).append(";");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈基于hibernate注解进行校验〉
     *
     * @param object
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String validateSecond(@Validated({ Second.class }) Object object) {
        StringBuilder sb = new StringBuilder();
        if (object != null) {
            Set<ConstraintViolation<Object>> set = validator.validate(object, Second.class);
            if (CollectionUtils.isNotEmpty(set)) {
                for (ConstraintViolation<Object> cv : set) {
                    sb.append(cv.getMessage()).append(";");
                }
            }
        }
        return sb.toString();
    }
}
