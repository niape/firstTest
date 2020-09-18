package com.hisoft.news.web;

import com.hisoft.news.entity.Topic;
import com.hisoft.news.service.TopicService;
import com.hisoft.news.service.impl.TopicServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.List;

@WebListener()
public class TopicContextListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TopicService topicService = new TopicServiceImpl();
        List<Topic> topiclist = topicService.findAllTopics();
        sce.getServletContext().setAttribute("topiclist",topiclist);
    }
}
