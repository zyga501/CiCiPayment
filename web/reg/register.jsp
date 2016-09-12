<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>信息添加</title>
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
            <form action="User!reg1" method="post" class="form-horizontal">
                <div class="form-group">
                    <div class="form-group">
                    <label class="col-sm-3 control-label">商户名称：</label>
                    <div class="col-sm-8">
                        <input type="text" name="store" placeholder="商户名称" class="form-control" value="${reginfo.store}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">商户住址：</label>
                    <div class="col-sm-8">
                        <input type="text" name="address" placeholder="商户住址" class="form-control" value="${reginfo.address}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">联系人：</label>
                    <div class="col-sm-8">
                        <input type="text" placeholder="联系人" name="contact" class="form-control" value="${reginfo.contact}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">身份证：</label>
                    <div class="col-sm-8">
                        <input type="text" name="idcardno" placeholder="身份证" class="form-control" value="${reginfo.idcardno}">
                    </div>
                </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">手机号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="tel" placeholder="手机号" class="form-control" value="${reginfo.tel}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">推广号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="uname" placeholder="推广号" class="form-control"value="${reginfo.uname}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-8">
                            <a class="btn btn-lg btn-primary btn-block" href="#" onclick="$('form').submit()">下一步</a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script>
    function nextpage(){

        $.ajax({
            type: 'post',
            url: 'User!reg1',
            dataType:"json",
            data:$("form").serialize(),
            success: function (data) {
                var json =  eval("(" + data + ")");
                if ( json.resultCode =="Failed") {
                    if ( json.msg!="undefined" )
                      alert(json.msg);
                    else
                      alert("请检查信息是否规范");
                }
                window.location.href = json.url;
            },
            error: function (data) {
                alert("Error");
            }
        });
    }
</script>
</body>

</html>

