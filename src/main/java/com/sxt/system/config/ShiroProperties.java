package com.sxt.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * create by zkc 2020.04.22
 */
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroProperties {

    private String  hashAlgorithmName = "md5";// 加密方式
    private Integer hashIterations = 2;// 散列次数
    private String  loginUrl = "/index.html";// 默认的登陆页面
    private String  [] anonUrls;
    private String  logOutUrl;
    private String  [] authcUlrs;
}
