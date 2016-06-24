<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouchenwei
  Date: 2016/6/16
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <meta charset="utf-8" />
    <script type="text/javascript" src="<c:url value="/scripts/jquery-1.8.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
    <title>检验报告查询</title>
    <style>
        * {
            padding: 0;
            margin: 0;
            outline: 0;
        }
        body, th, td, button, input, select, textarea {
            font-family: "Helvetica Neue","Helvetica,"Microsoft Yahei",tahoma,arial","Verdana","sans-serif","\5B8B\4F53";
            font-size: 13px;
            color: #222;

        }
        body{
            background: #ECF0F1;
            overflow: hidden;
        }
        .container{
            margin: 15px 15px;
            overflow: hidden;

        }
        .content .sample{
            float:left;
            width:350px;
            background-color:#EEF2F3;
            border:1px solid #ddd;
        }
        .content .samplelist{
            min-height:300px ;
            overflow: auto;
        }
        .content .test{
            position:relative;
            margin-left: 360px;
            /*background: #fff;*/

        }
        .content .test .patient{
            position: relative;
            height: 100px;
            border-bottom: 1px solid #eee;
        }
        .table{
            border-collapse: collapse;
            border: none;
            padding: 5px 5px;
            table-layout:fixed;
            width:100%;
            overflow: hidden;
        }
        .table th {
            height: 30px;
            text-align: left;
            padding-left: 5px;
            background-color: #F8F8F8;
            border-top: solid #ddd 1px;
            border-bottom: solid #B7E0FE 2px;
        }
        .table .td100{
            width:100px;
        }
        .table .td150{
            width:150px;
        }
        .table .td80{
            width:80px;
        }
        .table .td60{
            width:60px;
        }
        .table td  {
            height: 25px;
            text-align: left;
            border-bottom: dotted #ddd 1px;
            /*border-left: dotted #ddd 1px;*/
            white-space:nowrap;
            overflow:hidden;
            word-break:keep-all;
            text-overflow:ellipsis;

        }

        .table tr.dark {
            background-color: #F9F9F9;
        }
        .table tr.light {
            background-color: #ffffff;
        }
        .table tbody tr:hover td{
            cursor:pointer;
        }
        .subtable tbody tr.hover:hover td{
            background-color: #DFF7A4;
            cursor:pointer;
        }
        .subtable td{
            padding: 0 5px;
        }
        .table tbody tr.active{
            background-color: #DFF7A4;
            /*font-weight: bold;*/
        }
        .cl{
            font-size: 0;
            line-height: 0;
            clear: both;
            display: block;
            height: 0;
        }
        .title{
            /*border-bottom: 1px solid #ddd;*/
            font-size: 13px;
            font-weight:bold;
            /*margin-bottom: 5px;*/
            text-decoration: none;
            background:#2c6787; /*#E0ECFF;*/
            height: 20px;
            padding: 10px 0px 5px 15px;
        }
        .title .title-info{
            display: inline-block;
            text-indent: 8px;
            border-left: 3px solid #f15a23;
            color: #fff;
            word-break: break-all;
            word-wrap: break-word;
            line-height: 1.1;
        }
        .form-group{
            /*margin: 4px 8px 4px 0px;*/
            display: inline-block;
            float: left;
        }
        .patient-body{
            background-color:#EEF2F3;
            /*color: #31708f;*/
            padding: 5px 5px;
            border-top: 1px solid #ddd;

            /*margin: 10px 2px -8px;*/
        }
        .header{
            border: 1px solid #ddd;
            background-color: #f3f3f4;
            height: 50px;
            margin-bottom: 10px;
            line-height: 50px;
        }
        .row{
            margin-bottom: 10px;
            border: 1px solid #ddd;
        }
        .btn-primary{
            background-color: #18a689;
            color: #FFF;
            display: inline-block;
            font-size: 14px;
            font-weight: 400;
            line-height: 20px;
            text-align: center;
            cursor: pointer;
            width: 40px;
        }
        .test-result{
            overflow-y: auto;
            overflow-x: hidden;
        }
        input{border:1px solid #dddddd;
            height: 20px;}
        .text{
            width: 90px;
        }
        /*结果列表明细表头*/
        table.headRow{
            background-color: #18a689;
        }
        table tr.headRow td{
           font-weight: bold;
           border-bottom: solid 1px #ddd;
        }
        /*input{*/
        /*border:1px solid #dddddd;*/
        /*color: #555555;*/
        /*height: 25px;*/
        /*padding: 4px 6px;*/
        /*line-height: 25px;*/
        /*}*/
        /*select {*/
        /*border:1px solid #dddddd;*/
        /*display: inline;*/
        /*background: #fff;*/
        /*color: #555555;*/
        /*height: 36px;*/
        /*padding: 4px 5px;*/
        /*}*/
        .btn-primary{
            height:30px;
            background:#50c0e0;
            border:1px solid #dddddd;
            color:#ffffff;
            text-align: center;
            width: 60px;
        }
        .fieldname{
            text-align: right;
            padding: 0 5px;
        }
        .fieldvalue{
            text-align: left;
            font-weight: bold;
        }
        .toggerLable{
            color: #529807;
            font-weight: bold;
            padding-right: 2px;
        }
        /*差值 */
        .color1{
            background-color:#FFFF00;
        }
        /*比值 */
        .color2{
            background-color:#00c0bF;
        }
        /*复检*/
        .color3{
            background-color:#ffbbff;
        }
        /*危急 */
        .color4{
            background-color:#ff7070;
        }
        /*警戒1 */
        .color5{
            background-color:#00FF00;
        }
        /*警戒2 */
        .color6{
            background-color:#F4A460;
        }
        /*极值 */
        .color7{
            background-color:#EE4000;
        }
        /*阳性 */
        .color101{
            background-color:#bababa;
        }
    </style>
</head>
<body>

<div id="container" class="container">
    <div id="content" class="content">
        <div class="sample">
            <div class="title"><span class="title-info">样本列表</span></div>
            <div class=" margin-top">
                <div class="search-form pull-left">
                    <form id="SearchForm" class="form-inline" action="../doctor/getReportList" method="post">
                        <table>
                            <tr>
                                <td>日期</td>
                                <td>
                                    <input type="text" id="fromDate" class="text" name="fromDate" value="${fromDate}" class="laydate-icon">
                                    <span class="date-step-span">至</span>
                                    <input type="text" id="toDate" class="text" name="toDate" value="${toDate}" class="laydate-icon">
                                </td>
                                <td rowspan="2" style="text-align: center;padding-left:5px "><button type="submit" class="btn btn-primary" style="text-align: center">查询</button></td>
                            </tr>
                            <tr>
                                <td><div style=""><select id="selectType" name="selectType"  style="border:none;margin:-1px;background-color: #ECF0F1">
                                    <option value="1" <c:if test="${selectType==1}">selected</c:if>>病历号</option>
                                    <option value="2" <c:if test="${selectType==2}">selected</c:if>>姓名</option>
                                    <option value="3" <c:if test="${selectType==3}">selected</c:if>>医嘱号</option>
                                </select></div></td>
                                <td><input type="text" style="width: 203px" id="searchText" name="searchText" value="${searchText}" ></td>
                            </tr>

                        </table>
                    </form>
                </div>
            </div>
            <div class="samplelist">
                <table class="table subtable" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr>
                        <th style="width: 80px">时间</th>
                        <th style="width: 80px">病历号</th>
                        <th style="width: 120px">报告单</th>
                    </tr>
                    <c:forEach  items="${sampleList}" var="leftvo" varStatus="status">
                        <tr samplenos="${leftvo.sampleNos}" <c:choose>
                            <c:when test="${status.index%2==0}">class="light hover" </c:when>
                            <c:otherwise>class="dark hover" </c:otherwise>
                        </c:choose>>
                            <td title="${leftvo.dateTime}"  val="${leftvo.dateTime}">${leftvo.dateTime}</td>
                            <td title="${leftvo.patientBlh}" val="${leftvo.patientBlh}">${leftvo.patientBlh}</td>
                            <td title="${leftvo.reportNote}" val="${leftvo.reportNote}">${leftvo.reportNote}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="test">
            <div class="row">
                <div class="title"><span class="title-info">病人信息</span></div>
                <div class="patient-body">
                    <div id="patient-info" class="alert alert-info" style="padding:0px;">
                        <table style="width: 100%">
                            <colgroup>
                                <col width="80">
                                <col width="250">
                                <col width="80">
                                <col width="100">
                                <col width="80">
                                <col width="*">
                            </colgroup>
                            <tr>
                                <td class="fieldname">姓名：</td>
                                <td class="fieldvalue"><b id="pName"></b></td>
                                <td class="fieldname">性别：</td>
                                <td class="fieldvalue"><b id="pSex"></b></td>
                                <td class="fieldname">年龄：</td>
                                <td class="fieldvalue"><b id="pAge"></b></td>
                            </tr>
                            <tr>
                                <td class="fieldname">病历号：</td>
                                <td class="fieldvalue"> <b id="pBlh"></b></td>
                                <td class="fieldname">就诊卡号：</td>
                                <td class="fieldvalue"> <b id="pId"></b></td>
                                <td class="fieldname">样本类型：</td>
                                <td class="fieldvalue"><b id="pType"></b></td>
                            </tr>
                            <tr>
                                <td class="fieldname">科室：</td>
                                <td class="fieldvalue"><b id="pSection"></b></td>
                                <td class="fieldname">床号：</td>
                                <td class="fieldvalue"><b id="pBed" ></b></td>
                                <td class="fieldname"> 诊断：</td>
                                <td class="fieldvalue"><b id="pDiagnostic"></b></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="title"><div class="title-info" style="float: left" id="resultCaption">结果信息</div><div style=" float:right;line-height: 1.1;color: #fff;margin:0 15px;cursor: pointer" onclick="$.Custom.openDiag()">自定义</div></div>

                <div class="test-result">
                    <table class="table" id="testResultGrid" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <th class="td1">项目</th>
                            <th class="td2">结果</th>
                            <th class="td3">历史</th>
                            <th class="td3">历史</th>
                            <th class="td3">历史</th>
                            <th class="td3">历史</th>
                            <th class="td3">历史</th>
                            <th class="td3">测定时间</th>
                            <th class="td3">机器号</th>
                            <th class="td3">参考值</th>
                            <th class="td3">单位</th>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    ul{
        list-style: none;
        width:450px;
    }
    li{
        list-style-type:none;
        float: left;
        padding: 0px 5px;
        width:200px;
        line-height: 20px;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        vertical-align: middle;
        display: inline-block;
    }
    .list{
        position: absolute;
        border: 1px solid #c9cbce;
        z-index: 999999;
        background:#fff;
        height:150px;
        overflow-y: auto;
        overflow-x:hidden;
        /*width:250px;*/
    }
    .tableList{
        width:500px;
        border: 1px #eee solid;
        margin:5px 10px ;
    }
    .btnChose{
        height: 28px;
        line-height: 28px;
        border: 1px solid #dedede;
        background-color: #f1f1f1;
        color: #333;
        padding: 5px 15px;
        font-weight: 400;
        cursor: pointer;
        border-color: #4898d5;
        background-color: #2e8ded;
        color: #fff;
    }
    input,label { vertical-align:middle;}
</style>
<div style="display: none" id="indeDialog">
    <div style="padding: 10px 10px">
        <div>
            <input id="secarchText" proplaceholder="输入编号或名称" style="height: 30px"></input>
        </div>
        <div class="list" style="display: none">
            <div style="width: 100%;background: #eee;text-align: right;pointer-events: auto;">
                <a id="btnChose" class="btnChose" onclick="$.Custom.choseItem()">选择</a>
            </div>
            <ul id="testList">
            </ul>

        </div>

    </div>
    <div class="tableList">
        <table class="table" id="testTable">
            <tr><th>名称</th><th>英文名</th><th>操作</th></tr>
        </table>
    </div>
</div>
</body>

<script type="text/javascript">
    $.Custom=(function(){
        var public = {
            gridHead:[
                {name:'项目',width:'120'},
                {name:'结果',width:''},
                {name:'历史',width:''},
                {name:'历史1',width:''},
                {name:'历史2',width:''},
                {name:'机器号',width:'60'},
                {name:'参考范围',width:'100'},
                {name:'单位',width:'60'}],
            microGridHead:[
                {name:'结果',width:'220'},
                {name:'历史',width:''},
                {name:'历史1',width:''},
                {name:'历史2',width:''},
                {name:'机器号',width:'60'},
                {name:'参考范围',width:'100'},
                {name:'单位',width:'60'}],
            getPatientInfo:function(patientBlh,fromDate,samplenos){
                $.ajax({
                    url:"../doctor/getSampleData",
                    type:"post",
                    dataType:"json",
                    data:{patientBlh:patientBlh,fromDate:fromDate,samplenos:samplenos},
                    success:function(data){
                        if(window.console) window.console.log(data)
                        if(data && data.patientInfo){
                            $("#sampleTitle").html(data.patientInfo.examinaim ||'');
                            //$("#resultCaption").html(data.patientInfo.examinaim ||'');
                            $("#audit_reason").html(data.patientInfo.reason ||'');
                            $("#pName").html(data.patientInfo.name ||'');
                            $("#pAge").html(data.patientInfo.age ||'');
                            $("#pBlh").html(data.patientInfo.medicalnumber ||'');
                            $("#pId").html(data.patientInfo.id ||'');
                            $("#pSex").html(data.patientInfo.sex ||'');
                            $("#pSection").html(data.patientInfo.section ||'');
                            $("#pType").html(data.patientInfo.type ||'');
                            $("#pDiagnostic").html(data.patientInfo.diagnostic ||'');
                            $("#pBed").html(data.patientInfo.bedno ||'');
                            public.loadGrid(data.testResult)
                        }
                    }
                })
            },
            loadGrid:function(resultDatas){
                var table = $('#testResultGrid');
                table.empty();
                for(var i=0;i<resultDatas.length;i++){
                    var sampleInfo = resultDatas[i].sampleinfo;
                    var rowDatas = resultDatas[i].datas;
                    var type = resultDatas[i].type;

                    var headOtherInfo = '';
                    if(sampleInfo.sampleStatus>=6) {
                        headOtherInfo = '已打印';
                    }else{
                        if(sampleInfo.auditStatus <= -1){
                            headOtherInfo='<font color="red">无结果</font>';
                        }else{
                            //headOtherInfo='有结果';
                       }
                    }
                    var headInfo = (i+1)+'、'+ sampleInfo.examinaim +'<font color="#FF9800">['+ sampleInfo.sampleNo+'] </font> ' + headOtherInfo;
                    //add SubHead
                    var subHeadRow = $('<tr class="headRow show" groupid="0"><td colspan=8>'+headInfo+'</td></tr>');
                    table.append(subHeadRow);
                    subHeadRow.click (function(event){
                        public.togger(this);
                    });
                    //add subDetailGrid
                    if(rowDatas.length>0){
                        subHeadRow.attr('groupid','1');
                        if(window.console)console.log(subHeadRow.attr('groupid'))
                        var subTable = public.loadSubGrid(sampleInfo.id,rowDatas,type);
                        var subDataRow =$('<tr></tr>');
                        var subDataTd = $('<td colspan=8></td>');
                        subDataTd.append(subTable);
                        subDataRow.append(subDataTd);
                        table.append(subDataRow);
                    }
                }
            },
            getHLLable:function(val1,val2,color){
                //获取高低标记
                var hl = val2.split("-");
                var h = parseFloat(hl[1]);
                var l = parseFloat(hl[0]);
                var va = parseFloat(val1);
                var res ='';
                if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
                    if (va < l) {
                        res = "<font color='red'>\u2193</font>";
                    } else if (va > h) {
                        res = "<font color='red'>\u2191</font>";
                    }
                }
                return public.getColor(val1,color)+res;
            },
            getColor:function(val1,color){
                if(val1.indexOf("阳")>-1) color=101;
                switch (color) {
                    case 1:
                        return "<span class='color1'>"+val1+"</span>";
                    case 2:
                        return "<span class='color2'>"+val1+"</span>";
                    case 3:
                        return "<span class='color3'>"+val1+"</span>";
                    case 4:
                        return "<span class='color4'>"+val1+"</span>";
                    case 5:
                        return "<span class='color5'>"+val1+"</span>";
                    case 6:
                        return "<span class='color6'>"+val1+"</span>";
                    case 7:
                        return "<span class='color7'>"+val1+"</span>";
                    case 101:
                        //阳性
                        return "<span class='color101'>"+val1+"</span>";
                    default:
                        return val1;
                }
            },
            loadSubGrid:function(sampleid,rowDatas,type){
                //add sub header
                var header = $('<tr></tr>');
                for(i=0;i < public.gridHead.length; i++){
                    var width =public.gridHead[i].width ||0;
                    if(width==0) width = 60;
                    width = width+'px';
                    header.append("<th class='td"+i+"' width='"+width+"'>"+public.gridHead[i].name+"</th>");
                }
                //加载子表数据
                var subTable=$('<table class="table subtable" id='+sampleid+'></table>');
                subTable.append(header)
                for(j=0;j < rowDatas.length;j++){
                    var row=$("<tr class='hover'></tr>");
                    if(j%2 ==0){
                        row.addClass("light");
                    }else{
                        row.addClass("dark");
                    }
                    if(type==4){
                        var rNameTD=$("<td colspan='8'></td>");      //名称
                        rNameTD.html(rowDatas[j].name);
                        rNameTD.attr('title',rowDatas[j].name);
                        row.append(rNameTD);
                    }else{
                        var rNameTD=$("<td></td>");      //名称
                        rNameTD.html(rowDatas[j].name);
                        rNameTD.attr('title',rowDatas[j].name);
                        row.append(rNameTD);

                        var rResultTD=$("<td></td>");      //结果
                        rResultTD.html(public.getHLLable(rowDatas[j].result,rowDatas[j].scope,rowDatas[j].color));
                        rResultTD.attr('title',rowDatas[j].result);
                        row.append(rResultTD);

                        var rLastTD=$("<td></td>");      //历史
                        rLastTD.html(public.getHLLable(rowDatas[j].last,rowDatas[j].scope,rowDatas[j].color));
                        row.append(rLastTD);

                        var rLast1TD=$("<td></td>");      //历史1
                        rLast1TD.html(public.getHLLable(rowDatas[j].last1,rowDatas[j].scope,rowDatas[j].color));
                        row.append(rLast1TD);

                        var rLast2TD=$("<td></td>");      //历史2
                        rLast2TD.html(public.getHLLable(rowDatas[j].last2,rowDatas[j].scope,rowDatas[j].color));
                        row.append(rLast2TD);

                        var rDeviceTD=$("<td></td>");      //机器
                        rDeviceTD.html(rowDatas[j].device);
                        rDeviceTD.attr('title',rowDatas[j].device);
                        row.append(rDeviceTD);

                        var rSopeTD=$("<td></td>");      //参考范围
                        rSopeTD.html(rowDatas[j].scope);
                        rSopeTD.attr('title',rowDatas[j].scope);
                        row.append(rSopeTD);

                        var rUnitTD=$("<td></td>");      //单位
                        rUnitTD.html(rowDatas[j].unit);
                        row.append(rUnitTD);

                        row.bind('click',function(){
                            //alert('rowClick');
                        })

                    }

                    subTable.append(row);
                    //微生物结果加载药敏
                    if(type == 4){
                        //包含药敏
                        if(rowDatas[j].hasdrug=='1'){
                            var drugDatas = rowDatas[j].ymdatas;
                            if(drugDatas && drugDatas.length>0){
                                row.attr('groupid','1');
                                var subDrugTable = public.loadDrugGrid(drugDatas);
                                if(window.console)console.log(subDrugTable)
                                var drugDataRow =$('<tr style="display: none"></tr>');
                                var drugDataTd = $('<td colspan=8 ></td>');
                                drugDataTd.append(subDrugTable);
                                drugDataRow.append(drugDataTd);
                                subTable.append(drugDataRow);
                                var rowHtml = row.find("td:eq(0)").html();
                                row.find("td:eq(0)").html('<span class="toggerLable">+</span>'+rowHtml);
                                row.click (function(event){
                                    public.togger(this,$(this).find('.toggerLable'));
                                });
                            }
                        }

                    }
                }
                return subTable;
            },
            loadDrugGrid:function(drugDatas){
                //加载药敏
                //add sub header
                var header = $('<tr><td width="220px">抗生素名</td><td>结果</td><td>解释</td><td>折点</td><td>单位</td></tr>');
                //加载子表数据
                var drugTable=$('<table class="table subtable"></table>');
                drugTable.append(header)
                for(j=0;j < drugDatas.length;j++) {
                    var row = $("<tr class='hover'></tr>");
                    if (j % 2 == 0) {
                        row.addClass("light");
                    } else {
                        row.addClass("dark");
                    }

                    var rNameTD = $("<td></td>");      //抗生素名
                    rNameTD.html(drugDatas[j].testid);
                    rNameTD.attr('title',drugDatas[j].testid);
                    row.append(rNameTD)

                    var rResultTD = $("<td></td>");      //结果
                    rResultTD.html(drugDatas[j].testresult);
                    rResultTD.attr('title',drugDatas[j].testresult);
                    row.append(rResultTD)

                    var rHintTD = $("<td></td>");      //解释
                    rHintTD.html(drugDatas[j].hint);
                    rHintTD.attr('title',drugDatas[j].hint);
                    row.append(rHintTD)

                    var rRefhiTD = $("<td></td>");      //折点
                    var refhlo = '';
                    if(drugDatas[j].reflo !='' && drugDatas[j].refhi !=''){
                        refhlo = drugDatas[j].reflo +'~' +drugDatas[j].refhi;
                    }
                    rRefhiTD.html(refhlo);
                    row.append(rRefhiTD)

                    var rUnitTD = $("<td></td>");      //单位
                    rUnitTD.html(drugDatas[j].unit);
                    row.append(rUnitTD);

                    drugTable.append(row);
                }
                return drugTable;
            },
            togger:function(obj,lable){
                var obj =$(obj);
                if(obj.attr('groupid')=='1'){
                    if( obj.hasClass('show')){
                        obj.next().hide();
                        obj.removeClass('show');
                        if(lable) lable.html('+');
                    }else{
                        obj.next().show();
                        obj.addClass('show');
                        if(lable) lable.html('-');
                    }
                }else{
                    alert('对不起，没有结果数据');
                }
            },
            openDiag:function(){
                layer.open({
                    type: 1,
                    title: '项目选择',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['520px', '340px'], //宽高
                    content: $('#indeDialog'),
                    btn: ['关闭'] //可以无限个按钮
                    ,yes: function(index, layero){
                        //do something
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    }

                });
            },
            loadAutocomplete:function(key){
                $.ajax({
                    url: "../doctor/searchTest",
                    dataType: "json",
                    data: {
                        maxRows : 12,
                        name :key
                    },
                    success: function( data ) {
                        if(window.console)console.log(data);
                        var ul = $('#testList');
                        ul.empty();
                        for(i=0;i< data.length;i++){
                            var item = data[i];
                            var li = $('<li><input type="checkbox" testid="'+item.id+'" testname='+item.name+' testab='+item.ab+'>'+item.id +' '+ item.name+'</li>');
                            ul.append(li);
                        }
                        ul.parent().show();
                    }
                });
            },
            choseItem:function(){
                var ul = $('#testList');
                var table = $('#testTable');
                ul.children('li').each(function(index,obj){
                    var checkObj = $(obj).children('input')
                    if(checkObj.attr("checked")){
                        var tr ='<tr><td>'+checkObj.attr('testname')+'</td><td>'+checkObj.attr('testab')+'</td><td>删除</td></tr>'
                        table.append(tr);
                    }
                })
                ul.parent().hide();
            }
        }
        return public;
    })();

    $(function(){
        $(".samplelist tr").bind("click",function(){
            $(".samplelist tr").removeClass('active');

            var fromDate = this.cells[0].getAttribute("val") || '';
            var patientBlh = this.cells[1].getAttribute("val") || '';
            var samplenos = this.getAttribute("samplenos") || '';
            if(patientBlh !=''){
                jQuery.Custom.getPatientInfo(patientBlh,fromDate,samplenos);
            }
            if( $(this).hasClass('active') ){
                $(this).removeClass('active')
            }else{
                $(this).addClass('active')
            }
        })
        var height = $(window).height();
        //var height = (document.documentElement.scrollHeight >document.documentElement.clientHeight) ? document.documentElement.scrollHeight : document.documentElement.clientHeight;
        $('.samplelist').height(height-$('.samplelist').offset().top-20);
        $('.test-result').height(height-$(".test-result").offset().top-20)

        laydate({
            elem: '#fromDate',
            event: 'focus',
            format: 'YYYYMMDD'
        });
        laydate({
            elem: '#toDate',
            event: 'focus',
            format: 'YYYYMMDD'
        });
        var timeout = null;
        $('#secarchText').keydown(function (e) {
            if (timeout) clearTimeout(timeout);
            timeout = setTimeout(function(){
                $.Custom.loadAutocomplete($('#secarchText').val()||'');
            }, 400);
        });
    })

</script>
</html>

