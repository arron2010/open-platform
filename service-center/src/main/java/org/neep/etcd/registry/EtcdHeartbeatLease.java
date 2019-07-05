package org.neep.etcd.registry;



import io.etcd.jetcd.Client;
import io.etcd.jetcd.Observers;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.shaded.io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.neep.etcd.bean.EtcdProperties;
import org.neep.utils.exceptions.EtcdOperationException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class EtcdHeartbeatLease  {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final Client etcdClient;

    private final EtcdProperties properties;

    private Long leaseId;

    public EtcdHeartbeatLease(Client etcdClient, EtcdProperties properties) {
        this.etcdClient = etcdClient;
        this.properties = properties;
    }

    public void initLease() {
        if (leaseId == null) {
            synchronized (this) {
                if (leaseId == null) {
                    try {
                        // init lease
                        leaseId = etcdClient.getLeaseClient().grant(properties.getInterval()).get().getID();
                        log.info("Etcd init lease success. lease id: {}, hex: {}", leaseId, Long.toHexString(leaseId));
                        executor.execute(() -> {
                            try {
                                final StreamObserver<LeaseKeepAliveResponse> observer = Observers.observer(response -> {});
                                etcdClient.getLeaseClient().keepAlive(leaseId,observer);
                            } catch (Exception e) {
                                throw new EtcdOperationException(e);
                            }
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        throw new EtcdOperationException(e);
                    }
                }
            }
        }
    }

    public Long getLeaseId() {
        initLease();
        return leaseId;
    }

    public void revoke() {
        try {
            if (leaseId == null) return;
            etcdClient.getLeaseClient().revoke(leaseId).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new EtcdOperationException(e);
        }
    }



    public void close() throws Exception {
        revoke();
        executor.shutdown();
    }
}
