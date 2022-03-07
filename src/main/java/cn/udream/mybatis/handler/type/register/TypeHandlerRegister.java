package cn.udream.mybatis.handler.type.register;

import cn.udream.mybatis.handler.type.handler.TypeHandler;

public interface TypeHandlerRegister {

    <T> TypeHandlerRegister register(Class<T> javaType, TypeHandler<? extends T> typeHandler);

    <T> TypeHandler<? extends T> getTypeHandler(Class<T> javaType);
}
