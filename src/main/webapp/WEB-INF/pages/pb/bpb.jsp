<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.pb" /></title>
	<script type="text/javascript" src="<c:url value='../scripts/pb/pb.js'/> "></script>
	
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

<div class="form-inline" style="width:1024x;">
	<input type="text" id="date" class="form-control" sytle="width:50px;">
	<button id="changeMonth" class="btn btn-info form-control" style="margin-left:10px;"><fmt:message key='pb.changemonth' /></button>
	<button id="shiftBtn2" class="btn btn-success form-control"><fmt:message key='button.count' /></button>
	<button id="shiftBtn" class="btn btn-success form-control"><fmt:message key='button.submit' /></button>
	<button id="publish" class="btn btn-danger form-control"><fmt:message key='button.publish' /></button>
	
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
                <div class="col-sm-12">
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
		$.get("../pb/bpb/getWinfo",function(data){
			if(data!=null){
				var selectDev = $('#devicelist');
		        selectDev.empty();
		        var wiList = data;
		        alert(data);
		        for(var i=0;i<wiList.length;i++){
		        	var wi = wiList[i];
		        	var option = document.createElement("option");
		            option.value = wi.workid;
		            option.text = wi.name;
		            
		            selectDev[0].options.add(option);
		        }
		            
		        $('select[name="devicelist[]"]').bootstrapDualListbox("refresh");
			}
		});
		
		
	}
	
	 function personSave(){
		 alert($('select[name="devicelist[]"]').val());
	 }
	
	$(function(){
		initPersonListBox();
		loadPersonListBox();
	})


</script>
