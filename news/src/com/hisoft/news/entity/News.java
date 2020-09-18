package com.hisoft.news.entity;

import java.util.Date;
import java.util.List;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-25 09:07:56
 **/
public class News {
    private Integer nid;
    private Integer ntid;
    private String ntitle;
    private String nauthor;
    private Date ncreateDate;
    private String npicPath;
    private String ncontent;
    private Date nmodifyDate;
    private String nsummary;
    private List<Comment> comments;
    private String ntname;

    public News() {
    }

    public News(Integer nid, Integer ntid, String ntitle, String nauthor, Date ncreateDate, String npicPath, String ncontent, Date nmodifyDate, String nsummary, List<Comment> comments, String ntname) {
        this.nid = nid;
        this.ntid = ntid;
        this.ntitle = ntitle;
        this.nauthor = nauthor;
        this.ncreateDate = ncreateDate;
        this.npicPath = npicPath;
        this.ncontent = ncontent;
        this.nmodifyDate = nmodifyDate;
        this.nsummary = nsummary;
        this.comments = comments;
        this.ntname = ntname;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getNtid() {
        return ntid;
    }

    public void setNtid(Integer ntid) {
        this.ntid = ntid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getNauthor() {
        return nauthor;
    }

    public void setNauthor(String nauthor) {
        this.nauthor = nauthor;
    }

    public Date getNcreateDate() {
        return ncreateDate;
    }

    public void setNcreateDate(Date ncreateDate) {
        this.ncreateDate = ncreateDate;
    }

    public String getNpicPath() {
        return npicPath;
    }

    public void setNpicPath(String npicPath) {
        this.npicPath = npicPath;
    }

    public String getNcontent() {
        return ncontent;
    }

    public void setNcontent(String ncontent) {
        this.ncontent = ncontent;
    }

    public Date getNmodifyDate() {
        return nmodifyDate;
    }

    public void setNmodifyDate(Date nmodifyDate) {
        this.nmodifyDate = nmodifyDate;
    }

    public String getNsummary() {
        return nsummary;
    }

    public void setNsummary(String nsummary) {
        this.nsummary = nsummary;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



    public String getNtname() {
        return ntname;
    }

    public void setNtname(String ntname) {
        this.ntname = ntname;
    }
}
