<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/font-awesome.css'/>" />
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ace.min.css'/>" />

<<<<<<< HEAD
<!-- <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script> -->
=======
>>>>>>> origin/master
<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="../scripts/ace.min.js"></script>
<script type="text/javascript" src="../scripts/ace-elements.min.js"></script>
<script type="text/javascript" src="../scripts/bootstrap-tag.min.js"></script> 

<script type="text/javascript">
function labChange() {
}

$(function() {
	if( ${pageContext.request.remoteUser != null} ) {
		var url = window.location.href;
		if(url.indexOf("pb")>=0){
			url = "../users/ajax/hospital?ispb=1";
		}
		else{
			url = "../users/ajax/hospital";
		}
		
		$.ajax({
	        type : "GET",
	        url : url,
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
            <span class="form-control" style="width:55px;" ><a href="/lab/login"><fmt:message key="login.title"/></a></span>
            <span class="form-control" style="width:100px;"><a href="/lab/pb/pbcx"><fmt:message key="menu.pb.pbcx"/></a></span>
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
