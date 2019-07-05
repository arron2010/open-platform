package org.neep.test.rpc;

import org.neep.rpc.common.AnnotationBeanNameGeneratorEx;
import org.neep.spring.ext.boot.SpringApplicationEx;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @Title RpcStartApp
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-12 下午8:08
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@ComponentScan({"org.neep.rpc.bean","org.neep.test.rpc.pojo","org.neep.etcd.bean"})
public class RpcStartApp {
    public static void main(String[] args) {
        SpringApplicationEx.run(RpcStartApp.class, args);
    }
}
