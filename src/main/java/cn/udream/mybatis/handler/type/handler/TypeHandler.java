package cn.udream.mybatis.handler.type.handler;

import java.sql.*;

public interface TypeHandler<T> {

    /**
     * 描述: 设置参数
     * @param ps 准备信息
     * @param i  参数下标
     * @param parameter 参数
     * @param jdbcType jdbc类型
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JDBCType jdbcType) throws SQLException;

    /**
     * 描述: 设置参数
     * @param ps 准备信息
     * @param i  参数下标
     * @param parameter 参数
     */
    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;

    T getResult(ResultSet rs, String columnName) throws SQLException;

    T getResult(ResultSet rs, int columnIndex) throws SQLException;

    T getResult(CallableStatement cs, int columnIndex) throws SQLException;
}
