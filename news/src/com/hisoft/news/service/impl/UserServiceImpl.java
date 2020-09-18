package com.hisoft.news.service.impl;

import com.hisoft.news.dao.UserDao;
import com.hisoft.news.dao.impl.UserDaoImpl;
import com.hisoft.news.entity.NewsUser;
import com.hisoft.news.service.UserService;
import com.hisoft.news.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-29 18:04:59
 **/
public class UserServiceImpl implements UserService {
    private Connection connection;
    @Override
    public NewsUser toLogin(NewsUser newsUser) {
        connection = JdbcUtil.getConnection();
        UserDao userDao = new UserDaoImpl(connection);
        NewsUser user = null;
        try {
          user =  userDao.findNewsUser(newsUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtil.closeAll(connection,null,null);
        }
        return user;
    }
}
