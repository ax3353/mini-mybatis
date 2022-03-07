package cn.udream.mybatis.executor.impl;

import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.exception.MybatisException;
import cn.udream.mybatis.executor.Executor;
import cn.udream.mybatis.handler.ParameterHandler;
import cn.udream.mybatis.handler.ResultHandler;
import cn.udream.mybatis.mapper.MapperMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleExecutor implements Executor {

    @Override
    public <T> T query(Config config, MapperMethod method, Object[] args) {
        Connection connection = config.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(method.getSql())) {
            // 参数处理
            this.handlerParameter(preparedStatement, args);

            // 执行方法
            preparedStatement.execute();

            // 处理结果
            Class<?> resultType = method.getResultType();
            ResultSet resultSet = preparedStatement.getResultSet();
            ResultHandler resultHandler = new ResultHandler(resultType);
            Object o = resultHandler.handlerResult(resultSet);
            return (T) o;
        } catch (SQLException e) {
            throw new MybatisException(e);
        }
    }

    private void handlerParameter(PreparedStatement preparedStatement, Object[] args) {
        ParameterHandler handler = new ParameterHandler(preparedStatement);
        handler.setParameters(args);
    }
}
