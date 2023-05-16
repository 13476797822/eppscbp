/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: GoodsRMBPayService.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:54:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service;

import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈货物贸易单笔人民币结算〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface GoodsRMBPayService {
    
    /**
     * 
     * 货物贸易单笔人民币结算申请 <br>
     * 〈功能详细描述〉
     *
     * @param orderApplyAcquireDto
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<OrderlAcquireResponseDto> submiteSettleOrder(OrderApplyAcquireDto orderApplyAcquireDto);
    
    /**
     * 货物贸易单笔人民币结算确认支付<br>
     * 〈功能详细描述〉
     *
     * @param businessNo
     * @param operatingType
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<String> confirmPayment(OrderOperationDto orderOperationDto);
    
    /**
     * 
     * 查詢不同币种账户余额: <br>
     * 〈查詢不同币种账户余额〉
     *
     * @param payerMerchantId  会员户头号
     * @param accSrcCod     账户类型
     * @param balCcy        币种
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ExitQueryBalance queryBalance(String payerMerchantId,String accSrcCod,String balCcy);
    
    /**
     * 
     * 功能描述: <br>
     * 〈会员AES加解密秘钥查询服务〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<String> eppSecretKeyRsfServer();
    
    /**
     * 
     * 商户账户支付密码验证: <br>
     * 〈功能详细描述〉
     *
     * @param userNo       Epp会员编号
     * @param paymentPassword    支付密码
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<String> validatePaymentPassword(String userNo,String paymentPassword);
}
