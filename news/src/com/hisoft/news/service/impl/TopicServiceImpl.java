package com.hisoft.news.service.impl;

import com.hisoft.news.dao.CommentsDao;
import com.hisoft.news.dao.NewsDao;
import com.hisoft.news.dao.TopicDao;
import com.hisoft.news.dao.impl.CommentsDaoImpl;
import com.hisoft.news.dao.impl.NewsDaoImpl;
import com.hisoft.news.dao.impl.TopicDaoImpl;
import com.hisoft.news.entity.News;
import com.hisoft.news.entity.Topic;
import com.hisoft.news.service.TopicService;
import com.hisoft.news.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-29 17:15:00
 **/
public class TopicServiceImpl implements TopicService {
    private Connection connection;
    @Override
    public int delTopicById(int tid) {
        connection  = JdbcUtil.getConnection();
        NewsDao newsDao = new NewsDaoImpl(connection);
        TopicDao topicDao = new TopicDaoImpl(connection);
        int result = -1;
        try {
            connection.setAutoCommit(false);
            List<News> newsList = newsDao.queryNewsByTid(tid);
            if(!newsList.isEmpty()){
                return result;
            }
            result = topicDao.deleteTopic(tid);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result = -2;
        }finally {
            JdbcUtil.closeAll(connection,null,null);
        }
        return result;
    }


    @Override
    public int addTopic(Topic topic) {
        connection  = JdbcUtil.getConnection();
        TopicDao topicDao = new TopicDaoImpl(connection);
        int result = 0;
        try {
            connection.setAutoCommit(false);
            if(topicDao.findTopicByName(topic.getTname()) != null){
                result = -1;
            }else{
                result = topicDao.addTopic(topic);
            }
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

    @Override
    public int updateTopic(Topic topic) {
        connection  = JdbcUtil.getConnection();
        TopicDao topicDao = new TopicDaoImpl(connection);
        int result = 0;
        try {
            connection.setAutoCommit(false);
            if(topicDao.findTopicByName(topic.getTname()) != null){
                result = -1;
            }else{
                result = topicDao.updateTopic(topic);
            }
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

    @Override
    public List<Topic> findAllTopics() {
        connection  = JdbcUtil.getConnection();
        TopicDao topicDao = new TopicDaoImpl(connection);
        List<Topic> topicList = null;
        try {
            topicList = topicDao.findAllTopics();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(connection,null,null);
        }
        return topicList;
    }

    @Override
    public Topic findTopicByTid(int tid) {
        connection  = JdbcUtil.getConnection();
        TopicDao topicDao = new TopicDaoImpl(connection);
        Topic topic = null;
        try {
            topic = topicDao.findTopicByTid(tid);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(connection,null,null);
        }
        return topic;
    }
}
