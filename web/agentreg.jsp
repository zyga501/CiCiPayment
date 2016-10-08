<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" >
    <title>注册</title>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
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
    <script src="<%=request.getContextPath()%>/js/layer.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body onload="chakan()">
<form action="<%=request.getContextPath()%>/User!userreg" method="post">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="full-height-scroll">
        <div class="ibox-content">
                <div class="form-group">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">代理名称：</label>
                        <div class="col-sm-8">
                            <input type="text" name="username" placeholder="代理名称" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">密码：</label>
                        <div class="col-sm-8">
                            <input type="text" name="password" placeholder="密码" class="form-control">
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>
</form>
<div class="form-group">
    <div  >
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="javascript:$('form').submit()">确定</button>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script>
$().read(function(){
    });
    function checkone () {
        $.ajax({
        type: 'post',
        url: '<%=request.getContextPath()%>/User!userreg',
        dataType: "json",
        data: $("form").serialize(),
        success: function (data) {
        var json = eval("(" + data + ")");
        if (json.resultCode == 'Succeed') {
            layer.msg('操作成功', {icon: 1});
            parent.refresh();
        }
        else
        layer.msg('操作失败', {icon: 2});
        }
        })
    }
</script>
</body>
</html>

