/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: MemberInfoService1.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:48:24
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service;

import com.suning.epp.eppscbp.dto.res.ApiResDto;

/**
 * 〈会员支付密码验证〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface MemberInfoService {
	
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

}
