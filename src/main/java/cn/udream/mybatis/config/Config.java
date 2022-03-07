package cn.udream.mybatis.config;

import cn.udream.mybatis.mapper.MapperMethod;
import cn.udream.mybatis.plugin.Interceptor;
import cn.udream.mybatis.session.DataSource;

import java.sql.Connection;
import java.util.List;

/**
 * 描述: 配置信息
 * @author kun.zhu
 * @date 2022/3/2 17:02
 */
public interface Config {

    /**
     * 描述: 获取数据源
     */
    DataSource getDataSource();

    Connection getConnection();

    MapperMethod getMapperMethod(Class<?> clz, String methodName);

    /**
     * 获取拦截器列表
     */
    List<Interceptor> getInterceptorList();
}
