<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (!request.getSession().getAttribute("roletype").toString().equals("1"))
        request.getRequestDispatcher("page404.jsp").forward(request, response);
%>
<html>
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>注册</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap-combobox.css" rel="stylesheet" type="text/css"/>
    <script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/layer.min.js"></script>
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
    //<![CDATA[
    $(document).ready(function () {
        $('.combobox').combobox()
    });
    //]]>

    function sendmsg() {
        $.ajax({
            type: 'post',
            url: 'User!sendmakecardmsg',
            dataType: "json",
            data: $("form").serialize(),
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.resultCode == 'Succeed') {
                    layer.msg('操作成功', {icon: 1});
                    $("#idslist").text(json.idslist);
                }
                else
                    layer.msg('操作失败', {icon: 2});
            }
        })
    }
</script>
</body>
</html>

