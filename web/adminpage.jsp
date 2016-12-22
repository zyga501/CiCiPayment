<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  if  (!request.getSession().getAttribute("roletype").toString().equals("0"))
    request.getRequestDispatcher("page404.jsp").forward(request,response);
%>
<html>
  <head>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
  </head>
  <body>
  <a href="#" class="btn btn-primary" onclick="$('#div').load('<%=request.getContextPath()%>/reg/merchantlist.jsp')">审核CC卡申请</a>
  <a href="#" class="btn btn-info" onclick="$('#div').load('User!makeCardPage')">CC卡发售</a>
  <a href="#" class="btn btn-warning" onclick="$('#div').load('User!msgPage')">发送公众号消息</a>
  <div id="div"></div>
  </body>
</html>
