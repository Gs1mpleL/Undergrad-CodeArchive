package com.wanfeng.javalearn.RPC.serializer.impl;

import com.wanfeng.javalearn.RPC.serializer.Serializer;


public class kyroSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }
}
