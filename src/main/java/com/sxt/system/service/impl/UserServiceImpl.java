package com.sxt.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxt.system.common.AppUtils;
import com.sxt.system.common.Constant;
import com.sxt.system.common.DataGridView;
import com.sxt.system.domain.Dept;
import com.sxt.system.mapper.DeptMapper;
import com.sxt.system.mapper.RoleMapper;
import com.sxt.system.service.DeptService;
import com.sxt.system.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.system.domain.User;
import com.sxt.system.mapper.UserMapper;
import com.sxt.system.service.UserService;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    private Log log = LogFactory.getLog(UserService.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User queryUserByLoginName(String loginname) {
        //UserMapper userMapper = this.getBaseMapper();

        QueryWrapper<User> qw = new QueryWrapper<>();
        if(StringUtils.isBlank(loginname)){
            log.error("登录名不能为空!");
            return null;
        }
        qw.eq("loginname",loginname);
        User user = userMapper.selectOne(qw);
        return user;
    }



    @Override
    public DataGridView loadAllUser(UserVo userVo) {
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        qw.like(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        qw.like(StringUtils.isNotBlank(userVo.getRemark()),"remark",userVo.getRemark());
        qw.eq(null!=userVo.getDeptid(),"deptid",userVo.getDeptid());
        qw.eq("type", Constant.USER_TYPE_NOMAL);
        qw.orderByAsc("ordernum");
        this.userMapper.selectPage(page,qw);
        //拿到返回数据，根据deptid取值deptname
        List<User> records = page.getRecords();
        //第二次之后从缓存去取部门信息，不会再去数据库查询部门信息
        DeptService deptService = AppUtils.getContext().getBean(DeptService.class);
        for (User record : records) {
            if(null!=record.getDeptid()){
                Dept dept = deptService.getById(record.getDeptid());
                record.setDeptname(dept.getTitle());
            }
        }
        return new DataGridView(page.getTotal(),records);
    }

    @Override
    public User saveUser(User user) {
        this.userMapper.insert(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        this.userMapper.updateById(user);
        return user;
    }

    @Override
    public Integer queryUserMaxOrdernum() {
        return this.userMapper.queryUserMaxOrdernum();
    }

    /**
     * 保存用户和角色的关系
     * @param rids
     * @param uid
     */
    @Override
    public void saveUserRole(Integer[] rids, Integer uid) {
        //////根据用户id删除用户和角色中间表的数据
        this.roleMapper.deleteRoleUserByUid(uid);
        if(null!=rids&&rids.length>0){
            for (Integer rid : rids) {
                this.userMapper.insertUserRole(uid,rid);
            }
        }
    }


    /**
     * 重写删除方法，方便做缓存
     */
    @Override
    public boolean removeById(Serializable id) {
        //根据用户id删除用户和角色中间表的数据
        this.roleMapper.deleteRoleUserByUid(id);
        return super.removeById(id);
    }
}
