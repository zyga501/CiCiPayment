<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ((!request.getSession().getAttribute("roletype").toString().equals("0")) &&
            (!request.getSession().getAttribute("roletype").toString().equals("111")))
        request.getRequestDispatcher("page404.jsp").forward(request, response);
%>
<html>
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>上传照片</title>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <style>
        td {
            text-align: center;
            margin: 0 auto;
            height: 160px;
        }

        caption {
            text-align: center;
            margin: 0 auto;
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
                        <input type="text" name="address" placeholder="商户住址" class="form-control"
                               value="${reginfo.address}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">联系人：</label>

                    <div class="col-sm-8">
                        <input type="text" placeholder="联系人" name="contact" class="form-control"
                               value="${reginfo.contactName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">身份证：</label>

                    <div class="col-sm-8">
                        <input type="text" name="idcardno" id="idcardno" placeholder="身份证" class="form-control"
                               value="${reginfo.idCard}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">手机号：</label>

                    <div class="col-sm-8">
                        <input type="text" name="tel" id="tel" placeholder="手机号" class="form-control"
                               value="${reginfo.contactPhone}">
                    </div>
                </div>
                <div class="form-group" style="display: none">
                    <label class="col-sm-3 control-label">推广号：</label>

                    <div class="col-sm-8">
                        <input type="text" name="uname" placeholder="推广号" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户名：</label>

                    <div class="col-sm-8">
                        <input type="text" placeholder="开户名" name="acountname" class="form-control"
                               value="${reginfo.contactName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户市：</label>

                    <div class="col-sm-8">
                        <input name="city" id="city" class="form-control" value="${reginfo.bankCity}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">银行：</label>

                    <div class="col-sm-8">
                        <input name="bank" id="bank" placeholder="银行" class="form-control" value="${reginfo.bankName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">银行卡号：</label>

                    <div class="col-sm-8">
                        <input type="text" name="acountcode" placeholder="银行卡号" class="form-control"
                               value="${reginfo.accountNo}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户行：</label>

                    <div class="col-sm-8">
                        <input name="openbank" id="openbank" class="form-control" value="${reginfo.accountName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">联行号：</label>

                    <div class="col-sm-8">
                        <input name="contactnum" id="contactnum" class="form-control" value="${reginfo.bankCode}">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<form>
    <input type="hidden" name="cid" value="${reginfo.cid}">
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td>
                <input type="checkbox" checked id="wxpay" name="wxpay"><label for="wxpay">微信支付</label>
                <br><label>费率</label><input type="number" id="wxrate" name="wxrate" placeholder="微信费率" step="0.001"
                                            value="0.006">
            </td>
            <td>
                <input type="checkbox" checked id="alipay" name="alipay"><label for="alipay">支付宝支付</label>
                <br><label>费率</label><input type="number" id="alirate" name="alirate" placeholder="支付宝费率" step="0.001"
                                            value="0.006"></td>
            </td>
        </tr>
        <tr>
            <td>
                <input type="checkbox" checked id="jdpay" name="jdpay"><label for="jdpay">京东支付</label>
                <br><label>费率</label><input type="number" id="jdrate" name="jdrate" placeholder="京东支付费率" step="0.001"
                                            value="0.006"></td>
            <td>
                <input type="checkbox" checked id="bestpay" name="bestpay"><label for="bestpay">翼支付</label>
                <br><label>费率</label><input type="number" id="bestrate" name="bestrate" placeholder="翼支付费率" step="0.001"
                                            value="0.006"></td>
        </tr>
        <tr>
            <td colspan="2">
                <div class="form-group">
                    <div>
                        <input type="checkbox" name="canpay" id="canpay"><label for="canpay">开通支付</label>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <input type="hidden" name="cid" value="<%=request.getParameter("cid")%>">
    <input type="hidden" name="openid" value="<%=request.getParameter("openid")%>">
</form>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td>
            <img class="img" id="fsfzz" style="width: 100px;height: 80px" src="#">

            <p>身份证正面</p>

            <p>${reginfo.idCard}</p>
        </td>
        <td>
            <img class="img" id="fsfzf" style="width: 100px;height: 80px">

            <p>身份证反面</p>
        </td>
    </tr>
    <tr>
        <td>
            <img class="img" id="fscsfz" style="width: 80px;height: 80px">

            <p>手持身份证</p></td>
        <td>
            <img class="img" id="fyhk" style="width: 100px;height: 80px">

            <p>银行卡</p></td>
    </tr>
    <tr>
        <td>
            <img class="img" id="fyyzz" style="width: 100px;height: 80px">

            <p>营业执照</p></td>
        <td><img class="img" id="fswdjzp" style="width: 100px;height: 80px">

            <p>税务登记照片</p></td>
    </tr>
    <tr>
        <td>
            <img class="img" style="width: 100px;height: 80px" id="fzzjgdmz">

            <p>组织机构代码证照片</p></td>
        <td>
            <img class="img" style="width: 100px;height: 80px" id="ffrsfzz">

            <p>法人身份证照片正面</p></td>
    </tr>
    <tr>
        <td>
            <img class="img" style="width: 100px;height: 80px" id="ffrsfzf">

            <p>法人身份证照片反面</p></td>
        <td>
            <img class="img" style="width: 100px;height: 80px" id="fshsyt">

            <p>商户收银台照片</p></td>
    </tr>
    <tr>
        <td>
            <img class="img" style="width: 100px;height: 80px" id="fshmtz">

            <p>商户门头照片</p></td>
        <td>
            <img class="img" style="width: 100px;height: 80px" id="fdncs">

            <p>店内陈饰照片</p></td>
    </tr>
    <tr>
        <td colspan="2">
            <div class="form-group">
                <div>
                    <button class="btn btn-lg btn-primary btn-block" type="button" onclick="checkone()">确认</button>
                </div>
            </div>
        </td>
    </tr>
    </tbody>
</table>
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script>
    function chakan() {
        var openid = "<%=request.getAttribute("openid").toString()%>";
        var cid = "<%=request.getAttribute("cid").toString()%>";
        try {
            $("#fsfzz").attr("src", "<%=request.getContextPath()%>/Register!checkInfo?picname=sfzz&cid=" + cid + "&openid=" + openid);
        }
        catch (e) {
        }
        ;
        try {
            $("#fsfzf").attr("src", "Register!checkInfo?picname=sfzf&cid=" + cid + "&openid=" + openid);
        }
        catch (e) {
        }
        ;
        try {
            $("#fscsfz").attr("src", "Register!checkInfo?picname=scsfz&cid=" + cid + "&openid=" + openid);
        }
        catch (e) {
        }
        ;
        try {
            $("#fyhk").attr("src", "Register!checkInfo?picname=yhk&cid=" + cid + "&openid=" + openid);
        }
        catch (e) {
        }
        $("#fyyzz").attr("src", "Register!checkInfo?picname=yyzz&cid=" + cid + "&openid=" + openid);
        $("#fswdjzp").attr("src", "Register!checkInfo?picname=swdjzp&cid=" + cid + "&openid=" + openid);
        $("#fzzjgdmz").attr("src", "Register!checkInfo?picname=zzjgdmz&cid=" + cid + "&openid=" + openid);
        $("#ffrsfzz").attr("src", "Register!checkInfo?picname=frsfzz&cid=" + cid + "&openid=" + openid);
        $("#ffrsfzf").attr("src", "Register!checkInfo?picname=frsfzf&cid=" + cid + "&openid=" + openid);
        $("#fshsyt").attr("src", "Register!checkInfo?picname=shsyt&cid=" + cid + "&openid=" + openid);
        $("#fshmtz").attr("src", "Register!checkInfo?picname=shmtz&cid=" + cid + "&openid=" + openid);
        $("#fdncs").attr("src", "Register!checkInfo?picname=dncs&cid=" + cid + "&openid=" + openid);
        $(".img").on("click", function () {
            layer.full(layer.open({
                type: 1,
                skin: 'layui-layer-demo', //样式类名
                closeBtn: 1, //不显示关闭按钮
                shade: 0.8,
                area: ['100%', '100%'],
                shadeClose: true, //开启遮罩关闭
                content: '<img  src="' + this.src + '" style="width:90%">'
            }));
        });
    }

    function checkone() {
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/Register!checkOne',
            dataType: "json",
            data: $("form").serialize(),
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.resultCode == 'Succeed') {
                    alert('操作成功');
                    parent.refresh();
                    parent.layer.closeAll();
                }
                else
                    alert('操作失败');
            }
        })
    }
</script>
</body>
</html>