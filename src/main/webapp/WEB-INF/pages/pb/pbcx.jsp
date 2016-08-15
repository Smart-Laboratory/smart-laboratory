<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="../scripts/pb/pbcx.js"></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
</head>

<style>
select {
	width:auto;
	padding:0px 0px;
}
table tr td {
	width:60px;
}
table tr th {
	width:60px;
}
</style>

<input id="section" value="${section }" type="hidden"/>
<input id="jykCode" value="${jykCode }" type="hidden"/>
<input id="month" value="${month}" type="hidden"/>
<input id="type" value="${type }" type="hidden"/>
<input id="size" value="${size }" type="hidden"/>

<div class="form-inline">
	<input type="text" id="date" class="form-control" style="margin-left:20px;float:left;">
	<select id="sectionSel" class="form-control" style="margin-left:20px;float:left;">
		<c:forEach items="${pbSections }" var="pbsection" >
			<option value="${pbsection.code }">${pbsection.name }</option>
		</c:forEach>
	</select>
		<select id="typeSel" class="form-control" style="margin-left:20px;float:left;display:none">
			<option value="1" ><fmt:message key="pb.yb"/></option>
			<option value="4" >按天查询</option>
			<option value="7" ><fmt:message key="pb.sy"/></option>
			<option value="2" ><fmt:message key="menu.pb.sxcx"/></option>
			<option value="3" ><fmt:message key="pb.sxgroupcx"/></option>
			<%-- <option value="2" ><fmt:message key="pb.lz"/></option>
			<option value="3" ><fmt:message key="pb.wc"/></option>
			<option value="4" ><fmt:message key="pb.ybb"/></option>
			<option value="5" ><fmt:message key="pb.ry"/></option>
			<option value="6" ><fmt:message key="pb.hcy"/></option> --%>
			
		</select>
	<button id="searchPB" type="button" class="btn btn-success" style="margin-left:20px;float:left;"><fmt:message key='search'/></button>
	<button id="daochu" class="btn btn-info" style="margin-left:20px;" ><fmt:message key='pb.daochu' /></button>
</div>


<c:choose>
	<c:when test="${size == 0}">
	<div style="margin-top:70px;font-size:25px;">
		<p><b><fmt:message key="pb.nodata"/></b></p>
	</div>
	</c:when>
	<c:otherwise>
		<button id="print" type="button" class="btn btn-info" style="float:right;margin-top:-20px; margin-right:15px;" onclick='preview1()'><fmt:message key='audit.print'/></button>
		<!--startprint-->
		<div class="col-sm-12" style="">
			<h3 style="margin-left:320px;"><c:out value="${month}"/>检验科<fmt:message key="pb.biaoti"/></h3>		
			<p><fmt:message key="pb.annotation"/></p>
			<input id="cxdata" value="${arrString }" type="hidden"/>
			<div id="tabledata">
				
				<table id="data" class="table-hover" style="margin-top:10px;font-size:12px;text-align:center;" border="1px;">
					
				</table>
			</div>
			
		</div>
		<!--endprint-->
	</c:otherwise>
</c:choose>

<div id="footer2" class="">
        <span class="col-sm-6">
        	<fmt:message key="project.name"/> | 
        	<fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
            | <a target="_blank" href="<fmt:message key="update.url"/>"><fmt:message key="update.name"/></a>
       	    | <a target="_blank" href="<fmt:message key="wsdjk.url"/>"><fmt:message key="wsdjk.name"/></a>
   		</span>
   		<span class="col-sm-2" style="float:right;">
       		 &copy; <fmt:message key="copyright.year"/> <a target="_blank" href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
        </span>
</div>