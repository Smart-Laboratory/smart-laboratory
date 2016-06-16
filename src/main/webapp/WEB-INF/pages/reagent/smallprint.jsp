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

