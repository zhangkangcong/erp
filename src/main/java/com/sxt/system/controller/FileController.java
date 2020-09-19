package com.sxt.system.controller;

import com.sxt.system.common.ActiveUser;
import com.sxt.system.common.DataGridView;
import com.sxt.system.common.upload.UploadProperties;
import com.sxt.system.common.upload.UploadService;
import com.sxt.system.domain.User;
import com.sxt.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FileController
 * @Description TODO
 * @Author zkc
 * @Date 2020/9/12 22:51
 * @Version 1.0
 */
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UploadProperties properties;
    @Autowired
    private UserService userService;

    @RequestMapping("uploadFile")
    public Object uploadFile(MultipartFile file){

        String path = this.uploadService.uploadImage(file);
        String baseUrl = properties.getBaseUrl();
        Map<String,String> map = new HashMap<>();
        map.put("src",baseUrl+path);
        //更新数据库
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        User user = activeUser.getUser();
        user.setImgpath(path);
        this.userService.updateUser(user);
        return new DataGridView(map);
    }
}
