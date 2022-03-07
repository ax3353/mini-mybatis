package cn.udream.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;

public interface Interceptor {

    Object interceptor(Invocation invocation) throws InvocationTargetException, IllegalAccessException;

}
