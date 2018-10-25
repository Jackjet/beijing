package com.ysbd.beijing.ui.bean;

/**
 * Created by lcjing on 2018/8/20.
 */

public class LoginResBean {

    /**
     * success : true
     * userid : {BFA7820D-FFFF-FFFF-F16B-BA46000006FA}
     * username : 王栋
     * departmentguid : {BFA7820D-FFFF-FFFF-F16C-479000000025}
     * departmentname : 信息处（信息中心）
     * employee_jobtitles : 副主任
     */

    private String success;
    private String userid;
    private String username;
    private String departmentguid;
    private String departmentname;
    private String employee_jobtitles;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartmentguid() {
        return departmentguid;
    }

    public void setDepartmentguid(String departmentguid) {
        this.departmentguid = departmentguid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getEmployee_jobtitles() {
        return employee_jobtitles;
    }

    public void setEmployee_jobtitles(String employee_jobtitles) {
        this.employee_jobtitles = employee_jobtitles;
    }
}
