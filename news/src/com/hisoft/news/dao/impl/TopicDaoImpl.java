package com.hisoft.news.dao.impl;

import com.hisoft.news.dao.BaseDao;
import com.hisoft.news.dao.TopicDao;
import com.hisoft.news.entity.Topic;
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
 * @create: 2020-07-25 09:48:04
 **/
public class TopicDaoImpl extends BaseDao implements TopicDao {
    public TopicDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Topic findTopicByName(String tname) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("select * from topic where tname = ?");
        pstmt.setString(1, tname);
        ResultSet rs = pstmt.executeQuery();
        Topic topic = null;
        if(rs.next()){
            topic = new Topic(rs.getInt("tid"),rs.getString("tname"));
        }
        JdbcUtil.closeAll(null,pstmt,rs);
        return topic;
    }

    @Override
    public int addTopic(Topic topic) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("insert into topic(tname) values(?)");
        pstmt.setString(1, topic.getTname());
        int update = pstmt.executeUpdate();
        JdbcUtil.closeAll(null,pstmt,null);
        return update;
    }

    @Override
    public int updateTopic(Topic topic) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("update topic set tname = ? where tid = ?");
        pstmt.setString(1, topic.getTname());
        pstmt.setInt(2, topic.getTid());
        int update = pstmt.executeUpdate();
        JdbcUtil.closeAll(null,pstmt,null);
        return update;
    }

    @Override
    public List<Topic> findAllTopics() throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("select * from topic");
        ResultSet rs = pstmt.executeQuery();
        List<Topic> topicList = new ArrayList<>();
        Topic topic = null;
        while (rs.next()){
            topic = new Topic(rs.getInt(1),rs.getString(2));
            topicList.add(topic);
        }
        JdbcUtil.closeAll(null,pstmt,rs);
        return topicList;
    }

    @Override
    public Topic findTopicByTid(int tid) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("select * from topic where tid = ?");
        pstmt.setInt(1,tid);
        ResultSet rs = pstmt.executeQuery();
        Topic topic = null;
        if(rs.next()){
            topic = new Topic(rs.getInt(1),rs.getString(2));
        }
        JdbcUtil.closeAll(null,pstmt,rs);
        return topic;
    }

    @Override
    public int deleteTopic(int tid) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("delete from topic where tid = ?");
        pstmt.setInt(1, tid);
        int update = pstmt.executeUpdate();
        JdbcUtil.closeAll(null,pstmt,null);
        return update;
    }
}
