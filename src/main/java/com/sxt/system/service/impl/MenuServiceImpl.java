package com.sxt.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxt.system.common.Constant;
import com.sxt.system.common.DataGridView;
import com.sxt.system.mapper.RoleMapper;
import com.sxt.system.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.system.domain.Menu;
import com.sxt.system.mapper.MenuMapper;
import com.sxt.system.service.MenuService;
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 全查询所有菜单
     * @return
     */
    @Override
    public List<Menu> queryAllMenuForList() {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        qw.and(new Consumer<QueryWrapper<Menu>>() { //and 后跟的条件是or的关系
            @Override
            public void accept(QueryWrapper<Menu> menuQueryWrapper) {
                menuQueryWrapper.eq("type",Constant.MENU_TYPE_TOP)
                        .or().eq("type",Constant.MENU_TYPE_LEFT);
            }
        });
        qw.orderByAsc("ordernum");
        return this.menuMapper.selectList(qw);
    }

    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    @Override
    public List<Menu> queryMenuForListByUserId(Integer id) {
        //根据userId查询角色ID的集合
        List<Integer> roleIds = this.roleMapper.queryRoleIdsByUserId(id);
        //根据角色id的集合查询菜单id的集合
        if(null!=roleIds&&roleIds.size()>0){
            List<Integer> menuIds = this.roleMapper.queryMenuIdsByRids(roleIds);
            if(null!=menuIds&&menuIds.size()>0) {
                QueryWrapper<Menu> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.and(new Consumer<QueryWrapper<Menu>>() {
                    @Override
                    public void accept(QueryWrapper<Menu> menuQueryWrapper) {
                        menuQueryWrapper.eq("type", Constant.MENU_TYPE_TOP)
                                .or().eq("type", Constant.MENU_TYPE_LEFT);
                    }
                });
                qw.in("id",menuIds);
                qw.orderByAsc("ordernum");
                List<Menu> menus = this.menuMapper.selectList(qw);
                return menus;
            }else{
                return  new ArrayList<>();
            }
        }else{
            return  new ArrayList<>();
        }
    }

    @Override
    public DataGridView queryAllMenu(MenuVo menuVo) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq(menuVo.getAvailable()!=null,"available",menuVo.getAvailable());
        qw.orderByAsc("ordernum");
        List<Menu> menus = this.menuMapper.selectList(qw);
        return new DataGridView(Long.valueOf(menus.size()),menus);
    }



    @Override
    public Menu saveDept(Menu menu) {
        this.menuMapper.insert(menu);
        return menu;
    }

    @Override
    public Object getMenuById(Integer id) {
        return this.menuMapper.selectById(id);
    }

    @Override
    public Menu updateMenu(Menu menu) {
        this.menuMapper.updateById(menu);
        return menu;
    }

    @Override
    public Integer getMenuChildsById(Integer id) {
        return this.menuMapper.getMenuChildsById(id);
    }

    /**
     * 根据用户id查询权限编码
     * @param id
     * @return
     */
    @Override
    public List<String> queryPermissionCodesByUserId(Integer id) {
        //根据userId查询角色ID的集合
        List<Integer> roleIds = this.roleMapper.queryRoleIdsByUserId(id);
        //根据角色id的集合查询菜单id的集合
        if(null!=roleIds&&roleIds.size()>0){
            List<Integer> menuIds = this.roleMapper.queryMenuIdsByRids(roleIds);
            if(null!=menuIds&&menuIds.size()>0) {
                QueryWrapper<Menu> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.eq("type",Constant.MENU_TYPE_PERMISSION);
                qw.in("id",menuIds);
                qw.orderByAsc("ordernum");
                List<Menu> menus = this.menuMapper.selectList(qw);
                List<String> permissionsCode = new ArrayList<>();
                for (Menu menu : menus) {
                    permissionsCode.add(menu.getTypeCode());
                }
                return permissionsCode;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    @Override
    public Integer queryMenuMaxOrderNum() {
        return this.menuMapper.queryMenuMaxOrderNum();
    }

    /**
     * 重写该方法方便做缓存,加注解@CacheEvict做数据库缓存
     * @param id
     * @return
     */
    @Override
    public Menu getById(Serializable id) {
        return super.getById(id);
    }

    /**
     * 重写该方法方便做缓存,加注解@CacheEvict做数据库缓存
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
