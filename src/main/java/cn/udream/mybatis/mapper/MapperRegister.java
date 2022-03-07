package cn.udream.mybatis.mapper;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperRegister {

    private static final Map<Class<?>, MapperClass> MAPPER_MAP = new HashMap<>();

    private static final Map<String, MapperMethod> METHOD_MAP = new HashMap<>();

    public MapperRegister addMapper(String mapperPath) {
        MapperClass mapperClass = this.parseMapperXml(mapperPath);
        String namespace = mapperClass.getNamespace();
        Class<?> namespaceClz = ClassUtil.loadClass(namespace);
        this.addMapper(namespaceClz, mapperClass);
        return this;
    }

    public MapperClass getMapper(Class<?> clz) {
        return MAPPER_MAP.get(clz);
    }

    public MapperMethod getMapperMethod(Class<?> clz, String methodName) {
        return METHOD_MAP.get(clz.getName() + "#" + methodName);
    }

    private MapperRegister addMapper(Class<?> clz, MapperClass mapperClass) {
        String clzName = clz.getName();
        mapperClass.getMethodList().forEach(e -> {
            String methodName = e.getMethodName();
            METHOD_MAP.put(clzName + "#" + methodName, e);
        });
        MAPPER_MAP.put(clz, mapperClass);
        return this;
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
            method.setParameterType(ClassUtil.loadClass(e.getAttribute("parameterType")));
            method.setResultType(ClassUtil.loadClass(e.getAttribute("resultType")));
            method.setSql(e.getTextContent().trim());
            methodList.add(method);
        });

        MapperClass mapperClass = new MapperClass();
        mapperClass.setMethodList(methodList);
        mapperClass.setNamespace(root.getAttribute("namespace"));
        return mapperClass;
    }
}
