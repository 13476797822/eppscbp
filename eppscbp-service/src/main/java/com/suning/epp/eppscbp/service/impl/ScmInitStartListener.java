package com.suning.epp.eppscbp.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.suning.csrdc.scm.base.listener.DefaultScmListener;
import com.suning.csrdc.scm.base.resolver.PropertyLineConfigResolver;
import com.suning.csrdc.scm.cache.pool.LocalCachePool;
import com.suning.csrdc.scm.support.spring.observer.ScmValueSupportObserver;
import com.suning.epp.eppscbp.common.constant.ScmOnlineConfig;
import com.suning.epp.eppscbp.util.ExcelUtil;
import com.suning.framework.scm.client.SCMClientFactory;
import com.suning.framework.scm.client.SCMListener;
import com.suning.framework.scm.client.SCMNode;

/**
 * SCM配置文件监听Service 目前先固定一个配置文件eppcbs.config，既支持本地Local获取，同时支持Spring@ScmValue注解,未开启BeanValue注入值
 * <p>
 * Created by 12040356 on 2018/10/16
 */
@Service("scmInitStartListener")
public class ScmInitStartListener {
    private static final Logger logger = LoggerFactory.getLogger(ScmInitStartListener.class);

    // SCM上文件名
    private static final String FILE_NAME = "eppscbp.config";

    @PostConstruct
    public void init() {
        logger.info("SCM监听初始化开始...");
        DefaultScmListener listener = new DefaultScmListener(FILE_NAME);
        listener.registerConfigResolver(new PropertyLineConfigResolver());
        // 启用Spring注解监听
        listener.addObserver(new ScmValueSupportObserver());
        listener.init();

        SCMNode scmNode = SCMClientFactory.getSCMClient().getConfig(FILE_NAME);
        scmNode.monitor(ScmOnlineConfig.REGEX_MATCH_STRING_DATE, new SCMListener() {
            @Override
            public void execute(String oldValue, String newValue) {
                ExcelUtil.setDateFormat(getValue(ScmOnlineConfig.REGEX_MATCH_STRING_DATE));
            }
        });
        logger.info("SCM监听初始化结束...");
    }

    /**
     * 从默认配置文件中根据Key获取对应的值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return getValue(FILE_NAME, key);
    }

    /**
     * 从某个配置文件中根据Key获取对应的值
     *
     * @param conf
     * @param key
     * @return
     */
    public String getValue(String conf, String key) {
        return LocalCachePool.getInstance().getCache(conf).getString(key);
    }

    @PreDestroy
    public void destory() {
        LocalCachePool.getInstance().getCache(FILE_NAME).clear();
    }
}
