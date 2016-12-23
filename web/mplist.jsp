<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
<meta name = "format-detection" content="telephone = no" />
<title>我要查账</title>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script type="text/javascript" language="javascript" src="js/fontSize.js"></script>
<link type="text/css" href="css/mp.css" rel="stylesheet">
</head>

<body>
<div class="wrap">
	<div class="top_text"></div>
    <div class="top_img"></div>
    <h2>今日交易</h2>
    <div class="list_border">
    	<ul>
        	<li>
            	微信：<font>${payinfo.wx}</font>
            </li>
            <li>
            	支付宝：<font>${payinfo.ali}</font>
            </li>
            <li>
            	京东：<font>${payinfo.jd}</font>
            </li>
            <li>
            	百度：<font>${payinfo.bd}</font>
            </li>
            <li>
            	易付宝：<font>${payinfo.easy}</font>
            </li>
            <li>
            	冀支付：<font>${payinfo.best}</font>
            </li>
        </ul>
    </div>
    <div class="clear"></div>
    <div class="list_border">
    	<div class="list_title">
            到账卡号：${qcode}   商户：${merchantname} <br>
			服务专员：${agentinfo}
        </div>
        <div class="clear"></div>
        <table>
        	<tr>
        		<th>交易时间</th>
                <th>订单金额</th>
                <th>交易类型</th>
                <th>清算状态</th>
            </tr>
            <tbody id="tbody">
            </tbody>
        </table>
        <div class="clear"></div>
        <div class="page">
            <a href="javascript:fetchlist('last');">尾页</a>
            <a href="javascript:fetchlist('next');">下一页</a>
            <a href="javascript:fetchlist('pre');">上一页</a>
        	<a href="javascript:fetchlist('first');">首页</a>
        </div>
    </div>
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
</body>

<script>
    function test(){
        if ("${payinfo.wx}"=="") {
            window.location.href = "<%=request.getContextPath()%>/page404.jsp?" + Math.random();
            return;
        }
    }

    $().ready(function (){
        test();
        fetchlist("first");
    });

    var pagepid = 0;
    var pagenid = 0;

    function fetchlist(typestr){
        var pagecontent ;
        if (typestr=="first")
            pagecontent =pagenid;
        if (typestr=="next")
            pagecontent =pagenid;
        if (typestr=="pre")
            pagecontent =pagepid;
        if (typestr=="last")
            pagecontent =pagepid;
        $.ajax({
            type: 'post',
            url: 'User!payinfoList',
            dataType :"json",
            data:{pagetype:typestr,pid:pagecontent},
            success: function (data) {
                var json = eval("(" + data + ")");
                var htmlstr;
                if (json.length>0) {
                    pagepid = json[0].id;
                    pagenid = json[json.length-1].id;
                    for (var i = 0; i < json.length; i++) {
                        htmlstr += "<tr>";
                        htmlstr += "<td>" + json[i].tradeTime + "</td>";
                        htmlstr += "<td>" + json[i].tradeAmount + "</td>";
                        htmlstr += "<td>" + json[i].tradeType + "</td>";
                        htmlstr += "<td>" + json[i].paid + "</td>";
                        htmlstr += "</tr>";
                    }
                    $("#tbody").html(htmlstr);
                }
            }
        })
    }
</script>
</html>
