package org.neep.etcd.registry;

import lombok.Data;
import org.neep.utils.tools.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data

public class EtcdRegistration  {
    private static final Logger log = LoggerFactory.getLogger(EtcdRegistration.class);

    private String serviceName;

    private String address;

    private int port;

    private int weight;

    private String centerName;

    private String fullServiceName;

    private boolean health;

    public EtcdRegistration(String fullServiceName, String address, int port, int weight, String centerName) {

        this.serviceName =  fullServiceName;
        this.address = address;
        this.port = port;
        this.weight = weight;
        this.centerName = centerName;
        this.fullServiceName=fullServiceName;
        this.health= true;
    }

    private String parseServiceName(String fullServiceName){
        try{
            return StringHelper.parseServiceName(fullServiceName);
        }catch (Exception ex){
            log.error("服务名称解析异常",ex);
            return "";
        }
    }


    public String serviceUrl(){
        return String.format("%s:%d",
                this.address,
                this.port);
    }

    public String valueString(long leaseId){
        final String health =this.health ? "1":"0";
        //值的信息包含会话ID,权重值,健康状态（１表示正在服务，０表示服务失效）
        return Long.toString(leaseId)+"::"+weight+"::"+health+"::"+this.fullServiceName;
    }
    public String etcdKey(String prefix) {
        return String.format("/%s/%s/%s::%s:%d",
                prefix,
                this.centerName,
                this.serviceName,
                this.address,
                this.port);
    }

}
