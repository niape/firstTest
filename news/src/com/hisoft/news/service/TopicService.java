package com.hisoft.news.service;

import com.hisoft.news.entity.Topic;

import java.util.List;

/**
 * @program: MyWebProject
 * @description: 主题的业务层接口
 * @author: wlg
 * @create: 2020-07-29 17:13:35
 **/
public interface TopicService {

    /**
     * 删除主题
     */
    int delTopicById(int tid);

    /**
     * 新增主题
     */
    int addTopic(Topic topic);

    /**
     * 修改主题
     */
    int updateTopic(Topic topic);

    /**
     * 查询所有主题
     */
    List<Topic> findAllTopics();

    /**
     * 根据tid查询某主题
     */
    Topic findTopicByTid(int tid);

}
