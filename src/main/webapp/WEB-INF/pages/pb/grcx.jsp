<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.pb" /></title>
<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
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
			    $.get("<c:url value='/pb/grcx/data'/>",{name:name},function(data){
		    		for (var i=0 ; i < data.length ; i++) {
		    			$("#calendar").fullCalendar('renderEvent',data[i],true);
		    		}
				});
			    $(".fc-sat").css('backgroundColor','#deedf7');
				$(".fc-sun").css('backgroundColor','#deedf7');
			}
		});
	}
	renderCalendar();
	
});

</script>
</head>

<div class="alert alert-info" role="alert">${name}<fmt:message key="pb.grxx"/></div>
<div id='calendar'></div>