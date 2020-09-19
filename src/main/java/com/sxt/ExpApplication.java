package com.sxt;

import com.sxt.system.service.LoginfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = {"com.sxt.system.mapper","com.sxt.bussiness.mapper"})
@EnableCaching
public class ExpApplication {
    private static Log log = LogFactory.getLog(LoginfoService.class);

    public static void main(String[] args) {
        SpringApplication.run(ExpApplication.class, args);
        log.info("应用已启动");
    }

}
