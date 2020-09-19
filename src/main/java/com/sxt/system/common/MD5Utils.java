package com.sxt.system.common;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

public class MD5Utils {

    //生成一个UUID
    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 密码加密
     * @param source  对啥明文加密
     * @param salt
     * @param hasInterations  散列次数
     * @return
     */
    public static String md5(String source,String salt,Integer hasInterations){
        Md5Hash hash = new Md5Hash(source,salt,hasInterations);
        return hash.toString();
    }
}
