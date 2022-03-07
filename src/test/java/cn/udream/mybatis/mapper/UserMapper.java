package cn.udream.mybatis.mapper;

import cn.udream.mybatis.domain.User;

public interface UserMapper {

    User selectById(long id);
}
