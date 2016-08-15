<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<c:forEach var="temp" items="${list}" varStatus="status">
	<div style="float:left;width:190px;height:135px;margin-top:0px;">
		<div style="font-size:9px;margin-left:0px;"><c:out value="${temp.name}"/></div>
		<img src='<%=request.getContextPath() %>/barcode?msg=<c:out value="${temp.barcode}"/>&hrsize=0mm' style="align:left;height:60px;"/>
		<div style="font-size:9px;margin-left:0px;">LOT:<c:out value="${temp.batch}"/> BAR:<c:out value="${temp.barcode}"/></div>
		<div style="font-size:9px;margin-left:0px;">REP:<c:out value="${temp.indate}"/> EXP:<c:out value="${temp.exdate}"/></div>
	</div>
</c:forEach>




	