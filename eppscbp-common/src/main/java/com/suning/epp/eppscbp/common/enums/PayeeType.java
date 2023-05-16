/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * @author 17080704
 *
 */
public enum PayeeType {
    PUBLIC("C", "对公用户"),

    PRIVATE_CHINESE("D", "对私中国居民");

    private String code;
    private String description;

    /**
     * @param code
     * @param description
     */
    private PayeeType(String code, String description) {
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
}
