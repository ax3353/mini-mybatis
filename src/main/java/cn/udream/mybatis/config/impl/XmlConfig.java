package cn.udream.mybatis.config.impl;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.XmlUtil;
import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.constant.DataSourceConst;
import cn.udream.mybatis.exception.MybatisException;
import cn.udream.mybatis.mapper.MapperMethod;
import cn.udream.mybatis.mapper.MapperRegister;
import cn.udream.mybatis.plugin.Interceptor;
import cn.udream.mybatis.session.DataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlConfig implements Config {

    private final String configPath;

    private DataSource dataSource;

    private Element root;

    private final List<Interceptor> interceptorList = new ArrayList<>();

    private final MapperRegister mapperRegister = new MapperRegister();

    public XmlConfig(String configPath) {
        this.configPath = configPath;

        // 初始化配置
        initProperties();

        // 初始化数据源连接信息
        initDataSource();

        // 初始化mapper信息
        initMapper();

        // 初始化拦截器
        initInterceptors();
    }

    private void initProperties() {
        Document document = XmlUtil.readXML(this.configPath);
        this.root = XmlUtil.getRootElement(document);
    }

    private void initDataSource() {
        Element dataSource = XmlUtil.getElement(this.root, "dataSource");
        List<Element> properties = XmlUtil.getElements(dataSource, "property");

        Map<String, String> map = new HashMap<>(4);
        properties.forEach(e -> {
            String value = e.getAttribute("value");
            String name = e.getAttribute("name");
            map.put("jdbc." + name,  value);
        });

        this.dataSource = new DataSource()
                .username(map.get(DataSourceConst.USERNAME))
                .password(map.get(DataSourceConst.PASSWORD))
                .url(map.get(DataSourceConst.URL))
                .driver(map.get(DataSourceConst.DRIVER));
    }

    private void initMapper() {
        Element mappersEle = XmlUtil.getElement(this.root, "mappers");
        List<Element> mappers = XmlUtil.getElements(mappersEle, "mapper");
        mappers.forEach(e -> {
            String mapperPath = e.getAttribute("resource");
            mapperRegister.addMapper(mapperPath);
        });
    }

    private void initInterceptors() {
        Element mappersEle = XmlUtil.getElement(this.root, "plugins");
        List<Element> mappers = XmlUtil.getElements(mappersEle, "plugin");
        mappers.forEach(e -> {
            String interceptor = e.getAttribute("interceptor");
            interceptorList.add(ReflectUtil.newInstance(interceptor));
        });
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName(dataSource.driver());
            return DriverManager.getConnection(dataSource.url(), dataSource.username(), dataSource.password());
        } catch (ClassNotFoundException | SQLException e) {
            throw new MybatisException(e);
        }
    }

    @Override
    public MapperMethod getMapperMethod(Class<?> clz, String methodName) {
        return this.mapperRegister.getMapperMethod(clz, methodName);
    }

    @Override
    public List<Interceptor> getInterceptorList() {
        return this.interceptorList;
    }
}
