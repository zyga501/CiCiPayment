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
            <form action="Reg!reg1" method="post" class="form-horizontal">
                <input type="hidden" name="cid" value="${reginfo.id}">
                <input type="text"  value="${reginfo.id}">
                <div class="form-group">
                    <div class="form-group">
                    <label class="col-sm-3 control-label">商户名称：</label>
                    <div class="col-sm-8">
                        <input type="text" name="name" placeholder="商户名称" class="form-control" value="${reginfo.name}">
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
                        <input type="text" placeholder="联系人" name="contactName" class="form-control" value="${reginfo.contactName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">身份证：</label>
                    <div class="col-sm-8">
                        <input type="text" name="idCard" id="idCard" placeholder="身份证" class="form-control" value="${reginfo.idCard}">
                    </div>
                </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">手机号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="contactPhone" id="contactPhone" placeholder="手机号" class="form-control" value="${reginfo.contactPhone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">推广号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="uname" placeholder="推广号" class="form-control"value="${uname}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-8">
                            <a class="btn btn-lg btn-primary btn-block" href="#" onclick="checkvalidator()">下一步</a>
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
    function checkvalidator(){
        var idv = new IDValidator();
        if (!idv.isValid($("#idCard").val())){
            alert("身份证号码不符合要求");
            $("#idCard").focus();
            return;
        }
        if (!idv.checkMobile($("#contactPhone").val())){
            alert("手机号码不符合要求");
            $("#contactPhone").focus();
            return;
        }
        $('form').submit();
    }

    function nextpage(){
        $.ajax({
            type: 'post',
            url: 'Reg!reg1',
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

