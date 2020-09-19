package com.sxt.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * create by zkc 2020.08.31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVo extends  BaseVo{

     Integer available;
     Integer hasPermission;//传过来是0，就不要权限

}
