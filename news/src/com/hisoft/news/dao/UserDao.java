package com.hisoft.news.dao;

import com.hisoft.news.entity.NewsUser;

import java.sql.SQLException;

public interface UserDao {


    /*查找用户*/
    NewsUser findNewsUser(NewsUser newsUser) throws SQLException;
}
