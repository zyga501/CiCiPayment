<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>注册</title>
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
<form>
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="full-height-scroll">
            <div class="ibox-content">
                <table border="1" width="100%">
                    <tr>
                        <td rowspan=5>
                            <div class="form-group" id="listdiv"><ul class="list-group">
                                <s:iterator id="map" value="#request.session.userList" status="L">
                                    <li class="list-group-item"><input type="checkbox" name="ulist" value=
                                        <s:property value="#map.id"/>><s:property value="#map.name"/>
                                    </li>
                                </s:iterator>
                                </ul>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <span class="badge"> 标题：</span>
                                </div>
                                <div class="panel-footer">
                                    <textarea class="form-control" placeholder="通告" rows="1" name="msgstr"></textarea></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <span class="badge"> 时间范围：</span>
                                </div>
                                <div class="panel-footer">
                                    <textarea class="form-control" rows="1" name="msgstr"></textarea></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="panel panel-default">
                                <div class="panel-body">
                                   <span class="badge"> 内容：</span>
                                </div>
                                <div class="panel-footer">
                                    <textarea class="form-control" rows="2" name="msgstr"></textarea></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="panel panel-default">
                                <div class="panel-body">
                                 <span class="badge">   附记：</span>
                                </div>
                                <div class="panel-footer">
                                    <textarea class="form-control" rows="2" name="msgstr"></textarea></div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>
<div class="form-group">
    <div>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="sendmsg()">确定</button>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script>
    function sendmsg() {
        $.ajax({
            type: 'post',
            url: 'User!sendMsg',
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

