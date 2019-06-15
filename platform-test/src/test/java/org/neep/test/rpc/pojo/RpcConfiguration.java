package org.neep.test.rpc.pojo;

import org.neep.rpc.anno.RemoteServiceScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Title RpcConfiguration
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-12 下午10:48
 */
@Configuration
@RemoteServiceScan(basePackages = {"org.neep.test.rpc.pojo.api"})
public class RpcConfiguration {
}
