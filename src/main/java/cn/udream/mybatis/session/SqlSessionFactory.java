package cn.udream.mybatis.session;

import cn.udream.mybatis.config.Config;

public interface SqlSessionFactory {

    SqlSession openSession();

    Config getConfig();
}
