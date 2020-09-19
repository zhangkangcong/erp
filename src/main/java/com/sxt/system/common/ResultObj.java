package com.sxt.system.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by zkc 2020.04.22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    public static final ResultObj IS_LOGIN = new ResultObj(Constant.IS_LOGIN_CODE,"已登录");
    public static final ResultObj UN_LOGIN = new ResultObj(Constant.UN_LOGIN_CODE,"未登录");
    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constant.IS_DELETE_CODE,"删除成功");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constant.UN_DELETE_CODE,"删除失败");
    public static final ResultObj ADD_SUCCESS = new ResultObj(Constant.IS_ADD_CODE,"添加成功");
    public static final ResultObj ADD_ERROR = new ResultObj(Constant.UN_ADD_CODE,"添加失败");
    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constant.IS_UPDATE_CODE,"修改成功");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constant.UN_UPDATE_CODE,"修改失败");
    public static final ResultObj DISPATCH_ERROR = new ResultObj(Constant.DISPATCH_ERROR_CODE,"分配失败");
    public static final ResultObj DISPATCH_SUCCESS = new ResultObj(Constant.DISPATCH_SUCCESS_CODE,"分配成功");
    public static final ResultObj RESET_SUCCESS = new ResultObj(Constant.RESET_SUCCESS_CODE,"重置成功");
    public static final ResultObj RESET_ERROR = new ResultObj(Constant.RESET_ERROR_CODE,"重置失败");

    private Integer code=200;
    private String msg="";
    private Object token="";

    public  ResultObj(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
