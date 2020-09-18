package com.hisoft.news.dao;

import com.hisoft.news.entity.News;

import java.sql.SQLException;
import java.util.List;

public interface NewsDao {

    /**
     * 分页查询新闻
     */

    List<News> queryPageNews(Integer tid, int currNo, int pageSize) throws SQLException;

    /**
     * 查询新闻总记录数
     */
    int queryAllNewsCount(Integer tid) throws SQLException;

    /**
     * 根据tid查找新闻列表
     */
    List<News> queryNewsByTid(int tid) throws SQLException;

    /**
     * 查找所有新闻
     */
    List<News> queryNews() throws SQLException;
    /**
     * 根据主题名称查询新闻列表
     */
    List<News> queryNewsByTname(String tname) throws SQLException;

    /**
     * 根据id获取新闻
     * @param nid
     * @return
     */
    News getNewsByNID(int nid) throws SQLException;

    /**
     * 根据新闻id删除新闻
     */
    int delNewsById(int nid) throws SQLException;

    /**
     * 新增新闻
     */
    int addNews(News news) throws SQLException;
    /**
     * 修改新闻
     */
    Boolean updateNewsByNid(News news);
    /**
     * 查找所有新闻
     */

}
