<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.pb" /></title>
<script type="text/javascript" src="<c:url value='/scripts/moment.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/fullcalendar.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lang-all.js'/>"></script>

<link href="<c:url value='/styles/fullcalendar.css'/>" rel='stylesheet' />
<link href="<c:url value='/styles/fullcalendar.print.css'/>" rel='stylesheet' media='print' />



<style>
</style>

<script type="text/javascript">

$(function() {
	var name = "${name}";
	var currentLangCode = 'zh-cn';
	var date=$("#calendar").val();
	
	
	

	$.each($.fullCalendar.langs, function(langCode) {
		$('#lang-selector').append(
			$('<option/>')
				.attr('value', langCode)
				.prop('selected', langCode == currentLangCode)
				.text(langCode)
		);
	});
	
	function renderCalendar() {
		$('#calendar').fullCalendar({
			height: 500,
			businessHours: false,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			lang: currentLangCode,
			buttonIcons: false,
			weekNumbers: true,
			weekends:true,
			eventLimit: true,
			events: function() {
				$("#calendar").fullCalendar('removeEvents');
				var name = '${name}';
				var moment=$('#calendar').fullCalendar('getDate');
				var month = moment.format("YYYY-MM");
			    $.get("../pb/grcx/ajax/getData",{name:name,month:month},function(data){
		    		for (var i=0 ; i < data.length ; i++) {
		    			$("#calendar").fullCalendar('renderEvent',data[i],true);
		    		}
				});
			    $(".fc-sat").css('backgroundColor','#deedf7');
				$(".fc-sun").css('backgroundColor','#deedf7');
				
				var moment=$('#calendar').fullCalendar('getDate');
				$.get("../pb/grcx/ajax/getWInfo",{name:name,moment:moment.format()},function(data){
					
					if(data != "") {
						data = jQuery.parseJSON(data);
						$("#pName").html(data.name);
						$("#pSection").html(data.section);
						$("#pWorktime").html(data.sworktime);
						$("#pNx").html(data.nx);
						$("#pLnjx").html(data.lnjx);
						$("#pJx").html(data.jx);
						$("#pYjx").html(data.yjx);
						$("#pWorkdays").html(data.worktime);
					}
					
				}); 
			}
		});
	}
	renderCalendar();
	
	
	
});

</script>
</head>

<div class="clearfix alert alert-info" style="margin-bottom:5px;padding:5px;padding-bottom:4px;">
	<div class="col-sm-12  ">
			<div class="col-sm-2 pinfo">
				<span class='col-sm-6' style="padding-left: 30px;"><fmt:message key="patient.name" />:</span><b id="pName"></b>
			</div>
			<div class="col-sm-3 pinfo">
				<span class='col-sm-4' style="padding-left: 30px;"><fmt:message key="patient.section" />:</span><b id="pSection"></b>
			</div>
			<div class="col-sm-3 pinfo">
				<span class='col-sm-6'><fmt:message key="pb.startworktime" />:</span><b id="pWorktime"></b>
			</div>
			<div class="col-sm-2 pinfo">
				<span class='col-sm-6' style="padding-left: 30px;"><fmt:message key="pb.nx" />:</span><b id="pNx"></b>
			</div>
			<div class="col-sm-2 pinfo">
				<span class='col-sm-8'><fmt:message key="pb.lnjx" />:</span><b id="pLnjx"></b>
			</div>
	</div>
	<div class="col-sm-8 ">
			<div class="col-sm-4 pinfo">
				<span class='col-sm-5' style="padding-left: 18px;"><fmt:message key="pb.yjx" />:</span><b id="pYjx"></b>
			</div>
			<div class="col-sm-4 pinfo">
				<span class='col-sm-4'><fmt:message key="pb.jx" />:</span><b id="pJx"></b>
			</div>
			<div class="col-sm-4 pinfo">
				<span class='col-sm-6'><fmt:message key="pb.worktime" />:</span><b id="pWorkdays"></b>
			</div>
	</div>
	<button id="print" type="button" class="btn btn-info" style="float:right;margin-top:-20px; margin-right:15px;" onclick='window.print()'><fmt:message key='audit.print'/></button>
</div>
<div id='calendar'></div>