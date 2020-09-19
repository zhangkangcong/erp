package com.sxt.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.system.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    void deleteRoleMenuByMid(Serializable id);

    void deleteRoleMenuByRid(Serializable id);

    void deleteRoleUserByUid(Serializable id);

    void deleteRoleUserByRid(Serializable id);

    List<Integer> queryMenuIdsByRid(Integer id);

    void insertRoleMenu(@Param("rid") Integer rid,@Param("mid") Integer mid);

    List<Integer> queryRoleIdsByUserId(Integer userId);

    List<Integer> queryMenuIdsByRids(@Param("roleIds") List<Integer> roleIds);
}