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
    .tab-content{
        padding: 2px 12px;
        min-height: 510px;
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
    }
    h4{
    	margin-top:10px;
    }
</style>
<div class="main-container" id="content">
    <div class="row">
        <div class="space-4"></div>
        <div class="col-xs-12">
            <div class="widget-toolbar no-border">
                <button class="btn btn-xs btn-info" onclick="$.Custom.save()">
                    <i class="ace-icon fa fa-floppy-o bigger-120"></i>
                    	保存
                </button>
            </div>

            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab">
                    <li class="active">
                        <a data-toggle="tab" href="#sampleout">
                            <i class="blue ace-icon fa  fa-home bigger-120"></i>
                            	标本送出
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#samplereceive">
                            <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                            	标本接受
                        </a>
                    </li>
                 </ul>
                 <div class="tab-content">
                    <!--常用信息start-->
                    <div id="sampleout" class="tab-pane fade in active">
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
							<div id="outListDiv" class="col-sm-12">
								<table id="outList"></table>
								<div id="pager"></div>
							</div>
							
						</div>
                    </div>

                    <div id="samplereceive" class="tab-pane fade">
                        <div class="col-sm-12">
							<div class="row" style="margin-top:10px;">
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h5><fmt:message key="sample.id"/></h5></div>
									<div class="col-sm-7"><input type="text" id="doctin" name="doctin" class="form-control" onkeypress="getData(this,event);"></div>
								</div>
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h5><fmt:message key="tat.receiver"/></h5></div>
									<div class="col-sm-7"><input type="text" id="operator" name="operator" class="form-control" value="${name}"></div>
								</div>
								<div style="float:left;width:30%">
									<div class="col-sm-5"><h5><fmt:message key="receive.point"/></h5></div>
									<div class="col-sm-7">
										<select id="point" name="point" class="form-control">
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
										<div class="col-sm-3"><h5><b id="bed"></b></h5></div>
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
							
						</div>
                    </div>
                 </div>
            </div>
        </div>
    </div>
</div>