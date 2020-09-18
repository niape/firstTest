package com.hisoft.news.dao.impl;

import com.hisoft.news.dao.BaseDao;
import com.hisoft.news.dao.NewsDao;
import com.hisoft.news.entity.News;
import com.hisoft.news.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-25 09:12:46
 **/
public class NewsDaoImpl extends BaseDao implements NewsDao {
    public NewsDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<News> queryPageNews(Integer tid,int currNo,int pageSize) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;
        if(tid ==null){
            sql = "select nid,nauthor,ntitle,ncreateDate from news order by ncreateDate desc limit ?,?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,(currNo - 1) * pageSize);
            pstmt.setInt(2,pageSize);
        }else{
            sql = "select nid,nauthor,ntitle,ncreateDate from news where ntid = ? order by ncreateDate desc limit ?,?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,tid);
            pstmt.setInt(2,(currNo - 1) * pageSize);
            pstmt.setInt(3,pageSize);
        }

        ResultSet rs = pstmt.executeQuery();
        List<News> newsList = new ArrayList<>();
        News news = null;
        while (rs.next()) {
            news = new News();
            news.setNid(rs.getInt("nid"));
            news.setNauthor(rs.getString("nauthor"));
            news.setNtitle(rs.getString("ntitle"));
            news.setNcreateDate(rs.getTimestamp("ncreateDate"));
            //不需要全查出来，需要多少查多少
            newsList.add(news);
        }
        JdbcUtil.closeAll(null, pstmt, rs);
        return newsList;
    }

    @Override
    public int queryAllNewsCount(Integer tid) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;
        if(tid ==null){
            sql = "select count(1) from news";
            pstmt = connection.prepareStatement(sql);
        }else{
            sql = "select count(1) from news where ntid = ? ";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,tid);
        }
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        JdbcUtil.closeAll(null,pstmt,rs);
        return count;
    }

    @Override
    public List<News> queryNewsByTid(int tid) throws SQLException {

        PreparedStatement pstmt = connection.prepareStatement("select nid,nauthor,ntitle from news where ntid = ?");
        pstmt.setInt(1, tid);
        ResultSet rs = pstmt.executeQuery();
        List<News> newsList = new ArrayList<>();
        News news = null;
        while (rs.next()) {
            news = new News();
            news.setNid(rs.getInt("nid"));
            news.setNauthor(rs.getString("nauthor"));
            news.setNtitle(rs.getString("ntitle"));
            //不需要全查出来，需要多少查多少
            newsList.add(news);
        }
        JdbcUtil.closeAll(null, pstmt, rs);
        return newsList;
    }

    @Override
    public List<News> queryNews() throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("select nid,nauthor,ntitle from news order by nmodifyDate desc");
        ResultSet rs = pstmt.executeQuery();
        List<News> newsList = new ArrayList<>();
        News news = null;
        while (rs.next()) {
            news = new News();
            news.setNid(rs.getInt("nid"));
            news.setNauthor(rs.getString("nauthor"));
            news.setNtitle(rs.getString("ntitle"));
            //不需要全查出来，需要多少查多少
            newsList.add(news);
        }
        JdbcUtil.closeAll(null, pstmt, rs);
        return newsList;
    }

    @Override
    public List<News> queryNewsByTname(String tname) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("select nid,ntitle from news,topic where news.ntid = topic.tid and  tname = ? order by ncreateDate desc limit 5");
        pstmt.setString(1, tname);
        ResultSet rs = pstmt.executeQuery();
        List<News> newsList = new ArrayList<>();
        News news = null;
        while (rs.next()) {
            news = new News();
            news.setNid(rs.getInt("nid"));
            news.setNtitle(rs.getString("ntitle"));
            //不需要全查出来，需要多少查多少
            newsList.add(news);
        }
        JdbcUtil.closeAll(null, pstmt, rs);
        return newsList;
    }

    // 获取某条新闻
    @Override
    public News getNewsByNID(int nid) throws SQLException {
        String sql = "SELECT * FROM news, topic  where news.ntid = topic.tid and news.nid = ?"
                + " order by ncreateDate desc";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,nid);
        News news = null;
        ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                news = new News();
                news.setNid(rs.getInt("nid"));
                news.setNtid(rs.getInt("ntid"));
                news.setNtitle(rs.getString("ntitle"));
                news.setNauthor(rs.getString("nauthor"));
                news.setNcreateDate(rs.getTimestamp("ncreateDate"));
                news.setNpicPath(rs.getString("npicPath"));
                news.setNcontent(rs.getString("ncontent"));
                news.setNmodifyDate(rs.getTimestamp("nmodifyDate"));
                news.setNsummary(rs.getString("nsummary"));
                news.setNtname(rs.getString("tname"));
            }
            JdbcUtil.closeAll(null, pstmt, rs);
        return news;
    }

    @Override
    public int delNewsById(int nid) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("delete from news where nid = ?");
        pstmt.setInt(1,nid);
        int i = pstmt.executeUpdate();
        JdbcUtil.closeAll(null,pstmt,null);
        return i;
    }

    @Override
    public int addNews(News news) throws SQLException {
        String sql = "insert into news(ntid,ntitle,nauthor,ncreateDate,npicPath,ncontent,nsummary)" +
                " values(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,news.getNtid());
        pstmt.setString(2,news.getNtitle());
        pstmt.setString(3,news.getNauthor());
        pstmt.setObject(4,news.getNcreateDate());
        pstmt.setString(5,news.getNpicPath());
        pstmt.setString(6,news.getNcontent());
        pstmt.setString(7,news.getNsummary());
        int i = pstmt.executeUpdate();
        JdbcUtil.closeAll(null,pstmt,null);
        return i;
    }

    @Override
    public Boolean updateNewsByNid(News news) {
        String sql = "update news set ntid=?,ntitle=?,nauthor=?,nsummary=?,ncontent=?,nmodifyDate=sysDate() where nid = ?";
        PreparedStatement ps = null;
        Boolean bool=false;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,news.getNtid());
            ps.setString(2,news.getNtitle());
            ps.setString(3,news.getNauthor());
            ps.setString(4,news.getNsummary());
            ps.setString(5,news.getNcontent());
            ps.setInt(6,news.getNid());
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(null,ps,null);
        }
        if(i>=1)
            bool = true;
        return bool;
    }

}
