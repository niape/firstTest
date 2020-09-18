package com.hisoft.news.service.impl;

import com.hisoft.news.dao.CommentsDao;
import com.hisoft.news.dao.impl.CommentsDaoImpl;
import com.hisoft.news.entity.Comment;
import com.hisoft.news.service.CommentsService;
import com.hisoft.news.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName CommentsServiceImpl
 * @Description TODO
 * @Author Admin
 * @Date 2020/7/29 20:58
 * @Version 1.0
 */
public class CommentsServiceImpl implements CommentsService {

    private Connection connection;

    @Override
    public int addComment(Comment comment) {
        connection = JdbcUtil.getConnection();
        CommentsDao commentsDao = new CommentsDaoImpl(connection);
        int result = -1;
        try {
            connection.setAutoCommit(false);
            result = commentsDao.addComment(comment);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            JdbcUtil.closeAll(connection,null,null);
        }

        return result;
    }
}
