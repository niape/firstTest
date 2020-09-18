package com.hisoft.news.dao.impl;

import com.hisoft.news.dao.BaseDao;
import com.hisoft.news.dao.UserDao;
import com.hisoft.news.entity.NewsUser;
import com.hisoft.news.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-24 16:27:38
 **/
public class UserDaoImpl extends BaseDao implements UserDao {
    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public NewsUser findNewsUser(NewsUser newsUser) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("select * from news_users where uname = ? and upwd = ?");
        pstmt.setString(1, newsUser.getUname());
        pstmt.setString(2, newsUser.getUpwd());
        ResultSet rs = pstmt.executeQuery();
        NewsUser newsUser1 = null;
        if(rs.next()){
            newsUser1 = new NewsUser(rs.getInt("uid"),rs.getString("uname"),rs.getString("upwd"));
        }
        JdbcUtil.closeAll(null,pstmt,rs);
        return newsUser1;
    }
}
