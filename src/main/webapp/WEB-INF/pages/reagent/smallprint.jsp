<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<c:forEach var="i" begin="1" end="${fs }" step="1">
	<div style=" float:left;width:140px;height:70px;margin:-10px 0px;">
		<div style="font-size:12px;margin-left:5px;"><span style="text-align:center;">${reagent }</span></div>
		<div style="font-size:12px;margin-left:5px;"><span style="text-align:center;">配置：${begintime }</span></div>
		<div style="font-size:12px;margin-left:5px;"><span style="text-align:center;">失效：${endtime }</span></div>
		<div style="font-size:12px;margin-left:5px;"><span style="text-align:center;font-family:"verdana";>批号：${ts }</span></div>
	</div>
</c:forEach>

<%-- <div style="float:left;width:190px;height:135px;margin-top:5px;">
	<div style="font-size:10px;margin-left:10px;"><fmt:message key="labDepartment.1300501"/></div>
	<div style="font-size:10px;margin-left:10px;">IMMULITE 1</div>
	<img src='<%=request.getContextPath() %>/barcode?&msg=67&hrsize=0mm' style="align:left;height:75px;"/>
</div> --%>