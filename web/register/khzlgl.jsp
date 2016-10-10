<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <style>
        td
        {
            text-align:center;
            margin:0 auto;
            height: 160px;
        }
        caption
        {
            text-align:center;
            margin:0 auto;
            height: 60px;
        }
    </style>
    <script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<table class="table table-bordered" >
    <caption>${unick}</caption>
    <tbody>
    <tr>
        <td><a href="<%=request.getContextPath()%>\OA\khzlinfo.jsp"> <img src="<%=request.getContextPath()%>\img\khzl.JPG"><p>信息申报</p></a></td>
        <td><a href="#"><img src="<%=request.getContextPath()%>\img\khzlcx.JPG"><p>资料查询</p></a></td>
    </tr>
    <tr>
        <td><a href="#"><img src="<%=request.getContextPath()%>\img\khzldj.JPG"><p>临时存储</p></a></td>
        <td></td>
    </tr>
    </tbody>
</table>
</body>
</html>

