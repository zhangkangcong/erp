package com.sxt.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.system.domain.Dept;

public interface DeptMapper extends BaseMapper<Dept> {
    Integer queryDeptMaxOrderNum();

    Integer queryDeptChildsById(Integer id);
}