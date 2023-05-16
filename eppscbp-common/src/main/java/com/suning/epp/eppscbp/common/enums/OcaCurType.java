package com.suning.epp.eppscbp.common.enums;


public enum OcaCurType {
	AUD("AUD", "澳大利亚元", 2),
    SGD("SGD", "新加坡元", 2),
    KRW("KRW", "韩元", 0),
    GBP("GBP", "英镑", 2),
    HKD("HKD", "港币", 2),
    USD("USD", "美元", 2),
    CHF("CHF", "瑞士法郎", 2),
    SEK("SEK", "瑞典克朗", 2),
    DKK("DKK", "丹麦克朗", 2),
    NOK("NOK", "挪威克朗", 2),
    JPY("JPY", "日元", 0),
    CAD("CAD", "加拿大元", 2),
    EUR("EUR", "欧元", 2),
    NZD("NZD", "新西兰元", 2),
    CNY("CNY", "人民币", 2);

    private String code;
    private String description;
    private int decimalPlaces;

    /**
     * @param code
     * @param description
     */
    private OcaCurType(String code, String description, int decimalPlaces) {
        this.code = code;
        this.description = description;
        this.decimalPlaces = decimalPlaces;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * 功能描述: <br>
     * 〈根据代码取得对应的精度〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int getDecimalPlacesFromCode(String code) {
        if (code == null) {
            return -1;
        }
        for (OcaCurType type : OcaCurType.values()) {
            if (type.code.equals(code)) {
                return type.getDecimalPlaces();
            }
        }
        return -1;
    }

    /**
     * 功能描述: <br>
     * 〈获取业务数据来源描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDescriptionFromCode(String code) {
        if (null == code) {
            return "";
        }

        for (OcaCurType type : OcaCurType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return "";
    }
}
