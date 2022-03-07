package cn.udream.mybatis.config.impl;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.XmlUtil;
import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.config.alias.TypeAliasRegister;
import cn.udream.mybatis.config.alias.impl.DefaultTypeAliasRegister;
import cn.udream.mybatis.constant.DataSourceConst;
import cn.udream.mybatis.exception.MybatisException;
import cn.udream.mybatis.handler.type.handler.TypeHandler;
import cn.udream.mybatis.handler.type.register.TypeHandlerRegister;
import cn.udream.mybatis.handler.type.register.impl.DefaultTypeHandlerRegister;
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

    private final MapperRegister mapperRegister = new MapperRegister(this);

    private final List<Interceptor> interceptorList = new ArrayList<>();

    private final TypeHandlerRegister typeHandlerRegister = new DefaultTypeHandlerRegister();

    private final TypeAliasRegister typeAliasRegister = new DefaultTypeAliasRegister();

    public XmlConfig(String configPath) {
        this.configPath = configPath;

        // 初始化配置
        initProperties();

        // 初始化数据源连接信息
        initDataSource();

        // 初始化java类型别名(必须先于'类型处理器'初始化)
        initTypeAlias();

        // 初始化mapper信息
        initMapper();

        // 初始化拦截器
        initInterceptors();

        // 初始化类型处理器
        initTypeHandlers();
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

    private void initTypeAlias() {
        Element typeAliasesEle = XmlUtil.getElement(this.root, "typeAliases");
        List<Element> typeAliases = XmlUtil.getElements(typeAliasesEle, "typeAlias");
        typeAliases.forEach(e -> {
            String alias = e.getAttribute("alias");
            String type = e.getAttribute("type");
            typeAliasRegister.register(alias, type);
        });
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
        Element pluginsELe = XmlUtil.getElement(this.root, "plugins");
        List<Element> plugins = XmlUtil.getElements(pluginsELe, "plugin");
        plugins.forEach(e -> {
            String interceptor = e.getAttribute("interceptor");
            interceptorList.add(ReflectUtil.newInstance(interceptor));
        });
    }

    private void initTypeHandlers() {
        Element typeHandlersEle = XmlUtil.getElement(this.root, "typeHandlers");
        List<Element> typeHandlers = XmlUtil.getElements(typeHandlersEle, "typeHandler");
        typeHandlers.forEach(e -> {
            // 别名映射处理
            String javaType = e.getAttribute("javaType");
            String typeAlias = this.getTypeAlias(javaType);
            Class javaTypeClz = ClassUtil.loadClass(typeAlias);

            String handler = e.getAttribute("handler");
            TypeHandler<?> typeHandler = ReflectUtil.newInstance(handler);
            typeHandlerRegister.register(javaTypeClz, typeHandler);
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

    @Override
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType) {
        return (TypeHandler<T>) this.typeHandlerRegister.getTypeHandler(javaType);
    }

    @Override
    public String getTypeAlias(String alias) {
        return typeAliasRegister.getTypeAlias(alias);
    }
}
