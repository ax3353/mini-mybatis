package cn.udream.mybatis.handler.type.register.impl;

import cn.udream.mybatis.handler.type.handler.TypeHandler;
import cn.udream.mybatis.handler.type.handler.impl.LongTypeHandler;
import cn.udream.mybatis.handler.type.handler.impl.StringTypeHandler;
import cn.udream.mybatis.handler.type.register.TypeHandlerRegister;

import java.util.HashMap;
import java.util.Map;

public class DefaultTypeHandlerRegister implements TypeHandlerRegister {

    private final static Map<Class<?>, TypeHandler<?>> TYPE_HANDLER_MAP = new HashMap<>();

    static {
        TYPE_HANDLER_MAP.put(Long.class, new LongTypeHandler());
        TYPE_HANDLER_MAP.put(String.class, new StringTypeHandler());
    }

    @Override
    public <T> TypeHandlerRegister register(Class<T> javaType, TypeHandler<? extends T> typeHandler) {
        TYPE_HANDLER_MAP.put(javaType, typeHandler);
        return this;
    }

    @Override
    public <T> TypeHandler<? extends T> getTypeHandler(Class<T> javaType) {
        return (TypeHandler<? extends T>) TYPE_HANDLER_MAP.get(javaType);
    }
}
