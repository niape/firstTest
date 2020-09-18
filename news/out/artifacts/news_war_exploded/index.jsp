<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page import="com.hisoft.news.entity.News" %>
<%@ page import="com.hisoft.news.entity.Topic" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.hisoft.news.util.Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>新闻中国</title>
    <link href="css/main.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="header">
    <div id="top_login">
        <form action="user.html" method="post" autocomplete="off">
            <label> 登录名 </label>
            <input type="text" id="uname" name="uname"
                   value='<%=request.getParameter("uname") == null?"":request.getParameter("uname")%>'
                   class="login_input"/>
            <label> 密&#160;&#160;码 </label>
            <input type="password" id="upwd" name="upwd"
                   value='<%=request.getParameter("upwd") == null?"":request.getParameter("upwd")%>'
                   class="login_input"/>
            <input type="hidden" name="action" value="login"/>
            <input type="submit" class="login_sub" value="登录" onclick="login()"/>
            <label id="error">${message}
            </label>
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

    <%--<%
        Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");

        if (map == null) {
            request.getRequestDispatcher("news.html?action=index").forward(request, response);
            return;
        }

        Page newspage = (Page) map.get("page");
        List<News> list1 = (List<News>) map.get("list1");
        List<News> list2 = (List<News>) map.get("list2");
        List<News> list3 = (List<News>) map.get("list3");
        List<Topic> topiclist = (List<Topic>) map.get("topicList");

    %>--%>
    <c:if test="${empty map}">
        <jsp:forward page="news.html?action=index"/>
    </c:if>
<%--        <c:out  escapeXml="false" value="<%request.getRequestDispatcher('news.html?action=index').forward(request, response); %>" />--%>

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
            <%--<script src="js/jquery-1.8.3.min.js"></script>
            <script type="text/javascript">
                $(function(){
                    $.getJSON("news.html","action=index",callback);
                    function callback(data){
                        for (let i = 0; i < data.list1.length; i++) {
                            $(".side_list:eq(0) ul").append(
                                "<li>"+"<a href='news.html?action=readNew&nid="+data.list1[i].nid+"'>"+"<b>"+data.list1[i].ntitle+"</b>"+"</a>"+"</li>");
                        }
                    }
                })
            </script>--%>
        <h1><img src="images/title_2.gif" alt="国际新闻"/></h1>
        <div class="side_list">
            <ul>
                <c:forEach items="${map.list2}" var="news">
                    <li><a href='news.html?action=readNew&nid=${news.nid}'><b>${news.ntitle}%>
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
            <ul class="class_date">
                <li id='class_month'>
                    <c:forEach items="${map.topicList}" var="topic">
                        <a href='javascript:;' id="${topic.tid}"><b>${topic.tname}<%--通过javascript:;实现href不跳转--%>
                        </b></a>
                    </c:forEach>
                </li>

            </ul>
            <ul class="classlist">

                <%--<c:forEach items="${map.page.newsList}" var="news" varStatus="v">
                <li><a href='news.html?action=readNew&nid=${news.nid}'>${news.ntitle}
                </a><span> <fmt:formatDate value="${news.ncreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </span></li>
                    <c:if test="${v.count % 5 eq 0}">
                        <li class='space'></li>
                    </c:if>
                </c:forEach>--%>
                    <script src="js/jquery-1.8.3.min.js"></script>
                    <script type="text/javascript">
                        //ajax实现新闻显示，并分页
                        $(function(){
                            function getPage(tid,currPageNo){
                                if(tid==null){
                                    tid = "";
                                }
                                $.getJSON("news.html","action=newspage&tid="+tid+"&currPageNo="+currPageNo,callback);
                                function callback(data){
                                    var tid = data[0].tid;
                                    var page = data[1];
                                    $(".classlist").html("");
                                    $(page.newsList).each(function(){
                                        $(".classlist").append(
                                            "<li>"+
                                            "<a href='news.html?action=readNew&nid="+this.nid+"'>"+this.ntitle+"</a>"+
                                            "<span>"+this.ncreateDate+"</span>"+
                                            "</li>"
                                        );
                                    })
                                    var pagebar = $(".classlist").append("<p align=\"right\"></p>").find("p");
                                    pagebar.append(
                                        "当前页数:["+page.currPageNo+"/"+page.totalPageCount+"]&nbsp;");
                                    pagebar.append($("<a href='javascript:;'>首页</a>").click(function(){
                                        getPage(tid,1);
                                    }));
                                    if(page.currPageNo > 1){
                                        pagebar.append($("<a href='javascript:;'>上一页</a>").click(function(){
                                            getPage(tid,currPageNo - 1);
                                        }));
                                    }
                                    if(page.currPageNo < page.totalPageCount){
                                        pagebar.append($("<a href='javascript:;'>下一页</a>").click(function(){
                                            getPage(tid,currPageNo + 1);
                                        }));
                                    }
                                    pagebar.append($("<a href='javascript:;'>末页</a>").click(function(){
                                        getPage(tid,page.totalPageCount);
                                    }));
                                }
                            }
                            getPage(null,1);

                            //点击主题
                            $("#class_month a").each(function () {
                                $(this).click(function () {
                                    getPage(this.id,1);/*此id为通过Jquery获取的标签的id属性值*/
                                })
                            })
                        })
                    </script>

                <%--<p align="right"> 当前页数:[${map.page.currPageNo}/${map.page.totalPageCount}]&nbsp;
                    <a href='news.html?action=index&tid=${param.tid}&currPageNo=1'>首页</a>

                    <c:if test="${map.page.currPageNo gt 1}">
                    <a href="news.html?action=index&tid=${param.tid}&currPageNo=${map.page.currPageNo - 1}">上一页</a>
                    </c:if>

                    <c:if test="${map.page.currPageNo lt map.page.totalPageCount}">
                    <a href='news.html?action=index&tid=${param.tid}&currPageNo=${map.page.currPageNo + 1}'>下一页</a>
                    </c:if>

                    <a href="news.html?action=index&tid=${param.tid}&currPageNo=${map.page.totalPageCount}">末页</a>
                </p>--%>
            </ul>
        </div>
        <div class="picnews">
            <ul>
                <li><a href="#"><img src="images/Picture1.jpg" width="249" alt=""/> </a><a href="#">幻想中穿越时空</a></li>
                <li><a href="#"><img src="images/Picture2.jpg" width="249" alt=""/> </a><a href="#">国庆多变的发型</a></li>
                <li><a href="#"><img src="images/Picture3.jpg" width="249" alt=""/> </a><a href="#">新技术照亮都市</a></li>
                <li><a href="#"><img src="images/Picture4.jpg" width="249" alt=""/> </a><a href="#">群星闪耀红地毯</a></li>
            </ul>
        </div>
    </div>
</div>
<div id="footer">
    <iframe src="index-elements/index_bottom.html" scrolling="no" frameborder="0" width="947px" height="190px"></iframe>
</div>
</body>
</html>
	