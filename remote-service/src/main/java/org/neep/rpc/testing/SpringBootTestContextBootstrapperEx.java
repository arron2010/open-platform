package org.neep.rpc.testing;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.ContextLoader;

/**
 * @Title SpringBootTestContextBootstrapperEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-8 上午12:01
 */
public class SpringBootTestContextBootstrapperEx extends SpringBootTestContextBootstrapper {
    public SpringBootTestContextBootstrapperEx() {
        super();
    }
    @Override
    protected Class<? extends ContextLoader> getDefaultContextLoaderClass(Class<?> testClass) {
        return SpringBootContextLoaderEx.class;
    }
}
