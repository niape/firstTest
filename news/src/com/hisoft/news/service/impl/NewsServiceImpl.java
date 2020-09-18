package com.hisoft.news.service.impl;

import com.hisoft.news.dao.CommentsDao;
import com.hisoft.news.dao.NewsDao;
import com.hisoft.news.dao.TopicDao;
import com.hisoft.news.dao.impl.CommentsDaoImpl;
import com.hisoft.news.dao.impl.NewsDaoImpl;
import com.hisoft.news.dao.impl.TopicDaoImpl;
import com.hisoft.news.entity.News;
import com.hisoft.news.entity.Topic;
import com.hisoft.news.service.NewsService;
import com.hisoft.news.util.JdbcUtil;
import com.hisoft.news.util.Page;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-29 17:58:01
 **/
public class NewsServiceImpl implements NewsService {
    private Connection connection;
    @Override
    public Page queryAllNews(Integer tid,int currPageNo) {
        connection  = JdbcUtil.getConnection();
        NewsDao newsDao = new NewsDaoImpl(connection);
        List<News> newsList = null;
        Page page = new Page();
        try {
            //查记录总数
            int totalCount = newsDao.queryAllNewsCount(tid);
            page.setTotalCount(totalCount);

            //判断是否在合法范围
            if(currPageNo < 1){
                currPageNo = 1;
            }else if(currPageNo > page.getTotalPageCount()){
                currPageNo = page.getTotalPageCount();
            }
            page.setCurrPageNo(currPageNo);
            newsList = newsDao.queryPageNews(tid,currPageNo,page.getPageSize());
            page.setNewsList(newsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(connection,null,null);
        }
        return page;
    }

    @Override
    public Map<String, Object> queryIndexList(String gn, String gj, String yl) {
        connection  = JdbcUtil.getConnection();
        NewsDao newsDao = new NewsDaoImpl(connection);
        TopicDao topicDao = new TopicDaoImpl(connection);
        Map<String, Object> map = new HashMap<>();
        try {
            List<News> list1 = newsDao.queryNewsByTname(gn);
            List<News> list2 = newsDao.queryNewsByTname(gj);
            List<News> list3 = newsDao.queryNewsByTname(yl);
            List<Topic> topicList = topicDao.findAllTopics();

            //分页，封装page数据
            //查记录总数
            map.put("list1", list1);
            map.put("list2", list2);
            map.put("list3", list3);
            map.put("topicList", topicList);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtil.closeAll(connection,null,null);
        }
        return map;
    }

    @Override
    public List<News> queryNews() {
        connection  = JdbcUtil.getConnection();
        NewsDao newsDao = new NewsDaoImpl(connection);
        List<News> list = null;
        try {
            list = newsDao.queryNews();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtil.closeAll(connection,null,null);
        }
        return list;
    }

    @Override
    public News getNewsByNid(int nid) {
        connection  = JdbcUtil.getConnection();
        NewsDao newsDao = new NewsDaoImpl(connection);
        CommentsDao commentsDao = new CommentsDaoImpl(connection);
        News news = null;
        try {
            news = newsDao.getNewsByNID(nid);
            news.setComments(commentsDao.getCommentsByNid(nid));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtil.closeAll(connection,null,null);
        }
        return news;
    }

    @Override
    public int delNewsById(int nid) {
        connection  = JdbcUtil.getConnection();
        NewsDao newsDao = new NewsDaoImpl(connection);
        CommentsDao commentsDao = new CommentsDaoImpl(connection);
        int result = -1;
        try {
            connection.setAutoCommit(false);
            commentsDao.delComment(nid);
            newsDao.delNewsById(nid);
            result = 1;
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
    public int addNews(News news) {
        connection  = JdbcUtil.getConnection();
        NewsDao newsDao = new NewsDaoImpl(connection);
        int result = -1;
        try {
            connection.setAutoCommit(false);
            result = newsDao.addNews(news);
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
    public Boolean updateNewsByNid(News news) {
        Connection conn = JdbcUtil.getConnection();
        Boolean bool = new NewsDaoImpl(connection).updateNewsByNid(news);
        JdbcUtil.closeAll(connection,null,null);
        return bool;
    }
}
