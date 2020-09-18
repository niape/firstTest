<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.hisoft.news.entity.News" %>
<%@ page import="com.hisoft.news.entity.Comment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>新闻中国</title>
    <link href="css/read.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        function check() {
            var cauthor = document.getElementById("cauthor");
            var content = document.getElementById("ccontent");
            if (cauthor.value == "") {
                alert("用户名不能为空！！");
                return false;
            } else if (content.value == "") {
                alert("评论内容不能为空！！");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div id="header">
    <div id="top_login">
        <form method="post" action="user.html">
            <label> 登录名 </label>
            <input type="text" id="uname" name="uname" value="" class="login_input"/>
            <label> 密&#160;&#160;码 </label>
            <input type="password" id="upwd" name="upwd" value="" class="login_input"/>
            <input type="submit" class="login_sub" value="登录"/>
            <label id="error"> </label>
            <a href="index.jsp" class="login_link">返回首页</a>
          <img src="images/friend_logo.gif" alt="Google" id="friend_logo"/>
        </form>
    </div>
    <div id="nav">
        <div id="logo"><img src="images/logo.jpg" alt="新闻中国"/></div>
        <div id="a_b01"><img src="images/a_b01.gif" alt=""/></div>
        <!--mainnav end-->
    </div>
</div>
<div id="container">
    <div class="sidebar">
        <h1><img src="images/title_1.gif" alt="国内新闻"/></h1>
        <div class="side_list">
            <ul>
                <c:forEach items="${map.list1}" var="news">
                    <li><a href='news.html?action=readNew&nid=${news.nid}'><b>${news.ntitle}
                    </b></a></li>
                </c:forEach>
            </ul>
        </div>
        <h1><img src="images/title_2.gif" alt="国际新闻"/></h1>
        <div class="side_list">
            <ul>
                <c:forEach items="${map.list2}" var="news">
                    <li><a href='news.html?action=readNew&nid=${news.nid}'><b>${news.ntitle}
                    </b></a></li>
                </c:forEach>
            </ul>
        </div>
        <h1><img src="images/title_3.gif" alt="娱乐新闻"/></h1>
        <div class="side_list">
            <ul>
                <c:forEach items="${map.list3}" var="news">
                    <li><a href='news.html?action=readNew&nid=${news.nid}'><b>${news.ntitle}
                    </b></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="main">
        <div class="class_type"><img src="images/class_type.gif" alt="新闻中心"/></div>
        <div class="content">
            <ul class="classlist">
                <table width="80%" align="center" id="ctable">
                    <tr width="100%">
                        <td colspan="2" align="center">${news.ntitle}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">作者：${news.nauthor}
                        </td>
                        <td align="left">发布时间：${news.ncreateDate}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"></td>
                    </tr>
                    <tr>
                        <td colspan="2"> 　${news.ncontent}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr/>
                        </td>
                    </tr>
                </table>
            </ul>
            <ul class="classlist">
                <table width="80%" align="center" >
                    <c:if test="${empty news.comments}" >
                    <tr>
                        <td colspan="6"> 暂无评论！</td>
                    </tr>
                    <tr>
                        <td colspan="6">
                            <hr/>
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${not empty news.comments}" >
                        <c:forEach items="${news.comments}" var="comment">
                    <tr>
                        <td> 留言人：</td>
                        <td>${comment.cauthor}
                        </td>
                        <td> IP：</td>
                        <td>${comment.cip}
                        </td>
                        <td> 留言时间：</td>
                        <td>${comment.cdate}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">${comment.ccontent}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">
                            <hr/>
                        </td>
                    </tr>
                        </c:forEach>
                    </c:if>
                </table>
            </ul>
            <ul class="classlist">
                <form action="" method="post" onSubmit="return checkComment()" id="cform">
                    <input type="hidden" name="nid" value="${news.nid}"/>
                    <table width="80%" align="center">
                        <tr>
                            <td> 评 论</td>
                        </tr>
                        <tr>
                            <td> 用户名：</td>
                            <td>
                                <c:if test="${not empty sessionScope.admin}">
                                <input id="cauthor" name="cauthor" value="${sessionScope.admin}"
                                       readonly="readonly" style="border:0px;"/>
                                </c:if>
                                <c:if test="${empty sessionScope.admin}">
                                <input id="cauthor" name="cauthor" value="这家伙很懒什么也没留下"/>
                                </c:if>
                                IP：

                                <input name="cip" id="cip" value="${pageContext.request.remoteAddr}" readonly="readonly"
                                       style="border:0px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><textarea name="ccontent" id="ccontent" cols="70" rows="10"></textarea>
                            </td>
                        </tr>
                        <tr>
<%--                            <td><input name="submit" value="发  表"  type="submit"/>--%>
                            <td><input type="button" name="submit" value="发  表" id="csubmit" />
                            </td>
                        </tr>
                    </table>
                </form>

            </ul>
        </div>
    </div>
</div>
<div id="footer">
    <iframe src="index-elements/index_bottom.html" scrolling="no" frameborder="0" width="947px" height="190px"></iframe>
</div>

<script src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
$(function(){
    var $form = $("#cform");
    $("#csubmit").click(function(){
        var array = $form.serializeArray();
        var param = $.param(array);

        $.post("news.html?action=addComment",param,afterComment,"json");

        function afterComment(data){
            if(data.result == "success"){
                var $newComment = $(
                    "<tr><td> 留言人：</td><td>cauthor</td><td> IP：</td><td>cip</td><td> 留言时间：</td><td>cdate</td></tr>"+
                    "<tr><td colspan=\"6\">ccontent</td></tr>"+
                    "<tr><td colspan=\"6\"><hr/></td></tr>"
                );
                $(array).each(function(){
                    $newComment.find("td:contains('"+this.name+"')").text(this.value);
                })
                $("#ctable").prepend($newComment);
            }else{
                alert("发表失败");
            }
        }
    })
})
</script>
</body>
</html>
