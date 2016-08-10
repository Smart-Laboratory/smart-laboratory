<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhou
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
            padding: 0 5px;

        }
        .table tr.dark {
            background-color: #F9F9F9;
        }
        .table tr.light {
            background-color: #ffffff;
        }
        .table tbody tr:hover td{
            background-color: #DFF7A4;
            cursor:pointer;
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
            overflow: auto;
        }
        input{border:1px solid #dddddd;
            height: 20px;}
        .text{
            width: 90px;
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
                    <form id="SearchForm" class="form-inline" action="../doctor/getSampleList" method="post">
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
                                <td><div style=""><select id="selectType" name="selectType"  style="border:none;margin:-1px;">
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
            <table class="table" border="0" cellspacing="0" cellpadding="0">
                <tbody><tr>
                    <th style="width: 120px">样本号</th>
                    <th style="width: 150px">检验目的</th>
                    <th style="width: 50px">状态</th>
                </tr>
                <c:forEach  items="${sampleList}" var="sampelist" varStatus="status">
                <tr <c:choose><c:when test="${status.index%2==0}">class="light" </c:when><c:otherwise>class="dark" </c:otherwise></c:choose>>
                    <td sampleId="${sampelist.id}" style="display: none" id="sampleId" sampleNo="${sampelist.sampleNo}">
                        <c:out value="${sampelist.id}" />
                    </td>
                    <td title="${sampelist.sampleNo}">
                        <c:out value="${sampelist.sampleNo}" />
                    </td>
                    <td title="${sampelist.inspectionName}">
                        <c:out value="${sampelist.inspectionName}" />
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${sampelist.sampleStatus>=6}">已打印 </c:when>
                            <c:otherwise>
                                <c:choose> <c:when test="${sampelist.auditStatus>=-1}">
                                    <font color='red'>无结果</font>
                                </c:when>
                                    <c:otherwise> 有结果</c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </td>
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
                    <div id="patient-info" class="alert alert-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
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
                <div class="title"><span class="title-info" id="resultCaption">结果信息</span></div>
                <div class="test-result">
                    <table class="table" id="testResultGrid" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tbody><tr>
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
                        <%--<tr class="light">--%>
                            <%--<td >20160606BAA902</td>--%>
                            <%--<td>结核菌涂片检查</td>--%>
                            <%--<td>有结果</td>--%>
                        <%--</tr>--%>
                        </tbody>
                    </table>
                </div>
            </div>
            <%--<div class="row test-result">--%>
                <%--<div class="title"><span class="title-info">结果信息2</span></div>--%>
                <%--<table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">--%>
                    <%--<tbody><tr>--%>
                      <%----%>
                    <%--</tbody>--%>
                <%--</table>--%>
            <%--</div>--%>
        </div>
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
            getPatientInfo:function(sampleNo){
                $.ajax({
                    url:"../doctor/getData",
                    type:"post",
                    dataType:"json",
                    data:{id:sampleNo},
                    success:function(data){
                       if(window.console) window.console.log(data);
                        if(data && data.patientInfo){
                            $("#sampleTitle").html(data.patientInfo.examinaim ||'');
                            $("#resultCaption").html(data.patientInfo.examinaim ||'');
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
            loadGrid:function(rowDatas){
                var table = $('#testResultGrid');
                table.empty();
                //add header
                var header = $('<tr></tr>');
                for(i=0;i < public.gridHead.length; i++){
                    var width =public.gridHead[i].width ||0;
                    if(width==0) width = 60;
                    width = width+'px';
                    header.append("<th class='td"+i+"' width='"+width+"'>"+public.gridHead[i].name+"</th>");
                }
                if(window.console)window.console.log(rowDatas);
                table.append(header);
                for(var i=1;i<rowDatas.length;i++){
                    var row=$("<tr></tr>");
                    if(i%2 ==0){
                        row.addClass("dark");
                    }else{
                        row.addClass("light");
                    }
                    var rNameTD=$("<td></td>");      //名称
                    rNameTD.html(rowDatas[i].name);
                    rNameTD.addClass("td0");
                    row.append(rNameTD);

                    var rResultTD=$("<td></td>");      //结果
                    rResultTD.html(public.getHLLable(rowDatas[i].result,rowDatas[i].scope,rowDatas[i].color));
                    row.append(rResultTD);

                    var rLastTD=$("<td></td>");      //历史
                    rLastTD.html(rowDatas[i].last);
                    row.append(rLastTD);

                    var rLast1TD=$("<td></td>");      //历史1
                    rLast1TD.html(rowDatas[i].last1);
                    row.append(rLast1TD);

                    var rLast2TD=$("<td></td>");      //历史2
                    rLast2TD.html(rowDatas[i].last2);
                    row.append(rLast2TD);

                    var rDeviceTD=$("<td></td>");      //机器
                    rDeviceTD.html(rowDatas[i].device);
                    row.append(rDeviceTD);

                    var rSopeTD=$("<td></td>");      //参考范围
                    rSopeTD.html(rowDatas[i].scope);
                    row.append(rSopeTD);

                    var rUnitTD=$("<td></td>");      //单位
                    rUnitTD.html(rowDatas[i].unit);
                    row.append(rUnitTD);

                    row.bind('click',function(){
                        //alert('rowClick');
                    });
                    table.append(row);
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
            }
        };
        return public;
    })();

    $(function(){
        $(".samplelist tr").bind("click",function(){
            $(".samplelist tr").removeClass('active');
            var sampleId = this.cells[0].getAttribute("sampleId") || '';
            if(sampleId !=''){
                jQuery.Custom.getPatientInfo(sampleId);
            }
            if( $(this).hasClass('active') ){
                $(this).removeClass('active')
            }else{
                $(this).addClass('active')
            }
        });
        var height = $(window).height();
        //var height = (document.documentElement.scrollHeight >document.documentElement.clientHeight) ? document.documentElement.scrollHeight : document.documentElement.clientHeight;
        $('.samplelist').height(height-$('.samplelist').offset().top-20);
        $('.test-result').height(height-$(".test-result").offset().top-20);

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
    })
</script>
</html>

