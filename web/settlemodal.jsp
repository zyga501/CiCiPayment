<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (   (!request.getSession().getAttribute("roletype").toString().equals("111")))
        request.getRequestDispatcher("page404.jsp").forward(request, response);
%>
<html>
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title></title>
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
<form action="<%=request.getContextPath()%>/User!editSettle" method="post">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="full-height-scroll">
            <div class="ibox-content">
                <div class="form-group" id="listdiv">
                    <div class="row"><label class="col-md-3 control-label">代付模式</label>
                        <div class="col-md-9">
                            <select class="combobox input-large form-control" id="settleMethod" name="settleMethod">
                                <option value=0>T0</option>
                                <option value=1>T1</option>
                            </select>
                        </div>
                    </div>

                    <div class="row"><label class="col-md-3 control-label">T0下限金额</label>
                        <div class="col-md-9">
                            <input class="form-control" type="number" id="settleLimit" name="settleLimit" value="10">
                        </div>
                    </div>
                    <div class="row"><label class="col-md-3 col-md-offset-4 control-label warning" style="color: red">注意：修改后重启服务才能起效</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
<div class="form-group">
    <div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" >确定</button>
    </div>
</div
</form><script src="/js/bootstrap-combobox.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $.ajax({
            type: 'post',
            url: 'User!getSettleInfo',
            dataType: "json",
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.resultCode == 'Succeed') {
                    $("#settleLimit").val(json.settleLimit);
                    $("#settleMethod option[value="+json.settleMethod+"]").attr("selected", true);
                }
            }
        })
    })
</script>
</body>
</html>