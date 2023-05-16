package com.suning.epp.eppscbp.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 〈流关闭工具〉<br>
 * 〈功能详细描述〉
 *
 * @author 14072597
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class IOUtils {
    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

    /**
     * 
     * 功能描述: <br>
     * 〈关闭流〉
     *
     * @param x
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void close(Closeable x) {
        if (x != null) {
            try {
                x.close();
            } catch (IOException e) {
                // skip
                LOGGER.error("关闭流异常", e);
            }
        }
    }
}
