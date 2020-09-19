package com.sxt.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * create by zkc 2020.09.03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends  BaseVo{

    private Integer userId;
    private Integer available;
    private String name;
    private String remark;

}
