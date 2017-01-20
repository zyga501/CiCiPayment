<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
    <script>
        function reloadimg(){
            $("#checkimg").attr("src", "<%=request.getContextPath()%>/Register!randCheckimg"+ "?nocache=" + new Date().getTime());
        }
        function checkvalidator(obj) {
            $.ajax({
                url: '<%=request.getContextPath()%>/Register!chkrandval?randval='+$(obj).val(),
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.resultCode == 'Succeed') {
                        $("#msg").text("通过");
                    } else {
                        $("#msg").text("验证码错误");
                        reloadimg();
                    }
                }
            })
        }
        function postform() {
            $.ajax({
                type: 'post',
                url: '<%=request.getContextPath()%>/Register!addmultcode',
                dataType: "json",
                data: $("form").serialize(),
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.resultCode == 'Succeed') {
                        alert("操作成功！");
                        window.location.reload();
                    } else {
                        alert("操作错误！"+json.errormsg);
                        reloadimg();
                    }
                }
            })
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="full-height-scroll">
        <div class="ibox-content">
            <form  class="form-horizontal">
                <div class="form-group">
                    <div class="form-group">
                    <label class="col-sm-3 control-label">验证码：</label>
                    <div class="col-sm-4">
                        <input type="text" name="randval"  placeholder="先输入验证码" class="form-control" onblur="checkvalidator(this)" >
                    </div>
                        <div class="col-sm-2">
                            <img style="width: 80px" src="Register!randCheckimg" id="checkimg" onclick="reloadimg()">
                        </div>
                        <div class="col-sm-3">
                            <span id="msg" style="color: #ff0000"></span>
                        </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">新CC码：</label>
                    <div class="col-sm-8">
                        <input type="text" placeholder="CC码" name="cid" class="form-control" value="${reginfo.contactName}">
                    </div>
                </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">推广号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="uname" id="uname" placeholder="推广号" class="form-control"value="${uname}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-8">
                            <a class="btn btn-lg btn-primary btn-block" href="#" onclick="postform()">提交</a>
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
</script>
</body>

</html>

