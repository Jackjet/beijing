package com.ysbd.beijing.utils;

/**
 * Created by lcjing on 2018/8/13.
 */

public class LoginUtils {
    /**
     * 复杂（同时包含数字，字母，特殊符号）
     "^^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*_-]+$)(?![a-zA-z\\d]+$)(?![a-zA-z!@#$%^&*_-]+$)(?![\\d!@#$%^&*_-]+$)[a-zA-Z\\d!@#$%^&*_-]+$"

     简单（只包含数字或字母）
     "^(?:\\d+|[a-zA-Z]+|[!@#$%^&*]+)$"

     中级（包含字母和数字）
     "^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*]+$"
     *
     * 校验密码
     * 1、长度不小于6位
     * 2、必须以字母开头
     * 3、必须包含特殊字符
     * 4、必须包含数字
     * @param pwd
     * @return
     */
    public static String validPwd(String pwd){
        if(pwd==null||pwd.length()<1){
            return "密码不能为空";
        }
        if(pwd.length() < 6){
            return "密码长度不能小于6位";
        }
        if(pwd.length() >12){
            return "密码长度不能大于12位";
        }
        if(pwd.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,68}$")){
            return "";
        }else {
            if(pwd.length() > 68){
                return "密码过长";
            }
            return "密码必须包含字母和数字";
        }
    }

}
