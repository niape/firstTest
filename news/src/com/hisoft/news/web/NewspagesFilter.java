package com.hisoft.news.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "NewspagesFilter",urlPatterns = {"/newspages/*"})
public class NewspagesFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String user = (String)request.getSession().getAttribute("user");
        if(user!=null)
            chain.doFilter(req, resp);
        else{
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            System.out.println("basepath"+basePath);
            response.sendRedirect(basePath);

        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
