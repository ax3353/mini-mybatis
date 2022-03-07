package cn.udream.mybatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DefaultInvocationHandler implements InvocationHandler {

    /**
     * 被代理的目标对象
     */
    private final Object target;

    private final Interceptor interceptor;

    public DefaultInvocationHandler(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation(target, method, args);
        return this.interceptor.interceptor(invocation);
    }

    public static Object proxy(Object target, Interceptor interceptor) {
        DefaultInvocationHandler invocationHandler = new DefaultInvocationHandler(target, interceptor);
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                invocationHandler);
    }
}
