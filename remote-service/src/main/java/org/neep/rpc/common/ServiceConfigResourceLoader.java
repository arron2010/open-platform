package org.neep.rpc.common;

import com.google.common.base.Strings;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ResourceLoader;

/**
 * @Title ServiceConfigResourceLoader
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-7-4 下午6:32
 */
public class ServiceConfigResourceLoader implements ResourceLoaderAware {

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        if (resourceLoader instanceof AbstractApplicationContext){
            AbstractApplicationContext context = (AbstractApplicationContext)resourceLoader;
            boolean useGateway = this.getBooleanValue(context.getEnvironment().getProperty("USE_GATEWAY"),false);
            InitializationContext.getInstance().setUseGateway(useGateway);

            boolean register =  this.getBooleanValue(context.getEnvironment().getProperty("NEED_ETCD"),false);
            InitializationContext.getInstance().setRegister(register);
        }
    }

    private boolean getBooleanValue(String value,boolean defaultValue){
        if (Strings.isNullOrEmpty(value)){
            return defaultValue;
        }
        boolean b = Boolean.parseBoolean(value);
        return b;
    }
}
