package com.sxt.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxt.system.common.DataGridView;
import com.sxt.system.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.system.mapper.DeptMapper;
import com.sxt.system.domain.Dept;
import com.sxt.system.service.DeptService;
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService{

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public DataGridView queryAllDept(DeptVo deptVo) {
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        qw.orderByAsc("ordernum");
        List<Dept> depts = this.deptMapper.selectList(qw);
        return new DataGridView(Long.valueOf(depts.size()),depts);
    }

    @Override
    @CachePut(cacheNames = "com.sxt.system.service.impl.DeptServiceImpl",key = "#result.id")
    public Dept saveDept(Dept dept) {
        this.deptMapper.insert(dept);
        return dept;
    }

    @Override
    public Integer queryDeptMaxOrderNum() {
        return this.deptMapper.queryDeptMaxOrderNum();
    }

    /**
     * 默认在启动类开启缓存
     * @param dept
     * @return
     */
    @Override
    @CachePut(cacheNames = "com.sxt.system.service.impl.DeptServiceImpl",key = "#result.id")
    public Dept updateDept(Dept dept) {
        this.deptMapper.updateById(dept);
        return dept;
    }

    @Override
    public Object queryDeptById(Integer id) {
        return this.deptMapper.selectById(id);
    }

    /**
     * 根据部门id查询当前部门子部门的个数
     * @param id
     * @return
     */
    @Override
    public Integer queryDeptChildsById(Integer id) {
        return this.deptMapper.queryDeptChildsById(id);
    }

    /**
     * 只是重写该方法，为了以后做缓存
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "com.sxt.system.service.impl.DeptServiceImpl",key = "#id")
    @Override
    public Dept getById(Serializable id) {
        return super.getById(id);
    }

    /**
     * 只是重写该方法，为了以后做缓存 ,加注解@CacheEvict做数据库缓存
     * @param id
     * @return
     */
    @CacheEvict(cacheNames = "com.sxt.system.service.impl.DeptServiceImpl",key = "#id")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

}
