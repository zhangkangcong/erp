package com.sxt.system.service;

import com.sxt.system.common.DataGridView;
import com.sxt.system.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.system.vo.UserVo;

public interface UserService extends IService<User>{

    /**
     * 根据用户登录名返回登录用户信息
     */
    User queryUserByLoginName(String loginname);

    /**
     * 查询所有用户
     */
    DataGridView loadAllUser(UserVo userVo);

    /**
     * 添加用户
     */
    User saveUser(User user);

    /**
     * 修改用户
     */
    User updateUser(User user);

    /**
     * 查询最大权限吗
     */
    Integer queryUserMaxOrdernum();

    void saveUserRole(Integer[] rids, Integer uid);
}
