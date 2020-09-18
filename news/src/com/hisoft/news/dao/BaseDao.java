package com.hisoft.news.dao;

import java.sql.Connection;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-29 17:09:36
 **/
public class BaseDao {

    protected Connection connection;

    public BaseDao(Connection connection) {
        this.connection = connection;
    }
}
