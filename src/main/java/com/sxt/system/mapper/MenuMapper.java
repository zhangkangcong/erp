package com.sxt.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.system.domain.Menu;

public interface MenuMapper extends BaseMapper<Menu> {
    Integer queryMenuMaxOrderNum();

    Integer getMenuChildsById(Integer id);
}