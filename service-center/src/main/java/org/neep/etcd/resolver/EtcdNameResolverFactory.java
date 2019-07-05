package org.neep.etcd.resolver;
import io.etcd.jetcd.Client;
import io.grpc.Attributes;
import io.grpc.NameResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.neep.etcd.bean.EtcdProperties;

import javax.annotation.Nullable;
import java.net.URI;

/**
 * @Title EtcdNameResolverFactory
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-2-18 上午10:48
 */
@Data
@AllArgsConstructor
public class EtcdNameResolverFactory extends NameResolver.Factory {

    private final EtcdProperties properties;
    private final Client etcdClient;

    @Nullable
    @Override
    public NameResolver newNameResolver(URI uri, Attributes attributes) {
        NameResolver nameResolver = new EtcdNameExResolver(this.etcdClient,uri.getPath());
        return nameResolver;
    }

    @Override
    public String getDefaultScheme() {
        return "";
    }
}
