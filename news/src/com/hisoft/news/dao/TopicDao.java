package com.hisoft.news.dao;

import com.hisoft.news.entity.Topic;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-25 09:44:25
 **/
public interface TopicDao {

    /**
     * 根据主题名称查找主题
     */
    Topic findTopicByName(String tname) throws SQLException;

    /**
     * 新增主题
     */
    int addTopic(Topic topic) throws SQLException;

    /**
     * 修改主题
     */
    int updateTopic(Topic topic) throws SQLException;

    /**
     * 查询所有主题
     */
    List<Topic> findAllTopics() throws SQLException;

    /**
     * 根据tid查询某主题
     */
    Topic findTopicByTid(int tid) throws SQLException;

    /**
     * 删除主题
     * @param tid
     * @return
     */
    int deleteTopic(int tid) throws SQLException;
}
