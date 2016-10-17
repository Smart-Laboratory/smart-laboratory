package com.smart.model.util;

/**
 * Created by yuzh on 2016/10/8.
 * 医院用户信息
 */
public class HospitalUser {

    private String id;      //HIS ID
    private String workid;  //工号
    private String name;    //姓名
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
