package com.sxt.system.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sxt.system.common.*;
import com.sxt.system.domain.User;
import com.sxt.system.service.UserService;
import com.sxt.system.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * create by zkc to 2020.09.06
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有用户
     * @param userVo
     * @return
     */
    @GetMapping("loadAllUser")
    public Object loadAllUser(UserVo userVo){
        return  this.userService.loadAllUser(userVo);
    }

    @GetMapping("queryUserMaxOrdernum")
    public Object queryUserMaxOrdernum(){
        Integer maxValue = this.userService.queryUserMaxOrdernum();
        return new DataGridView(maxValue+1);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("addUser")
    public ResultObj addUser(User user){
        try{
            user.setSalt(MD5Utils.createUUID());
            user.setType(Constant.USER_TYPE_NOMAL);
            user.setPwd(MD5Utils.md5(Constant.DEFAULT_PWD,user.getSalt(),2));
            user.setAvailable(Constant.AVAILABLE_TRUE);
            user.setImgpath(Constant.DEFAULT_TITLE_IMAGE);
            this.userService.saveUser(user);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("updateUser")
    public ResultObj updateUser(User user){
        try{
            this.userService.updateUser(user);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @PostMapping("resetUserPwd")
    public ResultObj resetUserPwd(Integer id){
        try{
            User user = new User();
            user.setId(id);
            user.setSalt(MD5Utils.createUUID());
            user.setPwd(MD5Utils.md5(Constant.DEFAULT_PWD,user.getSalt(),2));
            this.userService.updateUser(user);
            return ResultObj.RESET_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("deleteUser")
    public ResultObj deleteUser(Integer id){
        try{
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @RequestMapping("batchDeleteUser")
    public ResultObj batchDeleteUser(Integer[] ids){
        try{
            if(null!=ids&&ids.length>0){
                List<Integer> idList = new ArrayList<>();
                for(Integer id:ids){
                    idList.add(id);
                }
                this.userService.removeByIds(idList);
                return ResultObj.DELETE_SUCCESS;
            }else{
                return new ResultObj(-1,"传入的ID不能为空!");
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 保存分配角色之后用户跟角色的关系
     * @param rids
     * @param uid
     * @return
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer[] rids,Integer uid){
        try{
            this.userService.saveUserRole(rids,uid);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * 查询当前用户
     * @return
     */
    @GetMapping("getCurrentUser")
    public Object getCurrentUser(){
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        return new DataGridView(activeUser.getUser());

    }
}
