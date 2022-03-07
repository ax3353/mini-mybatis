package cn.udream.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class Invocation {

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 执行的方法
     */
    private Method method;
    
    /**
     * 方法参数
     */
    private Object[] args;

    /**
     * 用户自定义, 额外的属性
     */
    private Map<String, Object> extraMap;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object process() throws InvocationTargetException, IllegalAccessException {
        return this.method.invoke(target, args);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Map<String, Object> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, Object> extraMap) {
        this.extraMap = extraMap;
    }
}
