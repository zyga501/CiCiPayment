<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    if ((!request.getSession().getAttribute("roletype").toString().equals("0"))&&
            (!request.getSession().getAttribute("roletype").toString().equals("111")))
        request.getRequestDispatcher("page404.jsp").forward(request, response);
%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.ext.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
    <script src="<%=request.getContextPath()%>/js/laypage.js"></script>
    <script src="<%=request.getContextPath()%>/js/laydate.js"></script>
</head>
<body>
<form id="searchform">
    <div class="container">
        <div class="row">
            <div class="form-group">
                <label class="col-sm-2">订单号</label>
                <div class="col-sm-4">
                    <input class="form-control" name="ord">
                </div>
                <label class="col-sm-2">代理</label>
                <div class="col-sm-4">
                    <input class="form-control" name="agent">
                </div>
                <label class="col-sm-2">注册时间</label>
                <div class="col-sm-2"><input name="createtime1" id="startdate" class="form-control layer-date" placeholder="开始日期"
                                             onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
                </div>
                <div class="col-sm-2"><input name="createtime2" id="enddate" class="form-control layer-date" placeholder="开始日期"
                                             onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 col-sm-offset-5">
                    <input class="form-control btn btn-primary" type="button" value="检索" onclick="search(1)">
                </div>
                </div>
    </div>
    </div>
</form>
<HR align=center width=100% color=#987cb9>
<div style="float:left" id="pagecountdiv"></div>
<div style="float:right;text-align: center" id="navigatediv"></div>
<center><div id="contentdiv"></div></center>
<div id="divimg" style="text-align: center" ></div>
</body>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/layer.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-qrcode-0.14.0.js"></script>
<script>
    function search(curr) {
        $("#pagecountdiv").html("");
        $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/Flow!orderList',
            dataType: "json",
            data: $("#searchform").serialize() + "&currpagenum=" + curr,
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.errorMessage != null) {
                    $("#contentdiv").html(json.errorMessage);
                }
                else {
                    laypage({
                        cont: 'navigatediv',
                        pages: json[0].pagecount,
                        skip: true,
                        skin: 'yahei',
                        jump: function (obj, first) {
                            if (!first) {
                                $("#pagecountdiv").html("");
                                $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
                                $.ajax({
                                    type: 'post',
                                    url: '<%=request.getContextPath()%>/Flow!OrderList',
                                    dataType: "json",
                                    data: $("#searchform").serialize() + "&currpagenum=" + obj.curr,
                                    success: function (data) {
                                        var json = eval("(" + data + ")");
                                        if (json.errorMessage != null) {
                                            $("#contentdiv").html(json.errorMessage);
                                        }
                                        else {
                                            $("#pagecountdiv").html("<span class='label label-success'>" +
                                                    "总共数据<span class='badge'>" + json[0]["totalcount"] + " </span></span>");
                                            var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                                            htmlStr += "<th>代理</th><th>订单号</th><th>数量</th><th>单价</th><th>操作</th>";
                                            htmlStr += "</thead><tbody>";
                                            for (var i = 1, l = json.length; i < l; i++) {
                                                htmlStr += "<tr shh='" + json[i]['orderno'] + "'>";
                                                htmlStr += "<td>" + json[i]['uname'] + "</td>"
                                                htmlStr += "<td>" + json[i]['orderno'] + "</td>"
                                                htmlStr += "<td>" + json[i]['num'] + "</td>"
                                                htmlStr += "<td>" + nulltoempty(json[i]['priceper']) + "</td>"
                                                if (json[i]['total'] > 0)
                                                    htmlStr += "<td><a shh='" + json[i]['orderno'] + "' href='#' onclick='showinfos(this)'>查看</a></td>"
                                                else
                                                    htmlStr += "<td><a shh='" + json[i]['orderno'] + "' href='#' onclick='sendok(this)'>通过</a></td>"
                                                htmlStr += "</tr>";
                                            }
                                            htmlStr += "</tobdy></table>";
                                            $("#contentdiv").html(htmlStr);
                                        }
                                    }
                                })
                            }
                        }
                    })
                    $("#pagecountdiv").html("<span class='label label-success'>" +
                            "总共数据<span class='badge'>" + json[0]["totalcount"] + " </span></span>");
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                    htmlStr += "<th>代理</th><th>订单号</th><th>数量</th><th>单价</th><th>操作</th>";
                    htmlStr += "</thead><tbody>";
                    for (var i = 1, l = json.length; i < l; i++) {
                        htmlStr += "<tr shh='" + json[i]['orderno'] + "'>";
                        htmlStr += "<td>" + json[i]['uname'] + "</td>"
                        htmlStr += "<td>" + json[i]['orderno'] + "</td>"
                        htmlStr += "<td>" + json[i]['num'] + "</td>"
                        htmlStr += "<td>" + nulltoempty(json[i]['priceper']) + "</td>"
                        if (json[i]['total'] > 0)
                            htmlStr += "<td><a shh='" + json[i]['orderno'] + "' href='#' onclick='showinfos(this)'>查看</a></td>"
                        else
                            htmlStr += "<td><a shh='" + json[i]['orderno'] + "' href='#' onclick='sendok(this)'>通过</a></td>"
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    $("#contentdiv").html(htmlStr);
                }
            }
        })
    }
        function nulltoempty(val){
            if  (val=="null" || val==null)
                return "";
            else
                return val;
        }
        function  sendok(obj) {
            var ono = $(obj).attr("shh");
            $.ajax({
                type: 'post',
                url: "<%=request.getContextPath()%>/Flow!orderTurnok?ord=" + ono,
                dataType: "json",
                async: false,
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.resultCode=="Succeed"){
                        $(obj).html("查看");
                        $(obj).attr("onclick","showinfos(this)");
                        alert("审核通过");
                    }
                }
            })
        }
    function  showinfos(obj) {
        var ono=$(obj).attr("shh")
        $.ajax({
            type: 'post',
            url: "<%=request.getContextPath()%>/Flow!orderDetail?ord=" + ono,
            dataType: "json",
            async: false,
            success: function (data) {
                var json = eval("(" + data + ")");
                if ((json.resultCode=="Succeed")  &&(json.rtlist.length>0)) {
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>";
                    htmlStr += "<tbody>";
                    for (var i = 0, l = Math.ceil(json.rtlist.length / 5); i < l; i++) {
                        htmlStr += "<tr>";
                        for (var j=i*5;j<Math.min(json.rtlist.length,(i+1)*5);j++)
                            htmlStr += "<td id="+"code".concat(json.rtlist[j].saltcode)+" onclick='mkqcode(this)'>" + json.rtlist[j].saltcode+ "</td>"
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    htmlStr += "<button onclick='downqcode()'>打包下载</button>";
                    layer.full(layer.open({
                        type: 1,
                        skin: 'layui-layer-demo', //样式类名
                        closeBtn: 1, //不显示关闭按钮
                        shade: 0.8,
                        area: ['100%', '100%'],
                        shadeClose: true, //开启遮罩关闭
                        content: htmlStr
                    }));
                }else{
                    layer.full(layer.open({
                        type: 1,
                        skin: 'layui-layer-demo', //样式类名
                        closeBtn: 1, //不显示关闭按钮
                        shade: 0.8,
                        area: ['100%', '100%'],
                        shadeClose: true, //开启遮罩关闭
                        content: '<h1>无结果</h1>'
                    }));
                }
            }
        })
    }

    function mkqcode(obj){
        var v =$(obj).text();
        var options;
        if (v != "") {
            options = {
                text: "http://www.jeanhk.top/CiCiPayment/Pay!payAdapter?cid="+v, //二维码的链接
                size: 100,
                render: "image"//设置生成的二维码是canvas格式，也有image、div格式
            }
            $('#divimg').html("");
            $('#divimg').qrcode(options);
        }
        layer.open({
            type: 1,
            title:v,
            content: $('#divimg') //这里content是一个DOM，这个元素要放在body根节点下
        });
    }
        function refresh(){
        searchlist(1);
    }
</script>
</html>