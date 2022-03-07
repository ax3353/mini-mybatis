package cn.udream.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractInterceptor implements Interceptor{

    @Override
    public Object interceptor(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        this.before(invocation);
        Object result = invocation.process();
        this.after(invocation, result);
        return result;
    }
    /**
     * @param invocation 上下文
     */
    abstract void before(Invocation invocation);

    /**
     * @param invocation 上下文
     * @param result 执行结果
     */
    abstract void after(Invocation invocation, Object result);
}
