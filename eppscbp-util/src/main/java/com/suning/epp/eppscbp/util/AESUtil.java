package com.suning.epp.eppscbp.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.framework.security.digest.impl.Base64Coder;

/**
 * AES加解密服务工具类 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 14073698
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AESUtil {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    /**
     * AES加密算法
     */
    private static final String ALGORITHM_AES = "AES";

    /**
     * base64编码AES加密后的字符串 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param content 需要加密的字符串
     * @param strKey 秘钥key
     * @return 经过base64编码后的AES加密的字符串
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String encryptBase64DecorateAES(String content, String strKey) {

        byte[] encryptAESbytes = encrypt(content, strKey);

        String base64DecorateAESStr = Base64Coder.encryptBase64String(encryptAESbytes);

        return base64DecorateAESStr;
    }

    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param strKey 加密秘钥
     * @return 加密后的比特流
     */
    private static byte[] encrypt(String content, String strKey) {

        try {

            SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), ALGORITHM_AES);

            // 创建密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);

            byte[] byteContent = content.getBytes("utf-8");

            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // 加密
            byte[] result = cipher.doFinal(byteContent);

            return result;

        } catch (NoSuchAlgorithmException e) {

            LOGGER.error("use AES algorithm encrypt error", e);

        } catch (Exception e) {

            LOGGER.error("use AES algorithm encrypt error", e);
        }
        return null;
    }
}
