package org.neep.etcd.utils;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.shaded.com.google.common.base.Charsets;

/**
 * @Title KVHelper
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-1-28 下午1:18
 */
public class KVHelper {

    public static ByteSequence fromString(String source){
        return ByteSequence.from(source, Charsets.UTF_8);
    }
}
