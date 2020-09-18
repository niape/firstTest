<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <base href="<%=basePath%>">
    <title>添加主题--管理后台</title>
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
    <div id="opt_area">
        <ul class="classlist">


            <%--<c:if test="${empty page}">
                <jsp:forward page="../news.html?action=backnews"/>
            </c:if>

            <c:forEach items="${page.newsList}" var="news" varStatus="v">
            <li>${news.ntitle}<span> 作者：
	        ${news.nauthor}
	        &#160;&#160;&#160;&#160; <a href='news.html?action=tomodify&nid=${news.nid}'>修改</a> &#160;&#160;&#160;&#160; <a
                        href='javascript:del(${news.nid})'>删除</a> </span></li>
                <c:if test="${v.count % 5 eq 0}">
                    <li class='space'></li>
                </c:if>
            </c:forEach>--%>


        </ul>
<script src="js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript">
            /*使用$.getJSON()实现,在页面中封装*/
            $(function() {
                $.getJSON("news.html", "action=adminJson", callback);

                function callback(data) {
                    for (let i = 0; i < data.length; i++) {
                        $(".classlist").append("<li>" + data[i].ntitle + "<span> 作者：" +
                            data[i].nauthor +
                            " &#160;&#160;&#160;&#160;" +
                            " <a href='news.html?action=tomodify&nid=" + data[i].nid + "'>修改</a> &#160;&#160;&#160;&#160;" +
                            " <a href='javascript:del(" + data[i].nid + ")'>删除</a> </span>" + "</li>");

                    }
                }

                //加载添加主题的表单
                $("#addTopicLink").click(function () {
                    $("#opt_area").load("newspages/topic_add.jsp #opt_area");
                });
                //执行添加主题
                $("#opt_area").on("click","#addtopicsubmit",function () {
                    $.post("topic.html",$("#addTopicForm").serialize(),afterAddTopic,"json");
                    function afterAddTopic(data) {
                        //成功则显示主题列表
                        //失败显示信息
                        if(data.message == "success"){
                            alert("添加成功1121");
                            $("#opt_area").html("");
                            $("#opt_area").append("<ul class=\"classlist\"></ul>");
                            $("#opt_area .classlist").load("topic.html","action=topiclist");
                        }else{
                            alert(data.message);
                        }
                    }
                })
            })
            /*使用$.ajax()实现，在页面中封装，借助json*/
            /*$(function(){
                $.ajax({
                    "url":"news.html",
                    "type":"get",
                    "data":"action=adminJson",
                    "dataType":"json",
                    "success":function (data) {
                        for (let i = 0; i < data.length; i++) {
                            $(".classlist").append("<li>"+data[i].ntitle+"<span> 作者："+
                                data[i].nauthor+
                                " &#160;&#160;&#160;&#160;"+
                                " <a href='news.html?action=tomodify&nid="+data[i].ntitle+"'>修改</a> &#160;&#160;&#160;&#160;"+
                                " <a href='javascript:del("+data[i].nid+")'>删除</a> </span>"+"</li>");

                        }
                    },
                    "error":function(){
                        alert("admin");
                    }
                });
            })*/
            /*使用$.ajax()实现，在服务器中封装，借助json*/
            /*$(function(){
                $.ajax({
                    "url":"news.html",
                    "type":"get",
                    "data":"action=adminJson",
                    "dataType":"html",
                    "success":function (data) {
                            $(".classlist").html(data);
                    },
                    "error":function(){
                        alert("admin");
                    }
                });
            })*/
            /*使用$(selector).load()实现，在服务器中封装*/
            /*$(function(){
                $(".classlist").load("news.html","action=adminJson");
            })*/


        </script>
            <p align="right"> 当前页数:[${page.currPageNo}/${page.totalPageCount}]&nbsp;
                <a href='news.html?action=backnews&currPageNo=1'>首页</a>

                <c:if test="${page.currPageNo gt 1}">
                    <a href="news.html?action=backnews&currPageNo=${page.currPageNo - 1}">上一页</a>
                </c:if>

                <c:if test="${page.currPageNo lt page.totalPageCount}">
                    <a href='news.html?action=backnews&currPageNo=${page.currPageNo + 1}'>下一页</a>
                </c:if>

                <a href="news.html?action=backnews&currPageNo=${page.totalPageCount}">末页</a>
            </p>
    </div>
<%--    <iframe src="newspages/console_element/bottom.html" scrolling="no" frameborder="0" width="947px" height="190px"></iframe>--%>
    <%@ include file="console_element/bottom.html"%>
</div>
<script type="text/javascript">
    function del(nid) {
        if (confirm("确定要删除吗？将会把该新闻的评论也一起删除！")) {
            location.href = "news.html?action=delnews&nid=" + nid;
        }
    }
</script>
</body>
</html>

	