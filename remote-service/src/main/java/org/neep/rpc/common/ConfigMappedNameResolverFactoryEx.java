package org.neep.rpc.common;

import io.grpc.Attributes;
import io.grpc.NameResolver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.config.GrpcChannelProperties;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.nameresolver.ConfigMappedNameResolverFactory;
import net.devh.boot.grpc.client.nameresolver.NameResolverConstants;

import java.net.URI;
import java.util.function.Function;

/**
 * @Title ConfigMappedNameResolverFactoryEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-28 下午1:50
 */
@Slf4j
public class ConfigMappedNameResolverFactoryEx extends ConfigMappedNameResolverFactory {
    private NameResolver.Factory childDelegate;
    private  Function<String, URI> childDefaultUriMapper;
    private GrpcChannelsProperties childConfig;
    public ConfigMappedNameResolverFactoryEx(GrpcChannelsProperties config, NameResolver.Factory delegate, Function<String, URI> defaultUriMapper) {
        super(config, delegate, defaultUriMapper);
        this.childDelegate=delegate;
        this.childDefaultUriMapper=defaultUriMapper;
        this.childConfig=config;
    }

    public NameResolver newNameResolver(URI targetUri, Attributes params) {
        String clientName = GrpcHelper.getCenterName(targetUri.toString());
        GrpcChannelProperties clientConfig = this.childConfig.getChannel(clientName);
        URI remappedUri = clientConfig.getAddress();
        if (remappedUri == null) {
            remappedUri = (URI)this.childDefaultUriMapper.apply(clientName);
            if (remappedUri == null) {
                throw new IllegalStateException("'" + clientName + "'没有提供目标的访问地址，也没有缺省的映射地址");
            }
        }

        log.debug("URI从 {} 映射到 {} ", new Object[]{clientName, remappedUri});
        Attributes extendedParas = params.toBuilder().set(NameResolverConstants.PARAMS_CLIENT_NAME, clientName)
                .set(NameResolverConstants.PARAMS_CLIENT_CONFIG, clientConfig)
                .set(RemoteConstants.PARAMS_SERVER_PATH,targetUri.toString())
                .build();

        return this.childDelegate.newNameResolver(remappedUri, extendedParas);
    }
}
