package cn.udream.mybatis.handler.result;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.exception.MybatisException;
import cn.udream.mybatis.handler.type.handler.TypeHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultHandler {

    private final Class<?> resultType;

    private final Config config;

    public ResultHandler(Class<?> resultType, Config config) {
        this.resultType = resultType;
        this.config = config;
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
        try {
            // 驼峰转下划线，用以匹配表的字段名，如(createTime -> create_time)
            String columnName = StrUtil.toUnderlineCase(field.getName());
            TypeHandler<?> typeHandler = config.getTypeHandler(field.getType());
            return typeHandler.getResult(resultSet, columnName);
        } catch (SQLException e) {
            throw new MybatisException(e);
        }
    }
}
