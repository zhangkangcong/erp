package com.sxt.system.service;

import com.sxt.system.common.DataGridView;
import com.sxt.system.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.system.vo.RoleVo;

import java.util.List;

public interface RoleService extends IService<Role>{


    DataGridView queryAllRole(RoleVo roleVo);

    Role saveDept(Role role);

    Role updateRole(Role role);

    /**
     * 根据角色id查询当前角色拥有的菜单和权限ID
     * @param id 角色id
     * @return
     */
    List<Integer> queryMenuIdsByRid(Integer id);

    void saveRoleMenu(Integer rid, Integer[] mids);

    DataGridView queryAllAvailableRoleNoPage(RoleVo roleVo);

    List<String> queryRoleNamesByUserId(Integer id);
}
