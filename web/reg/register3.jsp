<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>上传照片</title>
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
    <script type="text/javascript">
    </script>
</head>
<body>
<table class="table table-bordered" >
    <tbody>
    <tr>
        <td>
            <input type="file" class="infile" name="fsfzz" id="fsfzz" style="display:none">
            <img class="img" style="width: 100px;height: 80px" src="Reg!checkinfo?picname=sfzz" onclick="openBrowse('fsfzz')"><p>身份证正面</p></td>
        <td>
            <input type="file" class="infile" name="fsfzf" id="fsfzf" style="display:none">
            <img class="img" style="width: 100px;height: 80px"src="Reg!checkinfo?picname=sfzf" onclick="openBrowse('fsfzf')"><p>身份证反面</p>
        </td>
    </tr>
    <tr>
        <td>
            <input type="file" class="infile" name="fscsfz" id="fscsfz" style="display:none">
            <img  class="img" style="width: 80px;height: 80px"src="Reg!checkinfo?picname=scsfz" onclick="openBrowse('fscsfz')" ><p>手持身份证</p></td>
        <td>
            <input type="file" class="infile" name="fyhk" id="fyhk" style="display:none">
            <img  class="img" style="width: 100px;height: 80px"src="Reg!checkinfo?picname=yhk" onclick="openBrowse('fyhk')"><p>银行卡</p></td>
    </tr>
    <tr >
        <td colspan="2">
        <div class="form-group">
            <div  >
                <button class="btn btn-lg btn-primary btn-block" type="button" onclick="uploadinfo()">提交审核</button>
            </div>
        </div>
        </td>
    </tr>
    </tbody>
</table>
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script>
    jQuery.prototype.serializeObject=function(){
        var obj=new Object();
        $.each(this.serializeArray(),function(index,param){
            if(!(param.name in obj)){
                obj[param.name]=param.value;
            }
        });
        return obj;
    };

    function openBrowse(obj){
        $("#"+obj).click();
    }

    $(function() {
        $(".infile").on("change",function() {
            var srcs = getObjectURL(this.files[0]); //获取路径
            $(this).nextAll(".img").attr("src",srcs); //this指的是input
        })
    })

    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }

    function uploadinfo(){
      $.ajaxFileUpload({
          url: "Reg!uploadpic",
          secureuri: false,
          fileElementId: ['fsfzf','fsfzz','fscsfz','fyhk'],
          dataType: 'multipart/form-data',
          data: $("form").serializeObject(),
          type: 'POST',
          success: function (data) {
              data =  $.parseJSON(data.replace(/<.*?>/ig,""));
              var json =  eval("(" + data + ")");
              if ( json.msgstr !="") {
                  alert(json.msgstr);
                  history.go(-1);
              }
              else{
                  layer.confirm("请关注公众号等待审核消息<br><img style='width:200px;height:200px' src='<%=request.getContextPath()%>/img/gzh.jpg'>", {
                      btn: ['已经关注'] //按钮
                  }, function(){
                      WeixinJSBridge.call('closeWindow');
                      window.close();
                  })};
          },
          error:function(data,status,e){
              alert(e);
          }
      });
    }
</script>
</body>
</html>

