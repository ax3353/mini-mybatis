package cn.udream.mybatis.session.impl;

import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.executor.Executor;
import cn.udream.mybatis.mapper.MapperMethod;
import cn.udream.mybatis.mapper.MapperProxy;
import cn.udream.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

public class DefaultSqlSession implements SqlSession {

    private final Config config;

    private final Executor executor;

    public DefaultSqlSession(Config config, Executor executor) {
        this.config = config;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(MapperMethod method, Object[] args) {
        return executor.query(config, method, args);
    }

    @Override
    public <T> T getMapper(Class<T> clz) {
        MapperProxy proxy = new MapperProxy(clz, this);
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clz}, proxy);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }
}
