<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/31
  Time: 10:44
  Description:指标编辑表单
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
	<title>标本交接</title>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/i18n/grid.locale-cn.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.jqGrid.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/layer.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/validform/Validform.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-datetimepicker.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/manage/sampleHandover.js"></script>

    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/font-awesome.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ace.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/font-awesome.css"/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />
    <!-- page specific plugin styles -->
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">

</head>
<style>
	body{
            background: #ECF0F1;
     }
    .tab-content{
        padding: 2px 12px;
        min-height: 510px;
        border-bottom:0px;
    }
    .widget-toolbar{
        z-index: 999;
        line-height:34px;
    }
    .bootstrap-duallistbox-container .buttons{
        display: none;
    }
    .lazy_header .input-icon, .nav-search-input{
        width: 100%;
    }
    .form-horizontal .form-group{
        margin-right: 0px;
        margin-left: 0px;
    }
    div.treeList{
        overflow: auto;
        min-height: 510px;
        overflow: auto;
        border: 1px solid #eeeeee;
    }
    .info-container{
        display: none;
    }
    h5{
    	text-align:right;
    	margin:5px 5px;
    }
    h4{
    	margin:5px 5px;
    }
    #intext .row{
    	border:1px solid #000000;
    }
</style>
<input type="hidden" value="0" id="isfulltag">
<div class="main-container" id="content">
    <div class="row">
        <div class="space-4"></div>
        <div class="col-xs-12">
            <div class="widget-toolbar no-border">
            	<button id="sampleTraceLink" class="btn btn-info" style="width:50px;height:28px;padding:0px 0px;border:0px;" >查询</button>
                <button id="fullScreen" class="btn btn-info" style="height:28px;padding:0px 0px;">
					<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
				</button>
            </div>

            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab">
                    <li >
                        <a data-toggle="tab" href="#sampleout">
                            <i class="blue ace-icon fa  fa-home bigger-120"></i>
                            	标本送出
                        </a>
                    </li>
                    <li class="active">
                        <a data-toggle="tab" href="#samplereceive">
                            <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                            	标本接收
                        </a>
                    </li>
                 </ul>
                 <div class="tab-content">
                    <!--常用信息start-->
                    <div id="sampleout" class="tab-pane fade">
                    	<div class="col-sm-12" >
							<div class="row" style="margin-top:10px;">
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h5>医嘱号:</h5></div>
									<div class="col-sm-7"><input type="text" id="doctout" name="doctout" class="form-control" onkeypress="getData(this,event);"></div>
								</div>
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h5>送出者:</h5></div>
									<div class="col-sm-7"><input type="text" id="outer" name="outer" class="form-control" value="${name}"></div>
								</div>
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h5>送出地点:</h5></div>
									<div class="col-sm-7">
										<select id="pointout" name="pointout" class="form-control">
											<c:forEach items="${pointList}" var="point">
											<option value="${point.code}">${point.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<hr style="height:3px;"/>
							<div id="alert" class="alert alert-success alert-dismissable" style="display:none;"></div>
							<div id="text" style="display:none;">
								<div class="row">
									<div class="col-sm-2"><h5><fmt:message key='sample.inspectionName'/></h5></div>	
									<div class="col-sm-10"><h4><b id="examinaim"></b></h4></div>
									<div class="col-sm-2"><h5><fmt:message key='patient.name'/></h5></div>	
									<div class="col-sm-2"><h4><b id="name"></b></h4></div>
									<div class="col-sm-1"><h5><fmt:message key='patient.sex'/></h5></div>	
									<div class="col-sm-1"><h4><b id="sex"></b></h4></div>
									<div class="col-sm-1"><h5><fmt:message key='patient.age'/></h5></div>	
									<div class="col-sm-1"><h4><b id="age"></b></h4></div>
									<div class="col-sm-2"><h5><fmt:message key='sample.mode'/></h5></div>	
									<div class="col-sm-2"><h4><b id="stayhospitalmode"></b></h4></div>
								</div>
								<div id="outpatient" class="row">
									<div class="col-sm-2"><h5><fmt:message key='patient.section'/></h5></div>	
									<div class="col-sm-10"><h4><b id="section"></b></h4></div>
								</div>
								<div id="wardtext" class="row">
									<div class="col-sm-12">
										<div class="col-sm-2"><h5><fmt:message key='ward.section'/></h5></div>	
										<div class="col-sm-5"><h4><b id="ward"></b></h4></div>
										<div class="col-sm-2"><h5><fmt:message key='patient.departbed'/></h5></div>	
										<div class="col-sm-3"><h4><b id="bed"></b></h5></div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-2"><h5><fmt:message key='ward.type'/></h5></div>	
										<div class="col-sm-4"><h4><b id="type"></b></h4></div>
										<div class="col-sm-2"><h5><fmt:message key='ward.phone'/></h5></div>	
										<div class="col-sm-4"><h4><b id="phone"></b></h4></div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-2"><h5><fmt:message key='sample.lab'/></h5></div>	
									<div class="col-sm-10"><h4><b id="lab"></b></h4></div>
								</div>
								
							</div>
							
							<h4>标本送出列表</h4>
							<div id="outListDiv" class="col-sm-12" style="overflow:auto;">
								<table id="outList" class="table table-striped table-bordered table-hover"></table>
								<div id="pager"></div>
							</div>
							
						</div>
                    </div>
					<!-- 标本接收模块开始 -->
                    <div id="samplereceive" class="tab-pane fade in active">
                    	<input id="doctadviseno" type="hidden" />
                        <div class="col-sm-12">
							<div class="row" style="margin-top:10px;">
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h3 style=" text-align: right; margin: 10px 10px;"><fmt:message key="sample.id"/></h3></div>
									<div class="col-sm-7"><input type="text" style="height:45px;" id="doctin" name="doctin" class="form-control" onkeypress="getData(this,event);"></div>
								</div>
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h3 style=" text-align: right; margin: 10px 10px;"><fmt:message key="tat.receiver"/></h3></div>
									<div class="col-sm-7"><input type="text" id="operator" style="height:45px;" name="operator" class="form-control" value="${name}"></div>
								</div>
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h3 style=" text-align: right; margin: 10px 10px;"><fmt:message key="receive.point"/></h3></div>
									<div class="col-sm-7">
										<select id="point" style="height:45px;" name="point" class="form-control">
											<c:forEach items="${pointList}" var="point">
											<option value="${point.code}">${point.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div style="float:left;width:10%">
									<button id="refuse" type="button" class="btn btn-danger"><fmt:message key='invalidSamples.refuse'/></button>
								</div>
							</div>
							<hr style="height:3px;"/>
							<div id="inalert" style="font-size:14px;" class="alert alert-success alert-dismissable" style="display:none;"></div>
							<div id="intext" style="display:none;">
								<div class="row">
									<div class="col-sm-2"><h5><fmt:message key='sample.inspectionName'/></h5></div>	
									<div class="col-sm-10"><h4><b id="inexaminaim"></b></h4></div>
									<div class="col-sm-2"><h5><fmt:message key='patient.name'/></h5></div>	
									<div class="col-sm-2"><h4><b id="inname"></b></h4></div>
									<div class="col-sm-1"><h5><fmt:message key='patient.sex'/></h5></div>	
									<div class="col-sm-1"><h4><b id="insex"></b></h4></div>
									<div class="col-sm-1"><h5><fmt:message key='patient.age'/></h5></div>	
									<div class="col-sm-1"><h4><b id="inage"></b></h4></div>
									<div class="col-sm-2"><h5><fmt:message key='sample.mode'/></h5></div>	
									<div class="col-sm-2"><h4><b id="instayhospitalmode"></b></h4></div>
								</div>
								<div id="inoutpatient" class="row">
									<div class="col-sm-2"><h5><fmt:message key='patient.section'/></h5></div>	
									<div class="col-sm-10"><h4><b id="insection"></b></h4></div>
								</div>
								<div id="inwardtext" class="row">
									<div class="col-sm-12">
										<div class="col-sm-2"><h5><fmt:message key='ward.section'/></h5></div>	
										<div class="col-sm-4"><h4><b id="inward"></b></h4></div>
										<div class="col-sm-2"><h5><fmt:message key='patient.departbed'/></h5></div>	
										<div class="col-sm-4"><h4><b id="inbed"></b></h4></div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-2"><h5><fmt:message key='ward.type'/></h5></div>	
										<div class="col-sm-4"><h4><b id="intype"></b></h4></div>
										<div class="col-sm-2"><h5><fmt:message key='ward.phone'/></h5></div>	
										<div class="col-sm-4"><h4><b id="inphone"></b></h4></div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-sm-2"><h5><fmt:message key='sample.lab'/></h5></div>	
									<div class="col-sm-10"><h4><b id="inlab"></b></h4></div>
								</div> 
								
							</div>
							
							<h4>标本接收列表</h4>
							<div id="receiveListDiv" class="col-sm-12" style="overflow:auto;">
								<table id="receiveList" class="table table-striped table-bordered table-hover"></table>
								<div id="rpager"></div>
							</div>
							
						</div>
                    </div>
                 </div>
            </div>
        </div>
    </div>
</div>