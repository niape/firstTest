package com.hisoft.news.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hisoft.news.entity.Comment;
import com.hisoft.news.entity.News;
import com.hisoft.news.entity.Topic;
import com.hisoft.news.service.CommentsService;
import com.hisoft.news.service.NewsService;
import com.hisoft.news.service.TopicService;
import com.hisoft.news.service.impl.CommentsServiceImpl;
import com.hisoft.news.service.impl.NewsServiceImpl;
import com.hisoft.news.service.impl.TopicServiceImpl;
import com.hisoft.news.util.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "NewsServlet",urlPatterns = "/news.html")
public class NewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        NewsService newsService = new NewsServiceImpl();
        CommentsService commentsService = new CommentsServiceImpl();
        if ("index".equals(action)) {
            //查询国内-list1，国际-list2，娱乐-list3新闻，所有主题-topiclist，所有新闻-newslist，某个主题下的新闻-newslist
            //存入作用域，转发到首页
            Map<String, Object> map = newsService.queryIndexList("国内", "国际", "娱乐");

            request.setAttribute("map", map);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } else if("newspage".equals(action)){
            String tidstr = request.getParameter("tid");
            Integer tid = ((tidstr == null || tidstr.equals("null") || tidstr == "") ? null : Integer.parseInt(tidstr));

            String currPageNoStr = request.getParameter("currPageNo");
            if (currPageNoStr == null) {
                currPageNoStr = "1";
            }
            int currPageNo = Integer.parseInt(currPageNoStr);
            Page page = newsService.queryAllNews(tid,currPageNo);
            //转为Json字符串
            String s = JSON.toJSONStringWithDateFormat(page, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteNullStringAsEmpty);

            out.print("[{\"tid\":\""+tid+"\"},"+s+"]");
        }else if ("addComment".equals(action)) {//添加评论
            String cauthor = request.getParameter("cauthor");
            String cnid = request.getParameter("nid");
            String cip = request.getParameter("cip");
            String ccontent = request.getParameter("ccontent");
            Comment comment = new Comment();
            comment.setCnid(Integer.parseInt(cnid));
            comment.setCcontent(ccontent);
            comment.setCdate(new Date());
            comment.setCip(cip);
            comment.setCauthor(cauthor);

            if (commentsService.addComment(comment) > 0) {
                /*out.println("<script type=\"text/javascript\">" +
                        "                        alert(\"评论成功，点击确认返回原来页面\");" +
                        "                location.href = \"news.html?action=readNew&nid="+cnid+"\";" +
                        "</script>");*/
                out.print("{\"result\":\"success\",\"cdate\":\""+comment.getCdate()+"\"}");
            } else {
                /*out.println("<script type=\"text/javascript\">" +
                        "                        alert(\"评论添加失败！请联系管理员查找原因！点击确认返回原来页面\");" +
                        "                location.href = \"news.html?action=readNew&nid="+cnid+"\";" +
                        "</script>");*/
                out.print("{\"result\":\"error\"}");
            }
        } else if ("readNew".equals(action)) {//读取某条新闻
            String nid = request.getParameter("nid");
            News news = newsService.getNewsByNid(Integer.parseInt(nid));
            Map<String, Object> map = newsService.queryIndexList("国内", "国际", "娱乐");
//            String s = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty);
//            out.print(s);
            request.setAttribute("news", news);
            request.setAttribute("map", map);//左侧固定栏新闻
            request.getRequestDispatcher("newspages/news_read.jsp")
                    .forward(request, response);

        } else if ("delnews".equals(action)) {//删除新闻
            Integer nid = Integer.parseInt(request.getParameter("nid"));
            int i = newsService.delNewsById(nid);
            if (i == 1) {
                out.print("<script>alert('删除成功！');location.href='newspages/admin.jsp';</script>");
            } else {
                out.print("<script>alert('删除失败！请联系管理员');");
            }
        }else if("adminJson".equals(action)){
//            out.print("hello");
            /*在页面中封装html,在服务器中使用json*/
            /*List<News> newslist = newsService.queryNews();
            StringBuffer stringBuffer = new StringBuffer("[");
            News news = null;
            for (int i = 0; i < newslist.size(); i++) {
                news = newslist.get(i);
                if(i<newslist.size()-1){
                    stringBuffer.append("{\"nid\":\""+news.getNid()+"\",\"ntitle\":\""+news.getNtitle().replace("\"","&quot")+"\",\"nauthor\":\""+news.getNauthor()+"\"},");
                }else{
                    stringBuffer.append("{\"nid\":\""+news.getNid()+"\",\"ntitle\":\""+news.getNtitle().replace("\"","&quot")+"\",\"nauthor\":\""+news.getNauthor()+"\"}]");
                }
            }
            out.print(stringBuffer.toString());*/
            /*在服务器中封装html，不使用json*/
            /*List<News> newslist = newsService.queryNews();
            StringBuffer stringBuffer = new StringBuffer("");
            News news = null;
            for (int i = 0; i < newslist.size(); i++) {
                news = newslist.get(i);
                    stringBuffer.append("<li>"+news.getNtitle()+"<span> 作者："+
                                    news.getNauthor()+
                            " &#160;&#160;&#160;&#160;\n"+
                            " <a href='news.html?action=tomodify&nid="+news.getNtitle()+"'>修改</a> &#160;&#160;&#160;&#160;\n"+
                            " <a href='javascript:del("+news.getNid()+")'>删除</a> </span>"+"</li>\n");
            }
            out.print(stringBuffer.toString());*/
            /*FastJSON实现，将java对象转化为JSON字符串*/
            List<News> newslist = newsService.queryNews();
            String toJSONString = JSON.toJSONString(newslist,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.QuoteFieldNames,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero);
            out.print(toJSONString);
        }else if("topicJson".equals(action)){
            /*TopicService topicService = new TopicServiceImpl();
            StringBuffer stringBuffer = new StringBuffer("[");
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
            TopicService topicService = new TopicServiceImpl();
            List<Topic> topicList = topicService.findAllTopics();
            Topic topic = null;
            stringBuffer.append("<option value='" + 1 + "'>选择</option>");
            for (int i = 0; i < topicList.size(); i++) {
                topic = topicList.get(i);
                    stringBuffer.append("<option value='" + topic.getTid() + "'>" + topic.getTname() + "</option>");
            }
            out.print(stringBuffer.toString());
        }else if ("backnews".equals(action)) {
            String currPageNoStr = request.getParameter("currPageNo");
            if (currPageNoStr == null) {
                currPageNoStr = "1";
            }
            int currPageNo = Integer.parseInt(currPageNoStr);
            Page page1 = newsService.queryAllNews(null, currPageNo);
            request.setAttribute("page", page1);
            request.getRequestDispatcher("newspages/admin.jsp").forward(request, response);
        }else if("addnews".equals(action)){
            String fieldName = "";  //表单字段元素的name属性值
            //请求信息中的内容是否是multipart类型
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            //上传文件的存储路径（服务器文件系统上的绝对文件路径）
            String uploadFilePath = request.getServletContext().getRealPath("upload/");
            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                //设置缓冲区大小和临时目录
                factory.setSizeThreshold(1024 * 10);
                factory.setRepository(new File("D:\\test\\fileupload_temp"));
                ServletFileUpload upload = new ServletFileUpload(factory);
                //设置上传文件的大小,不超过500KB
                upload.setSizeMax(1024 * 1024 * 5);
                //封装news对象
                News news = new News();
                try {
                    //解析form表单中所有文件
                    List<FileItem> items = upload.parseRequest(request);
                    Iterator<FileItem> iter = items.iterator();
                    while (iter.hasNext()) {   //依次处理每个文件

                        FileItem item = iter.next();

                        if (item.isFormField()) {  //普通表单字段
                            fieldName = item.getFieldName();   //表单字段的name属性值
                            switch (fieldName){
                                case "ntid":
                                    news.setNtid(Integer.parseInt(item.getString("utf-8")));
                                    break;
                                case "ntitle":
                                    news.setNtitle(item.getString("utf-8"));
                                    break;
                                case "nauthor":
                                    news.setNauthor(item.getString("utf-8"));
                                    break;
                                case "ncontent":
                                    news.setNcontent(item.getString("utf-8"));
                                    break;
                                case "nsummary":
                                    news.setNsummary(item.getString("utf-8"));
                                    break;
                            }
                        } else {
                            //文件表单字段
                            String fileName = item.getName();//上传文件名
                            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
                            //bmp jbg gif
                            List<String> list = Arrays.asList("gif", "jpg", "jpeg");
                            if (!list.contains(ext)) {
                                out.print("<script>alert('上传图片失败，上传的文件类型只能是 bmp,jpg,gif类型');location.href='newspages/news_add.jsp'</script>");
                                return;
                            }
                            if (fileName != null && !fileName.equals("")) {
                                File fullFile = new File(fileName);
                                File saveFile = new File(uploadFilePath, fullFile.getName());
//                                System.out.println(saveFile.getAbsolutePath());
                                item.write(saveFile);
                                //上传的图片的路径保存到数据库
                                news.setNpicPath(saveFile.getAbsolutePath());
                                out.print("<script>alert('上传成功后的文件名是：" + fileName+"')</script>");
                            }
                        }
                    }
                } catch (FileUploadBase.SizeLimitExceededException e) {
                    out.print("<script>alert('上传失败！上传的最大文件限制是：" + upload.getSizeMax()+"');location.href='newspages/news_add.jsp'</script>");
                    return;
                } catch (FileUploadException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                news.setNcreateDate(new Date());
                int i = newsService.addNews(news);
                if(i == 1){
                    out.print("<script>alert('新闻添加成功！');location.href='news.html?action=backnews'</script>");
                }else{
                    out.print("<script>alert('新闻添加失败！请联系管理员');location.href='newspages/news_add.jsp'</script>");
                }
            }
        }else if("tomodify".equals(action)){
            Integer nid = Integer.parseInt(request.getParameter("nid"));
            News news = newsService.getNewsByNid(nid);
            List<Topic> topicList = new TopicServiceImpl().findAllTopics();
            request.setAttribute("news",news);
            request.setAttribute("topicList",topicList);
            request.getRequestDispatcher("newspages/news_modify.jsp").forward(request,response);
        }else if("modifynews".equals(action)){
            Integer nid = Integer.parseInt(request.getParameter("nid"));
            News news = new News();
            news.setNid(nid);
            //处理上传的图片
            String fileName = ""; //上传的文件名
            String fieldName = "";  //表单字段元素的name属性值
            //请求信息中的内容是否是multipart类型
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            //上传文件的存储路径（服务器文件系统上的绝对文件路径）
            String uploadFilePath = request.getSession().getServletContext().getRealPath("upload/" );
//            String uploadFilePath = application.getRealPath("upload/");
            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                //设置缓冲区大小和临时目录
                factory.setSizeThreshold(1024*1024*10);
                factory.setRepository(new File("D:\\test\\fileupload_temp"));
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(1024 *1024*5);
                try {
                    try {
                        //解析form表单中所有文件
                        List<FileItem> items = upload.parseRequest(request);
                        Iterator<FileItem> iter = items.iterator();
                        while (iter.hasNext()) {   //依次处理每个文件
                            FileItem item = iter.next();
                            if (item.isFormField()) {  //普通表单字段
                                fieldName = item.getFieldName();   //表单字段的name属性值
                                switch(fieldName){
                                    case "nid":news.setNid(Integer.parseInt(item.getString("UTF-8")));break;
                                    case "ntid":news.setNtid(Integer.parseInt(item.getString("UTF-8")));break;
                                    case "ntitle":news.setNtitle(item.getString("UTF-8"));break;
                                    case "nauthor":news.setNauthor(item.getString("UTF-8"));break;
                                    case "nsummary":news.setNsummary(item.getString("UTF-8"));break;
                                    case "ncontent":news.setNcontent(item.getString("UTF-8"));break;
                                    case "nPicPath":news.setNpicPath(item.getString("UTF-8"));break;
                                    default : break;
                                }
                            } else {  //文件表单字段
                                fileName = item.getName();//获取上传文件名
                                List<String> fileType = Arrays.asList("gif", "bmp", "jpg", "png");
                                String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
                                if (!fileType.contains(ext)) {  // 判断文件类型是否在允许范围内
                                    out.print("<script>alert('上传失败，文件类型只能是gif、bmp、jpg、png');location.href='news.html?action=tomodify&nid=" + nid + "'</script>");
                                    return;
                                } else {
                                    //上传文件
                                    if (fileName != null && !fileName.equals("")) {
                                        File fullFile = new File(fileName);
                                        File saveFile = new File(uploadFilePath, fullFile.getName());
//                                        System.out.println(saveFile.getAbsolutePath());
                                        item.write(saveFile);
                                        //uploadFileName = fullFile.getName();
                                        news.setNpicPath(saveFile.getAbsolutePath());
                                        newsService.updateNewsByNid(news);
                                        out.print("<script>alert(\"上传成功\");location.href='newspages/admin.jsp'</script>");
                                        return;
                                    }
                                }
                            }
                        }
                    }catch(FileUploadBase.SizeLimitExceededException ex){
                        out.print("<script>alert('上传失败，文件太大，全部文件的最大限制是：" + upload.getSizeMax() + " bytes!');location.href='news.html?action=tomodify&nid=" + nid + "'</script>");
                        ex.printStackTrace();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.print("<script>alert(\"上传失败，未知错误\");location.href='news.html?action=tomodify&nid=" + nid + "'</script>");
                    return;
                }
            }
        }
    }
}
