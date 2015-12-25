<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
$(function() {
	if( ${pageContext.request.remoteUser != null} ) {
		$.ajax({
	        type : "GET",
	        url : "<c:url value='/users/ajax/hospital'/>",
	        success : function(data) {
	           $("#hospital").append(data);
	        }
	    });
	}
});
</script>

<style>
.navbar {
    min-height: 40px;
    font-size: 15px;
}
.navbar-collapse.collapse {
		height: 30px
	}
	.navbar-nav>li>a {
		padding-top: 8px;
		padding-bottom: 7px
	}
	.navbar-brand {
	height: 30px;
	padding-top: 8px;
    padding-bottom: 7px;
}
</style>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
<div class="collapse navbar-collapse" id="navbar">
<ul class="nav navbar-nav">
    <c:if test="${empty pageContext.request.remoteUser}">
        <li class="active">
            <a href="<c:url value="/login"/>"><fmt:message key="login.title"/></a>
        </li>
    </c:if>
    <menu:displayMenu name="SampleManage"/>
    <menu:displayMenu name="SampleSet"/>
    <menu:displayMenu name="Reagent"/>
    <menu:displayMenu name="Statistic"/>
    <menu:displayMenu name="Quality"/>
    <menu:displayMenu name="Individual"/>
    <menu:displayMenu name="Logout"/>
</ul>

<div id="hospital" style="folat:right;height:50px;padding-top:13px;">
	<c:if test="${pageContext.request.remoteUser != null}">
		${pageContext.request.remoteUser} |
	</c:if>
</div>
</div>
</menu:useMenuDisplayer>
