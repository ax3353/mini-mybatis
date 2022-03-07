package cn.udream.mybatis.typeHandler;

import cn.udream.mybatis.handler.type.handler.impl.TypeHandlerAdaptor;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeHandler extends TypeHandlerAdaptor<LocalDateTime> {
    private final static DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Override
    public void setParameter(PreparedStatement ps, int i, LocalDateTime parameter, JDBCType jdbcType) throws SQLException {
        String dateTime = DF.format(parameter);
        ps.setString(i, dateTime);
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, String columnName) throws SQLException {
        return LocalDateTime.parse(rs.getString(columnName), DF);
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
        return LocalDateTime.parse(rs.getString(columnIndex), DF);
    }
}
