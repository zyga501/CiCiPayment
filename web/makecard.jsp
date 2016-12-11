<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ((!request.getSession().getAttribute("roletype").toString().equals("1"))&&
            (!request.getSession().getAttribute("roletype").toString().equals("111")))
        request.getRequestDispatcher("page404.jsp").forward(request, response);
%>
<html>
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>注册</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/style.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap-combobox.css" rel="stylesheet" type="text/css"/>
    <script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/layer.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-qrcode-0.14.0.js"></script>
</head>
<body>
<form>
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="full-height-scroll">
            <div class="ibox-content">
                <div class="form-group" id="listdiv">
                    <div class="row"><label class="col-md-3 control-label">销售/代理</label>
                        <div class="col-md-9">
                            <select class="combobox input-large form-control" name="agentid">
                                <s:iterator id="map" value="#request.session.userList" status="L">
                                    <option value=
                                            <s:property value="#map.id"/>><s:property value="#map.username"/>
                                    </option>
                                </s:iterator>
                            </select>
                        </div>
                    </div>

                    <div class="row"><label class="col-md-3 control-label">所需数量</label>
                        <div class="col-md-9">
                            <input class="form-control" type="number" name="cardnum" value="100">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="form-group">
    <div>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="sendmakecardmsg()">确定</button>
    </div>
</div>
<div id="idslist"></div>
<script src="/js/bootstrap-combobox.js" type="text/javascript"></script>
<script type="text/javascript">
    var codear;
    //<![CDATA[
    $(document).ready(function () {
        $('.combobox').combobox()
    });
    //]]>
    function sendmakecardmsg() {
        $("#idslist").text("");
        $.ajax({
            type: 'post',
            url: 'User!sendMakeCardMsg',
            dataType: "json",
            data: $("form").serialize(),
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.resultCode == 'Succeed') {
                    layer.msg('操作成功', {icon: 1});
                    $("#idslist").text(json.idslist);
                    codear = json.idslist.split(",");
                    var htmlStr = "<table class='table table-striped table-bordered table-hover'>";
                    htmlStr += "<tbody>";alert( Math.ceil(codear.length / 5));
                    for (var i = 0, l = Math.ceil(codear.length / 5); i < l; i++) {
                        htmlStr += "<tr>";
                        for (var j=i*5;j<Math.min(codear.length,(i+1)*5);j++)
                            htmlStr += "<td id="+"code".concat(codear[j])+">" + codear[j]+ "</td>"
                        htmlStr += "</tr>";
                    }
                    htmlStr += "</tobdy></table>";
                    htmlStr += "<button onclick='mkqcode()'>生成二维码</button>";
                    htmlStr += "<button onclick='downqcode()'>直接打包下载</button>";
                    $("#idslist").html(htmlStr);

                }
                else
                    layer.msg('操作失败', {icon: 2});
            }
        })
    }
    function downqcode() {
        $("#idslist").text("");
        $.ajax({
            type: 'post',
            url: 'User!downQcode',
            dataType: "json",
            data: $("form").serialize(),
            success: function (data) {
                var json = eval("(" + data + ")");
            }
        })
    }

    function mkqcode(){
        var options;
        for (var i = 0 ; i <  codear.length; i++) {
            if (codear[i] != "") {
                options = null;
                options = {
                    text: "www.jeanhk.top/CiCiPayment/Pay!payAdapter?cid="+codear[i], //二维码的链接
                    dom: "#code" + codear[i],//二维码生成的位置
                    size: 100,
                    render: "image"//设置生成的二维码是canvas格式，也有image、div格式
                }
                $(options.dom).qrcode(options);
            }
        }
    }
</script>
</body>
</html>