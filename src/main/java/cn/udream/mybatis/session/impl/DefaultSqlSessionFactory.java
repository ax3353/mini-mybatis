package cn.udream.mybatis.session.impl;

import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.executor.Executor;
import cn.udream.mybatis.executor.impl.SimpleExecutor;
import cn.udream.mybatis.plugin.InterceptorChain;
import cn.udream.mybatis.session.SqlSession;
import cn.udream.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Config config;

    public DefaultSqlSessionFactory(Config config) {
        this.config = config;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = new SimpleExecutor();

        // 获取所有的插件
        InterceptorChain chain = new InterceptorChain();
        chain.add(this.config.getInterceptorList());

        // 执行插件
        executor = (Executor) chain.proxyAll(executor);

        // 创建session
        return new DefaultSqlSession(this.config, executor);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }
}
