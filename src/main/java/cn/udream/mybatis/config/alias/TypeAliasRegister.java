package cn.udream.mybatis.config.alias;

public interface TypeAliasRegister {

    /**
     * 描述: 注册
     * @param alias 别名
     * @param typeName 类型名称
     */
    TypeAliasRegister register(String alias, String typeName);

    /**
     * 描述: 获取别名
     * @param alias 别名
     */
    String getTypeAlias(String alias);
}
