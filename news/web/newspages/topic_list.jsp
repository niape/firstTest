<%@ page import="com.hisoft.news.entity.Topic" %>
<%@ page import="java.util.List" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <base href="<%=basePath%>">
    <title>编辑主题--管理后台</title>
    <link href="css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="main">
    <div>
        <iframe src="newspages/console_element/top.html" scrolling="no" frameborder="0" width="947px"
                height="180px"></iframe>
    </div>
    <div id="opt_list">
        <iframe src="newspages/console_element/left.html" scrolling="no" frameborder="0" width="130px" id="iframeLeft"></iframe>

    </div>
    <div id="opt_area">
        <ul class="classlist">

            <%--<c:forEach items="${applicationScope.topiclist}" var="topic" >
            <li>${topic.tname}
                &nbsp;&nbsp;&nbsp;&nbsp; <a href="topic.html?action=tomodify&tid=${topic.tid}">修改</a>
                &nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:del(${topic.tid})">删除</a>
            </li>
            </c:forEach>--%>


        </ul>
        <script src="js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript">
            /*使用$(selector).load()实现，在服务器中封装*/
            $(function() {
                    $(".classlist").load("topic.html","action=topicJson");
            })
            /*使用$.ajax()实现，在服务器中封装*/
            /*$(function(){
                $.ajax({
                    "url":"topic.html",
                    "data":"action=topicJson",
                    "type":"get",
                    "dataType":"html",
                    "success":function(data){
                        $(".classlist").html(data);
                    }
                });
            })*/
            /*使用$.getJSON()实现,在页面中封装，借助json*/
            /*$(function() {
                $.getJSON("topic.html", "action=topicJson", callback);
                function callback(data) {
                    for (let i = 0; i < data.length; i++) {
                        $(".classlist").append("<li>"+data[i].tname+
                            "&nbsp;&nbsp;&nbsp;&nbsp;"+ "<a href=\"topic.html?action=tomodify&tid="+data[i].tid+"\">修改</a>"
                            +"&nbsp;&nbsp;&nbsp;&nbsp; "+
                            "<a href=\"javascript:del("+data[i].tid+")\">删除</a>"+
                            "</li>");
                    }
                }
            })*/
            /*使用$.ajax()实现，在页面中封装，借助json*/
            /*$(function(){
                $.ajax({
                    "url":"topic.html",
                    "type":"get",
                    "data":"action=topicJson",
                    "dataType":"json",
                    "success":function (data) {
                        for (let i = 0; i < data.length; i++) {
                            $(".classlist").append("<li>"+data[i].tname+
                                "&nbsp;&nbsp;&nbsp;&nbsp;"+ "<a href=\"topic.html?action=tomodify&tid="+data[i].tid+"\">修改</a>"
                                +"&nbsp;&nbsp;&nbsp;&nbsp; "+
                            "<a href=\"javascript:del("+data[i].tid+")\">删除</a>"+
                            "</li>");
                        }
                    },
                    "error":function(){
                        alert("123456");
                    }
                });
            })*/
        </script>
    </div>
    <iframe src="newspages/console_element/bottom.html" scrolling="no" frameborder="0" width="947px"
            height="190px"></iframe>
</div>
<script type="text/javascript">
    function check() {
        var tname = document.getElementById("tname");

        if (tname.value == "") {
            alert("请输入主题名称！！");
            tname.focus();
            return false;
        }
        return true;
    }

    function del(tid) {
        if (confirm("你确定要删除吗？")) {
            location.href = "topic.html?action=delete&tid=" + tid;
        }
    }

    /*加载页面*/

    $(function(){
        function appendNodesToContent (path) {
            $('#opt_area').load (path);
        }
        $("#iframeLeft").contents().find("#modify").bind ('click', function () {
            appendNodesToContent ('newspages/topic_add.jsp #opt_area');
            console.log("123456")
        })
    });
</script>
</body>
</html>