<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    if ((!request.getSession().getAttribute("roletype").toString().equals("0"))&&
            (!request.getSession().getAttribute("roletype").toString().equals("111")))
        request.getRequestDispatcher("page404.jsp").forward(request, response);
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加新卡</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/style.min.css?v=4.1.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="full-height-scroll">
        <div class="ibox-content">
            <form  class="form-horizontal" action ="/User!changPayWay">
                <div class="form-group">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">微信通道：</label>
                        <div class="col-sm-9">
                            <select name="weixinway" id="weixinway" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">支付宝通道：</label>
                        <div class="col-sm-9">
                            <select name="aliway" id="aliway" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">京东通道：</label>
                        <div class="col-sm-9">
                            <select name="jdway" id="jdway" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">翼支付通道：</label>
                        <div class="col-sm-9">
                            <select name="bestway" id="bestway" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-8">
                            <button type="submit" class="btn btn-lg btn-primary btn-block">提交</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/IDValidator.js"></script>
<script>
    $().ready(function(){
        $.ajax({
            url: '<%=request.getContextPath()%>/User!fetchNowPaytype',
            success: function (data) {
                var json = eval("(" + data + ")");
                var wxid=json[0].payMethodWeixinId;
                var aliid=json[0].payMethodAliId;
                var jdid=json[0].payMethodJDId;
                var bestid=json[0].payMethodBestId;
                $.ajax({
                    url: '<%=request.getContextPath()%>/User!fetchAllPaytype',
                    success: function (data) {
                        var json = eval("(" + data + ")");
                        for (var i = 0; i < json.length; i++) {
                            if (json[i].payType == "WEIXIN") {
                                if (json[i].id == wxid)
                                    $('#weixinway').append("<option value='" + json[i].id + "' selected>" + json[i].comment + "</option>");
                                else
                                    $('#weixinway').append("<option value='" + json[i].id + "'>" + json[i].comment + "</option>");
                            } else if (json[i].payType == "ALI") {
                                if (json[i].id == aliid)
                                    $('#aliway').append("<option value='" + json[i].id + "' selected>" + json[i].comment + "</option>");
                                else
                                    $('#aliway').append("<option value='" + json[i].id + "'>" + json[i].comment + "</option>");
                            }
                        }
                    }
                })
            }
        })
    });
</script>
</body>

</html>

