<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>SwiftPass</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/qrcode.js"></script>
    <script type="text/javascript">
      function jsPay() {
        $.ajax({
          type: 'post',
          url: '<%=request.getContextPath()%>/swiftpass/Pay!jsPay',
          dataType:"json",
          data:$("form").serialize(),
          success: function (data) {
          }
        })
      }
    </script>
  </head>
  <body>
    <form class="form form-horizontal" >
      <input id="code" name="code" type="hidden" value="<%=request.getParameter("code")%>" />
      <input id="state" name="state" type="hidden" value="<%=request.getParameter("state")%>" />
      <table>
        <tr>
          <td>商品描述:</td>
          <td>
            <input type="text" id="body" name="body" value="测试商品"/>
          </td>
        </tr>
        <tr><td>总金额:</td>
          <td>
            <input type="text" id="total_fee" name="total_fee" value="1"/>
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" onclick="jsPay()" value="公众号支付"/>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
