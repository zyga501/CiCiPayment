<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
<meta name = "format-detection" content="telephone = no" />
<title>我的CiCi码</title>
<script type="text/javascript" language="javascript" src="js/fontSize.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-qrcode-0.14.0.js"></script>
<link type="text/css" href="css/mp.css" rel="stylesheet">
</head>

<body onLoad="test();">
<div class="wrap">
	<div class="top_text"></div>
    <div class="top_img"></div>
    <div class="code_box">
    	<div class="code_top">
        	<div class="text1">商户：${merchantname}</div>
            <div class="text2">状态：${status}</div>
        </div>
        <div class="code_img">
        </div>
        <div class="code_fot">${qcode}</div>
    </div>
    <div class="index_text1">支持信用卡、零钱</div>
    <div class="index_text2">告别零币 时尚 安全 便捷</div>
    <div class="index_text3">CC卡热线：<font>400-711-5170</font></div>
    <div class="footer">
    	<ul>
        	<li>
            	<a href="javascript:;">
                	<img src="images/footer_icon1.png">
                </a>
            </li>
            <li>
            	<a href="javascript:;">
                	<img src="images/footer_icon2.png">
                </a>
            </li>
            <li>
            	<a href="javascript:;">
                	<img src="images/footer_icon3.png">
                </a>
            </li>
            <li>
            	<a href="javascript:;">
                	<img src="images/footer_icon4.png">
                </a>
            </li>
            <li>
            	<a href="javascript:;">
                	<img src="images/footer_icon5.png">
                </a>
            </li>
            <li>
            	<a href="javascript:;">
                	<img src="images/footer_icon6.png">
                </a>
            </li>
        </ul>
    </div>
    
</div>
<script>
    function test(){
    if ("${qcode}"=="") {
        window.location.href = "<%=request.getContextPath()%>/page404.jsp?" + Math.random();
        return;
    }
    }
    $().ready(function (){
     var  options = {
        text: "http://www.jeanhk.top/CiCiPayment/Pay!payAdapter?cid="+"${qcode}", //二维码的链接
        size: 100,
        render: "image"//设置生成的二维码是canvas格式，也有image、div格式
    }
    $('.code_img').html("");
    $('.code_img').qrcode(options);
    });
</script>
</body>
</html>
