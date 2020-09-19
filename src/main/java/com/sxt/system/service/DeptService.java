package com.sxt.system.service;

import com.sxt.system.common.DataGridView;
import com.sxt.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.system.vo.DeptVo;

public interface DeptService extends IService<Dept>{


    DataGridView queryAllDept(DeptVo deptVo);

    Dept saveDept(Dept dept);

    Integer queryDeptMaxOrderNum();

    Dept updateDept(Dept dept);

    Object queryDeptById(Integer id);

    //根据部门id查询当前部门子部门的个数
    Integer queryDeptChildsById(Integer id);
}
