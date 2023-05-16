/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: OSSClientService.java
 * Author:   17033387
 * Date:     2018年3月27日 下午2:52:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.util.oss;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.epp.eppscbp.util.StringUtil;

import net.oss.client.OSSClient;
import net.oss.client.OSSClientImpl;
import net.oss.result.SdossResult;
import net.oss.util.Sdoss_Head_Util;

/**
 * 〈OSS工具类〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OSSClientUtil {

    /**
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OSSClientUtil.class);
    
    private static final OSSClient CLIENT = new OSSClientImpl(false);
    
    private OSSClientUtil() {}

    /**
     * 
     * 功能描述: <br>
     * 〈上传〉
     *
     * @param OSSUploadParams 上传到的bucket名称
     * @return 上传文件的ID,用于下载
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String upload(OSSUploadParams params) {
        LOGGER.info("上传开始，参数:{}", params);
        File file = new File(params.getPathName());
        String objectName = file.getName();
        if (!StringUtil.isEmpty(params.getRemotePath())) {
            objectName = params.getRemotePath();
        }
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult authResult = CLIENT.putObject(params.getBucketName(), objectName, headerArgument);// 获取上传文件权限
        if (null != params.getValidDays() && (params.getValidDays() > 0 && params.getValidDays() < 365)) {
            headerArgument.put(Sdoss_Head_Util.Cycle, String.valueOf(params.getValidDays())); // 文件有效期天数（1-365，如果是永久保存的文件，这行可以不加）
        }
        if (null != params.getPos()) {
            headerArgument.put("pos", String.valueOf(params.getPos()));
        }
        String url = authResult.getFileraddr() + "/" + authResult.getAccount_id() + "/" + params.getBucketName() + "/" + objectName;// 拼接上传URL地址
        SdossResult opResult = CLIENT.putObject(file, headerArgument, authResult.getAuthorization(), authResult.getCurrent_time(), url);// 上传文件
        LOGGER.info("上传结果为:{}", opResult.isSuccess());
        return opResult.getObjectId();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈流上传〉
     *
     * @param OSSUploadParams 上传到的bucket名称
     * @return 上传文件的ID,用于下载
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String uploadStream(OSSUploadParams params) throws IOException {
        LOGGER.info("上传开始，参数:{}", params);
        String objectName = params.getMultipartFile().getName();
        if (!StringUtil.isEmpty(params.getRemotePath())) {
            objectName = params.getRemotePath();
        }
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult authResult = CLIENT.putObject(params.getBucketName(), objectName, headerArgument);// 获取上传文件权限
        if (null != params.getValidDays() && (params.getValidDays() > 0 && params.getValidDays() < 365)) {
            headerArgument.put(Sdoss_Head_Util.Cycle, String.valueOf(params.getValidDays())); // 文件有效期天数（1-365，如果是永久保存的文件，这行可以不加）
        }
        if (null != params.getPos()) {
            headerArgument.put("pos", String.valueOf(params.getPos()));
        }
        String url = authResult.getFileraddr() + "/" + authResult.getAccount_id() + "/" + params.getBucketName() + "/" + objectName;// 拼接上传URL地址
        SdossResult opResult = CLIENT.putObject(params.getMultipartFile().getInputStream(), objectName, params.getMimeType(), headerArgument, authResult.getAuthorization(), authResult.getCurrent_time(), url);// 上传文件
        LOGGER.info("上传结果为:{}", opResult.isSuccess());
        return opResult.getObjectId();
    }
    
    /**
     * 
     * 功能描述: <br>
     * 〈流上传〉
     *
     * @param OSSUploadParams 上传到的bucket名称
     * @return 上传文件的ID,用于下载
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String uploadStreamOriginalFilename(OSSUploadParams params) throws IOException {
        LOGGER.info("上传开始，参数:{}", params);
        String objectName = params.getMultipartFile().getOriginalFilename();
        if (!StringUtil.isEmpty(params.getRemotePath())) {
            objectName = params.getRemotePath();
        }
        
        //objectName = URLEncoder.encode(objectName);
        LOGGER.info("上传中，文件名称:{}", objectName);
        
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult authResult = CLIENT.putObject(params.getBucketName(), objectName, headerArgument);// 获取上传文件权限
        if (null != params.getValidDays() && (params.getValidDays() > 0 && params.getValidDays() < 365)) {
            headerArgument.put(Sdoss_Head_Util.Cycle, String.valueOf(params.getValidDays())); // 文件有效期天数（1-365，如果是永久保存的文件，这行可以不加）
        }
        if (null != params.getPos()) {
            headerArgument.put("pos", String.valueOf(params.getPos()));
        }
        String url = authResult.getFileraddr() + "/" + authResult.getAccount_id() + "/" + params.getBucketName() + "/" + objectName;// 拼接上传URL地址
        SdossResult opResult = CLIENT.putObject(params.getMultipartFile().getInputStream(), objectName, params.getMimeType(), headerArgument, authResult.getAuthorization(), authResult.getCurrent_time(), url);// 上传文件
        LOGGER.info("上传结果为:{}", opResult.isSuccess());
        return opResult.getObjectId();
    }
    
    /**
     * 功能描述: <br>
     * 〈流上传〉
     *
     * @param OSSUploadParams 上传到的bucket名称
     * @return 上传文件的ID, 用于下载
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String uploadInputStream(OSSUploadParams params) {
        LOGGER.info("上传开始，参数:{}", params);
        String objectName = params.getPathName();
        if (!StringUtil.isEmpty(params.getRemotePath())) {
            objectName = params.getRemotePath();
        }
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult authResult = CLIENT.putObject(params.getBucketName(), objectName, headerArgument);// 获取上传文件权限
        if (null != params.getValidDays() && (params.getValidDays() > 0 && params.getValidDays() < 365)) {
            headerArgument.put(Sdoss_Head_Util.Cycle, String.valueOf(params.getValidDays())); // 文件有效期天数（1-365，如果是永久保存的文件，这行可以不加）
        }
        if (null != params.getPos()) {
            headerArgument.put("pos", String.valueOf(params.getPos()));
        }
        String url = authResult.getFileraddr() + "/" + authResult.getAccount_id() + "/" + params.getBucketName() + "/" + objectName;// 拼接上传URL地址
        SdossResult opResult = CLIENT.putObject(params.getInputStream(), objectName, params.getMimeType(), headerArgument, authResult.getAuthorization(), authResult.getCurrent_time(), url);// 上传文件
        LOGGER.info("上传结果为:{}", opResult.isSuccess());
        return opResult.getObjectId();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈分片上传，适用于大文件〉
     *
     * @param params
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String multiPartUpload(OSSUploadParams params) {       
        Map<String, String> headerArgument = new HashMap<String, String>();
        File file = new File(params.getPathName());
        SdossResult opResult = CLIENT.MutiPartUpload(params.getBucketName(), file.getName(), file, headerArgument);
        return opResult.getObjectId();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈下载到本地〉
     *
     * @param bucketname 下载源的bucket名称
     * @param objectid 上传时返回的文件ID
     * @param localPathName 本地路径（含文件名称）
     * @return 是否下载成功
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Boolean download(OSSDownloadParams params) {
        LOGGER.info("下载开始，参数:{}", params);       
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult opResult = CLIENT.getObject(params.getBucketName(), params.getObjectId(), params.getPathName(), headerArgument);
        LOGGER.info("下载结果为:{}", opResult.isSuccess());
        return opResult.isSuccess();
    }
    

    /**
     * 
     * 功能描述: <br>
     * 〈获取文件流〉
     *
     * @param bucketname 下载源的bucket名称
     * @param objectid 上传时返回的文件ID
     * @param localPathName 本地路径（含文件名称）
     * @return 是否下载成功
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static InputStream getInputStream(OSSDownloadParams params) {
        LOGGER.info("下载文件流开始，参数:{}", params);       
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult authResult = CLIENT.getObject(params.getBucketName(), params.getObjectId(), headerArgument);// 获取下载文件权限
        String url = authResult.getFileraddr() + "/" + authResult.getAccount_id() + "/" + params.getBucketName() + "/" + params.getObjectId();
        SdossResult opResult = CLIENT.getObject(headerArgument, authResult.getAuthorization(), authResult.getCurrent_time(), url);
        LOGGER.info("下载文件流结果为:{}", opResult.isSuccess());
        return opResult.getDownloadStream();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈获取代签名下载URL〉
     *
     * @param bucketname 下载源的bucket名称
     * @param objectid 上传时返回的文件ID
     * @param secure 是否需要加密URL
     * @return URL
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDownloadURL(String bucketname, String objectid, Boolean secure) {        
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult opResult = CLIENT.getObject(bucketname, objectid, headerArgument);
        String targetURL = opResult.getFileraddr() + "/" + opResult.getAccount_id() + "/" + bucketname + "/" + objectid + "?SDOSSAccessKeyId=" + opResult.getAccess_id() + "&Expires=" + opResult.getExpiretime() + "&Signature=" + opResult.getSignature();
        if (secure) {
            targetURL = targetURL.replaceFirst("http", "https");
        }
        return targetURL;
    }
    
    /**
     * 功能描述: <br>
     * 〈删除本地文件〉
     *
     * @param bucketname 下载源的bucket名称
     * @param objectid 上传时返回的文件ID
     * @param localPathName 本地路径（含文件名称）
     * @return 是否下载成功
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Boolean remove(OSSDownloadParams params) {
        LOGGER.info("删除开始，参数:{}", params);
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult opResult = CLIENT.removeObject(params.getBucketName(), params.getObjectId(), headerArgument);
        LOGGER.info("删除结果为:{}", opResult.isSuccess());
        return opResult.isSuccess();
    }
    
    /**
     * 判断文件是否存在
     *
     * @param bucketname
     * @param objectid
     * @return
     */
    public static boolean exists(String bucketname, String objectid) {
        Map<String, String> headerArgument = new HashMap<String, String>();
        SdossResult authResult = CLIENT.getObject(bucketname, objectid, headerArgument);
        String url = authResult.getFileraddr() + "/" + authResult.getAccount_id() + "/" + bucketname + "/" + objectid;
        SdossResult opResult = CLIENT.getObject(headerArgument, authResult.getAuthorization(), authResult.getCurrent_time(), url);
        if (opResult.isSuccess() && opResult.isFileExist()) {
            return true;
        } else {
            LOGGER.error("OSS上文件不存在,bucketname:{},objectid:{}", bucketname, objectid);
            return false;
        }

    }

}
