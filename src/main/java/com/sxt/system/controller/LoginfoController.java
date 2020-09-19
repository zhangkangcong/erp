package com.sxt.system.controller;

import com.sxt.system.common.ResultObj;
import com.sxt.system.service.LoginfoService;
import com.sxt.system.vo.LoginfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * create by zkc 2020.06.03
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {

    @Autowired
    private LoginfoService loginfoService;

    @RequestMapping("loadAllLoginfo")
    public Object loadAllLoginfo(LoginfoVo loginfoVo){


        return this.loginfoService.queryAllLoginfo(loginfoVo);
    }
    /**
     * 删除
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo (Integer id){
        try{
            loginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo (Integer[] ids){
        try{
            if(null!=ids&&ids.length>0){
                List<Integer> idsList = new ArrayList<>();
                for (Integer id : ids) {
                    idsList.add(id);
                }
                this.loginfoService.removeByIds(idsList);
                return ResultObj.DELETE_SUCCESS;
            }else{
                return new ResultObj(-1,"传入的id不能为空!");
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }


    }

}
