package com.hisoft.news.service;

import com.hisoft.news.entity.News;
import com.hisoft.news.util.Page;

import java.util.List;
import java.util.Map;

/**
 * @program: MyWebProject
 * @description: 新闻业务层接口
 * @author: wlg
 * @create: 2020-07-29 17:50:46
 **/
public interface NewsService {

    /**
     * 管理员分页查询所有新闻
     * 不需要根据主题id查询，tid为null
     */

    Page queryAllNews(Integer tid, int currPageNo);

    /**
     * 查询首页数据（国内，国际，娱乐新闻，主题，中间新闻）
     */
    Map<String,Object> queryIndexList(String gn, String gj, String yl);

    /**
     * 查询所有新闻
     */
    List<News> queryNews();

    /**
     * 根据id获取新闻
     *
     * @param nid
     * @return
     */
    News getNewsByNid(int nid);

    /**
     * 删除新闻
     */
    int delNewsById(int nid);

    /**
     * 新增新闻
     */
    int addNews(News news);

    /**
     * 修改新闻
     */
    Boolean updateNewsByNid(News news);
}
