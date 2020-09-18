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
    <title>新闻中国</title>
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
    </script>
    <link href="css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="main">
    <div>
        <iframe src="newspages/console_element/top.html" scrolling="no" frameborder="0" width="947px"
                height="180px"></iframe>
    </div>
    <div id="opt_list">
        <iframe src="newspages/console_element/left.html" scrolling="no" frameborder="0" width="130px"></iframe>
    </div>
    <div id="opt_area">
        <h1 id="opt_type"> 修改主题： </h1>
        <form action="topic.html" method="post" onsubmit="return check()">
            <p>
                <label> 主题名称 </label>
                <input name="tname" type="text" class="opt_input"
                       value='<c:choose><c:when test="${empty topic}">${param.tname}</c:when><c:otherwise>${topic.tname}</c:otherwise></c:choose>'/>
                <span style="color: red">${message}</span>
            </p>
            <input name="action" type="hidden" value="updatetopic">
            <input name="tid" type="hidden" value="${topic.tid}">
            <input type="submit" value="提交" class="opt_sub"/>
            <input type="reset" value="重置" class="opt_sub"/>
        </form>
    </div>
    <iframe src="newspages/console_element/bottom.html" scrolling="no" frameborder="0" width="947px"
            height="190px"></iframe>
</div>
</body>
</html>	