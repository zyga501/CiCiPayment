<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/layer.css" rel="stylesheet">
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/layer.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-table.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-table-zh-CN.js"></script>
</head>
<body >
<div>
    <div id="contactlist" >
        <table  id="ctable"  class="table table-striped table-hover table-bordered"> <thead><tr>
            <th  data-checkbox="true" ><th  data-field="index" data-formatter="indexFormatter" >序号</th>
            <th  data-field="name"  >商家</th><th  data-field="tradeNo"  >支付订单</th><th  data-field="chantradeno">代付单号</th>
            <th  data-field="tradeAmount">支付额</th><th  data-field="tradeTime">支付时间</th></tr></thead>
        </table>
    </div>

    <div class="directory-info-row" style="display: none">
        <div class="row">
        </div>
    </div>
</div>
</body>
<script>
    function indexFormatter(value, row, index) {
        return index+1;
    }

    function typeFormatter(value, row, index) {
        if (value.indexOf("AliJsPay")>0)
            return "支付宝";
        if (value.indexOf("WeixinJsPay")>0)
            return "微信支付";
    }

    function chgdis(v) {
        if (v == 1) {
            $(".directory-info-row").css("display", "none");
            $("#contactlist").css("display", "block");
        }
        else if (v == 2) {
            $(".directory-info-row").css("display", "block");
            $("#contactlist").css("display", "none");
        }
    }

    function myinitTable() {
        //  $.get('< %=request.getContextPath()%>/c!contactList', function(data) {
        // data=JSON.parse(data);
        //先销毁表格
        $('#ctable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#ctable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            url: "<%=request.getContextPath()%>/User!fetchChanorderList", //获取数据的Servlet地址
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            sortOrder: "asc",
            showToggle:true,
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            pageSize:2,  //每页显示的记录数
            pageNumber:1, //当前第几页
            pageList: [5, 10, 15, 20, 25],  //记录数可选列表
            search: true,  //是否启用查询
            showColumns: true,  //显示下拉框勾选要显示的列
            sidePagination: "server",//表格分页的位置
            queryParams: queryParams, //参数
            queryParamsType: "undefined",
        });
    }
    function queryParams(params) {  //配置参数
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.pageSize,   //页面大小
            pageNumber: params.pageNumber,  //页码
            fliter:params.searchText ,
            sortOrder: params.sortOrder//排位命令（desc，asc）
        };
        return temp;
    }
    function getIdSelections() {
        return $.map($('#ctable').bootstrapTable('getSelections'), function(row) {
            return row.id
        });
    }
    $().ready(function () {myinitTable();});
</script>
</html>
