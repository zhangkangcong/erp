package com.sxt.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * create by zkc 2020.08.28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends  BaseVo{

    private String title;


}
