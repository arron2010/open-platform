package org.neep.etcd.resolver;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.resolver.URIResolver;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.SharedResourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.GuardedBy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @Title EtcdNameExResolver
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-3-15 下午10:35
 */
public class EtcdNameExResolver extends NameResolver {

    private final Client etcd;
    private final String serviceDir;
    private final Object lock;

    private volatile boolean shutdown;
    private volatile boolean resolving;
    private final List<URIResolver> resolvers;
    @GuardedBy("lock")
    private Listener listener;

    @GuardedBy("lock")
    private Executor executor;

    private static final Logger logger = LoggerFactory.getLogger(EtcdNameExResolver.class);
    public static final String DEFAULT_SCHEME = "tcp";

    public EtcdNameExResolver(Client etcd, String serviceDir) {
        super();
        this.etcd = etcd;
        this.serviceDir = serviceDir;
        this.lock = new Object();
        this.resolvers = new ArrayList<>();
        this.resolvers.add(new GrpcUriResolver());
    }

    @Override
    public void refresh() {
     this.resolve();
    }

    @Override
    public String getServiceAuthority() {
        return this.serviceDir;
    }

    @Override
    public void start(Listener listener) {
        synchronized (lock) {
            Preconditions.checkState(this.listener == null, "侦听器已经存在");
            this.executor = SharedResourceHolder.get(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
            this.listener = Preconditions.checkNotNull(listener, "侦听器不能为空");
            resolve();
        }
    }

    @Override
    public void shutdown() {
        if (shutdown) {
            return;
        }
        shutdown = true;

        synchronized (lock) {
            if (executor != null) {
                executor = SharedResourceHolder.release(GrpcUtil.SHARED_CHANNEL_EXECUTOR, executor);
            }
        }
    }

    private void resolve() {
        if (resolving || shutdown) {
            return;
        }
        synchronized (lock) {
            executor.execute(this::doResolve);
        }
    }
    private void doResolve() {
        Listener savedListener;
        synchronized (lock) {
            if (shutdown) {
                return;
            }
            resolving = true;
            savedListener = listener;
        }
        try {
            List<EquivalentAddressGroup> groups = new ArrayList<>();
            List<URI> uris = this.getURIFromEtcd();
            for (URI uri : uris) {
                resolvers.stream()
                        .filter(r -> r.supports(uri))
                        .flatMap(r -> r.resolve(uri).stream())
                        .map(EquivalentAddressGroup::new)
                        .forEach(groups::add);
            }
            if (groups.isEmpty()) {
                throw new RuntimeException("获取服务注册地址为空");
            }
            savedListener.onAddresses(groups, Attributes.EMPTY);
        } catch (Exception e) {
            logger.error("获取服务列表清单错误：", e);
            savedListener.onError(Status.NOT_FOUND);
        } finally {
            resolving = false;
        }
    }

    private List<URI> getURIFromEtcd(){
        ByteSequence prefix = ByteSequence.from(this.serviceDir, Charsets.UTF_8);
        GetOption option = GetOption.newBuilder()
                .withPrefix(prefix)
                .build();
        GetResponse query;
        List<URI> uriList = new ArrayList<URI>();
        try (KV kv = etcd.getKVClient()) {
            query = kv.get(prefix, option).get();
        } catch (Exception e) {
            logger.error("访问ETCD失败",e);
            throw new RuntimeException("获取服务注册信息失败", e);
        }
        for (KeyValue kv : query.getKvs()) {
            String addr = parseAddr(kv.getKey().toString(Charsets.UTF_8));
            try {
                URI uri = new URI(addr);
                uriList.add(uri);
            } catch (URISyntaxException e) {
                logger.error(String.format("不能解析服务地址 serviceDir='%s', addr='%s'", serviceDir, addr), e);
            }
        }
        return uriList;
    }
    private String parseAddr(String addr){
        String[] addrInfo = addr.split("::");
        String newAddr = String.format("%s://%s",DEFAULT_SCHEME,addrInfo[1]);
        return  newAddr;
    }

}
