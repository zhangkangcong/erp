package com.sxt.system.controller;

import com.sxt.system.common.Constant;
import com.sxt.system.common.DataGridView;
import com.sxt.system.common.ResultObj;
import com.sxt.system.domain.Menu;
import com.sxt.system.service.MenuService;
import com.sxt.system.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by zkc 2020.08.31
 */

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单和权限
     * @param menuVo
     * @return
     */
    @RequestMapping("loadAllMenu")
    public Object loadAllMenu(MenuVo menuVo){

        return this.menuService.queryAllMenu(menuVo);
    }

    /**
     * 只查询菜单
     * @param menuVo
     * @return
     */
    @RequestMapping("loadMenu")
    public Object loadMenu(MenuVo menuVo){
        List<Menu> menus = this.menuService.queryAllMenuForList();
        return  new DataGridView(Long.valueOf(menus.size()),menus);
    }


    /**
     * 根据ID查询菜单和权限
     * @param id
     * @return
     */
    @GetMapping("loadMenuById")
    public Object loadMenuById(Integer id){
        return  this.menuService.getById(id);

    }

    /**
     * 查询菜单最大的权限码
     * @return
     */
    @GetMapping("queryMenuMaxOrderNum")
    public Object queryMenuMaxOrderNum(){
        Integer maxValue = this.menuService.queryMenuMaxOrderNum();
        return new DataGridView(maxValue+1);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping("addMenu")
    public ResultObj addMenu(Menu menu){
        try{
            //添加头部菜单时，pid为null
            if(menu.getType().equals("topmenu")){
                menu.setPid(0);
            }
            menu.setSpread(Constant.SPREAD_FALSE);
            menu.setAvailable(Constant.AVAILABLE_TRUE);
            this.menuService.saveDept(menu);
            return ResultObj.ADD_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 根据ID查询菜单和权限
     * @param id
     * @return
     */
    @GetMapping("queryMenuById")
    public Object queryMenuById(Integer id){
        return new DataGridView(this.menuService.getMenuById(id));
    }

    /**
     * 修改菜单和权限
     * @param menu
     * @return
     */
    @PostMapping("updateMenu")
    public ResultObj updateMenu(Menu menu){
        try{
            this.menuService.updateMenu(menu);
            return ResultObj.UPDATE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 根据菜单id查询当前部门子部门的个数
     * @param id
     * @return
     */
    @GetMapping("getMenuChildsById")
    public Object getMenuChildsById(Integer id){
       Integer count =  this.menuService.getMenuChildsById(id);
       return new DataGridView(count);
    }

    /**
     * 删除菜单和权限
     * @param id
     * @return
     */
    @PostMapping("deleteMenu")
    public ResultObj deleteMenu(Integer id){
        try{
            this.menuService.removeById(id);
            return  ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }


}
