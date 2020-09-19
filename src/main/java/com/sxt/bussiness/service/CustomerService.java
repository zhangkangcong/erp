package com.sxt.bussiness.service;

import com.sxt.bussiness.vo.CustomerVo;
import com.sxt.bussiness.domain.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.system.common.DataGridView;
import org.springframework.stereotype.Service;


public interface CustomerService extends IService<Customer> {


    DataGridView loadAllCustomer(CustomerVo customerVo);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customer);
}


