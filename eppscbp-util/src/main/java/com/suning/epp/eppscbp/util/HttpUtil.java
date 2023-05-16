/**
 * 
 */
package com.suning.epp.eppscbp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈HTTP工具类〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private HttpUtil() {

    }

    /**
     * 
     * 功能描述: 发送HTTP GET请求<br>
     * 〈功能详细描述〉
     *
     * @param requestUrl
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static InputStream httpGetRequestIO(String requestUrl) {
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setRequestProperty("contentType", "UTF-8");
            httpUrlConn.connect();
            int responseCode = httpUrlConn.getResponseCode();
            // 获得返回的输入流
            inputStream = httpUrlConn.getInputStream();
        } catch (Exception ex) {
            LOGGER.error("HTTP GET请求异常:{}", ExceptionUtils.getFullStackTrace(ex));
        }
        return inputStream;
    }

    /**
     * 
     * 功能描述:InputStream转byte[] <br>
     * 〈功能详细描述〉
     *
     * @param inputStream
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();

        return bos.toByteArray();

    }

    /**
     * 
     * 功能描述:map转换成url参数 <br>
     * 〈功能详细描述〉
     *
     * @param param
     * @return
     * @throws UnsupportedEncodingException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String transferToUrlFromBean(Map<String, Object> param) throws UnsupportedEncodingException {

        StringBuffer sb = new StringBuffer();
        for (Entry<String, Object> entry : param.entrySet()) {
            sb.append(entry.getKey() + "=" + (entry.getValue() == null ? "" : URLEncoder.encode((String) entry.getValue(), "UTF-8")) + "&");
        }
        return sb.toString();

    }

}
