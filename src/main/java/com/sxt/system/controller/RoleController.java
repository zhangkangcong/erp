package com.sxt.system.controller;


import com.sxt.system.common.Constant;
import com.sxt.system.common.DataGridView;
import com.sxt.system.common.ResultObj;
import com.sxt.system.domain.Role;
import com.sxt.system.service.RoleService;
import com.sxt.system.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * create by zkc from 2020/09/03
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询角色
     * @return
     */
    @GetMapping("loadAllRole")
    public Object loadAllRole(RoleVo roleVo){
        return this.roleService.queryAllRole(roleVo);
    }

    /**
     * 查询所有可用角色不分页
     * @return
     */
    @GetMapping("loadAllAvailableRoleNoPage")
    public Object loadAllAvailableRoleNoPage(RoleVo roleVo){
        roleVo.setAvailable(Constant.AVAILABLE_TRUE);
        return this.roleService.queryAllAvailableRoleNoPage(roleVo);
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping("addRole")
    public ResultObj addRole(Role role){
        try{
            role.setAvailable(Constant.AVAILABLE_TRUE);
            role.setCreatetime(new Date());
            this.roleService.saveDept(role);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PostMapping("updateRole")
    public ResultObj updateRole(Role role){
        try{
            this.roleService.updateRole(role);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @PostMapping("deleteRole")
    public ResultObj deleteRole(Integer id){
        try{
            this.roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("batchDeleteRole")
    public ResultObj batchDeleteRole(Integer[] ids){
        try{
            if(null!=ids&&ids.length>0){
                List<Integer> idList = new ArrayList<>();
                for(Integer id:ids){
                    idList.add(id);
                }
                this.roleService.removeByIds(idList);
                return ResultObj.DELETE_SUCCESS;
            }else{
                return new ResultObj(-1,"传入的ID不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据角色id查询当前角色拥有的菜单和权限ID
     * @param id 角色id
     * @return
     */
    @GetMapping("queryMenuIdsByRid")
    public Object queryMenuIdsByRid(Integer id){
        List<Integer> mids = this.roleService.queryMenuIdsByRid(id);
        return new DataGridView(mids);
    }

    /**
     *保存角色和菜单权限之间的关系
     * @return
     */
    @PostMapping("saveRoleMenu")
    public ResultObj saveRoleMenu(Integer rid,Integer[] mids){
        try{
            this.roleService.saveRoleMenu(rid,mids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }



}
