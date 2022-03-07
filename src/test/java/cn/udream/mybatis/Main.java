package cn.udream.mybatis;

import cn.udream.mybatis.config.Config;
import cn.udream.mybatis.config.impl.XmlConfig;
import cn.udream.mybatis.domain.User;
import cn.udream.mybatis.mapper.UserMapper;
import cn.udream.mybatis.session.SqlSession;
import cn.udream.mybatis.session.impl.DefaultSqlSessionFactory;

public class Main {

    public static void main(String[] args) {
        Config config = new XmlConfig("mybatis-config.xml");

        DefaultSqlSessionFactory sessionFactory = new DefaultSqlSessionFactory(config);
        SqlSession sqlSession = sessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(1L);
        System.out.println(user);
    }
}
