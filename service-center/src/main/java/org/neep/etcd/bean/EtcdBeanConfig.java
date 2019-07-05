package org.neep.etcd.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.etcd.jetcd.Client;
import org.springframework.context.annotation.PropertySource;

/**
 * @Title EtcdBeanConfig
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-30 下午10:31
 */
//@Configuration
//@PropertySource(value = { "classpath:etcd.properties" })
//@EnableConfigurationProperties(EtcdProperties.class)
public class EtcdBeanConfig {

//    @Bean
//    @ConditionalOnExpression("${NEED_ETCD:true}")
//    public Client etcdClient(EtcdProperties properties) {
//        String endpoints = properties.getEndpoints();
//        return Client.builder()
//                .endpoints(endpoints)
//                .build();
//    }
}
