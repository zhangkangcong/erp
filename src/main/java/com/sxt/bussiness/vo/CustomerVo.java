package com.sxt.bussiness.vo;

import com.sxt.system.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName CustomerVo
 * @Description TODO
 * @Author zkc
 * @Date 2020/9/19 15:20
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends BaseVo {

    private String customername;
    private String phone;
    private String connectionperson;
    private Integer available;
}
