package cn.udream.mybatis.mapper;

import java.util.List;

public class MapperClass {

    private String namespace;

    private List<MapperMethod> methodList;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<MapperMethod> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<MapperMethod> methodList) {
        this.methodList = methodList;
    }

    @Override
    public String toString() {
        return "MapperClass{" +
                "namespace='" + namespace + '\'' +
                ", methodList=" + methodList +
                '}';
    }
}
