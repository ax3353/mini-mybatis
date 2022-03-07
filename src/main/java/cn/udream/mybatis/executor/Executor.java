package cn.udream.mybatis.executor;

import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.mapper.MapperMethod;

public interface Executor {

    <T> T query(Config config, MapperMethod method, Object[] args);
}
