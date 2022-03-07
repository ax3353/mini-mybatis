package cn.udream.mybatis.handler;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.udream.mybatis.exception.MybatisException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultHandler {

    private final Class<?> resultType;

    public ResultHandler(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Object handlerResult(ResultSet resultSet) {
        try {
            Object o = resultType.newInstance();
            if (resultSet.next()) {
                Field[] declaredFields = ClassUtil.getDeclaredFields(resultType);
                for (Field field : declaredFields) {
                    Object value = this.getResult(field, resultSet);
                    ReflectUtil.setFieldValue(o, field, value);
                }
                return o;
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            throw new MybatisException(e);
       }
        return null;
    }

    private Object getResult(Field field, ResultSet resultSet) {
        Class<?> type = field.getType();
        try {
            if (type == Integer.class) {
                return resultSet.getInt(field.getName());
            }
            if (type == String.class) {
                return resultSet.getString(field.getName());
            }
            if (type == Long.class) {
                return resultSet.getLong(field.getName());
            }
            return resultSet.getString(field.getName());
        } catch (SQLException e) {
            throw new MybatisException(e);
        }
    }
}
