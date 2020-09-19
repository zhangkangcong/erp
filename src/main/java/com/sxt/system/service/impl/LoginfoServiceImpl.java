package com.sxt.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxt.system.common.DataGridView;
import com.sxt.system.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.system.domain.Loginfo;
import com.sxt.system.mapper.LoginfoMapper;
import com.sxt.system.service.LoginfoService;
@Service
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService{

    private static Log log = LogFactory.getLog(LoginfoService.class);
    @Autowired
    private LoginfoMapper loginfoMapper;

    /**
     *
     * @param loginfoVo
     * 查询所有登录日志
     * @return
     */
    @Override
    public DataGridView queryAllLoginfo(LoginfoVo loginfoVo) {

        /**
         * 分页操作
         * Page类：
         * total:总条数，current：当前页数 size：当前每页显示数
         * records："records":[{"id":"1","name":"zkc","age":"20"},{"id":"2","name":"zzz","age":"12"}]
         * Vo类：
         * page:当前页数  limiti：每页几条
         */
        IPage<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        QueryWrapper<Loginfo> qw = new QueryWrapper<>();
        //查询条件，登录名和登录ip模糊查询，大于登录开始时间小于登录结束时间
        qw.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        qw.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        qw.ge(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        qw.le(loginfoVo.getEndTime()!=null,"logintime",loginfoVo.getEndTime());
        qw.orderByDesc("logintime");
        this.loginfoMapper.selectPage(page,qw);
        return new DataGridView(page.getTotal(),page.getRecords());//总条数和数据
    }
}
