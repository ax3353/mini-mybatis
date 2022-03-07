package cn.udream.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;

public class InterceptorChain {
    private final List<Interceptor> interceptorList = new ArrayList<>();

    public synchronized InterceptorChain add(Interceptor interceptor) {
        interceptorList.add(interceptor);
        return this;
    }

    public synchronized InterceptorChain add(List<Interceptor> interceptors) {
        interceptorList.addAll(interceptors);
        return this;
    }

    public Object proxyAll(Object target) {
        for(Interceptor interceptor : interceptorList) {
            target = DefaultInvocationHandler.proxy(target, interceptor);
        }
        return target;
    }
}
