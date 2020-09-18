package com.hisoft.news.entity;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-25 09:45:08
 **/
public class Topic {
    private Integer tid;
    private String tname;

    public Topic() {
    }

    public Topic(Integer tid, String tname) {
        this.tid = tid;
        this.tname = tname;
    }
    public Topic(String tname) {
        this.tname = tname;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                '}';
    }
}
