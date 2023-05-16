package com.suning.epp.eppscbp.rsf.service;

import com.suning.epp.eppscbp.dto.req.AddStoreDto;
import com.suning.epp.eppscbp.dto.req.StoreManageDto;
import com.suning.epp.eppscbp.dto.req.StoreWithDrawAndPaymentDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.StoreResDto;
import com.suning.epp.eppscbp.dto.res.StoreSiteResDto;
import com.suning.epp.pu.common.aop.lang.CommonResult;

import java.util.List;

/**
 * @author 88412423
 *
 */
public interface StoreManageService {

    ApiResDto<StoreWithDrawAndPaymentDto> getStoreInfos(StoreManageDto storeManageDto);

    ApiResDto<List<StoreResDto>> getStoreManages(StoreManageDto storeManageDto);

    ApiResDto<String> addStore(AddStoreDto storeManageDto);

    ApiResDto<String> cashWithdraw(StoreManageDto storeManageDto);

    CommonResult deleteStore(StoreManageDto storeManageDto);

    ApiResDto<StoreSiteResDto> getStoreSites(StoreManageDto storeManageDto);

}
