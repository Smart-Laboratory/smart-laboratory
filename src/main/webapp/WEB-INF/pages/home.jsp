<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="menu" content="Home"/>
    


</head>
<body class="home">

<h2><fmt:message key="home.heading"/></h2>
<p><fmt:message key="home.message"/></p>

<ul class="glassList">
    <li>
        <a href="<c:url value='/manage/input'/>"><fmt:message key="sample.manage.input"/></a>
    </li>
    <li>
        <a href="<c:url value='/manage/receive'/>"><fmt:message key="sample.manage.receive"/></a>
    </li>
    <li>
        <a href="<c:url value='/manage/audit'/>"><fmt:message key="sample.manage.audit"/></a>
    </li>
    <li>
        <a href="<c:url value='/nursestation/inexecute?ward=1001&userid=${pageContext.request.remoteUser}'/>">住院条码打印</a>
    </li>
    <li>
        <a href="<c:url value='/manage/testerSet'/>">检验者设置</a>
    </li>
</ul>
</body>
