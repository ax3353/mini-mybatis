package cn.udream.mybatis.mapper;

public class MapperMethod {

    private String type;

    private String methodName;

    private String sql;

    private Class<?> resultType;

    private Class<?> parameterType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    @Override
    public String toString() {
        return "MapperMethod{" +
                "type='" + type + '\'' +
                ", methodName='" + methodName + '\'' +
                ", sql='" + sql + '\'' +
                ", resultType=" + resultType +
                ", parameterType=" + parameterType +
                '}';
    }
}
