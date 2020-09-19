package com.sxt.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.system.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    Integer queryUserMaxOrdernum();

    void insertUserRole(@Param("uid") Integer uid, @Param("rid") Integer rid);
}