package org.neep.proxy.impl;

public class PlatformProxyException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public PlatformProxyException() {
    }

    public PlatformProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformProxyException(String message) {
        super(message);
    }

    public PlatformProxyException(Throwable cause) {
        super(cause);
    }

}
