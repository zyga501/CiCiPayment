<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>ChanPay</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/qrcode.js"></script>
    <script type="text/javascript">
      function singlePay() {
        $.ajax({
          type: 'post',
          url: '<%=request.getContextPath()%>/chanpay/Pay!singlePay',
          dataType:"json",
          data:$("form").serialize(),
          success: function (data) {
          }
        })
      }

      function verified() {
        $.ajax({
          type: 'post',
          url: '<%=request.getContextPath()%>/chanpay/General!verified',
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
      <table>
        <tr>
          <td>总金额:</td>
          <td>
            <input type="text" id="amount" name="amount" value="1"/>
          </td>
        </tr>
        <tr>
          <td>银行通用名称:</td>
          <td>
            <input type="text" id="bankGeneralName" name="bankGeneralName" value="平安银行"/>
          </td>
        </tr>
        <tr>
          <td>开户行名称:</td>
          <td>
            <input type="text" id="bankName" name="bankName" value="平安银行杭州滨江支行"/>
          </td>
        </tr>
        <tr>
          <td>开户行号:</td>
          <td>
            <input type="text" id="bankCode" name="bankCode" value="307331002478"/>
          </td>
        </tr>
        <tr>
          <td>账号类型:</td>
          <td>
            <input type="text" id="accountType" name="accountType" value="00"/>
          </td>
        </tr>
        <tr>
          <td>账号:</td>
          <td>
            <input type="text" id="accountNo" name="accountNo" value="6230582000028919515"/>
          </td>
        </tr>
        <tr>
          <td>账户名称:</td>
          <td>
            <input type="text" id="accountName" name="accountName" value="陈锋"/>
          </td>
        </tr>
        <tr>
          <td>手机:</td>
          <td>
            <input type="text" id="tel" name="tel" value="13867433895"/>
          </td>
        </tr>
        <tr>
          <td>id类型:</td>
          <td>
            <input type="text" id="idType" name="idType" value="0"/>
          </td>
        </tr>
        <tr>
          <td>id:</td>
          <td>
            <input type="text" id="id" name="id" value="331081198806135150"/>
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" onclick="singlePay()" value="单次支付"/>
          </td>
          <td>
            <input type="button" onclick="verified()" value="实名认证"/>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
