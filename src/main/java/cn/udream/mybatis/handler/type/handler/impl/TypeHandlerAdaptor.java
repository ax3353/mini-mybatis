package cn.udream.mybatis.handler.type.handler.impl;

import cn.udream.mybatis.handler.type.handler.TypeHandler;

import java.sql.*;

public class TypeHandlerAdaptor<T> implements TypeHandler<T> {

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JDBCType jdbcType) throws SQLException {

    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException {
        this.setParameter(ps, i, parameter, null);
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
