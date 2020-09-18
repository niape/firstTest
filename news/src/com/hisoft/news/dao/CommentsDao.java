package com.hisoft.news.dao;

import com.hisoft.news.entity.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentsDao {
    // 通过新闻id查找评论
    public List<Comment> getCommentsByNid(int nid) throws SQLException;

    // 添加评论
    public int addComment(Comment comment) throws SQLException;

    //根据某条新闻的所有评论
    int delComment(int nid) throws  SQLException;
}