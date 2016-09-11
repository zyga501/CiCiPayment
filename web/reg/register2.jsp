<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>银行信息</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/style.min.css?v=4.1.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="full-height-scroll">
        <div class="ibox-content">
            <form action="User!reg2" method="post" class="form-horizontal">
                <div class="form-group">
                    <div class="form-group">
                    <label class="col-sm-3 control-label">开户省：</label>
                    <div class="col-sm-8">
                        <select  name="province" id="province"  class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户市：</label>
                    <div class="col-sm-8">
                        <input type="text"   name="accountpc"  id="city" class="form-control" value="${reginfo.accountpc}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户名：</label>
                    <div class="col-sm-8">
                        <input type="text" placeholder="开户名" name="acountname" class="form-control" value="${reginfo.acountname}">
                    </div>
                </div>
                    <div class="form-group">
                        <div class="col-sm-4 control-label">
                        <img id="verifyImg" src="https://www.hebbank.com/corporbank/VerifyImage" align="absmiddle" onclick="this.src='https://www.hebbank.com/corporbank/VerifyImage?update=' + Math.random()" style="cursor:pointer;" title="获取验证码">
                        </div>
                        <div class="col-sm-7">
                            <input type="text" name="checkCode" id="checkCode" placeholder="验证码" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开户行：</label>
                        <div class="col-sm-8">
                            <input type="text" name="bank" placeholder="开户行" class="form-control" value="${reginfo.bank}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行卡号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="acountcode" placeholder="银行卡号" class="form-control" value="${reginfo.acountcode}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">联行号：</label>
                        <div class="col-sm-5">
                            <input type="text" name="contactnum" placeholder="联行号" class="form-control" value="${reginfo.contactnum}">
                        </div>
                        <div class="col-sm-3">
                           <button class=" btn btn-sm btn-primary btn-block" type="button" onclick="getbankinfo();">获_取</button>
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
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script src="<%=request.getContextPath()%>/js/pccs.js"  ></script>
<script>
    function nextpage(){
        $.ajax({
            type: 'post',
            url: 'User!reg2',
            dataType:"json",
            data:$("form").serialize(),
            success: function (data) {
                alert(data);
                var json =  eval("(" + data + ")");
                window.location.href = json.url;
            },
            error: function (data) {
                alert("Error");
            }
        });
    }

   function getbankinfo(){
       alert($("#checkCode").val());
       /*$.ajax({
           type: 'get',
           url: 'https://www.hebbank.com/corporbank/webBankQueryAjax.do?checkCode='+$("#checkCode").val()+'&bankType=103&cityCode=3450&bankName=城',
           dataType: 'JSONP',
           jsonpCallback: 'callback',
           // data:$("form").serialize(),
           success: function (data) {
               alert(data);
           }
       });
       */
           c_url = 'https://www.hebbank.com/corporbank/webBankQueryAjax.do?checkCode='+$("#checkCode").val()+'&bankType=103&cityCode=3450&bankName=城';
           //注意下面只需一个问号，不用具体的函数名
           c_url += '&jsonp=?'
           $.get(c_url, function(data){
               //这里要这么处理、放到哪里就看你自己喜欢咯.而且这还和你博客使用的模板有关的哦
               console.log(data)
           });
   }
    function callback(dd){
        alert(dd);
    }
</script>
</body>

</html>

