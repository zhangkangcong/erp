package com.sxt.system.controller;

import com.sxt.system.common.ActiveUser;
import com.sxt.system.common.Constant;
import com.sxt.system.common.MenuTreeNode;
import com.sxt.system.common.ResultObj;
import com.sxt.system.domain.Loginfo;
import com.sxt.system.domain.Menu;
import com.sxt.system.domain.User;
import com.sxt.system.service.LoginfoService;
import com.sxt.system.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Consumer;

/**
 * create by zkc 2020.04.22
 *  CrossOrigin 跨域注解
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private LoginfoService loginfoService;


    /**
     * 用户登录
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public ResultObj doLogin(String loginname, String password, HttpServletRequest request){

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken loginToken = new UsernamePasswordToken(loginname,password);
            subject.login(loginToken); //登陆成功
            //获取user,放到session里面 session.put("User",user);
            //ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            //获取shiro的sessionID==token,改id，若我给前端，前端把它放在cookies里面
            String token = subject.getSession().getId().toString();
            //写入登陆日志
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            User user = activeUser.getUser();
            Loginfo loginfo = new Loginfo();
            loginfo.setLoginname(user.getName()+"-"+user.getLoginname());
            loginfo.setLoginip(request.getRemoteAddr());
            loginfo.setLogintime(new Date());
            //保存入库
            this.loginfoService.save(loginfo);
            //获取权限
            List<String> permissions = activeUser.getPermission();
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("permissions",permissions);
            //防止超级管理员权限丢失问题,通过将type放到login.html种的localStroage中,并在common.js里面判断
            map.put("userType",user.getType());
            //解决页面上显示用户登录名，存localStroage
            map.put("username",user.getName());
            return  new ResultObj(200,"登陆成功",map);

        }catch(AuthenticationException e){
            e.printStackTrace();
            return new ResultObj(-1,"用户名或者密码不正确");


        }
    }


    /**
     * 返回验证码
     */


    /**
     * 验证当前token是否登陆
     */
    @RequestMapping("checkLogin")
    @ResponseBody
    public ResultObj checkLogin(){
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //判断是否认证
        if(subject.isAuthenticated()){
            return ResultObj.IS_LOGIN;
        }else{
            return ResultObj.UN_LOGIN;
        }
    }


    /**
     * 加载所有菜单【顶部菜单和左侧菜单】
     */
    @RequestMapping("loadIndexMenu")
    @ResponseBody
    public Object loadIndexMenu(){
        //得到当前登录的用户
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if(null == activeUser){
            return  null;
        }
        User user = activeUser.getUser();
        List<Menu> menus = null;

        if(user.getType().equals(Constant.USER_TYPE_SUPER)){//超级管理员
            //查询所有菜单
            menus = menuService.queryAllMenuForList();
        }else{//普通用户根据用户ID查询菜单
            menus = menuService.queryMenuForListByUserId(user.getId());
        }

        List<MenuTreeNode> treeNodes = new ArrayList<>();
        //循环menus，把menus放到新建的treeNodes里，主要是用treeNodes去适应页面
        //因为menus是数据库中数据，与页面对应不上
        for (Menu m : menus) {
            Boolean spread = m.getSpread()==Constant.SPREAD_TRUE?true:false;
            treeNodes.add(new MenuTreeNode(m.getId(),m.getPid(),m.getTitle(),m.getHref(),m.getIcon(),spread,m.getTarget(),m.getTypeCode()));
        }
        //拿到两个顶部菜单
        List<MenuTreeNode> nodes = MenuTreeNode.MenuTreeNodeBuilder.build(treeNodes, 0);
        //老版本layui得到的数据格式,只需要一层map结构
        Map<String,Object> res = new HashMap<>();
        for (MenuTreeNode n : nodes) {
            res.put(n.getTypecode(),n);
        }
        //当layui框架不一致时，init.json数据格式需要核对，封装成自己layui版本的init.json格式
        //{"menuInfo":{"system":{"child":[{"child":[{"href":"","icon":"fa fa-gear","id":15,"pid":13,"spread":false,"target":"_self","title":"部门管理","typecode":"system"},{"href":"","icon":"fa fa-gear","id":16,"pid":13,"spread":false,"target":"_self","title":"菜单管理","typecode":"system"},{"href":"","icon":"fa fa-gear","id":17,"pid":13,"spread":false,"target":"_self","title":"角色管理","typecode":"system"},{"href":"","icon":"fa fa-gear","id":18,"pid":13,"spread":false,"target":"_self","title":"用户管理","typecode":"system"}],"href":"","icon":"fa fa-gear","id":13,"pid":2,"spread":false,"target":"_self","title":"系统管理","typecode":"system"},{"child":[{"href":"","icon":"fa fa-gear","id":19,"pid":14,"spread":false,"target":"_self","title":"登录日志","typecode":"system"},{"href":"","icon":"fa fa-gear","id":20,"pid":14,"spread":false,"target":"_self","title":"数据源监控","typecode":"system"},{"href":"","icon":"fa fa-gear","id":21,"pid":14,"spread":false,"target":"_self","title":"系统公告","typecode":"system"},{"href":"","icon":"fa fa-gear","id":22,"pid":14,"spread":false,"target":"_self","title":"图标管理","typecode":"system"}],"href":"","icon":"fa fa-gear","id":14,"pid":2,"spread":false,"target":"_self","title":"其他管理","typecode":"system"}],"href":"","icon":"fa fa-gear","id":2,"pid":0,"spread":false,"target":"_self","title":"系统管理","typecode":"system"},"business":{"child":[{"child":[{"href":"","icon":"fa fa-gear","id":6,"pid":3,"spread":false,"target":"_self","title":"客户管理","typecode":"business"},{"href":"","icon":"fa fa-gear","id":7,"pid":3,"spread":false,"target":"_self","title":"供应商管理","typecode":"business"},{"href":"","icon":"fa fa-gear","id":8,"pid":3,"spread":false,"target":"_self","title":"商品管理","typecode":"business"}],"href":"","icon":"fa fa-gear","id":3,"pid":1,"spread":false,"target":"_self","title":"基础数据管理","typecode":"business"},{"child":[{"href":"","icon":"fa fa-gear","id":9,"pid":4,"spread":false,"target":"_self","title":"商品进货","typecode":"business"},{"href":"","icon":"fa fa-gear","id":10,"pid":4,"spread":false,"target":"_self","title":"商品退货","typecode":"business"}],"href":"","icon":"fa fa-gear","id":4,"pid":1,"spread":false,"target":"_self","title":"进货管理","typecode":"business"},{"child":[{"href":"","icon":"fa fa-gear","id":11,"pid":5,"spread":false,"target":"_self","title":"商品销售","typecode":"business"},{"href":"","icon":"fa fa-gear","id":12,"pid":5,"spread":false,"target":"_self","title":"销售退货","typecode":"business"}],"href":"","icon":"fa fa-gear","id":5,"pid":1,"spread":false,"target":"_self","title":"销售管理","typecode":"business"}],"href":"","icon":"fa fa-newspaper-o","id":1,"pid":0,"spread":false,"target":"_self","title":"业务管理","typecode":"business"}}}
        Map<String,Map<String,Object>> result = new HashMap<>();
        result.put("menuInfo",res);

        //将返回的res转成string类型json串，需要将字符串里面的空"child":[]替换掉
        //可以加 注解@JsonInclude(JsonInclude.Include.NON_EMPTY)代替json.replace("\"child\":[],","")
        //String json = JSON.toJSONString(result);
        //return json.replace("\"child\":[],","");
        return result;
    }

}
