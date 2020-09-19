package com.sxt.system.service;

import com.sxt.system.common.DataGridView;
import com.sxt.system.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.system.vo.MenuVo;

import java.util.List;

public interface MenuService extends IService<Menu>{

    /**
     * 全查询菜单
     * @return
     */
    List<Menu> queryAllMenuForList();

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    List<Menu> queryMenuForListByUserId(Integer id);

    DataGridView queryAllMenu(MenuVo menuVo);

    Integer queryMenuMaxOrderNum();

    Menu saveDept(Menu menu);

    Object getMenuById(Integer id);

    Menu updateMenu(Menu menu);

    Integer getMenuChildsById(Integer id);

    List<String> queryPermissionCodesByUserId(Integer id);
}
