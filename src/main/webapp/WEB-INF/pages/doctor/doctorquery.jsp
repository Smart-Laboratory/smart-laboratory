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
    <script type="text/javascript" src="<c:url value="/scripts/layer/extend/layer.ext.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/highcharts.js"/>"></script>
    <%--<script type="text/javascript" src="http://getfirebug.com/releases/lite/1.3/firebug-lite.js"></script>--%>
    <title>检验报告查询</title>
    <style>
        *{margin:0;padding:0;border:0;}
        *html{
            margin-right:-3px;
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
            /*margin: 15px 15px;*/
            overflow: hidden;
        }
        .content .sample{
            float:left;
            width:250px;
            background-color:#EEF2F3;
            border:1px solid #ddd;
            display:inline;
        }
        .content .samplelist{
            width: 250px;
            overflow: hidden;
        }
        .content .test{
            position:relative;
            margin-left: 260px;
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
            overflow: auto;
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
            line-height: 20px;
            height: 20px;
        }
        .form-group{
            /*margin: 4px 8px 4px 0px;*/
            display: inline-block;
            float: left;
            display:inline;
        }
        .patient-body{
            background-color:#EEF2F3;
            /*color: #31708f;*/
            padding: 5px 5px;
            border-top: 1px solid #ddd;
            height: 45px;
            /*margin: 10px 2px -8px;*/
        }
        .header{
            border: 1px solid #ddd;
            background-color: #f3f3f4;
            height: 50px;
            /*margin-bottom: 10px;*/
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
            position: relative;
            overflow-y: scroll;
            overflow-x: hidden;
            width: 500px;
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
        /*药敏I */
        .color102{
            /*background-color:#F44336;*/
            color:#F44336 !important;
        }
        .color102 TD{
            /*background-color:#F44336;*/
            color:#F44336 !important;
        }
        /*药敏S */
        .color103{
            /*background-color:#9C27B0;*/
            color:#9C27B0 !important;
        }
        .color103 TD{
            /*background-color:#9C27B0;*/
            color:#9C27B0 !important;
        }
        .hideRow{
            display: none;
        }

        /*TAB选项卡*/
        #tabbox{ width:600px; overflow:hidden; margin:0 auto;}
        .tab_conbox{border: 1px solid #999;border-top: none;padding-top:20px;}
        .tab_con{ display:none;}

        .tabs{height: 32px;border-bottom:1px solid #999;border-left: 1px solid #999;width: 100%;}
        .tabs li{height:31px;line-height:31px;float:left;border:1px solid #999;border-left:none;margin-bottom: -1px;background: #e0e0e0;overflow: hidden;position: relative;}
        .tabs li a {display: block;padding: 0 20px;border: 1px solid #fff;outline: none;}
        .tabs li a:hover {background: #ccc;}
        .tabs .thistab,.tabs .thistab a:hover{background: #fff;border-bottom: 1px solid #fff;}
        .tab_con {padding:12px;font-size: 14px; line-height:175%;}
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
                                <td>起始日期</td>
                                <td>
                                    <input type="text" id="fromDate" class="text" name="fromDate" value="${fromDate}" class="laydate-icon">
                                </td>
                                <td rowspan="3" style="text-align: center;padding-left:5px ">
                                    <button type="submit" class="btn btn-primary" style="text-align: center">查询</button></td>
                            </tr>
                            <tr>
                                <td>截止日期</td>
                                <td><input type="text" id="toDate" class="text" name="toDate" value="${toDate}" class="laydate-icon"></td>
                            </tr>
                            <tr>
                                <td><div style=""><select id="selectType" name="selectType"  style="border:none;margin:-1px;background-color: #ECF0F1">
                                    <option value="1" <c:if test="${selectType==1}">selected</c:if>>病历号</option>
                                    <option value="2" <c:if test="${selectType==2}">selected</c:if>>姓名</option>
                                    <option value="3" <c:if test="${selectType==3}">selected</c:if>>医嘱号</option>
                                </select></div></td>
                                <td><input type="text"  class="text" id="searchText" name="searchText" value="${searchText}" ></td>
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
                        <th style="width: 60px">病历号</th>
                        <th style="width: 80px">报告单</th>
                    </tr>
                    <c:forEach  items="${sampleList}" var="leftvo" varStatus="status">
                        <tr samplenos="${leftvo.sampleNos}" <c:choose>
                            <c:when test="${status.index%2==0}">class="light hover" </c:when>
                            <c:otherwise>class="dark hover" </c:otherwise>
                        </c:choose>>
                            <td title="${leftvo.dateTime}"  val="${leftvo.dateTime}">${leftvo.dateTime}</td>
                            <td title="${leftvo.patientBlh}" val="${leftvo.patientBlh}">${leftvo.patientBlh}</td>
                            <td title="${leftvo.reportNote}" val="${leftvo.reportNote}">${leftvo.reportNote}<c:if test="${leftvo.microNum>0}"><font color="red">微(${leftvo.microNum})</font></c:if> </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="test">
            <div class="row" style="height: 100px">
                <div class="title"><span class="title-info">病人信息</span></div>
                <div class="patient-body">
                    <div id="patient-info" class="alert alert-info" style="padding:0px;width: 100%">
                        <table style="width: 100%">
                            <colgroup>
                                <col width="80">
                                <col width="150">
                                <col width="80">
                                <col width="150">
                                <col width="80">
                                <col width="*">
                            </colgroup>
                            <tr>
                                <td class="fieldname">姓名：</td>
                                <td class="fieldvalue"><b id="pName">&nbsp;</b></td>
                                <td class="fieldname">性别：</td>
                                <td class="fieldvalue"><b id="pSex">&nbsp;</b></td>
                                <td class="fieldname">年龄：</td>
                                <td class="fieldvalue"><b id="pAge">&nbsp;</b></td>
                            </tr>
                            <tr>
                                <td class="fieldname">病历号：</td>
                                <td class="fieldvalue"> <b id="pBlh">&nbsp;</b></td>
                                <td class="fieldname">就诊卡号：</td>
                                <td class="fieldvalue"> <b id="pId">&nbsp;</b></td>
                                <td class="fieldname"> 诊断：</td>
                                <td class="fieldvalue"><b id="pDiagnostic">&nbsp;</b></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="title">
                    <div class="title-info" style="float: left;display:inline;" id="resultCaption">结果信息</div>
                    <div style=" float:right;display:inline;line-height: 1.1;color: #fff;margin:0 15px;cursor: pointer"><input type="checkbox" value="0" id="abnormal" name="abnormal"><label for="abnormal">显示异常值</label><span style="padding-left: 5px" onclick="$.Custom.openAbnormalDialog()"> 检验摘要</span></div>
                    <div class="cl">&nbsp;</div>
                </div>

                <div class="test-result">
                    <table class="table" id="testResultGrid" border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <th class="td1">项目</th>
                            <th class="td2">结果</th>
                            <th class="td3">历史</th>
                            <th class="td3">历史1</th>
                            <th class="td3">历史2</th>
                            <th class="td3">机器号</th>
                            <th class="td3">参考值</th>
                            <th class="td3">单位</th>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="cl">&nbsp;</div>
    </div>
</div>
<style>
    ul{
        list-style: none;
    }
    .testlist ul{
        width:450px;
    }
    .testlist li{
        list-style-type:none;
        float: left;
        padding: 0px 5px;
        width:200px;
        line-height: 20px;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        vertical-align: middle;
        display:inline;
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
        width:400px;
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
    a{
        text-decoration: none;
        color: #222;
    }
    a:hover{
        color: #fe9e19;
    }
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
            <ul id="testList" class="testlist">
            </ul>

        </div>

    </div>
    <div class="tableList">
        <table class="table" id="testTable" style="width: 100%;">
            <tr><th>名称</th><th>英文名</th><th>操作</th></tr>
        </table>
    </div>
</div>

<div id="chartDialog" style="text-align:left;display:none;" >
    <p class="alert alert-danger" id="chartAlert"><fmt:message key='result.chart.alert'/></p>
    <div id="singleChartPanel" style="width:640px;height:320px"></div>
    <table id="chartTongji" class="table">
        <tbody>
        <tr><th><fmt:message key='tongji.min' /></th><td><span id="tongji_min"></span></td><th><fmt:message key='tongji.max'/></th><td><span id="tongji_max"></span></td><th><fmt:message key='tongji.mid' /></th><td><span id="tongji_mid"></span></td></tr>
        <tr><th><fmt:message key='tongji.ave'/></th><td><span id="tongji_ave"></span></td><th><fmt:message key='tongji.sd' /></th><td><span id="tongji_sd"></span></td><th><fmt:message key='tongji.cov'/></th><td><span id="tongji_cov"></span></td></tr>
        </tbody>
    </table>
    <div id="hmInfo"></div>
</div>
<div id="dialog" align="left" style="display:none;">
</div>
<div id="textDialog" align="left" style="display:none;">
    <textarea rows="" cols="" id="abnormalNote" style="width:650px;height:200px;padding:10px 10px"></textarea>
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
                            //$("#pType").html(data.patientInfo.type ||'');
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
                    if(sampleInfo.sampleNo.indexOf("BAA")>=0){
                    	
                    }else if(sampleInfo.sampleStatus>=6) {
                        headOtherInfo = '已打印';
                    }else{
                        if(sampleInfo.auditStatus <= -1){
                            headOtherInfo='<font color="red">无结果</font>';
                        }else{
                            //headOtherInfo='有结果';
                       }
                    }
                    var headInfo = (i+1)+'、'+ sampleInfo.examinaim +'<font color=\'#FF9800\'>['+ sampleInfo.sampleNo+'] </font> (样本:' +sampleInfo.type+' 科室:'+sampleInfo.section+' 床号:'+sampleInfo.bedno+') ' + '<button id=\'SampleTrace\' Onclick=\'showSampleTrace('+sampleInfo.doctadviseno+')\'>样本踪迹</button>' + headOtherInfo;
                    //add SubHead
                    var subHeadRow = $('<tr class="headRow show" groupid="0" title="'+'样本:' +sampleInfo.type+' 科室:'+sampleInfo.section+' 床号:'+sampleInfo.bedno+')' +'"><td colspan=8>'+headInfo+'</td></tr>');
                    table.append(subHeadRow);
                    subHeadRow.click (function(event){
                        public.togger(this);
                    });
                    //add subDetailGrid
                    if(rowDatas.length>0){
                        subHeadRow.attr('groupid','1');
                        var subTable = public.loadSubGrid(sampleInfo.sampleNo,sampleInfo.examinaim,rowDatas,type);
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
                if(val1.indexOf("阳")>-1 || val1.indexOf("+") > -1) color=101;
                //if($.trim(val1)=='I') color =102;    //药敏结果 中间
                //if($.trim(val1)=='S') color =103;   //敏感

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
                    case 102:
                        //I
                        return "<span class='color102'>"+val1+"</span>";
                    case 103:
                        //s
                        return "<span class='color103'>"+val1+"</span>";
                    default:
                        return val1;
                }
            },
            getAbnormal:function(val1,val2){
                //获取高低标记
                //flag:0正常 1低 2 高 3 阳
                val2 = val2 ||'';
                var flag = "0";

                if(val2 != ''){
                    var hl = val2.split("-");
                    var h = parseFloat(hl[1]);
                    var l = parseFloat(hl[0]);
                    var va = parseFloat(val1);
                    if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
                        if (va < l) {
                            flag = "1";
                        } else if (va > h) {
                            flag = "2";
                        }
                    }
                }else{
                    if(val1.indexOf("阳")>-1 || val1.indexOf("+") > -1) {
                        flag  ="3";
                    }
                }
                return flag;
            },
            loadSubGrid:function(sampleid,examinaim,rowDatas,type){
                //add sub header
                var header = $('<tr></tr>');
                for(i=0;i < public.gridHead.length; i++){
                    var width =public.gridHead[i].width ||0;
                    if(width==0) width = 60;
                    width = width+'px';
                    header.append("<th class='td"+i+"' width='"+width+"'>"+public.gridHead[i].name+"</th>");
                }
                //加载子表数据
                var subTable=$('<table class="table subtable testdetail" id='+sampleid+' examinaim='+examinaim+'></table>');
                subTable.append(header);
                for(j=0;j < rowDatas.length;j++) {
                    var row = $("<tr class='hover testitem' testid=" + rowDatas[j].id + " sampleid=" + sampleid + " abnormal=''></tr>");
                    if (j % 2 == 0) {
                        row.addClass("light");
                    } else {
                        row.addClass("dark");
                    }
                    var flag = public.getAbnormal(rowDatas[j].result, rowDatas[j].scope);

                    if (flag =='1' || flag =='2' || flag == '3') {
                        row.attr('abnormal', flag);
                        if ($('#abnormal').attr('checked')) {
                            row.addClass('showAbnormal');
                        }
                    }else{
                        row.attr('abnormal', flag);
                        if ($('#abnormal').attr('checked')) {
                            row.addClass('hideRow');
                        }else{
                            row.removeClass('hideRow');
                        }
                    }

                    if(type==4){
                        var rNameTD=$("<td colspan='8'></td>");      //名称
                        var a= $('<a href="#_" val="'+rowDatas[j].knowledgeName+'" style="color: green;"></a>');
                        a.append(rowDatas[j].name);
                        a.click(function(){
                        	public.show_knowledge($(this).attr('val'));
                        });
                        row.attr('abnormal', '');
                        rNameTD.append(a);
                        //rNameTD.html('<a>'+rowDatas[j].name+'</a>');
                        rNameTD.attr('title',rowDatas[j].name);
                        row.append(rNameTD);
                    }else{
                        var rNameTD=$("<td></td>");      //名称
                        var a= $('<a href="#_" val='+rowDatas[j].knowledgeName+'></a>');
                        a.append(rowDatas[j].name);
                        a.click(function(){
                        	public.show_knowledge($(this).attr('val'));
                        });
                        rNameTD.append(a);
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
                        });
                        row.mousedown(function(e){
                            if(e.which == 3 ){
                                public.showCharts($(this).attr('testid'),$(this).attr('sampleid'));
                                //alert('rightmenu==' + $(this).attr('testid') +"  sampleid="+$(this).attr('sampleid'))
                            }
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
                                row.addClass("show");
                                var subDrugTable = public.loadDrugGrid(drugDatas);
                                var drugDataRow =$('<tr></tr>');
                                var drugDataTd = $('<td colspan=8 ></td>');
                                drugDataTd.append(subDrugTable);
                                drugDataRow.append(drugDataTd);
                                subTable.append(drugDataRow);
                                var rowHtml = row.find("td:eq(0)").html();
                                row.find("td:eq(0)").html('<span class="toggerLable">-</span>'+rowHtml);
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
                drugTable.append(header);
                for(j=0;j < drugDatas.length;j++) {
                    var row = $("<tr class='hover'></tr>");
                    if (j % 2 == 0) {
                        row.addClass("light");
                    } else {
                        row.addClass("dark");
                    }

                    var rNameTD = $("<td></td>");      //抗生素名
                    var a= $('<a href="#_" val='+drugDatas[j].testid+'></a>');
                    a.append(drugDatas[j].testid);
                    a.click(function(){
                    	public.show_knowledge($(this).attr('val'));
                    });
                    rNameTD.append(a);
                    rNameTD.attr('title',drugDatas[j].testid);
                    row.append(rNameTD);

                    var rResultTD = $("<td></td>");      //结果
                    rResultTD.html(drugDatas[j].testresult);
                    rResultTD.attr('title',drugDatas[j].testresult);
                    row.append(rResultTD);

                    var rHintTD = $("<td></td>");      //解释
                    rHintTD.html(drugDatas[j].hint);
                    rHintTD.attr('title',drugDatas[j].hint);
                    row.append(rHintTD);
                    if(drugDatas[j].hint=='I'){
                        row.addClass("color102");
                    }else if(drugDatas[j].hint=='S'){
                        row.addClass("color103");
                    }

                    var rRefhiTD = $("<td></td>");      //折点
                    var refhlo = '';
                    if(drugDatas[j].reflo !='' && drugDatas[j].refhi !=''){
                        refhlo = drugDatas[j].reflo +'~' +drugDatas[j].refhi;
                    }
                    rRefhiTD.html(refhlo);
                    row.append(rRefhiTD);

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
                    var checkObj = $(obj).children('input');
                    if(checkObj.attr("checked")){
                        var tr ='<tr><td>'+checkObj.attr('testname')+'</td><td>'+checkObj.attr('testab')+'</td><td>删除</td></tr>';
                        table.append(tr);
                    }
                });
                ul.parent().hide();
            },
            showCharts:function(id,sample){
                //关闭浏览器右键
                document.oncontextmenu=function(){return false;};
                $.get("../doctor/singleChart",{id:id, sample:sample},function(data){
                    $("#chartAlert").css("display","none");
                    public.openChartDialog();
                    $("#hmInfo").empty();
                    for (var i=0; i< data.hmList.length; i++) {
                        $("#hmInfo").append(data.hmList[i]);
                    }

                    if (data.num > 1) {
                        $("#tongji_max").html(data.max);
                        $("#tongji_min").html(data.min);
                        $("#tongji_mid").html(data.mid);
                        $("#tongji_ave").html(data.ave);
                        $("#tongji_sd").html(data.sd);
                        $("#tongji_cov").html(data.cov);
                        var xset = data.time;
                        var yset1 = data.lo;
                        var yset2 = data.re;
                        var yset3 = data.hi;
                        var chart = new Highcharts.Chart({
                            title: {
                                text: data.name
                            },
                            credits: {
                                enabled:false
                            },
                            chart: {
                                renderTo: 'singleChartPanel',
                                type: 'line',
                                marginRight: 130,
                                marginBottom: 25
                            },
                            xAxis: {
                                type: 'datetime',
                                categories: xset
                            },
                            yAxis: {
                                title: {
                                    text: '结果'
                                },
                                plotLines: [{
                                    value: 0,
                                    width: 1,
                                    color: '#808080'
                                }]
                            },
                            legend: {
                                layout: 'vertical',
                                align: 'right',
                                verticalAlign: 'middle',
                                borderWidth: 0
                            },
                            series: [{
                                name: '低值',
                                data: yset1
                            }, {
                                name: '检验结果',
                                data: yset2
                            }, {
                                name: '高值',
                                data: yset3
                            }]
                        });
                    } else {
                        $("#chartAlert").css("display","block");
                    }
                });
            },
            openChartDialog:function () {
            layer.open({
                type: 1,
                shade: 0.4,
                skin: 'layui-layer-lan',
                area:['680px','540px'],
                title: '样本项目试剂信息和历史记录',
                content: $('#chartDialog'),
                cancel: function(index){
                    layer.close(index);
                    //关闭浏览器右键
                    document.oncontextmenu=function(){return true;};
                }
            });
        },
            show_knowledge:function (item) {
                //显示相关知识
                jQuery.ajax({
                    type:'GET',
                    //url: encodeURI('../doctor/item.jsp?page='+item),
                    url: encodeURI('/item.jsp?page='+item),
                    dataType: 'html',
                    success: function(data) {
                        var data2=public.dataProcess(data);
                        document.getElementById("dialog").innerHTML = data2;
                        public.jqTab("#tabs",".tab_con");
                        public.openKnowledgeDialog();
                    }
                });
            },
            dataProcess:function(data){
                var title=$('<ul class="tabs" id="tabs"></ul>');
                var content=$("<ul class='tab_conbox' id='tab_conbox'></ul>");
                $(data).find('.tabbedSection').children('div').each(function(i,obj){
                    var classname=$(obj).attr("class").replace('tab-','');
                    var li=$('<li><a href="#_"  tab="tab'+i+'">'+classname+'</a></li>');
                    var licontent =$('<li id="tab'+i+'" class="tab_con" tabname='+classname+'>'+$(obj).html()+'<\/li>');
                    title.append(li);
                    content.append(licontent);
                });
                var result = '<div id="tabbox">'+title.prop("outerHTML")+content.prop("outerHTML");
                result = result + "<\/div>";
                return result;
            },
            openAbnormalDialog:function(){
                var text='';
                var sampledate = '';
                $('#testResultGrid').find('.testdetail').each(function(i,obj){
                    var examinaim = $(obj).attr('examinaim') || '';
                    var sampleno = $(obj).attr('id') || '';
                    if(sampleno != ''){
                        sampledate = sampleno.substring(0,8);
                    }
                    if(examinaim !=''){
                        var index = examinaim.indexOf('（');
                        if(index>0){
                            examinaim = examinaim.substring(0,index);
                        }
                    }
                    var subtext ="";
                    $(obj).find('tr.testitem').each(function(j,tr){
                        //console.log(tr)
                        var cellName = $(tr).find('td').eq(0).attr('title') ||'';
                        var cellResult = $(tr).find('td').eq(1).attr('title') ||'';
                        var cellUnit = $(tr).find('td').eq(7).text() ||'';
                        if(subtext !='') subtext +="、";
                        subtext +=cellName+' '+cellResult+cellUnit;
                    });
                    if(subtext!=''){
                        if(text !='') text +="\r\n";
                        text += examinaim+"：" ;
                        text += subtext;
                        subtext = "";
                    }
                });
                text = sampledate +'查'+text;
                $('#abnormalNote').text(text);
                layer.open({
                    type: 1,
                    shade: 0.4,
                    area:['680px','360px'],
                    title: '相关知识',
                    content: $("#textDialog"),
                    btn:['复制','取消'],
                    yes:function(index){
                        //复制
                        var note=document.getElementById("abnormalNote");
                        note.select(); // 选择对象
                        document.execCommand("Copy"); // 执行浏览器复制命令
                        alert('复制成功!');
                    },
                    cancel: function(index){
                        layer.close(index);
                    }
                });
            },
            openKnowledgeDialog:function() {
                layer.open({
                    type: 1,
                    shade: 0.4,
                    skin: 'layui-layer-lan',
                    area:['680px','360px'],
                    title: '相关知识',
                    content: $("#dialog"),
                    cancel: function(index){
                        layer.close(index);
                    }
                });
            },
            jqTab:function(tabtit,tabcon){
                $(tabcon).hide();
                $(tabtit+" li:first").addClass("thistab").show();
                $(tabcon+":first").show();
                $(tabtit+" li").click(function() {
                    $(tabtit+" li").removeClass("thistab");
                    $(this).addClass("thistab");
                    $(tabcon).hide();
                    var activeTab = $(this).find("a").attr("tab");
                    $("#"+activeTab).fadeIn();
                    return false;
                });
            }

        };
        return public;
    })();
    
    function showSampleTrace(doctadviseno){
    	window.open("../doctor/sampleTrace?type=4&name="+doctadviseno);
    }

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
        });
        var height = $(window).height();
        var width = $(window).width();
        var height = (document.documentElement.scrollHeight >document.documentElement.clientHeight) ? document.documentElement.scrollHeight : document.documentElement.clientHeight;
        $('.samplelist').height(height-$('.samplelist').offset().top-20);
        $('.test-result').height(height-$('.samplelist').offset().top-60);
        $('.test-result').width(width-$(".samplelist").width()-20);

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
        $("#abnormal").bind("click", function () {
            if( $("#abnormal").attr('checked')=='checked'){
                $('#testResultGrid').find('.subtable tr').each(function(i,obj){
                    obj=$(obj);
                    var abnormal = obj.attr('abnormal') ||'';
                    if(obj.attr('abnormal')=='0'){
                        obj.addClass("hideRow");
                    }else {
                        obj.removeClass("hideRow");
                    }
                });
                $(this).siblings('label').text('显示全部')
            }else{
                $('#testResultGrid .hideRow').removeClass("hideRow");
                $(this).siblings('label').text('显示异常值')
            }
            //obj.click();
        });
    })

</script>
</html>

