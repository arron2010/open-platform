package org.neep.etcd.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix="platform.etcd")
public class EtcdProperties {

    /*
    被注册服务的前缀，主要有两个用途：
    １）将不同的服务注册到不同的服务器节点，避免在相同的服务器节点上注册相同服务
    ２）将同一个服务注册到同一个URL，再通过这个URL进行负载均衡。
     */
    private String prefix = "services";

    private String endpoints = "";

    private String serviceName="";

    private String center ="";

    private String clientCenter ="";

    private boolean autoIp = false;

    /*
    ETCD KEY的租约时间
     */
    private int interval = -1;

    public String[] getClusterEndpoints(){
        return this.endpoints.split(";");
    }
}
