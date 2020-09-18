<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.hisoft.news.entity.Topic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <base href="<%=basePath%>">
    <title>添加新闻--管理后台</title>
    <link href="css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<div id="main">
    <div>
<%--        <iframe src="newspages/console_element/top.html" scrolling="no" frameborder="0" width="947px" height="180px"></iframe>--%>
        <%@ include file="console_element/top.html"%>
    </div>
    <div id="opt_list">
<%--        <iframe src="newspages/console_element/left.html" scrolling="no" frameborder="0" width="130px"></iframe>--%>
        <%@ include file="console_element/left.html"%>
    </div>

    <c:if test="${empty topiclist}"><jsp:forward page="../topic.html?action=topiclist&from=addnews"></jsp:forward></c:if>
    <div id="opt_area">
        <h1 id="opt_type"> 添加新闻： </h1>
        <form action="news.html?action=addnews" method="post" enctype="multipart/form-data">
            <p>
                <label> 主题 </label>
                <select name="ntid" id="topic">
                    <option value="1">选择</option>
<%--                    <c:forEach items="${topiclist}" var="topic">--%>
<%--                        <option value='${topic.tid}'>${topic.tname}</option>--%>
<%--                    </c:forEach>--%>

                </select>

                <script src="js/jquery-1.8.3.min.js"></script>
                    <script type="text/javascript">
                        /*使用$.getJSON()实现,在页面中封装*/
                        /*$(function() {
                            $.getJSON("news.html", "action=topicJson", callback);
                            function callback(data) {
                                for (let i = 0; i < data.length; i++) {
                                    $("#topic").append("<option value='"+data[i].tid+"'>"+data[i].tname+"</option>");
                                }
                            }
                        })*/
                        /*使用$.ajax()实现，在页面中封装，借助json*/
                        /*$(function(){
                            $.ajax({
                                "url":"news.html",
                                "type":"get",
                                "data":"action=topicJson",
                                "dataType":"json",
                                "success":function (data) {
                                    for (let i = 0; i < data.length; i++) {
                                        $("#topic").append("<option value='"+data[i].tid+"'>"+data[i].tname+"</option>");
                                    }
                                },
                                "error":function(){
                                    alert("123456");
                                }
                            });
                        })*/
                        /*使用$.ajax()实现，在服务器中封装，借助json*/
                        $(function(){
                            $.ajax({
                                "url":"news.html",
                                "type":"get",
                                "data":"action=topicJson",
                                "dataType":"html",
                                "success":function (data) {
                                        $("#topic").html(data);
                                },
                                "error":function(){
                                    alert("123456");
                                }
                            });
                        })
                    </script>

            </p>
            <p>
                <label> 标题 </label>
                <input name="ntitle" type="text" class="opt_input"/>
            </p>
            <p>
                <label> 作者 </label>
                <input name="nauthor" type="text" class="opt_input"/>
            </p>
            <p>
                <label> 摘要 </label>
                <textarea name="nsummary" cols="40" rows="3"></textarea>
            </p>
            <p>
                <label> 内容 </label>
                <textarea name="ncontent" cols="70" rows="10"></textarea>
            </p>
            <p>
                <label> 上传图片 </label>
                <input name="file" type="file" class="opt_input"/>
            </p>
            <input name="action" type="hidden" value="addnews"/>
            <input type="submit" value="提交" class="opt_sub"/>
            <input type="reset" value="重置" class="opt_sub"/>
        </form>
    </div>
<%--    <iframe src="newspages/console_element/bottom.html" scrolling="no" frameborder="0" width="947px" height="190px"></iframe>--%>
    <%@ include file="console_element/bottom.html"%>
</div>
</body>
</html>
  