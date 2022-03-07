package cn.udream.mybatis.session;

import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.mapper.MapperMethod;

public interface SqlSession {

    <T> T selectOne(MapperMethod method, Object[] args);

    <T> T getMapper(Class<T> clz);

    Config getConfig();
}
