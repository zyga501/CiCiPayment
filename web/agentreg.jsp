<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" >
    <title>注册</title>
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
    <script src="<%=request.getContextPath()%>/js/laypage.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body >
<form>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="full-height-scroll">
        <div class="ibox-content">
            <input type="hidden" name="agentid" value="${agentinfo.id}" />
                <div class="form-group col-lg-8 col-lg-offset-2">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">代理名称：</label>
                        <div class="col-sm-5">
                            <input type="text" name="agentname" placeholder="代理名称" class="form-control" value="${agentinfo.name}">
                        </div>
                        <div class="col-sm-4">
                            <button placeholder="代理名称" class="btn btn-warning form-control" onclick="chgpwd()">初始化密码(1234567)</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系电话：</label>
                        <div class="col-sm-9">
                            <input type="text" name="contactphone" placeholder="联系电话" class="form-control" value="${agentinfo.contactPhone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开户城市：</label>
                        <div class="col-sm-9">
                            <input type="text" name="bankcity" placeholder="开户城市" class="form-control" value="${agentinfo.bankCity}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开户行：</label>
                        <div class="col-sm-9">
                            <input type="text" name="bankname" placeholder="开户行" class="form-control" value="${agentinfo.bankName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">联行号：</label>
                        <div class="col-sm-9">
                            <input type="text" name="bankcode" placeholder="联行号" class="form-control" value="${agentinfo.bankCode}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">银行账号：</label>
                        <div class="col-sm-9">
                            <input type="text" name="accountno" placeholder="银行账号" class="form-control" value="${agentinfo.accountNo}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开户名：</label>
                        <div class="col-sm-9">
                            <input type="text" name="accountname" placeholder="开户名" class="form-control" value="${agentinfo.accountName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">留行电话：</label>
                        <div class="col-sm-9">
                            <input type="text" name="accountphone" placeholder="留行电话" class="form-control" value="${agentinfo.accountPhone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">微信分润：</label>
                        <div class="col-sm-4">
                            <input type="text" name="wxprofit" placeholder="微信分润" class="form-control" value="${agentinfo.wxProfit}">
                        </div>
                        <label class="col-sm-2 control-label">成本：</label>
                        <div class="col-sm-4">
                            <input type="text" name="wxcost" placeholder="成本" class="form-control" value="${agentinfo.wxCost}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">翼支付分润：</label>
                        <div class="col-sm-4">
                            <input type="text" name="bestprofit" placeholder="微信分润" class="form-control" value="${agentinfo.bestProfit}">
                        </div>
                        <label class="col-sm-2 control-label">成本：</label>
                        <div class="col-sm-4">
                            <input type="text" name="bestcost" placeholder="成本" class="form-control" value="${agentinfo.bestCost}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">支付宝分润：</label>
                        <div class="col-sm-4">
                            <input type="text" name="aliprofit" placeholder="支付宝分润" class="form-control" value="${agentinfo.aliProfit}">
                        </div>
                        <label class="col-sm-2 control-label">成本：</label>
                        <div class="col-sm-4">
                            <input type="text" name="alicost" placeholder="成本" class="form-control" value="${agentinfo.aliCost}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">京东分润：</label>
                        <div class="col-sm-4">
                            <input type="text" name="jdprofit" placeholder="微信分润" class="form-control" value="${agentinfo.jdProfit}">
                        </div>
                        <label class="col-sm-2 control-label">成本：</label>
                        <div class="col-sm-4">
                            <input type="text" name="jdcost" placeholder="成本" class="form-control" value="${agentinfo.jdCost}">
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>
</form>
<div class="form-group col-lg-8 col-lg-offset-2">
    <div  >
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="submitform()">确定</button>
    </div>
</div>
<script>
    function chgpwd(){
        $.ajax({
            type: 'post',
            url: '<%=request.getContextPath()%>/User!initPassword',
            dataType: "json",
            data: $("form").serialize(),
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.resultCode == "Succeed") {
                    alert("操作成功！");
                }
            }
        })
    }

    function submitform(){
       <% if (request.getSession().getAttribute("dotype").toString().compareTo("select")==0) {%>
        parent.layer.closeAll();
       <%} else if (request.getSession().getAttribute("dotype").toString().compareTo("edit")==0) {%>
       $.ajax({
           type: 'post',
           url: '<%=request.getContextPath()%>/User!editAgent',
           dataType: "json",
           data: $("form").serialize()  ,
           success: function (data) {
               var json = eval("(" + data + ")");
               if (json.resultCode=="Succeed"){
                   alert("操作成功！");
                   parent.layer.closeAll();}
           }
       })
       <%} else if (request.getSession().getAttribute("dotype").toString().compareTo("insert")==0) {%>
       $.ajax({
           type: 'post',
           url: '<%=request.getContextPath()%>/User!insertAgent',
           dataType: "json",
           data: $("form").serialize(),
           success: function (data) {
               var json = eval("(" + data + ")");
               if (json.resultCode=="Succeed"){
                   alert("操作成功！");
                   parent.layer.closeAll();}
           }
       })
       <%}%>
   }
</script>
</body>
</html>

