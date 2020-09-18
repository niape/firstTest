package com.hisoft.news.service;

import com.hisoft.news.entity.NewsUser;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-29 18:03:32
 **/
public interface UserService {

    /**
     * 用户登录
     */
    NewsUser toLogin(NewsUser newsUser);
}
