package com.hisoft.news.web;

import com.hisoft.news.entity.Topic;
import com.hisoft.news.service.TopicService;
import com.hisoft.news.service.impl.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @program: MyWebProject
 * @description:
 * @author: wlg
 * @create: 2020-08-03 11:45:44
 **/
public class TopicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        TopicService topicService = new TopicServiceImpl();
        if ("addtopic".equals(action)) {//添加主题
            String tname = request.getParameter("tname").trim();//去掉前后空格
            if (tname.equals("")) {
                out.print("{\"message\":\"主题不能为空\"}");
//                request.setAttribute("message", "主题不能为空！！");
//                request.getRequestDispatcher("newspages/topic_add.jsp").forward(request, response);
                return;
            }

            Topic topic = new Topic(tname);
            int result = topicService.addTopic(topic);

            if (result == -1) {
                out.print("{\"message\":\"该主题已存在，再想个名字吧\"}");
                return;
//                request.setAttribute("message", "该主题已存在，再想个名字吧！");
//                request.getRequestDispatcher("newspages/topic_add.jsp").forward(request, response);
            } else {
                if (result == 1) {
                    List<Topic> topiclist = topicService.findAllTopics();
                    request.getServletContext().setAttribute("topiclist", topiclist);
                    out.print("{\"message\":\"success\"}");
                    return;
//                    out.print("<script>alert('添加主题成功！');location.href='topic.html?action=topiclist';</script>");
                } else {
                    out.print("{\"message\":\"添加主题失败，请联系管理员！\"}");
                    return;
//                    request.setAttribute("message", "添加主题失败，请联系管理员！");
//                    request.getRequestDispatcher("newspages/topic_add.jsp").forward(request, response);
                }
            }
        } else if ("topiclist".equals(action)) {//显示所有主题
            //查询所有主题，存入request作用域，转发到topic_list.jsp
            List<Topic> allTopics = topicService.findAllTopics();
            StringBuffer sb = new StringBuffer();
            for (Topic topic : allTopics) {
                sb.append("<li>"+topic.getTname()+"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"topic.html?action=tomodifu&tid="+topic.getTid()+"\">修改</a>"+
                        "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:del("+topic.getTid()+")\">删除</a>"+"</li>");
            }
            out.print(sb.toString());
//            request.setAttribute("topiclist", allTopics);
//            if (request.getParameter("from") != null) {
//                request.getRequestDispatcher("newspages/news_add.jsp").forward(request, response);
//                return;
//            }
//            request.getRequestDispatcher("newspages/topic_list.jsp").forward(request, response);
        } else if ("tomodify".equals(action)) {//打开修改页面，准备修改
            //获取要修改的记录的信息
            String tidstr = request.getParameter("tid");
            int tid = (tidstr == null ? -1 : Integer.parseInt(tidstr));
            Topic topic = null;
            if (tid != -1) {
                topic = topicService.findTopicByTid(tid);
            }
            request.setAttribute("topic", topic);
            request.getRequestDispatcher("newspages/topic_modify.jsp").forward(request, response);
        } else if ("updatetopic".equals(action)) {//修改操作
            String tname = request.getParameter("tname");
            int tid = Integer.parseInt(request.getParameter("tid"));
            Topic topic = new Topic(tid, tname);
            int updateTopic = topicService.updateTopic(topic);
            if (updateTopic == -1) {
                request.setAttribute("message", "主题已存在，不能修改");
                request.getRequestDispatcher("newspages/topic_modify.jsp").forward(request, response);
            }

            if (updateTopic == 1) {
                List<Topic> topiclist = topicService.findAllTopics();
                request.getServletContext().setAttribute("topiclist", topiclist);
                out.print("<script>alert('修改成功');location.href='topic.html?action=topiclist'</script>");
            } else {
                request.setAttribute("message", "修改失败，联系管理员");
                request.getRequestDispatcher("newspages/topic_modify.jsp").forward(request, response);
            }
        } else if ("topicJson".equals(action)) {
            /*StringBuffer stringBuffer = new StringBuffer("[");
            List<Topic> topicList = topicService.findAllTopics();
            Topic topic = null;
            for (int i = 0; i < topicList.size(); i++) {
                topic = topicList.get(i);
                if(i<topicList.size()-1){
                    stringBuffer.append("{\"tid\":\""+topic.getTid()+"\",\"tname\":\""+topic.getTname()+"\"},");
                }else{
                    stringBuffer.append("{\"tid\":\""+topic.getTid()+"\",\"tname\":\""+topic.getTname()+"\"}]");
                }
            }
            out.print(stringBuffer.toString());*/
            StringBuffer stringBuffer = new StringBuffer("");
            List<Topic> topicList = topicService.findAllTopics();
            Topic topic = null;
            for (int i = 0; i < topicList.size(); i++) {
                topic = topicList.get(i);
                stringBuffer.append("<li>" + topic.getTname() +
                        "&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"topic.html?action=tomodify&tid=" + topic.getTid() + "\">修改</a>"
                        + "&nbsp;&nbsp;&nbsp;&nbsp; " +
                        "<a href=\"javascript:del(" + topic.getTid() + ")\">删除</a>" +
                        "</li>");
            }
            out.print(stringBuffer.toString());
        }else if("delete".equals(action)){
            //删除操作
            int tid = Integer.parseInt(request.getParameter("tid"));
            //判断该主题下有没有新闻
            int result = topicService.delTopicById(tid);
            if(result == -1){
                out.print("<script>alert('该主题下有新闻，不能删除');location.href='topic.html?action=topiclist'</script>");
            }
            if(result == 1){
                List<Topic> topiclist = topicService.findAllTopics();
                request.getServletContext().setAttribute("topiclist",topiclist);
                out.print("<script>alert('删除成功');location.href='topic.html?action=topiclist'</script>");
            }
            if(result == -2){
                out.print("<script>alert('删除失败,联系管理员');</script>");
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
