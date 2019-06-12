package org.neep.proxy.api;

import java.io.Serializable;

public interface ObjectProvider<T> extends Serializable {

    T getObject();
}
