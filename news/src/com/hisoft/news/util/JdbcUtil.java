package com.hisoft.news.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-07-24 16:28:50
 **/
public class JdbcUtil {
    private static DataSource dataSource;

    static {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/news");
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("connection opened:" + conn);
        return conn;
    }

    /**
     * 释放资源
     */
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
//                System.out.println("connection closed:" + conn);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
