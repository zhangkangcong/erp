package com.sxt.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 登录名
     */
    @TableField(value = "loginname")
    private String loginname;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 密码
     */
    @TableField(value = "pwd")
    @JsonIgnore//生成字符串时不序列化
    private String pwd;

    /**
     * 部门id
     */
    @TableField(value = "deptid")
    private Integer deptid;

    @TableField(exist = false)
    private String deptname;

    /**
     * 入职时间
     */
    @TableField(value = "hiredate")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hiredate;

    @TableField(value = "ordernum")
    private Integer ordernum;

    /**
     * 用户类型[0超级管理员，1普通用户]
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 头像地址
     */
    @TableField(value = "imgpath")
    private String imgpath;

    /**
     * 盐加密
     */
    @TableField(value = "salt")
    @JsonIgnore//生成字符串时不序列化
    private String salt;

    /**
     * 是否可用
     */
    @TableField(value = "available")
    private Integer available;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_LOGINNAME = "loginname";

    public static final String COL_ADDRESS = "address";

    public static final String COL_SEX = "sex";

    public static final String COL_REMARK = "remark";

    public static final String COL_PWD = "pwd";

    public static final String COL_DEPTID = "deptid";

    public static final String COL_HIREDATE = "hiredate";

    public static final String COL_ORDERNUM = "ordernum";

    public static final String COL_TYPE = "type";

    public static final String COL_IMGPATH = "imgpath";

    public static final String COL_SALT = "salt";

    public static final String COL_AVAILABLE = "available";
}