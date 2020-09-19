package com.sxt.system.common;

import com.sxt.system.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户的登录信息保存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements Serializable {
    private User user;
    private List<String> roles;
    private List<String> permission;
}
