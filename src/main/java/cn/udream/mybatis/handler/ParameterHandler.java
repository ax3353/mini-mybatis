package cn.udream.mybatis.handler;

import cn.udream.mybatis.exception.MybatisException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterHandler {

    private final PreparedStatement preparedStatement;

    public ParameterHandler(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public void setParameters(Object[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                // jdbc参数下标从1开始
                this.preparedStatement.setObject(i + 1, args[i]);
            }
        } catch (SQLException e) {
            throw new MybatisException(e);
        }
    }
}
