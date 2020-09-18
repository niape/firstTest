package com.hisoft.news.entity;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-24 16:23:32
 **/
public class NewsUser {
    private Integer uid;
    private String uname;
    private String upwd;

    public NewsUser() {
    }

    public NewsUser(Integer uid, String uname, String upwd) {
        this.uid = uid;
        this.uname = uname;
        this.upwd = upwd;
    }

    public NewsUser(String uname, String upwd) {
        this.uname = uname;
        this.upwd = upwd;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
}
