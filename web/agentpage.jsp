<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
  if ((!request.getSession().getAttribute("roletype").toString().equals("111")))
    request.getRequestDispatcher("page404.jsp").forward(request, response);
%>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
  <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
  <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
  <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
  <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
  <link href="<%=request.getContextPath()%>/css/layer.ext.css" rel="stylesheet" type="text/css"/>
  <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
  <script src="<%=request.getContextPath()%>/js/laypage.js"></script>
  <script src="<%=request.getContextPath()%>/js/laydate.js"></script>
</head>
<body>
<form id="searchform">
  <div class="container">
    <div class="row">
      <div class="form-group">
        <label class="col-sm-2">代理推广号</label>
        <div class="col-sm-4">
          <input class="form-control" name="agentid">
        </div>
        <label class="col-sm-2">代理名称</label>
        <div class="col-sm-4">
          <input class="form-control" name="agentname">
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-2 col-sm-offset-5">
          <input class="form-control btn btn-primary" type="button" value="检索" onclick="search(1)">
        </div>
      </div>
    </div>
  </div>
</form>
<HR align=center width=100% color=#987cb9>
<div style="float:left" id="pagecountdiv"></div>
<div style="float:right;text-align: center" id="navigatediv"></div>
<center><div id="contentdiv"></div></center>
</body>
<script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dateRangeUtil.js"></script>
<script>
  function search(curr) {
    $("#pagecountdiv").html("");
    $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
    $.ajax({
      type: 'post',
      url: '<%=request.getContextPath()%>/User!fetchAgent',
      dataType: "json",
      data: $("#searchform").serialize() + "&currpagenum=" + curr,
      success: function (data) {
        var json = eval("(" + data + ")");
        if (json.errorMessage != null) {
          $("#contentdiv").html(json.errorMessage);
        }
        else {
          $("#contentdiv").html("");
          laypage({
            cont: 'navigatediv',
           // pages: json[0].pagecount,
            skip: true,
            skin: 'yahei',
            jump: function (obj, first) {
              if(!first){
                $("#pagecountdiv").html("");
                $("#contentdiv").html("<img  src='<%=request.getContextPath()%>/img/loading.gif'>");
                $.ajax({
                  type: 'post',
                  url: '<%=request.getContextPath()%>/User!fetchAgent',
                  dataType: "json",
                  data: $("#searchform").serialize() + "&currpagenum=" + obj.curr,
                  success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.errorMessage != null) {
                      $("#contentdiv").html(json.errorMessage);
                    }
                    else {
                      $("#pagecountdiv").html("<span class='label label-success'>" +
                              "总共数据<span class='badge'>" + json.totalcount + " </span></span>");
                      var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
                      htmlStr += "<th>推广号</th><th>代理</th><th>联系电话</th><th>操作</th>";
                      htmlStr += "</thead><tbody>";
                      for (var i = 0; (i< json.rtlist.length)&&(null!=json.rtlist);  i++) {
                        htmlStr += "<tr shh='" + json.rtlist[i]['id'] +"'>";
                        htmlStr += "<td>" + json.rtlist[i]['id'] + "</td>"
                        htmlStr += "<td>" + nulltoempty(json.rtlist[i]['name']) + "</td>"
                        htmlStr += "<td>" + nulltoempty(json.rtlist[i]['contactPhone']) + "</td>"
                        htmlStr += "</tr>";
                      }
                      htmlStr += "</tobdy></table>";
                      $("#contentdiv").html(htmlStr);
                      $("table > tbody > tr").click(function () {
                        showinfo($(this).attr("shh"),$(this).attr("sh"));
                      });
                    }
                  }
                })}
            }
          })
          $("#pagecountdiv").html("<span class='label label-success'>" +
                  "总共数据<span class='badge'>" + json.totalcount + " </span></span>");
          var htmlStr = "<table class='table table-striped table-bordered table-hover'>" + "<thead>";
          htmlStr += "<th>推广号</th><th>代理</th><th>联系电话</th><th>操作" +
                  "<input type='button' class='btn btn-sm btn-primary charu' value='+新增' /></th>";
          htmlStr += "</thead><tbody>";
          for (var i =0 ;(i< json.rtlist.length)&&(null!=json.rtlist); i++) {
            htmlStr += "<tr shh='" + json.rtlist[i]['id'] +"' sh='"+json.rtlist[i]['name']+"'>";
            htmlStr += "<td>" + json.rtlist[i]['id'] + "</td>"
            htmlStr += "<td>" + nulltoempty(json.rtlist[i]['name']) + "</td>"
            htmlStr += "<td>" + nulltoempty(json.rtlist[i]['contactPhone']) + "</td>"
            htmlStr += "<td><input type='button' class='btn btn-sm btn-info chakan' value='查看' />" +
                    "<input type='button' class='btn btn-sm btn-danger bianji' value='编辑' /></td>"
            htmlStr += "</tr>";
          }
          htmlStr += "</tobdy></table>";
          $("#contentdiv").html(htmlStr);
          $("table > tbody > tr  .chakan").click(function () {
            showinfo($(this).parent().parent().attr("shh"),$(this).parent().parent().attr("sh"),"select");
          });
          $("table > tbody > tr  .bianji").click(function () {
            showinfo($(this).parent().parent().attr("shh"),$(this).parent().parent().attr("sh"),"edit");
          });
          $("table    .charu").click(function () {
            showinfo(0,'新增','insert');
          });
        }
      }
    })
    function nulltoempty(val){
      if  (val=="null" || val==null)
        return "";
      else
        return val;
    }
    function  showinfo(id,oid,dotype) {
      layer.open({
        type: 2,
        title:oid+"      "+dotype,
        skin: 'layui-layer-demo',
        shade: 0.8,
        area: ['100%', '100%'],
        shadeClose: true, //开启遮罩关闭
        content: "<%=request.getContextPath()%>/User!selectOneAgent?id="+id+"&dotype="+dotype
      });
    }
  }
  function refresh(){
    searchlist(1);
  }
</script>
</html>