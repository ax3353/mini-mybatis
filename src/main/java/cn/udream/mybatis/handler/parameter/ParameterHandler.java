package cn.udream.mybatis.handler.parameter;

import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.exception.MybatisException;
import cn.udream.mybatis.handler.type.handler.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterHandler {

    private final PreparedStatement preparedStatement;

    private final Config config;

    public ParameterHandler(PreparedStatement preparedStatement, Config config) {
        this.preparedStatement = preparedStatement;
        this.config = config;
    }

    public void setParameters(Object[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                Object value = args[i];
                // 根据参数类型获取对应的类型处理器
                TypeHandler typeHandler = config.getTypeHandler(value.getClass());
                typeHandler.setParameter(preparedStatement, i + 1, value);
            }
        } catch (SQLException e) {
            throw new MybatisException(e);
        }
    }
}
