package com.sxt.system.common;

/**
 * creat by zkc 2020.05.03
 */
public class Constant {

    /**
     * 用户类型
     */
    public static final Integer USER_TYPE_SUPER=0;
    public static final Integer USER_TYPE_NOMAL=1;
    /**
     * 可用类型
     */
    public static final Integer AVAILABLE_TRUE=1;
    public static final Integer AVAILABLE_FALSE=0;
    /**
     * 权限类型
     */
    public static final String MENU_TYPE_TOP="topmenu";
    public static final String MENU_TYPE_LEFT="leftmenu";
    public static final String MENU_TYPE_PERMISSION="permission";
    /**
     * 是否展开
     */
    public static final Integer SPREAD_TRUE=1;
    public static final Integer SPREAD_FALSE=0;
    /**
     * 是否展开
     */
    public static final Boolean SPREAD_SUCCESS=true;
    public static final Boolean SPREAD_ERROR=false;
    /**
     * 判断登录状态码
     */
    public static final Integer IS_LOGIN_CODE = 200;
    public static final Integer UN_LOGIN_CODE = -1;
    /**
     * 判断删除状态
     */
    public static final Integer IS_DELETE_CODE = 200;
    public static final Integer UN_DELETE_CODE = -1;
    /**
     * 判断添加状态
     */
    public static final Integer IS_ADD_CODE = 200;
    public static final Integer UN_ADD_CODE = -1;
    /**
     * 判断修改状态
     */
    public static final Integer IS_UPDATE_CODE = 200;
    public static final Integer UN_UPDATE_CODE = -1;

    /**
     * 权限分配
     */
    public static final Integer DISPATCH_ERROR_CODE = -1;
    public static final Integer DISPATCH_SUCCESS_CODE = 200;

    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD = "123456";

    /**
     * 默认头像地址
     */
    public static final String DEFAULT_TITLE_IMAGE = "";
    /**
     * 重置
     */
    public static final Integer RESET_ERROR_CODE = -1;
    public static final Integer RESET_SUCCESS_CODE = 200;
}
