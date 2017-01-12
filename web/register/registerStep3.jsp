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
<form>
    <input type="hidden" name="cid" value="${reginfo.cid}">
    <table class="table table-bordered" >
        <tbody>
        <tr>
            <td>
                <input type="file" class="infile" name="fsfzz" id="fsfzz" style="display:none">
                <img class="img" style="width: 100px;height: 80px" src="Register!checkInfo?picname=sfzz&cid=${reginfo.cid}" onclick="openBrowse('fsfzz')"><p>身份证正面</p></td>
            <td>
                <input type="file" class="infile" name="fsfzf" id="fsfzf" style="display:none">
                <img class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=sfzf&cid=${reginfo.cid}" onclick="openBrowse('fsfzf')"><p>身份证反面</p>
            </td>
        </tr>
        <tr>
            <td>
                <input type="file" class="infile" name="fscsfz" id="fscsfz" style="display:none">
                <img  class="img" style="width: 80px;height: 80px"src="Register!checkInfo?picname=scsfz&cid=${reginfo.cid}" onclick="openBrowse('fscsfz')" ><p>手持身份证</p></td>
            <td>
                <input type="file" class="infile" name="fyhk" id="fyhk" style="display:none">
                <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=yhk&cid=${reginfo.cid}" onclick="openBrowse('fyhk')"><p>银行卡</p></td>
        </tr>
        <tr >
            <td colspan="2"><input type="checkbox" value="自有资质" id="chkzyzz" name='chkzyzz' onchange="zyzzchange()"><label for="chkzyzz">自有资质</label>
                <div id="zyzzContent" style="display: none">
                    <table class="table table-bordered" >
                        <tr><td><input type="file" class="infile" name="fyyzz" id="fyyzz" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=yyzz&cid=${reginfo.cid}" onclick="openBrowse('fyyzz')"><p>营业执照</p></td>
                            <td><input type="file" class="infile" name="fswdjzp" id="fswdjzp" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=swdjzp&cid=${reginfo.cid}" onclick="openBrowse('fswdjzp')"><p>税务登记照片</p></td></tr>
                        <tr><td><input type="file" class="infile" name="fzzjgdmz" id="fzzjgdmz" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=zzjgdmz&cid=${reginfo.cid}" onclick="openBrowse('fzzjgdmz')"><p>组织机构代码证照片</p></td>
                            <td><input type="file" class="infile" name="ffrsfzz" id="ffrsfzz" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=frsfzz&cid=${reginfo.cid}" onclick="openBrowse('ffrsfzz')"><p>法人身份证照片正面</p></td></tr>
                        <tr><td><input type="file" class="infile" name="ffrsfzf" id="ffrsfzf" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=frsfzf&cid=${reginfo.cid}" onclick="openBrowse('ffrsfzf')"><p>法人身份证照片反面</p></td>
                            <td><input type="file" class="infile" name="fshsyt" id="fshsyt" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=shsyt&cid=${reginfo.cid}" onclick="openBrowse('fshsyt')"><p>商户收银台照片</p></td></tr>
                        <tr><td><input type="file" class="infile" name="fshmtz" id="fshmtz" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=shmtz&cid=${reginfo.cid}" onclick="openBrowse('fshmtz')"><p>商户门头照片</p></td>
                            <td><input type="file" class="infile" name="fdncs" id="fdncs" style="display:none">
                            <img  class="img" style="width: 100px;height: 80px"src="Register!checkInfo?picname=dncs&cid=${reginfo.cid}" onclick="openBrowse('fdncs')"><p>店内陈饰照片</p></td></tr>
                    </table>
                </div></td>
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
</form>
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
        layer.msg("正在上传中");
        var index = layer.load(1, {
            shade: [0.1,'#fff'],
            time: 20000
        });
        $.ajaxFileUpload({
            url: "Register!uploadIDCard",
            secureuri: false,
            fileElementId: ['fsfzf','fsfzz','fscsfz','fyhk','fyyzz','fswdjzp','fzzjgdmz','ffrsfzz','ffrsfzf','fshsyt','fshmtz','fdncs'],
            dataType: 'multipart/form-data',
            data: $("form").serializeObject(),
            type: 'POST',
            success: function (data) {
                layer.close(index);
                data =  $.parseJSON(data.replace(/<.*?>/ig,""));
                var json =  eval("(" + data + ")");
                if ( json.ErrorMsg !="") {
                    alert(json.ErrorMsg);
                    history.go(0);
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

    function zyzzchange(){
        if ($("input[type='checkbox']").is(':checked')) {
            $("#zyzzContent").css("display","block");
        }else $("#zyzzContent").css("display","none");
    }
</script>
</body>
</html>