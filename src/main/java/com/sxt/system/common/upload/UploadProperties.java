package com.sxt.system.common.upload;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * create by zkc 2020.09.12
 * 文件上传fastdfs配置类
 */
@ConfigurationProperties(prefix = "upload")
@Data
public class UploadProperties {

    private  String baseUrl;
    private List<String> allowTypes;
}
