package com.sxt.bussiness.controller;

import com.sxt.bussiness.domain.Customer;
import com.sxt.bussiness.vo.CustomerVo;
import com.sxt.bussiness.service.CustomerService;
import com.sxt.system.common.Constant;
import com.sxt.system.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CustomerController
 * @Description TODO
 * @Author zkc
 * @Date 2020/9/19 15:21
 * @Version 1.0
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 查询
     * @param customerVo
     * @return
     */
    @GetMapping("loadAllCustomer")
    public Object loadAllCustomer(CustomerVo customerVo){
        return this.customerService.loadAllCustomer(customerVo);
    }

    /**
     * 新增
     * @param customer
     * @return
     */
    @RequestMapping("addCustomer")
    public ResultObj addCustomer(Customer customer){
        try{
            customer.setAvailable(Constant.AVAILABLE_TRUE);
            this.customerService.saveCustomer(customer);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     * @param customer
     * @return
     */
    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(Customer customer){
        try{
            this.customerService.updateCustomer(customer);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteCustomer")
    public ResultObj deleteCustomer(Integer id){
        try{
            this.customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("batchDeleteCustomer")
    public ResultObj batchDeleteCustomer(Integer[] ids){
        try{
            if(null!=ids&&ids.length>0){
                List<Integer> idsList = new ArrayList<>();
                for (Integer id : ids) {
                    idsList.add(id);
                }
                this.customerService.removeByIds(idsList);
                return ResultObj.DELETE_SUCCESS;
            }else{
                return new ResultObj(-1,"传入ID不能为空");
            }

            
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
