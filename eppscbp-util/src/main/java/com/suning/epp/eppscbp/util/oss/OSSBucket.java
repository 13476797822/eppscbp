/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: OSSBucket.java
 * Author:   17033387
 * Date:     2018年3月28日 上午10:47:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.util.oss;

/**
 * 〈管理bucketname〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OSSBucket {

    private OSSBucket() {

    }

    // 购付汇明细文件
    public static final String DETAIL_FILE = "detail_file";

    // 收结汇明细文件
    public static final String COLL_SETT_FILE = "coll_sett_file";

    // 批量商户新增
    public static final String MERCHANT_FILE = "merchant_file";

    // 监管信息货物贸易
    public static final String TRADE_SUPERVISE = "trade_supervise";

    // 监管信息留学教育
    public static final String STUDY_SUPERVISE = "study_supervise";

    // 批付明细文件
    public static final String BATCH_PAYMENT_FILE = "batch_payment_file";

    //店铺管理明细文件
    public static final String STORE_HANDLE_FILE = "store_handle_file";
    
    //oca buckName
    public static final String OCA = "oca";
    
    // oca物流 bucket
    public static final String UNDERLINE_FILE_BUCKET = "underline_file_bucket";
    
    //oca拒付订单-申诉材料
    public static final String REJECT_APPEAL_FILE = "rejectAppealFile/";
    
    //oca物流文件_成功的文件
    public static final String LOGISTICS_FILE_SUCC = "logisticsFileSucc/";
    
    //oca物流文件_失败的文件
    public static final String LOGISTICS_FILE_FAIL = "logisticsFileFail/";
    
    //oca banner背景图片
    public static final String BANNER = "banner/banner.jpg";

}
