<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='/scripts/pb/sz.js'/> "></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	
	 <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.tablednd_0_5.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/jquery.validate.js"></script>
    <script type="text/javascript" src="../scripts/raphael.2.1.0.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.form.js"></script>
	
</head>


<input id="section" type="hidden" value="${section }"/>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><fmt:message key="pb.wi"/></a></li>
		<li><a href="#tabs-2"><fmt:message key="pb.bc"/></a></li>
		<li><a href="#tabs-3">实习生学校</a></li>
		<li><a href="#tabs-4">工作统计</a></li>
	</ul>
	<div id="tabs-1">
		<button id="resetHoliday" class="bth btn-success"  ><fmt:message key="pb.resetHoliday"/></button>
		<table id="witable"></table>
		<div id="wipager"></div>
	</div>
	<div id="tabs-2">
		<table id="bctable"></table>
		<div id="bcpager"></div>
	</div>
	<div id="tabs-3" class="colsm-12">
		<div class="col-sm-10">
			<table id="sxschooltable"></table>
			<div id="sxschoolpager"></div>
		</div>
		<div class="col-sm-2">
			<c:forEach items="${sxshifts }" var ="shift">
				<div>
					<span>${shift.key } = ${shift.value }</span>
				</div>
			</c:forEach>
		</div>
		
	</div>
	<div id="tabs-4">
	<div class="form-inline">
		<label for="from" style="margin-left : 20px;"><b><fmt:message key="from" /></b></label>
		<input type="text" id="from" name="from" class="form-control"  value="${from }"/>
		<label for="to" style="margin-left : 10px;" ><b><fmt:message key="to" /></b></label>
		<input type="text" id="to" name="to" class="form-control" value="${to }">
		
		<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
		<button id="workCount" class="btn btn-success form-control">工作量统计</button>
	</div>	
	<div>
		<table id="workData" class=" table" style="font-size:12px;text-align:center;" border="1px;"></table>
		<div id="workpager"></div>
	</div>
	</div>
	
</div>
<div style="margin-top:10px;font-size:15px;">
		<p>22-检验科；
		220100-临检组；
		220200-生化组；
		220300-免疫组；
		220400-微生物组；
		220600-血库组；
		220700-分子实验室；
		</p>
		<p>1300000-<fmt:message key="labDepartment.1300000"/>;  1300100-<fmt:message key="labDepartment.1300100"/>; 1300400-<fmt:message key="labDepartment.1300400"/>;
			1300200-<fmt:message key="labDepartment.1300200"/>;  1300300-<fmt:message key="labDepartment.1300300"/>;
			1300500-<fmt:message key="labDepartment.1300500"/>;  1300501-<fmt:message key="labDepartment.1300501"/>;
			1300600-<fmt:message key="labDepartment.1300600"/>;  1300700-<fmt:message key="labDepartment.1300700"/>;
			1300800-<fmt:message key="labDepartment.1300800"/>;
		<p><fmt:message key="pb.sort"/></p>
</div>


