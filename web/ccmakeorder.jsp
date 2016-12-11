<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  if  (!request.getSession().getAttribute("roletype").toString().equals("0"))
    request.getRequestDispatcher("page404.jsp").forward(request,response);
%>
<html>
  <head>
    <title></title>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <script src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/layer.min.js"></script>
    <script type="text/javascript">
      function makeorder(){
        $.ajax({
          type: 'post',
          url: '<%=request.getContextPath()%>/Flow!makeOrder',
          dataType: "json",
          data: $("form").serialize(),
          success: function (data) {
            var json = eval("(" + data + ")");
            if (json.resultCode=="Succeed"){
              $("#rtcontain").text(json.rtval);
            }
            else alert("出错了！");
          }
        })
      }
    </script>
  </head>
  <body>
  <form  >
    <div class="wrapper wrapper-content  animated fadeInRight">
      <div class="full-height-scroll">
        <div class="ibox-content">
            <div class="form-group">
              <label class="col-sm-3 control-label">申购数量</label>
              <div class="col-sm-8">
                <input type="text" name="num" placeholder="申购数量" class="form-control">
              </div>
            </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">单价</label>
            <div class="col-sm-8">
              <input type="number" name="price" placeholder="单价" value="1.00" step="0.01" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 col-sm-offset-5">
              <input type="button"   value="确定" class="btn btn-primary form-control" onclick="makeorder()">
            </div>
          </div>
          </div>
      </div>
    </div>
  </form>
  <div id="rtcontain"></div>
  </body>
</html>
