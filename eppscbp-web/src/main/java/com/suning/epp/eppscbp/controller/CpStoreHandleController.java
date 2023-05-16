package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.StoreCurEnum;
import com.suning.epp.eppscbp.common.enums.StorePlatformEnum;
import com.suning.epp.eppscbp.common.enums.StoreSiteEnum;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.AddStoreDto;
import com.suning.epp.eppscbp.dto.req.StoreManageDto;
import com.suning.epp.eppscbp.dto.req.StoreWithDrawAndPaymentDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.StoreResDto;
import com.suning.epp.eppscbp.dto.res.StoreSiteResDto;
import com.suning.epp.eppscbp.rsf.service.StoreManageService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.pu.common.aop.lang.CommonResult;

/**
 * 〈店铺管理〉
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/cpStoreHandle/cpStoreHandleQuery/")
public class CpStoreHandleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CpStoreHandleController.class);

    private static final String STORE_HANDLE = "screen/cpStoreHandle/cpStoreHandle";

    @Autowired
    private StoreManageService storeManageService;

    @Autowired
    private ApplyFileUploadService applyFileUploadService;


    /**
     * 功能描述: <br>
     * 〈页面初期化〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init(HttpServletRequest request) {
        LOGGER.info("店铺管理初始化开始");
        ModelAndView mav = new ModelAndView(STORE_HANDLE);
        StoreManageDto storeManageDto = new StoreManageDto();

        try {
            //获取币种金额和站点信息
            getCurAndSites(request, mav, StorePlatformEnum.SRORE_PLATFORM_AMAZON.getCode(), storeManageDto);
            
            //店铺初始化时，展示店铺列表数据
            LOGGER.info("初始化查询店铺列表开始.获取付款方户头号:{}", request.getRemoteUser());
    		storeManageDto.setPayerAccount(request.getRemoteUser());
    		storeManageDto.seteCommercePlatform(StorePlatformEnum.SRORE_PLATFORM_AMAZON.getCode());
            batchQueryStoreInfo(request, mav, storeManageDto);
            LOGGER.info("初始化查询店铺列表结束.");
            
        } catch (Exception ex) {
            LOGGER.error("获取店铺管理信息异常:{}", ExceptionUtils.getStackTrace(ex));
            mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.ERROR_QUERY_MSG);
           }

        LOGGER.info("店铺管理初始化结束");
        return mav;
    }


    /**
     * 功能描述: <br>
     * 〈店铺初始化时，展示店铺列表数据〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
	private void batchQueryStoreInfo(HttpServletRequest request, ModelAndView mav, StoreManageDto storeManageDto) {
		//批量查询店铺信息
		LOGGER.info("触发rsf调用进行查询,requestDto:{}", storeManageDto);
		ApiResDto<List<StoreResDto>> apiResponse = storeManageService.getStoreManages(storeManageDto);
		LOGGER.info("rsf查询结果,apiResDto:{}", apiResponse);
		if (apiResponse.isSuccess()) {
		    mav.addObject(EPPSCBPConstants.RESULT_LIST, apiResponse.getResult());
		    LOGGER.info("获取到店铺管理查询结果,resultList:{}", apiResponse.getResult());
		    mav.addObject(EPPSCBPConstants.PAGE, apiResponse.getPage());
		    LOGGER.info("获取到店铺管理分页信息,page:{}", apiResponse.getPage().toString());
		} else {
		    // 未查询到数据或查询出错
		    mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);
		}
		LOGGER.info("查询详情{}", apiResponse.getResult());
	}


    /**
     * 功能描述: <br>
     * 〈批量店铺信息查询〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    public ModelAndView batchStoresQuery(@ModelAttribute("requestDto") StoreManageDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到店铺管理查询请求,requestDto:{}", requestDto);

        ModelAndView mav = new ModelAndView(STORE_HANDLE);
        String platformCode = requestDto.geteCommercePlatform();

        //获取币种金额
        StoreManageDto storeManageDto = new StoreManageDto();
        //获取币种金额和站点
        getCurAndSites(request, mav, platformCode, storeManageDto);

        //获取店铺列表
        LOGGER.info("批量查询店铺列表开始。");
        LOGGER.info("查询店铺列表开始.获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());
        requestDto.setSite(StoreSiteEnum.getCodeFromDescription(requestDto.getSite()));
        batchQueryStoreInfo(request, mav, requestDto);
        LOGGER.info("批量查询店铺列表开始。");
        
        requestDto.setSite(StoreSiteEnum.getDescriptionFromCode(requestDto.getSite()));
        requestDto.seteCommercePlatform(platformCode);
        mav.addObject("requestDto",requestDto);
        return mav;
    }

    /**
     * 获取币种金额和站点
     * @param request
     * @param mav
     * @param platformCode
     * @param storeManageDto
     */
    private void getCurAndSites(HttpServletRequest request, ModelAndView mav, String platformCode, StoreManageDto storeManageDto) {
        storeManageDto.seteCommercePlatform(platformCode);
        storeManageDto.setPayerAccount(request.getRemoteUser());
        ApiResDto<StoreWithDrawAndPaymentDto> shopResDto = storeManageService.getStoreInfos(storeManageDto);
        LOGGER.info("查询币种金额返回结果，{}", shopResDto);
        if (shopResDto.isSuccess()) {
            mav.addObject(EPPSCBPConstants.CRITERIA, shopResDto.getResult());
        }
        ApiResDto<StoreSiteResDto> resDto = storeManageService.getStoreSites(storeManageDto);
        if (resDto.isSuccess()) {
            List<String> siteResDtos = new ArrayList<String>();
            StoreSiteResDto siteResDto = resDto.getResult();
            if (!StringUtil.isEmpty(siteResDto.getUSD())) {
                siteResDtos.add(StoreSiteEnum.USA_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getEUR())) {
                siteResDtos.add(StoreSiteEnum.EUR_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getGBP())) {
                siteResDtos.add(StoreSiteEnum.GBP_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getJPY())) {
                siteResDtos.add(StoreSiteEnum.JPY_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getCNH())) {
                siteResDtos.add(StoreSiteEnum.CNH_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getAUD())) {
                siteResDtos.add(StoreSiteEnum.AUD_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getHKD())) {
                siteResDtos.add(StoreSiteEnum.HKD_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getCAD())) {
                siteResDtos.add(StoreSiteEnum.CAD_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getTWD())) {
                siteResDtos.add(StoreSiteEnum.TWD_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getSGD())) {
                siteResDtos.add(StoreSiteEnum.SGD_SITE.getDescription());
            }
            if (!StringUtil.isEmpty(siteResDto.getNZD())) {
                siteResDtos.add(StoreSiteEnum.NZD_SITE.getDescription());
            }
            mav.addObject("site", siteResDtos);
        }
        mav.addObject("requestDto", storeManageDto);
    }

    /**
     * 新增店铺
     *
     * @param request
     * @return
     */
    @RequestMapping("addOrUpdateStore")
    @ResponseBody
    public String addStoreManage(@ModelAttribute("requestDto") AddStoreDto requestDto, HttpServletRequest request) {
        LOGGER.info("新增店铺信息开始");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(EPPSCBPConstants.STORE_SITE, StoreSiteEnum.getAllDescription());
        // 获取户头号
        String userNo = request.getRemoteUser();
        if (StringUtil.isNotNull(userNo)) {
            requestDto.setPayerAccount(userNo);
        }
        requestDto.setSite(StoreSiteEnum.getCodeFromDescription(requestDto.getSite()));
        LOGGER.info("收到店铺管理新增店铺请求,requestDto:{}", requestDto);
        LOGGER.info("触发rsf调用进行新增店铺,requestDto:{}", requestDto);
        ApiResDto<String> apiResDto = storeManageService.addStore(requestDto);
        LOGGER.info("触发rsf调用进行新增店铺返回信息,response:{}", apiResDto);

        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        requestDto.setSite(StoreSiteEnum.getDescriptionFromCode(requestDto.getSite()));
        result.put("requestDto", requestDto);
        return JSON.toJSONString(result);
    }


    /**
     * 删除店铺信息
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("deleteStore")
    @ResponseBody
    public CommonResult deleteStore(@ModelAttribute("id") String id, HttpServletRequest request) {
        LOGGER.info("删除店铺请求的店铺编号:{}", id);
        //获取户头号
        String payerAccount = request.getRemoteUser();
        StoreManageDto storeManageDto = new StoreManageDto();
        storeManageDto.setPayerAccount(payerAccount);
        storeManageDto.setId(id);
        LOGGER.info("触发rsf查询店铺审核进度,店铺id:{},户头号{}", id, payerAccount);
        CommonResult apiResDto = storeManageService.deleteStore(storeManageDto);
        LOGGER.info("触发rsf查询店铺审核进度接口返回信息,response:{}", apiResDto);
        return apiResDto;
    }


    /**
     * 店铺管理提现
     *
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("batchAdd")
    public String cashWithdraw(@ModelAttribute("id") String id, @ModelAttribute("fileAddress") String fileAddress,@ModelAttribute("detailAmount") String detailAmount, HttpServletRequest request) {
        LOGGER.info("店铺提现请求的店铺编号:{}", id);
        Map<String, Object> result = new HashMap<String, Object>();
        //获取户头号
        String payerAccount = request.getRemoteUser();
        StoreManageDto storeManageDto = new StoreManageDto();
        storeManageDto.setPayerAccount(payerAccount);
        storeManageDto.setId(id);
        storeManageDto.setFileAddress(fileAddress);
        storeManageDto.setDetailAmount(detailAmount);
        LOGGER.info("触发rsf进行店铺管理提现,店铺id:{},户头号{},文件路径{}", id, payerAccount, fileAddress);
        ApiResDto<String> apiResDto = storeManageService.cashWithdraw(storeManageDto);
        LOGGER.info("触发rsf店铺管理提现返回信息,response:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("店铺提现返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        return JSON.toJSONString(result);
    }


    /**
     * 功能描述: <br>
     * 〈上传明细文件校验〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileSubmit", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, @ModelAttribute("cur") String cur, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("店铺管理提现明细文件上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            String curCode = StoreCurEnum.getCodeFromDescription(cur);
            res = applyFileUploadService.uploadStoreFile(targetFile, curCode, request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("店铺管理提现明细文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }

}
