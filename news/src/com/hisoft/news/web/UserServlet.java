package com.hisoft.news.web;

import com.hisoft.news.entity.NewsUser;
import com.hisoft.news.service.UserService;
import com.hisoft.news.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet",urlPatterns = "/user.html")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        if ("login".equals(action)){
            String uname = request.getParameter("uname");
            String upwd = request.getParameter("upwd");

            //服务器端验证
            if(uname.equals("")){
                request.setAttribute("message","用户名不能为空");
                request.getRequestDispatcher("index.jsp").forward(request,response);
                return;
            }
            if(upwd.equals("")){
                request.setAttribute("message","密码不能为空");
                request.getRequestDispatcher("index.jsp").forward(request,response);
                return;
            }

            NewsUser user = new NewsUser(uname,upwd);
            UserService userService = new UserServiceImpl();
            NewsUser findUser = userService.toLogin(user);
            if(findUser != null){
                request.getSession().setAttribute("user",uname);
            }
            request.getRequestDispatcher("newspages/admin.jsp").forward(request,response);
//            request.getRequestDispatcher("news.html?action=adminJson").forward(request,response);

        }else if("logout".equals(action)){
            request.getSession().invalidate();
            out.print("<script>window.top.location=\"index.jsp\";</script>");
        }
    }
}
