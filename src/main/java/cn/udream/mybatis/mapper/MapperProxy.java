package cn.udream.mybatis.mapper;

import cn.udream.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler {

    private final Class<?> target;

    private final SqlSession sqlSession;

    public MapperProxy(Class<?> target, SqlSession sqlSession) {
        this.target = target;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperMethod mapperMethod = sqlSession.getConfig().getMapperMethod(target, method.getName());
        if (mapperMethod != null) {
            return this.sqlSession.selectOne(mapperMethod, args);
        }
        return method.invoke(proxy, args);
    }
}
