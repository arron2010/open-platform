package org.neep.etcd.registry;


import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.shaded.com.google.common.base.Charsets;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.neep.etcd.bean.EtcdProperties;
import org.neep.utils.exceptions.EtcdOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

import static org.neep.etcd.utils.KVHelper.fromString;




@AllArgsConstructor
@Data
public class EtcdServiceRegistry  {


    private final Client etcdClient;

    private final EtcdProperties properties;

    private final EtcdHeartbeatLease lease;

   /// private final ObjectMapper objectMapper = new ObjectMapper();
//Long.toString(leaseId)
   private static final Logger log = LoggerFactory.getLogger(EtcdServiceRegistry.class);
    public void register(EtcdRegistration registration) {
        String etcdKey = registration.etcdKey(properties.getPrefix());
        try {

            PutOption putOption;
            String etcdValue;
            //如果租约间隔时间为-1,KEY就永不过期
            if (properties.getInterval() == -1){
                putOption = PutOption.newBuilder()
                        .build();
                etcdValue = registration.valueString(-1L);
            }else{
                long leaseId = lease.getLeaseId();
                putOption = PutOption.newBuilder()
                        .withLeaseId(leaseId)
                        .build();
                etcdValue = registration.valueString(leaseId);
            }

            etcdClient.getKVClient()
                    .put(fromString(etcdKey),fromString(etcdValue),  putOption)
                    .get();
            log.info("服务注册已完成："+etcdKey + "->" +etcdValue);
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdOperationException(e);
        }
    }

//    private int getWeight(){
//        return 1;
//    }

    private String valueString(long leaseId,int weight ){
        final String health = "1";
        //值的信息包含会话ID,权重值,健康状态（１表示正在服务，０表示服务失效）
        return Long.toString(leaseId)+"::"+weight+"::"+health;
    }


    public void deregister(EtcdRegistration registration) {
        String etcdKey = registration.etcdKey(properties.getPrefix());
        try {
            etcdClient.getKVClient()
                    .delete(ByteSequence.from(etcdKey, Charsets.UTF_8))
                    .get();
            //lease.revoke();
        } catch (InterruptedException | ExecutionException e) {
            throw new EtcdOperationException(e);
        }
    }


    public void close() {

    }


//    public void setStatus(EtcdRegistration registration, String status) {
//        if (status.equalsIgnoreCase(OUT_OF_SERVICE.getCode())) {
//            deregister(registration);
//        } else if (status.equalsIgnoreCase(UP.getCode())) {
//            register(registration);
//        } else {
//            throw new IllegalArgumentException("Unknown status: " + status);
//        }
//    }


//    public Object getStatus(EtcdRegistration registration) {
//        String etcdKey = registration.etcdKey(properties.getPrefix());
//        try {
//            GetResponse response = etcdClient.getKVClient()
//                    .get(ByteSequence.from(etcdKey, Charsets.UTF_8))
//                    .get();
//            if (response.getKvs().isEmpty()) {
//                return OUT_OF_SERVICE.getCode();
//            }
//            return UP.getCode();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new EtcdOperationException(e);
//        }
//    }
}
