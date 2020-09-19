package com.sxt.system.service;

import com.sxt.system.common.DataGridView;
import com.sxt.system.domain.Loginfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.system.vo.LoginfoVo;

public interface LoginfoService extends IService<Loginfo>{


    DataGridView queryAllLoginfo(LoginfoVo loginfoVo);
}
