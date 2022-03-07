package cn.udream.mybatis.mapper;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.XmlUtil;
import cn.udream.mybatis.config.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperRegister {

    private static final Map<String, MapperMethod> METHOD_MAP = new HashMap<>();

    private final Config config;

    public MapperRegister(Config config) {
        this.config = config;
    }

    public MapperRegister addMapper(String mapperPath) {
        MapperClass mapperClass = this.parseMapperXml(mapperPath);
        String namespace = mapperClass.getNamespace();
        Class<?> namespaceClz = ClassUtil.loadClass(namespace);
        this.addMapper(namespaceClz, mapperClass);
        return this;
    }

    private MapperRegister addMapper(Class<?> clz, MapperClass mapperClass) {
        String clzName = clz.getName();
        mapperClass.getMethodList().forEach(e -> {
            String methodName = e.getMethodName();
            METHOD_MAP.put(clzName + "#" + methodName, e);
        });
        return this;
    }

    public MapperMethod getMapperMethod(Class<?> clz, String methodName) {
        return METHOD_MAP.get(clz.getName() + "#" + methodName);
    }

    private MapperClass parseMapperXml(String mapperPath) {
        Document document = XmlUtil.readXML(mapperPath);
        Element root = XmlUtil.getRootElement(document);
        List<Element> elements = XmlUtil.getElements(root, null);

        List<MapperMethod> methodList = new ArrayList<>(elements.size());
        elements.forEach(e -> {
            MapperMethod method = new MapperMethod();
            method.setType(e.getTagName());
            method.setMethodName(e.getAttribute("id"));
            String parameterType = e.getAttribute("parameterType");
            String resultType = e.getAttribute("resultType");
            // 处理别名映射java类型
            method.setParameterType(ClassUtil.loadClass(this.config.getTypeAlias(parameterType)));
            method.setResultType(ClassUtil.loadClass(this.config.getTypeAlias(resultType)));
            method.setSql(e.getTextContent().trim());
            methodList.add(method);
        });

        MapperClass mapperClass = new MapperClass();
        mapperClass.setMethodList(methodList);
        mapperClass.setNamespace(root.getAttribute("namespace"));
        return mapperClass;
    }
}
