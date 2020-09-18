package com.hisoft.news.dao.impl;

import com.hisoft.news.dao.BaseDao;
import com.hisoft.news.dao.CommentsDao;
import com.hisoft.news.entity.Comment;
import com.hisoft.news.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentsDaoImpl extends BaseDao implements CommentsDao {
    public CommentsDaoImpl(Connection connection) {
        super(connection);
    }

    // 通过nid查找评论
    public List<Comment> getCommentsByNid(int nid) throws SQLException {
        List<Comment> list = new ArrayList<>();
        String sql = "select * from comments where cnid = ? order by cdate desc";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, nid);
        ResultSet rs = pstmt.executeQuery();
        Comment comment = null;
        while (rs.next()) {
            comment = new Comment();
            comment.setCid(rs.getInt("cid"));
            comment.setCnid(rs.getInt("cnid"));
            comment.setCauthor(rs.getString("cauthor"));
            comment.setCip(rs.getString("cip"));
            comment.setCcontent(rs.getString("ccontent"));
            comment.setCdate(rs.getTimestamp("cdate"));
            list.add(comment);
        }
        JdbcUtil.closeAll(null, pstmt, rs);
        return list;
    }

    // 添加评论
    public int addComment(Comment comment) throws SQLException {
        String sql = "insert into comments(cnid, ccontent, cdate,cip,cauthor) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, comment.getCnid());
        pstmt.setString(2, comment.getCcontent());
        pstmt.setObject(3, comment.getCdate());
        pstmt.setString(4, comment.getCip());
        pstmt.setString(5, comment.getCauthor());
        int result = pstmt.executeUpdate();
        JdbcUtil.closeAll(null, pstmt, null);
        return result;
    }

    @Override
    public int delComment(int nid) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("delete from comments where cnid = ?");
        pstmt.setInt(1,nid);
        int i = pstmt.executeUpdate();
        JdbcUtil.closeAll(null,pstmt,null);
        return i;
    }
}
