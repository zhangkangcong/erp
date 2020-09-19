package com.sxt.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * create by zkc 2020.09.06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends  BaseVo{

    private String address;
    private String name;
    private String remark;
    private Integer deptid;
    private Integer available;

}
