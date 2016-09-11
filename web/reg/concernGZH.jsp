<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>关注公众号</title>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<center>
    <img src="./img/gzh.jpg"/>
    <p>关注公众号，接受审核消息</p>
<input id="Message" value="我已关注了" type="button" onclick="WeixinJSBridge.call('closeWindow');" /></center>
</body>
</html>
