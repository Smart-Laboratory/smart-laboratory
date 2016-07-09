<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
	
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>

<style>
</style>
</head>

<input type="hidden" id="selperson" /> 
<input type="hidden" id="weeknum" value="${week }" /> 
<input id="section" value="${section }" type="hidden"/>
<input id="month" value="${month }" type="hidden"/>

<div class="form-inline" style="width:1024x;">
	<input type="text" id="date" class="form-control" sytle="width:50px;">
	<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
	<button id="shiftBtn2" class="btn btn-success form-control">随机排班</button>
	<button id="shiftBtn" class="btn btn-success form-control"><fmt:message key='button.submit' /></button>
	<button id="publish" class="btn btn-danger form-control"><fmt:message key='button.publish' /></button>
	
</div>

<div id="weekSelect" class="form-inline" >		
</div>

<div id="shiftSelect" class="checkbox form-inline" >
	<c:forEach items="${wshifts}" var="shift">
		<div class="form-control" style="width:110px;padding:1px 2px;height:25px;margin-bottom: 1px;"><label >
    		<input type="checkbox" name="${shift.key }" value="${shift.key }"> ${shift.value } 
  		</label></div>
	</c:forEach>	
	<div class="form-control" style="width:110px;padding:1px 2px;height:25px;margin-bottom: 1px;"><label >
   		<input type="checkbox" name="cleartd" value="">清空数据
	</label></div>
</div>
<hr>
<div id="shiftNotSelect" class="checkbox form-inline" >
	<c:forEach items="${nowshifts}" var="shift">
		<div class="form-control" style="width:110px;padding:1px 2px;height:25px;margin-bottom: 1px;"><label >
    		<input type="checkbox" name="${shift.key }" value="${shift.key }"> ${shift.value } 
  		</label></div>
	</c:forEach>	
</div>

<div class="fixed">
	<input id="test" value="${arrString}" type="hidden"/>
	<table id="pbhead" class="table" style="margin-top:10px;margin-bottom:0px;font-size:12px;text-align:center;" border="1px;">
	
	
	</table>
</div>
<div class="fixed data">
	<input id="test1" value="${arrBodyString}" type="hidden"/>
	<table id="pbdata" class="table" style="font-size:12px;text-align:center;" border="1px;">
	
	</table>
</div>

		<!--人员start-->
        <div id="relation" class="col-sm-11" style='margin:10px 15px 50px 15px;'>
            <div class="row">
                <div class="col-sm-12" style="">
                    <h4 class="widget-title lighter col-sm-10">
                        <i class="ace-icon fa fa-star orange"></i>  人员选择
                    </h4>
                    <button class='btn btn-info' id='personSel' style='float:right;' onclick="personSave();">保存</button>
                    <div class="col-xs-12">
                        <div class="form-group">
                            <select multiple="multiple" size="8" name="devicelist[]" id="devicelist">
                            </select>
                            <!--div class="hr hr-16 hr-dotted"></div-->
                        </div>
                    </div>
                 </div>
              </div>
          </div>
	
		
<script type="text/javascript">

labChange=function(select){
	$.ajax({
		  type: 'POST',
		  url: "../audit/labChange?lab="+$(select).children().attr("title"),
		  success:function(data){
			  selectNoteAdd = true;
			  var section = $(select).children().attr("title");
			  if(section == '1320511'){
				  window.location.href="../pb/bpb?date=" + $("#date").val()+"&section=" + section;
			  }
			  $("#labText").html($(select).children().html());
			  window.location.href="../pb/pb?date=" + $("#date").val()+"&section=" + $(select).children().attr("title");
		  }
	});
	
}
	/**初始化人员选择列表**/
	 function	initPersonListBox(){
		var personListBox = $('select[name="devicelist[]"]').bootstrapDualListbox({
            infoText: false,
            filterTextClear:"",
            filterPlaceHolder: '过滤',
            selectorMinimalHeight: 150,
            preserveSelectionOnMove: false
        });
	}
	/**加载人员选择列表**/
	 function loadPersonListBox(){
		$.get("../pb/bpb/ajax/getWinfo",{section:$("#section").val()},function(data){
			if(data!=null){
				var selectDev = $('#devicelist');
		        selectDev.empty();
		        var wiList = jQuery.parseJSON(data);
		        for(var i=0;i<wiList.length;i++){
		        	var wi = wiList[i];
		        	var option = document.createElement("option");
		            option.value = wi.name;
		            option.text = wi.name;
		            
		            selectDev[0].options.add(option);
		        }
		            
		        $('select[name="devicelist[]"]').bootstrapDualListbox("refresh");
			}
		});
		
		
	}
	
	 function personSave(){
		 var personsel = new Array();
		 personsel = $('select[name="devicelist[]"]').val();
		 $("#selperson").val(personsel);
		 if(personsel != null){
			 $("#shiftSelect").html("");
			 var person;
			 for(person in personsel){
				 $("#shiftSelect").append("<div class='form-control' style='width:110px;padding:1px 2px;height:25px;margin-bottom: 1px;''><label><input type='checkbox' name="+personsel[person]+" value="+personsel[person]+">"+personsel[person]+"</label></div>");
			 }
		 };
		 
	 }
	 
	 function getdata(item){
		 var week = $(item).attr("name");
		 window.location.href="../pb/bpb?week=" + week+"&date=" + $("#date").val()+"&section=" + $("#section").val();
	 }
	
	$(function(){
		initPersonListBox();
		loadPersonListBox();
		$("#pbhead").html($("#test").val());
		$("#labSelect").val($("#section").val());
		$("#selperson").val("${selperson}");
		
		if('${query}'=='1'){
			$("#relation").css("display","none");
		}
		
		$("#pbhead tr td").click(function(){
			var id=this.id;
			
			var shifts=$("#"+id).html();
			
			$.each($("#shiftSelect input"),function(name,v){
				if(v.checked){
					var name = $(v).attr("name");
					if(name=="cleartd"){
						$("#"+id).html("");
						return;
					}
						
					
					if(shifts.indexOf(v.value+";")>=0){
						shifts=shifts.replace(v.value+";","");
					}else{
						shifts = shifts + v.value+";";
					}
				}
			});
			
			$.each($("#shiftNotSelect input"),function(name,v){
				if(v.checked){
					
					if(shifts.indexOf(v.value+";")>=0){
						shifts=shifts.replace(v.value+";","");
					}else{
						shifts = shifts + v.value+";";
					}
				}
			});
			$("#"+id).html(shifts);
//			}
		});
		
		$("#date").datepicker({
			changeMonth: true,
		    changeYear: true,
			dateFormat: 'yy-mm',
			monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			dayNamesMin: ['一','二','三','四','五','六','日'],
			showButtonPanel: true, 
			onClose: function(dateText, inst) { 
			var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
			$(this).datepicker('setDate', new Date(year, month, 1)); 
			} 
		});
		
		$("#changeMonth").click(function() {
			$.get("../pb/bpb/ajax/getWeek",{date:$("#date").val()},function(data){
				if(data!=null){
					$("#weekSelect").html(data);
				}
			});
		});
		$("#date").val("${month}");
		
		$("#shiftBtn2").click(function(){
			var selperson = $("#selperson").val();
			var date = $("#date").val();
			var week = $("#weeknum").val();
			window.location.href="../pb/bpb?week=" + week+"&date=" + date+"&selperson="+selperson+"&section=" + $("#section").val()+"&random=1";
		});
		
		//提交
		$("#shiftBtn").click(function() {
			var ischecked = true;
			if (ischecked) {
				var section = $("#section").val();
				var text = "";
				var date = $("#month").val();
				$("td[name^='td']").each(function(i){
					var id = $(this).attr("id");
					var array = $(this).attr("id").split("-");
					var day = "";
					day = id.replace(array[0]+"-","");
					var temp = "";
					var value = $(this).html();
					
					temp = array[0] + ":" + day + ":" + value +",";
					text = text + temp;
				});
				$.post("../pb/pb/bpbsubmit",{text:text,section:section,date:date},function(data) {
					if(data){
						alert("Success!");
				//		window.location.href="../pb/pb?date=" + $("#date").val()+"&section=" + $("#section").val();
					}else{
						alert("Fail!")
					}
					
				});
				
			}
		});
	})

	

</script>
