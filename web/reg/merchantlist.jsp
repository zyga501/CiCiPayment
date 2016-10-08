<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
   // if  (!request.getSession().getAttribute("roletype").toString().equals("1"))
   //     request.getRequestDispatcher("page404.jsp").forward(request,response);
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
</head>
<body>
<form id="searchform">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="text" name="item" placeholder="查找商户" class="input form-control">
                            <span class="input-group-btn">
                                        <button type="button" class="btn btn btn-primary" onclick="searchlist(1)"> <i class="fa fa-search"></i> 搜索</button>
                                </span>
                </div>
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
    function searchlist(curr) {
        $("#pagecountdiv").html("");
        $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/Reg!Fetchmerchant',
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
                                    url: '<%=request.getContextPath()%>/Reg!Fetchmerchant',
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
                                            htmlStr += "<th>CC卡号</th><th>店名</th><th>联系电话</th>";
                                            htmlStr += "</thead><tbody>";
                                            for (var i = 1, l = json.length; i < l; i++) {
                                                htmlStr += "<tr shh='" + json[i]['id'] +"' sh='"+json[i]['openid']+"'>";
                                                htmlStr += "<td>" + json[i]['id'] + "</td>"
                                                htmlStr += "<td>" + nulltoempty(json[i]['name']) + "</td>"
                                                htmlStr += "<td>" + nulltoempty(json[i]['contactPhone']) + "</td>"
                                                htmlStr += "</tr>";
                                            }
                                            htmlStr += "</tobdy></table>";
                                            $("#contentdiv").html(htmlStr);
                                            $("table > tbody > tr").click(function () {
                                                showinfo($(this).attr("shh"),$(this).attr("sh"));
                                            });
                                        }
                                    }
                                })}
                        }
                    })
                    $("#pagecountdiv").html("<span class='label label-success'>" +
                            "总共数据<span class='badge'>" + json[0]["totalcount"] + " </span></span>");
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                    htmlStr += "<th>CC卡号</th><th>店名</th><th>联系电话</th>";
                    htmlStr += "</thead><tbody>";
                    for (var i = 1, l = json.length; i < l; i++) {
                        htmlStr += "<tr shh='" + json[i]['id'] +"' sh='"+json[i]['openid']+"'>";
                        htmlStr += "<td>" + json[i]['id'] + "</td>"
                        htmlStr += "<td>" + nulltoempty(json[i]['name']) + "</td>"
                        htmlStr += "<td>" + nulltoempty(json[i]['contactPhone']) + "</td>"
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    $("#contentdiv").html(htmlStr);
                    $("table > tbody > tr").click(function () {
                        showinfo($(this).attr("shh"),$(this).attr("sh"));
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
        function  showinfo(id,oid) {
            layer.open({
                type: 2,
                title:id,
                skin: 'layui-layer-demo',
                shade: 0.8,
                area: ['100%', '100%'],
                shadeClose: true, //开启遮罩关闭
                content: "<%=request.getContextPath()%>/Reg!selectone?cid="+id+"&openid="+oid
            });
        }
    }

    function refresh(){
        searchlist(1);
    }
</script>
</html>