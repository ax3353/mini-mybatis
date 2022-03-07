package cn.udream.mybatis.config.alias.impl;

import cn.hutool.core.util.StrUtil;
import cn.udream.mybatis.config.alias.TypeAliasRegister;
import cn.udream.mybatis.exception.MybatisException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.*;

public class DefaultTypeAliasRegister implements TypeAliasRegister {

    private static final Map<String, String> TYPE_ALIAS_MAP = new HashMap<>();

    static {
        TYPE_ALIAS_MAP.put("string", String.class.getName());
        TYPE_ALIAS_MAP.put("byte", Byte.class.getName());
        TYPE_ALIAS_MAP.put("long", Long.class.getName());
        TYPE_ALIAS_MAP.put("short", Short.class.getName());
        TYPE_ALIAS_MAP.put("int", Integer.class.getName());
        TYPE_ALIAS_MAP.put("integer", Integer.class.getName());
        TYPE_ALIAS_MAP.put("double", Double.class.getName());
        TYPE_ALIAS_MAP.put("float", Float.class.getName());
        TYPE_ALIAS_MAP.put("boolean", Boolean.class.getName());
        TYPE_ALIAS_MAP.put("byte[]", Byte[].class.getName());
        TYPE_ALIAS_MAP.put("long[]", Long[].class.getName());
        TYPE_ALIAS_MAP.put("short[]", Short[].class.getName());
        TYPE_ALIAS_MAP.put("int[]", Integer[].class.getName());
        TYPE_ALIAS_MAP.put("integer[]", Integer[].class.getName());
        TYPE_ALIAS_MAP.put("double[]", Double[].class.getName());
        TYPE_ALIAS_MAP.put("float[]", Float[].class.getName());
        TYPE_ALIAS_MAP.put("boolean[]", Boolean[].class.getName());
        TYPE_ALIAS_MAP.put("_byte", Byte.TYPE.getName());
        TYPE_ALIAS_MAP.put("_long", Long.TYPE.getName());
        TYPE_ALIAS_MAP.put("_short", Short.TYPE.getName());
        TYPE_ALIAS_MAP.put("_int", Integer.TYPE.getName());
        TYPE_ALIAS_MAP.put("_integer", Integer.TYPE.getName());
        TYPE_ALIAS_MAP.put("_double", Double.TYPE.getName());
        TYPE_ALIAS_MAP.put("_float", Float.TYPE.getName());
        TYPE_ALIAS_MAP.put("_boolean", Boolean.TYPE.getName());
        TYPE_ALIAS_MAP.put("_byte[]", byte[].class.getName());
        TYPE_ALIAS_MAP.put("_long[]", long[].class.getName());
        TYPE_ALIAS_MAP.put("_short[]", short[].class.getName());
        TYPE_ALIAS_MAP.put("_int[]", int[].class.getName());
        TYPE_ALIAS_MAP.put("_integer[]", int[].class.getName());
        TYPE_ALIAS_MAP.put("_double[]", double[].class.getName());
        TYPE_ALIAS_MAP.put("_float[]", float[].class.getName());
        TYPE_ALIAS_MAP.put("_boolean[]", boolean[].class.getName());
        TYPE_ALIAS_MAP.put("date", Date.class.getName());
        TYPE_ALIAS_MAP.put("decimal", BigDecimal.class.getName());
        TYPE_ALIAS_MAP.put("bigdecimal", BigDecimal.class.getName());
        TYPE_ALIAS_MAP.put("biginteger", BigInteger.class.getName());
        TYPE_ALIAS_MAP.put("object", Object.class.getName());
        TYPE_ALIAS_MAP.put("date[]", Date[].class.getName());
        TYPE_ALIAS_MAP.put("decimal[]", BigDecimal[].class.getName());
        TYPE_ALIAS_MAP.put("bigdecimal[]", BigDecimal[].class.getName());
        TYPE_ALIAS_MAP.put("biginteger[]", BigInteger[].class.getName());
        TYPE_ALIAS_MAP.put("object[]", Object[].class.getName());
        TYPE_ALIAS_MAP.put("map", Map.class.getName());
        TYPE_ALIAS_MAP.put("hashmap", HashMap.class.getName());
        TYPE_ALIAS_MAP.put("list", List.class.getName());
        TYPE_ALIAS_MAP.put("arraylist", ArrayList.class.getName());
        TYPE_ALIAS_MAP.put("collection", Collection.class.getName());
        TYPE_ALIAS_MAP.put("iterator", Iterator.class.getName());
        TYPE_ALIAS_MAP.put("ResultSet", ResultSet.class.getName());
    }

    @Override
    public TypeAliasRegister register(String alias, String typeName) {
        if (TYPE_ALIAS_MAP.containsKey(alias) && TYPE_ALIAS_MAP.get(alias) != null && !(TYPE_ALIAS_MAP.get(alias)).equals(typeName)) {
            throw new MybatisException("alias '" + alias + "' is already registered");
        }

        TYPE_ALIAS_MAP.put(alias, typeName);
        return this;
    }

    @Override
    public String getTypeAlias(String alias) {
        String fullTypeName = TYPE_ALIAS_MAP.get(alias);
        return StrUtil.isEmpty(fullTypeName) ? alias : fullTypeName;
    }
}
