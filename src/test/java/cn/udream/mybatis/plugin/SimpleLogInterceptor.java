package cn.udream.mybatis.plugin;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class SimpleLogInterceptor extends AbstractInterceptor {

    @Override
    public void before(Invocation invocation) {
        Object[] args = invocation.getArgs();
        Object target = invocation.getTarget();
        Method method = invocation.getMethod();
        Map<String, Object> extraMap = invocation.getExtraMap();
        System.out.println("args: " + Arrays.toString(args));
        System.out.println("target: " + target);
        System.out.println("method: " + method );
        System.out.println("extraMap: " + extraMap);
    }

    @Override
    public void after(Invocation invocation, Object result) {
        System.out.println("result: " + result);
    }
}
