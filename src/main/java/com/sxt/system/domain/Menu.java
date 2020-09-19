package com.sxt.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.sxt.system.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class Menu implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级菜单id
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 权限类型[topmenu/leftmenu/permission]
     */
    @TableField(value = "type")
    private String type;
    /**
     *权限编码
     */
    @TableField(value = "typeCode")
    private String typeCode;

    /**
     * 名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 链接地址
     */
    @TableField(value = "href")
    private String href;

    @TableField(value = "target")
    private String target;

    /**
     * 是否展开
     */
    @TableField(value = "spread")
    private Integer spread;

//    @TableField(exist = false)
//    private Boolean open;
//
//    public Boolean getOpen() {
//        return this.spread==1? Constant.SPREAD_SUCCESS :Constant.SPREAD_ERROR;
//    }

    /**
     * 排序码
     */
    @TableField(value = "ordernum")
    private Integer ordernum;

    /**
     * 状态【0不可用1可用】
     */
    @TableField(value = "available")
    private Integer available;

    /**
     * 构造权限
     * @param id
     * @param pid
     * @param type
     * @param typeCode
     * @param title
     */
    public Menu(Integer id,Integer pid,String type,String typeCode,String title,Integer ordernum,Integer available){
        this.id = id;
        this.pid = pid;
        this.type = type;
        this.typeCode = typeCode;
        this.title = title;
        this.ordernum = ordernum;
        this.available = available;
    }


    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PID = "pid";

    public static final String COL_TYPE = "type";

    public static final String COL_TYPECODE = "typeCode";

    public static final String COL_TITLE = "title";

    public static final String COL_ICON = "icon";

    public static final String COL_HREF = "href";

    public static final String COL_TARGET = "target";

    public static final String COL_SPREAD = "spread";

    public static final String COL_ORDERNUM = "ordernum";

    public static final String COL_AVAILABLE = "available";

}