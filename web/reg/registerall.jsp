<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" >
    <title>上传照片</title>
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
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="full-height-scroll">
        <div class="ibox-content">
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
                            <input type="text" placeholder="联系人" name="contact" class="form-control"  value="${reginfo.contactName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">身份证：</label>
                        <div class="col-sm-8">
                            <input type="text" name="idcardno" id="idcardno" placeholder="身份证" class="form-control"  value="${reginfo.idCard}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">手机号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="tel" id="tel" placeholder="手机号" class="form-control" value="${reginfo.contactPhone}" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">推广号：</label>
                        <div class="col-sm-8">
                            <input type="text" name="uname" placeholder="推广号" class="form-control" value="${reginfo.idCard}">
                        </div>
                    </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">开户名：</label>
                            <div class="col-sm-8">
                                <input type="text" placeholder="开户名" name="acountname" class="form-control" value="${reginfo.contactName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">开户市：</label>
                            <div class="col-sm-8">
                                <input  name="city" id="city" class="form-control" value="${reginfo.bankCity}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">银行：</label>

                            <div class="col-sm-8">
                                <input   name="bank" id="bank" placeholder="银行" class="form-control" value="${reginfo.bankName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">银行卡号：</label>

                            <div class="col-sm-8">
                                <input type="text" name="acountcode" placeholder="银行卡号" class="form-control" value="${reginfo.accountNo}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">开户行：</label>
                            <div class="col-sm-8">
                                <input   name="openbank" id="openbank"  class="form-control" value="${reginfo.accountName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">联行号：</label>
                            <div class="col-sm-8">
                                <input   name="contactnum" id="contactnum"  class="form-control" value="${reginfo.bankCode}">
                            </div>
                        </div>
                </div>
        </div>
    </div>
</div>
<form>
    <input type="hidden" name="cid" value="${reginfo.id}">
<table class="table table-bordered" >
    <tbody>
    <tr>
        <td>
            <input type="checkbox" checked id="wxpay" name="wxpay"><label for="wxpay">微信支付</label></td>
        <td>
            <input type="checkbox" checked id="alipay" name="alipay"><label for="alipay">支付宝支付</label></td>
        </td>
    </tr>
    <tr>
        <td>
            <input type="checkbox" checked id="jdpay" name="jdpay"><label for="jdpay">京东支付</label></td>
        <td>
            <input type="checkbox" checked id="bestpay" name="bestpay"><label for="bestpay">翼支付</label></td>
    </tr>
    <tr >
        <td colspan="2">
            <div class="form-group">
                <div  >
            <input type="checkbox"  name="canpay" id="canpay"><label for="canpay">开通支付</label>
                </div>
            </div>
        </td>
    </tr>
    </tbody>
</table>
    <input type="hidden" name="cid" value="<%=request.getParameter("cid")%>">
    <input type="hidden" name="openid" value="<%=request.getParameter("openid")%>">
</form>
<table class="table table-bordered" >
    <tbody>
    <tr>
        <td>
            <img class="img" id="fsfzz" style="width: 100px;height: 80px" src="#" ><p>身份证正面</p></td>
    </tr><tr>
        <td>
            <img class="img" id="fsfzf" style="width: 100px;height: 80px"><p>身份证反面</p>
        </td>
    </tr>
    <tr>
        <td>
            <img  class="img"  id="fscsfz" style="width: 80px;height: 80px" ><p>手持身份证</p></td>
    </tr><tr>
        <td>
            <img  class="img"  id="fyhk"style="width: 100px;height: 80px"><p>银行卡</p></td>
    </tr>
    <tr >
        <td colspan="2">
            <div class="form-group">
                <div  >
                    <button class="btn btn-lg btn-primary btn-block" type="button" onclick="checkone()">审核通过</button>
                </div>
            </div>
        </td>
    </tr>
    </tbody>
</table>
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script>
    $().read(function(){
    });
    function chakan(){
        var openid="<%=request.getAttribute("openid").toString()%>";
        var cid="<%=request.getAttribute("cid").toString()%>";
        try{
            $("#fsfzz").attr("src","<%=request.getContextPath()%>/Reg!checkinfo?picname=sfzz&cid="+cid+"&openid="+openid);
        }
        catch(e) {

        };
        try{
            $("#fsfzf").attr("src","Reg!checkinfo?picname=sfzf&cid="+cid+"&openid="+openid);
        }
        catch(e) {

        };
        try{
            $("#fscsfz").attr("src","Reg!checkinfo?picname=scsfz&cid="+cid+"&openid="+openid);
        }
        catch(e) {

        };
        try{
            $("#fyhk").attr("src","Reg!checkinfo?picname=yhk&cid="+cid+"&openid="+openid);
        }
        catch(e) {

        }
        $(".img").on("click", function () {
            layer.full(layer.open({
                type: 1,
                skin: 'layui-layer-demo', //样式类名
                closeBtn: 1, //不显示关闭按钮
                shade: 0.8,
                area: ['100%', '100%'],
                shadeClose: true, //开启遮罩关闭
                content:'<img  src="'+this.src+'" style="width:90%">'
            }));
        });
    }

    function checkone () {
        $.ajax({
        type: 'post',
        url: '<%=request.getContextPath()%>/Reg!checkone',
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

