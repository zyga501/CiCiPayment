<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>银行信息</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/style.min.css?v=4.1.0" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laypage.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/laydate.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/css/danlanlaydate.css" rel="stylesheet" type="text/css"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="full-height-scroll">
        <div class="ibox-content">
            <form action="Register!registerStep2" method="post" class="form-horizontal">
                <input type="hidden" name="cid" value="${reginfo.id}">
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户名：</label>

                    <div class="col-sm-8">
                        <input type="text" placeholder="开户名" name="accountName" class="form-control"
                               value="${reginfo.accountName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户省：</label>

                    <div class="col-sm-8">
                        <select name="province" id="province" class="form-control" onchange="pchg()">
                            <option value="">==请选择省份==</option>
                            <option value="11">北京市</option>
                            <option value="12">天津市</option>
                            <option value="13">河北省</option>
                            <option value="14">山西省</option>
                            <option value="15">内蒙古自治区</option>
                            <option value="21">辽宁省</option>
                            <option value="22">吉林省</option>
                            <option value="23">黑龙江省</option>
                            <option value="31">上海市</option>
                            <option value="32">江苏省</option>
                            <option value="33">浙江省</option>
                            <option value="34">安徽省</option>
                            <option value="35">福建省</option>
                            <option value="36">江西省</option>
                            <option value="37">山东省</option>
                            <option value="41">河南省</option>
                            <option value="42">湖北省</option>
                            <option value="43">湖南省</option>
                            <option value="44">广东省</option>
                            <option value="45">广西壮族自治区</option>
                            <option value="46">海南省</option>
                            <option value="50">重庆市</option>
                            <option value="51">四川省</option>
                            <option value="52">贵州省</option>
                            <option value="53">云南省</option>
                            <option value="54">西藏自治区</option>
                            <option value="61">陕西省</option>
                            <option value="62">甘肃省</option>
                            <option value="63">青海省</option>
                            <option value="64">宁夏回族自治区</option>
                            <option value="65">新疆维吾尔自治区</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户市：</label>
                    <div class="col-sm-8">
                        <select  name="bankCity" id="city" class="form-control"
                                value="${reginfo.bankCity}"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">银行：</label>

                    <div class="col-sm-8">
                        <input type="hidden" class="bankName"    name="bankName" value="${reginfo.bankName}" >
                        <select id="bankName" placeholder="银行" class="form-control" onchange="javascript:$('.bankName').val($(this).find('option:selected').text())" >
                            <option value="">==请选择银行==</option>
                            <option value="100">中原银行</option>
                            <option value="102">中国工商银行</option>
                            <option value="103">中国农业银行</option>
                            <option value="104">中国银行</option>
                            <option value="105">中国建设银行</option>
                            <option value="201">国家开发银行</option>
                            <option value="202">中国进出口银行</option>
                            <option value="203">中国农业发展银行</option>
                            <option value="301">交通银行</option>
                            <option value="302">中信银行</option>
                            <option value="303">中国光大银行</option>
                            <option value="304">华夏银行</option>
                            <option value="305">中国民生银行</option>
                            <option value="306">广东发展银行</option>
                            <option value="307">深圳发展银行</option>
                            <option value="308">招商银行</option>
                            <option value="309">兴业银行</option>
                            <option value="310">上海浦东发展银行</option>
                            <option value="313">城市商业银行</option>
                            <option value="314">农村商业银行</option>
                            <option value="315">恒丰银行</option>
                            <option value="316">浙商银行</option>
                            <option value="317">农村合作银行</option>
                            <option value="318">渤海银行</option>
                            <option value="319">徽商银行</option>
                            <option value="401">城市信用社</option>
                            <option value="402">农村信用社</option>
                            <option value="403">中国邮政储蓄银行</option>
                            <option value="501">汇丰银行</option>
                            <option value="502">东亚银行</option>
                            <option value="503">南洋商业银行</option>
                            <option value="504">恒生银行</option>
                            <option value="505">中国银行（香港）</option>
                            <option value="506">集友银行</option>
                            <option value="507">创兴银行</option>
                            <option value="509">星展银行</option>
                            <option value="510">永亨银行</option>
                            <option value="512">永隆银行</option>
                            <option value="531">花旗银行</option>
                            <option value="532">美国银行</option>
                            <option value="533">摩根大通银行</option>
                            <option value="561">三菱东京日联银行</option>
                            <option value="562">日本日联银行</option>
                            <option value="563">三井住友银行</option>
                            <option value="564">瑞穗实业银行</option>
                            <option value="565">日本山口银行</option>
                            <option value="591">韩国外换银行</option>
                            <option value="593">友利银行</option>
                            <option value="594">韩国产业银行</option>
                            <option value="595">新韩银行</option>
                            <option value="596">韩国中小企业银行</option>
                            <option value="597">韩亚银行</option>
                            <option value="621">华侨银行</option>
                            <option value="622">大华银行</option>
                            <option value="623">星展银行（中国）</option>
                            <option value="631">泰国盘谷银行</option>
                            <option value="641">奥地利中央合作银行</option>
                            <option value="651">比利时联合银行</option>
                            <option value="652">比利时富通银行</option>
                            <option value="661">荷兰银行</option>
                            <option value="662">荷兰安智银行</option>
                            <option value="671">渣打银行</option>
                            <option value="672">英国苏格兰皇家银行</option>
                            <option value="691">法国兴业银行</option>
                            <option value="692">法国巴黎银行</option>
                            <option value="693">法国东方汇理银行</option>
                            <option value="695">法国外贸银行</option>
                            <option value="711">德国德累斯登银行</option>
                            <option value="712">德意志银行</option>
                            <option value="713">德国商业银行</option>
                            <option value="714">德国西德银行</option>
                            <option value="715">德国巴伐利亚州银行</option>
                            <option value="716">德国北德意志州银行</option>
                            <option value="732">意大利联合圣保罗银行</option>
                            <option value="741">瑞士信贷银行</option>
                            <option value="742">瑞士银行</option>
                            <option value="751">加拿大丰业银行</option>
                            <option value="752">加拿大蒙特利尔银行</option>
                            <option value="761">澳大利亚和新西兰银行</option>
                            <option value="771">摩根士丹利国际银行</option>
                            <option value="775">联合银行</option>
                            <option value="776">荷兰合作银行</option>
                            <option value="781">厦门国际银行</option>
                            <option value="782">法国巴黎银行</option>
                            <option value="783">平安银行</option>
                            <option value="785">华商银行</option>
                            <option value="787">华一银行</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">银行卡号：</label>

                    <div class="col-sm-8">
                        <input type="text" name="accountNo" placeholder="银行卡号" class="form-control"
                               value="${reginfo.accountNo}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">开户行：</label>
                    <div class="col-sm-5">
                        <select id="banklist" onchange="fetchnum()"  class="form-control" ></select>
                    </div>
                    <div class="col-sm-3">
                        <button class=" btn btn-sm btn-primary btn-block" type="button" onclick="getnum();">获_取
                        </button>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">联行号：</label>
                    <div class="col-sm-8">
                        <input   name="bankCode" id="bankCode"  class="form-control"
                                  value="${reginfo.bankCode}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-8">
                        <a class="btn btn-lg btn-primary btn-block" href="#" onclick="$('form').submit()">下一步</a>
                    </div>
                </div>
        </form>
    </div>
</div>
</div>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script>
    function fetchnum(obj){
        $("#accountName").val($("#banklist").find('option:selected').text());
        $("#bankCode").val($("#banklist").val());
    }

        function pchg() {
            $.ajax({
                type: "post",
                url: "Register!queryCityByProvinceId",
                data: {provinceCode: $("#province").val()},
                dataType: 'json',
                success: function (data) {
                    var json = eval("(" + data.substring(1,data.length-1) + ")");
                    if (json != null && json != undefined) {
                        var $parent = $("#city");
                        var $dom = $("<option></option>");
                        $parent.empty();
                        for (var i = 0; i < json.cityList.length; i++) {
                            var option = $dom.clone();
                            option.html(json.cityList[i].cityName).attr("value", json.cityList[i].cityCode);
                            $parent.append(option);
                        }
                    }
                }
            });
    }

    function nextpage() {
        $.ajax({
            type: 'post',
            url: 'Register!reg2',
            dataType: "json",
            data: $("form").serialize(),
            success: function (data) {
                alert(data);
                var json = eval("(" + data + ")");
                window.location.href = json.url;
            },
            error: function (data) {
                alert("Error");
            }
        });
    }

    function getnum () {
        $.ajax({
            type: "post",
            url: "Register!queryAllRtgsNode",
            data: {'cityCode': $("#city").val(), 'clsCode': $("#bankName").val()},
            dataType: 'json',
            success: function (data) {
                var json = eval( data.substring(1,data.length-1) );
                if (json != null && json != undefined) {
                    var $parente = $("#banklist");
                    var $doms = $("<option></option>");
                    $parente.empty();
                    for (var i = 0; i < json.length; i++) {
                        var optionf = $doms.clone();
                        optionf.html(json[i].lName).attr("value", json[i].bankCode);
                        $parente.append(optionf);
                    }
                }
            }
        });
    }

</script>
</body>

</html>

