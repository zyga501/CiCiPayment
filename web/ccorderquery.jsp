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
                <label class="col-sm-2">卡号</label>
                <div class="col-sm-4">
                    <input class="form-control" name="cid">
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
</body>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dateRangeUtil.js"></script>
<script>
    function search(curr) {
        $("#pagecountdiv").html("");
        $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/Flow!fetchCiCiOrderNo',
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
                            if(!first){
                                $("#pagecountdiv").html("");
                                $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
                                $.ajax({
                                    type: 'post',
                                    url: '<%=request.getContextPath()%>/Flow!fetchCiCiOrderNo',
                                    dataType: "json",
                                    data: $("#searchform").serialize() + "&currpagenum=" + obj.curr,
                                    success: function (data) {
                                        var json = eval("(" + data + ")");
                                        if (json.errorMessage != null) {
                                            $("#contentdiv").html(json.errorMessage);
                                        }
                                        else {
                                            $("#pagecountdiv").html("<span class='label label-success'>" +
                                                    "总共数据<span class='badge'>" + json.totalcount+ " </span></span>");
                                            var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                                            htmlStr += "<th>订单号</th><th>数量</th><th>单价</th><th>查看明细</th>";
                                            htmlStr += "</thead><tbody>";
                                            for (var i = 1, l = json.rtlist.length; i < l; i++) {
                                                htmlStr += "<tr shh='" + json.rtlist[i]['orderno'] +"'>";
                                                htmlStr += "<td>" + json.rtlist[i]['orderno'] + "</td>"
                                                htmlStr += "<td>" + json.rtlist[i]['num'] + "</td>"
                                                htmlStr += "<td>" + nulltoempty(json.rtlist[i]['priceper']) + "</td>"
                                                htmlStr += "<td><a shh='" + json.rtlist[i]['orderno'] +"' href='#' >查看</a></td>"
                                                htmlStr += "</tr>";
                                            }
                                            htmlStr += "</tobdy></table>";
                                            $("#contentdiv").html(htmlStr);
                                            $("table > tbody > tr > td > a").click(function () {
                                                showinfos($(this).attr("shh"));
                                            });
                                        }
                                    }
                                })}
                        }
                    })
                    $("#pagecountdiv").html("<span class='label label-success'>" +
                            "总共数据<span class='badge'>" + json.totalcount + " </span></span>");
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                    htmlStr += "<th>订单号</th><th>数量</th><th>单价</th><th>查看明细</th>";
                    htmlStr += "</thead><tbody>";
                    for (var i = 1, l = json.rtlist.length; i < l; i++) {
                        htmlStr += "<tr shh='" + json.rtlist[i]['orderno'] +"'>";
                        htmlStr += "<td>" + json.rtlist[i]['orderno'] + "</td>"
                        htmlStr += "<td>" + json.rtlist[i]['num'] + "</td>"
                        htmlStr += "<td>" + nulltoempty(json.rtlist[i]['priceper']) + "</td>"
                        htmlStr += "<td><a shh='" + json.rtlist[i]['orderno'] +"' href='#' >查看</a></td>"
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    $("#contentdiv").html(htmlStr);
                    $("table > tbody > tr > td > a").click(function () {
                        showinfos($(this).attr("shh"));
                    });
                }
            }
        })
        function nulltoempty(val){
            if  (val=="null" || val==null)
                return "";
            else
                return val;
        }
        function  showinfos(ono) {
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
                                htmlStr += "<td id="+"code".concat(json.rtlist[j])+">" + json.rtlist[j]+ "</td>"
                            htmlStr += "</tr>";
                        }
                        htmlStr += "</tobdy></table>";
                        htmlStr += "<button onclick='mkqcode()'>生成二维码</button>";
                        htmlStr += "<button onclick='downqcode()'>直接打包下载</button>";
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
    }
    function refresh(){
        searchlist(1);
    }
</script>
</html>