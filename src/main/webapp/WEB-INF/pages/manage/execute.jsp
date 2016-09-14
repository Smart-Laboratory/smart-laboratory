<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="sample.manage.execute"/></title>
    
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/layer/extend/layer.ext.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
	<script type="text/javascript" src="../scripts/manage/execute.js"></script>
</head>
<style>

.sampleInfo{
	height:70px;
	margin-bottom:0px;
}

#tests span{
	margin-left:10px;
}

#tests input[type=checkbox]{
	width:50px;
	height:50px;
	margin:0px;
}

#checkAll {
	width:15px;
	height:15px;
	vertical-align:middle;
	margin:auto;
}

#cxhis span{
	margin:2px 5px;
}
	.alert {
		padding:8px;
	}


</style>

<body>

<input type="hidden" id="laborder" />

<div class="row">
	<div class="col-sm-3">
		<div class="widget-box widget-color-green">
			<div class="widget-header widget-header-small">
				<h4 class="widget-title"><fmt:message key="execute.cxxx" /></h4>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse">
						<i class="ace-icon fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main">
					<div>
						<label ><fmt:message key="execute.jzkh" /></label>
						<input type="text" id="jzkh" class="form-control" onkeypress="getData(this,event)">
					</div>
					<%--<div style="padding-top:5px;">
						<button><fmt:message key="execute.smk" /></button>
						<button><fmt:message key="execute.jkk" /></button>
					</div>--%>
					<div class="checkbox">
						<label>
							<input type="checkbox" id="bloodCheck" class="ace ace-switch ace-switch-4" ><fmt:message key="execute.bloodSample" />
						</label>
					</div>
					<div id="dateChose" style="padding:5px 0px;">
						<span><fmt:message key="execute.date" /></span>
						<div class="form-inline">
							<label for="from" style="margin-left : 10px; width:50px;"><b><fmt:message key="from" /></b></label>
							<input type="text" id="from" name="from" class="laydate-icon-molv" style="height:33px;"/>
						</div>
						<div class="form-inline">
							<label for="to" style="margin-left : 10px; width:50px;" ><b><fmt:message key="to" /></b></label>
							<input type="text" id="to" name="to" class="laydate-icon-molv" style="height:33px;">
						</div>
						
					</div>
					<button id="conform" class="btn btn-sm btn-success"><fmt:message key="button.confirm" /></button>
					<button id="unusualRegister" class="btn btn-sm btn-warning">异常登记</button>
					<button type="button" class="btn btn-sm btn-info" title="打印设计" onclick="printSet()">
						<i class="ace-icon fa fa-pencil-square bigger-110"></i>
						回执单打印设计
					</button>
					<div>
						<div class="radio">
							<label class="radio">
			  				<input type="radio" name="select_type" id="uncheck" value="0" checked>
			  				<fmt:message key="execute.uncheck" />
							</label>
						</div>
						<div class="radio">
							<label class="radio">
				  			<input type="radio" name="select_type" id="all" value="100" >
				  			<fmt:message key="execute.all" />
							</label>
						</div>
						<div class="radio">
							<label class="radio">
				  			<input type="radio" name="select_type" id="executed" value="999" >
				  			<fmt:message key="execute.executed" />
							</label>
						</div>
					</div>
					<div class="checkbox" style="border-top:1px solid #000000;">
			    		<label>
			      			<input type="checkbox" id="selfexecute" value=""> 自抽
			    		</label>
			    	</div>
			    	<div class="checkbox">
			    		<label>
			      			<input type="checkbox" value=""> <fmt:message key="execute.printbarcode" />
			    		</label>
			  		</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12" id="cxhis" style="margin:5px 0px;border:1px solid #82af6f;">
			<div class="col-sm-12 ">
				<span class='col-sm-4'>抽血次数:</span><b id="cxcx"></b>
			</div>
			<span class='col-sm-12'>最后一次抽血情况:</span>
			<div class="col-sm-12 ">
				<span class='col-sm-4'>抽血人员:</span><b id="cxry"></b>
			</div>
			<div class="col-sm-12 ">
				<span class='col-sm-4'>抽血时间:</span><b id="cxsj"></b>
			</div>
			<div class="col-sm-12 ">
				<span class='col-sm-4'>检验项目:</span><b id="cxxm"></b>
			</div>
		</div>
	</div>
	<div class="col-sm-9" style="">
		<div id="patientInfo" style="width:99%;margin:10px 5px;">
			<div class="widget-box widget-color-green">
				<div class="widget-header widget-header-small">
					<h4 class="widget-title"><fmt:message key="execute.brxx" /></h4>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="ace-icon fa fa-chevron-up"></i>
						</a>
					</div>
				</div>
				<div class="widget-body" style="overflow:auto;">
					<div class="widget-main">
						<div  style="height:30px;padding-top:5px;">
							<div class="col-sm-2 ">
									<span class='col-sm-6'><fmt:message key="patient.blh" />:</span><b id="blh"></b>
							</div>
							<div class="col-sm-3 ">
									<span class='col-sm-4'><fmt:message key="patient.patientId" />:</span><b id="patientId"></b>
							</div>
							<div class="col-sm-2 ">
								<span class='col-sm-6'><fmt:message key="patient.name" />:</span><b id="pName"></b>
							</div>
							<div class="col-sm-3 ">
								<span class='col-sm-3'><fmt:message key="patient.sex" />:</span><b id="pSex" class='col-sm-3'></b>
								<span class='col-sm-3'><fmt:message key="patient.age" />:</span><b id="pAge" class='col-sm-3'></b>
							</div>
							<button class="btn btn-info btn-minier" id="sampleQuery">检验结果查询</button>
						</div>
						<div class="col-sm-12">
							<div id="warnLabel" class="alert alert-success col-sm-5" style="display: block; margin:5px 5px; padding: 0px 0px;">
							</div>
							<div id="examtodo" class="alert alert-success col-sm-6" style="display: block; margin:5px 5px; padding: 0px 0px;">
							</div>
						</div>
						
						<div class="col-sm-12" id="samplehis" style="margin:5px 5px;">
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<div style="width:99%; width:99%;margin:10px 5px;">
			<div class="widget-box widget-color-green">
				<div class="widget-header widget-header-small">
					<h4 class="widget-title"><fmt:message key="execute.datalist" /></h4>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="ace-icon fa fa-chevron-up"></i>
						</a>
					</div>
					<div class="widget-toolbar">
						<select id="requestModeSelect">
							<option value="0" selected>全部</option>
							<option value="1">门诊</option>
							<option value="2">急诊</option>
						</select>
					</div>
					<div class="widget-toolbar">
						<input id="checkAll" type='checkbox'/>全选/全不选
					</div>
				</div>
				<div class="widget-body" style="overflow-x:scroll;">
					<div id="tests" class="widget-main">

					</div>
				</div>
			</div>
		</div>
	
	</div>


</div>


<div id="executeUnusualDialog" class="col-sm-11" style="display:none;">
	<form class="form-horizontal" role="form" style="margin-top:5px;" id="executeUnusualForm">
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="unpatientid">就诊卡号：</label>
			<div class="col-sm-9">
				<input type="text" class="col-sm-10" id="unpatientid" disabled />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="part">抽血位置：</label>
			<div class="col-sm-9">
				<input type="text" class="col-sm-10" id="part" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="mode">抽血方式：</label>
			<div class="col-sm-9">
				<input type="text" class="col-sm-10" id="mode" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="reaction">抽血反应：</label>
			<div class="col-sm-9">
				<input type="text" class="col-sm-10" id="reaction" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="time">抽血次数：</label>
			<div class="col-sm-9">
				<input type="text" class="col-sm-10" id="time" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="note">备注：</label>
			<div class="col-sm-9">
				<textarea id="note" rows="3" class="form-control" style="width:90%;"></textarea>
			</div>
		</div>
		<input type="button" id="tijiao" class="btn btn-info" onclick="unusual()" value="提交">
	</form>
</div>

</body>