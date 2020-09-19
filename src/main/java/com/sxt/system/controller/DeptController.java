package com.sxt.system.controller;

import com.sxt.system.common.Constant;
import com.sxt.system.common.DataGridView;
import com.sxt.system.common.ResultObj;
import com.sxt.system.domain.Dept;
import com.sxt.system.domain.Role;
import com.sxt.system.service.DeptService;
import com.sxt.system.vo.DeptVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * create by zkc 2020.08.28
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询部门
     * @param deptVo
     * @return
     */
    @RequestMapping("loadAllDept")
    public Object loadAllDept(DeptVo deptVo){
        return this.deptService.queryAllDept(deptVo);
    }

    /**
     * 根据id查询部门对象
     * @param id
     * @return
     */
    @GetMapping("loadDeptById")
    public Object loadDeptById(Integer id){
        return this.deptService.getById(id);
    }





    /**
     * 查询部门最大的排序码
     * @return
     */
    @GetMapping("queryDeptMaxOrderNum")
    public Object queryDeptMaxOrderNum(){
        Integer maxValue = this.deptService.queryDeptMaxOrderNum();
        return new DataGridView(maxValue+1);
    }

    /**
     * 新增部门
     * @return
     */
    @PostMapping("addDept")
    @RequiresPermissions("dept:add")
    public ResultObj addDept(Dept dept){
        try{
            dept.setSpread(Constant.SPREAD_FALSE);
            dept.setAvailable(Constant.AVAILABLE_TRUE);
            this.deptService.saveDept(dept);
            return  ResultObj.ADD_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }



    /**
     * 根据部门id查询
     * @param id
     * @return
     */
    @GetMapping("queryDeptById")
    public Object getById(Integer id){
        return new DataGridView(this.deptService.queryDeptById(id));
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @PostMapping("updateDept")
    @RequiresPermissions("dept:update")
    public ResultObj updateDept(Dept dept){
        try{
            this.deptService.updateDept(dept);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }



    /**
     * 根据部门id查询当前部门子部门的个数
     * @param id
     * @return
     */
    @GetMapping("getDeptChildById")
    public Object getDeptChildsById(Integer id){
        Integer count = this.deptService.queryDeptChildsById(id);
        return new DataGridView(count);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping("deleteDept")
    @RequiresPermissions("dept:delete")
    public ResultObj deleteDept(Integer id){
        try{
            this.deptService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }


}
