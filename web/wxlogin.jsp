<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript">
        function updateWeixinIdById() {
            $.ajax({
                type: 'post',
                url: "<%=request.getContextPath()%>/User!fetchOpenid",
                dataType:"json",
                data:{id: "<%=request.getParameter("state")%>", code : "<%=request.getParameter("code")%>"},
                success: function (data) {
                     var json = eval("(" + data + ")");
                     if (json.resultCode == 'Succeed') {
                         window.location.href =json.url;
                     }
                     else {
                         window.location.href ="page404.jsp";
                     }
                }
            }) ;
        }
        $(function(){
            updateWeixinIdById();
        })
    </script>

</head>
<body>
<center>
    <input id="Message" type="button" value="正在授权中..." onclick="javascript:WeixinJSBridge.call('closeWindow');" />
</body>
</html>