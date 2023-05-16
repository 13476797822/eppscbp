/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: TradeCoreService.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:49:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service;

import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈账户核心接口〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface TradeCoreService {
	
	/**
	 * 
	 * 查詢不同币种账户余额: <br>
	 * 〈查詢不同币种账户余额〉
	 *
	 * @param payerMerchantId  会员户头号
	 * @param accSrcCod		账户类型
	 * @param balCcy		币种
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public ExitQueryBalance queryBalance(String payerMerchantId,String accSrcCod,String balCcy);

}
