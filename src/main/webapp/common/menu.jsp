<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ace.min.css'/>" />
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/font-awesome.css'/>" />
<script type="text/javascript">
function labChange() {
}

$(function() {
	if(${pageContext.request.remoteUser != null}) {
		$.ajax({
	        type : "GET",
	        url : "../users/ajax/hospital",
	        success : function(data) {
	           var json = jQuery.parseJSON(data);
	           $("#userText").html(json.username);
	           for(var key in json.labMap) {
	        	   $("#labSelect").append("<li onclick='labChange(this)'><a title='" + key + "'>" + json.labMap[key] + "</a></li>");
	           }
	           $("#labText").html(json.lab);
	           $("#hospitalText").append(json.hospital);
	           $("#hospital").css("display","block");
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
<div id="menuheader" class="collapse navbar-collapse" id="navbar">
<ul class="nav navbar-nav">
    <c:if test="${empty pageContext.request.remoteUser}">
        <li class="active form-inline">
            <span class="form-control" style="width:55px;" ><a href="../login"><fmt:message key="login.title"/></a></span>
            <span class="form-control" style="width:100px;"><a href="../pb/pbcx"><fmt:message key="menu.pb.pbcx"/></a></span>
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


<!-- <div id="hospital" style="display:none;float:right;height:30px;padding-top:8px;" class="col-sm-3">
	<div id="userText" class="col-sm-3" style="padding-top: 8px;"></div>
	<ul id="labSelect" style="float:left;" onchange="labChange(this)" class="col-sm-5 dropdown"></ul>
	
	<div id="hospitalText" class="col-sm-4" style="padding-top: 8px;"></div>
</div> -->
<ul id="hospital" class="nav navbar-nav" style="display:none;float:right;">
	<li><a href="#" id="userText"></a></li>
	<li class="dropdown">
		<a href="#" id="labText" class="dropdown-toggle" data-toggle="dropdown"></a>
		<ul class="dropdown-menu" id="labSelect" style="height:200px;overflow:auto;">
		</ul>
	</li>
	<li><a href="#" id="hospitalText"></a></li>
</ul>
</div>

</menu:useMenuDisplayer>
