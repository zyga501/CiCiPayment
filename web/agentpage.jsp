<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  if  (!request.getSession().getAttribute("roletype").toString().equals("0"))
    request.getRequestDispatcher("page404.jsp").forward(request,response);
%>
<html>
  <head>
    <title></title>
  </head>
  <body>
  agentchaxun
  </body>
</html>
