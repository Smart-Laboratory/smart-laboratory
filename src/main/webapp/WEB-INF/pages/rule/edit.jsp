<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="set.rule"/></title>
<meta name="menu" content="SampleSet"/>

<script>
function initItemPanel() {
	$("#dataType").css('display', 'none');
	$("#enumType").css('display', 'none');
	$("#stringType").css('display', 'none');
	$("#indexPanel").css('display', 'none');
	$("#exsitItemPanel").css('display', 'none');
	$("#searchIndex").val("");
	$("#selectItem").empty();
	$("#itemPanel").dialog({
		width : 320
	});
}

function addItemToRule(self) {

	var tree = $("#ruleTree").jstree("get_json", -1);
	var method;
	if (tree == "") {
		method = -1;
	} else {
		method = null;
	}
	var value = self.parent().find('.tag-name').html();
	value = value.replace(/&lt;/g, "<").replace(/&gt;/g, ">");
	$("#ruleTree").jstree("create", method, "last", {
		"data" : value,
		"metadata" : {
			"id" : self.parent().children().eq(0).html()
		}
	}, false, true);
}

function removeItemWithoutUsed(self) {
	var id = self.parent().children().eq(0).html();
	$.get("../item/ajax/deleteItem", {
		id : id
	}, function(data) {
		if (data == 0) {
			self.parent().remove();
		} else if (data > 0) {
			alert(data + " Rule had used this item...");
		}
	});
}

function showAddItemPanel(ui) {

	$("#selectItem").empty();
	$.get("../item/ajax/getItem", { id : ui.item.id },
		function(data) {
			$("#exsit_item").html("");
			var array = jQuery.parseJSON(data);
			for ( var i = 0; i < array.length; i++) {
				$("#exsit_item").append("<div class=\"tag-section\"><div class=\"selbtn edit-sel\"><div style=\"display:none;\">"
					+ array[i].id
					+ "</div><span class=\"tag-name\">"
					+ array[i].content
					+ "</span><a class=\"follow-btn add\"></a><a class=\"follow-btn remove\"></a></div></div>");
				}

			$(".edit-sel").unbind();
			$(".follow-btn").unbind();
			$(".edit-sel").bind("mouseover", function() {
				$(this).find('.add').show();
				$(this).find('.remove').show();
			});
			$(".edit-sel").bind("mouseout", function() {
				$(this).find('.add').hide();
				$(this).find('.remove').hide();
			});
			$(".add").bind("click", function() {
				addItemToRule($(this));
			});
			$(".remove").bind("click", function() {
				removeItemWithoutUsed($(this));
			});
		});
	$("#index_id").val(ui.item.id);
	$("#index_name").html(ui.item.value + " (" + ui.item.sample + ")");
	$("#index_unit").val(ui.item.unit);
	$("#index_number").html(ui.item.indexId);
	$("#dataType").css('display', 'none');
	$("#enumType").css('display', 'none');
	$("#stringType").css('display', 'none');
	var type = ui.item.type;
	if (type == "E") {
		$("#enumType").css('display', 'block');
		$("#current_type").val("1");
	} else if (type == "S") {
		$("#stringType").css('display', 'block');
		$("#current_type").val("2");
	} else {
		$("#dataType").css('display', 'block');
		$("#current_type").val("0");
	}

	$("#relation_sign").val(0);
	$("#second_number_div").css('display', 'none');
	$("#first_sign").get(0).selectedIndex = 0;
	$("#second_sign").get(0).selectedIndex = 0;
	$("#first_value").val("");
	$("#second_value").val("");
	$("#string_value").val("");
	$("#enum_value").empty();
	var enumValue = ui.item.data;
	if (enumValue != "") {
		var values = enumValue.split(",");
		for ( var i = 0; i < values.length; i++) {
			$("#enum_value").append("<option>" + values[i] + "</option>");
		}
	}
	$("#indexPanel").css('display', 'block');
}

function relationSignChanged() {
	var value = $("#relation_sign").val();
	if (value == "0") {
		$("#second_number_div").css('display', 'none');
	} else {
		$("#second_number_div").css('display', 'block');
	}
}

function checkError(obj) {
	var result = 1;
	if (obj.metadata.id == "and" || obj.metadata.id == "or") {
		if (obj.children == null || obj.children.length < 2) {
			result = result * 2;
		}
	} else if (obj.metadata.id == "not") {
		if (obj.children == null) {
			result = result * 5;
		}
	} else if (obj.children != null) {
		result = result * 3;
	}

	if (obj.children != null) {
		for ( var i = 0; i < obj.children.length; i++) {
			result = result * checkError(obj.children[i]);
		}
	}
	return result;
}

function checkSubmit() {
	var tree = $("#ruleTree").jstree("get_json", -1);

	var result = 1;
	var errorStr = "";
	if (tree.length != 1) {
		errorStr += "<fmt:message key='error.rule.root.unique'/>";
	}
	for ( var i = 0; i < tree.length; i++) {
		result = result * checkError(tree[0]);
	}
	if (result % 2 == 0) {
		if (errorStr != "") {
			errorStr = errorStr + "\n";
		}
		errorStr += "<fmt:message key='error.rule.relation.child'/>";
	}
	if (result % 3 == 0) {
		if (errorStr != "") {
			errorStr = errorStr + "\n";
		}
		errorStr += "<fmt:message key='error.rule.item.noChild'/>";
	}
	if (result % 5 == 0) {
		if (errorStr != "") {
			errorStr = errorStr + "\n";
		}
		errorStr += "<fmt:message key='error.rule.relation.not'/>";
	}

	return errorStr;
}

function checkRule() {
	var tree = $("#ruleTree").jstree("get_json", -1);

	var result = 1;
	var errorStr = "";
	if (tree.length != 1) {
		errorStr += "<fmt:message key='error.rule.root.unique'/>";
	}
	for ( var i = 0; i < tree.length; i++) {
		result = result * checkError(tree[0]);
	}
	if (result % 2 == 0) {
		if (errorStr != "") {
			errorStr = errorStr + "\n";
		}
		errorStr += "<fmt:message key='error.rule.relation.child'/>";
	}
	if (result % 3 == 0) {
		if (errorStr != "") {
			errorStr = errorStr + "\n";
		}
		errorStr += "<fmt:message key='error.rule.item.noChild'/>";
	}
	if (result % 5 == 0) {
		if (errorStr != "") {
			errorStr = errorStr + "\n";
		}
		errorStr += "<fmt:message key='error.rule.relation.not'/>";
	}

	if (errorStr != "") {
		alert(errorStr);
	} else {
		alert("<fmt:message key="rule.item.correct"/>");
	}
}

function ruleFormSubmit(form) {

	var str = $("#ruleTree").jstree("get_json", -1);
	//alert($.toJSON(str));
	$("#relationJson").val($.toJSON(str));
	$("#resultId").val("");
	$("#result_select option").each(function() {
		if ($("#resultId").val() != "") {
			$("#resultId").val($("#resultId").val() + ",");
		}
		$("#resultId").val($("#resultId").val() + $(this).val());
	});
	$("#bagId").val("");
	$("#bag_select option").each(function() {
		if ($("#bagId").val() != "") {
			$("#bagId").val($("#bagId").val() + ",");
		}
		$("#bagId").val($("#bagId").val() + $(this).val());
	});
	var result = "";
	if ($("#name").val() == "") {
		result = "<fmt:message key="error.rule.name.notNull"/>";
	}
	var treeResult = checkSubmit();
	if (treeResult != "") {
		result = result + "\n" + treeResult;
	}

	if (result == "") {
		return true;
	} else {
		alert(result);
		return false;
	}
}

$(function() {
	$.jstree._themes = "../styles/themes/";

	$("#ruleTree").jstree({
		"json_data" : {

			"ajax" : {
				"url" : '../ajax/getRule?id=<c:out value="${rule.id}" />'
			}
		},
		"crrm" : {
			"move" : {
				"check_move" : function(m) {
					return true;
				}
			}
		},
		"dnd" : {
			"drop_target" : false,
			"drag_target" : false
		},
		"themes" : {
			"theme" : "classic",
			"dots" : true,
			"icons" : true
		},
		"plugins" : [ "themes", "json_data", "ui", "crrm",
				"dnd", "contextmenu" ]
	}).bind("loaded.jstree", function(e, data) {
		data.inst.open_all(-1); // -1 opens all nodes in the container
	});
});

$(function() {
	$("#itemPanel").dialog({
		autoOpen : false,
		resizable : false,
		width : 350,
		height : 360
	});

	$("#resultPanel").dialog({
		autoOpen : false,
		resizable : false,
		width : 300,
		height : 200
	});

	$("#bagPanel").dialog({
		autoOpen : false,
		resizable : false,
		width : 300,
		height : 200
	});

	$("#addResult").click(function() {
		$("#result_id").val("");
		$("#searchResult").val("");
		$("#createResultPanel").css('display', 'none');
		$("#resultPanel").dialog({
			width : 300,
			height : 200
		});

		$("#resultPanel").dialog("open");
	});

	$("#addBag").click(function() {
		$("#bag_id").val("");
		$("#searchBag").val("");
		$("#bagPanel").dialog("open");
	});
});

$(function() {

	$("#searchIndex").autocomplete({
		source : function(request, response) {
			$.ajax({url : "../ajax/getIndex",
				dataType : "json",
				data : {
					maxRows : 12,
					name : request.term
				},
				success : function(data) {

					response($.map(data,function(item) {
						return {
							label : (item.category == "I") ? (item.indexId
									+ " " + item.name + " (" + item.sample
									+ (item.unit ? ("," + item.unit) : "") + ")") : item.name,
							value : item.name,
							id : item.id,
							indexId : item.indexId,
							sample : item.sample,
							type : item.type,
							category : item.category,
							unit : item.unit ? item.unit : "",
							data : item.data ? item.data : ""
						}
					}));

					$("#searchIndex").removeClass("ui-autocomplete-loading");
				}
			});
		},
		minLength : 1,
		delay : 500,
		select : function(event, ui) {
			var c = ui.item.category;
			$("#exsitItemPanel").css('display', 'block');
			$("#itemPanel").dialog({
				width : 650
			});
			if (c == "I") {
				showAddItemPanel(ui);
			} else {
				$("#indexPanel").css('display', 'none');
				$("#exsit_item").html("");
				$("#exsit_item").append("<div class=\"tag-section\"><div class=\"selbtn edit-sel\"><div style=\"display:none;\">"
					+ ui.item.id
					+ "</div><span class=\"tag-name\">"
					+ ui.item.label
					+ "</span><a class=\"follow-btn add\"></a><a class=\"follow-btn remove\"></a></div></div>");
				$(".edit-sel").unbind();
				$(".follow-btn").unbind();
				$(".edit-sel").bind("mouseover",function() {
					$(this).find('.add').show();
					$(this).find('.remove').show();
				});
				$(".edit-sel").bind("mouseout", function() {
					$(this).find('.add').hide();
					$(this).find('.remove').hide();
				});
				$(".add").bind("click", function() {
					addItemToRule($(this));
				});
				$(".remove").bind("click", function() {
					removeItemWithoutUsed($(this));
				});
			}
		}
	});

	$("#searchResult").autocomplete({
		source : function(request, response) {
			$.ajax({
				url : "../result/ajax/getResult",
				dataType : "json",
				data : {
					name : request.term
				},
				success : function(data) {

					response($.map(data, function(result) {
						return {
							label : result.content,
							value : result.content,
							id : result.id
						}
					}));

					$("#searchResult").removeClass("ui-autocomplete-loading");
				}
			});
		},
		minLength : 1,
		select : function(event, ui) {
			$("#result_id").val(ui.item.id);
		}
	});

	$("#searchBag").autocomplete({
		source : function(request, response) {
			$.ajax({
				url : "../ajax/searchBag",
				dataType : "json",
				data : {
					name : request.term
				},
				success : function(data) {

					response($.map(data, function(bag) {
						return {
							label : bag.name,
							value : bag.name,
							id : bag.id
						}
					}));

					$("#searchResult").removeClass("ui-autocomplete-loading");
				}
			});
		},
		minLength : 1,
		select : function(event, ui) {
			$("#bag_id").val(ui.item.id);
		}
	});

	$("#createItemBtn").click(function() {
		var type = $("#current_type").val();
		var value = "";
		if (type == "1") {
			value = $("#enum_value").val();
		} else if (type == "2") {
			value = $("#string_value").val();
		} else {
			if ($("#relation_sign").val() == "0") {
				var v = $("#first_value").val();
				if (v == "") {
					alert("Enter the value !");
					return;
				}
				value = $("#first_sign").val() + v;
			} else {
				var v1 = $("#first_value").val();
				var v2 = $("#second_value").val();
				if (v1 == "" || v2 == "") {
					alert("Enter the value !");
					return;
				}

				value = $("#first_sign").val() + v1
					+ $("#relation_sign").val()
					+ $("#second_sign").val() + v2;
			}
		}

		if (!confirm('<fmt:message key="item.create.confirm"/>' + "\n" + $("#index_name").html() + ":" + value)) {
			return;
		}

		$.post("../item/ajax/addItem",
		{
			id : $("#index_id").val(),
			value : value,
			unit : $("#index_unit").val()
		},
		function(data) {
			if (data != null) {
				$("#exsit_item").prepend(
					"<div class=\"tag-section\"><div class=\"selbtn edit-sel\"><div style=\"display:none;\">"
							+ data.id + "</div><span class=\"tag-name\">"
							+ data.value + "</span><a class=\"follow-btn add\"></a><a class=\"follow-btn remove\"></a></div></div>");
				$(".edit-sel").unbind();
				$(".follow-btn").unbind();
				$(".edit-sel").bind("mouseover",function() {
					$(this).find('.add').show();
					$(this).find('.remove').show();
				});
				$(".edit-sel").bind("mouseout",function() {
					$(this).find('.add').hide();
					$(this).find('.remove').hide();
				});
				$(".add").bind("click",function() {
					addItemToRule($(this));
				});
				$(".remove").bind("click",function() {
					removeItemWithoutUsed($(this));
				});
			}
		}, "json");
	});

	$("#addResultBtn").click(function() {
		if ($("#result_id").val() != "") {
			$("#result_select").append("<option value='"+ $("#result_id").val() + "'>"
				+ $("#searchResult").val()+ "</option>");
		} else {
			alert("<fmt:message key="warning.result.select.first"/>");
		}
	});

	$("#createResultBtn").click(function() {

		$.post("../result/ajax/add", {
			content : $("#result_content").val(),
			category : $("#result_category").val(),
			percent : $("#result_percent").val()
		}, function(data) {
			$("#result_select").append("<option value='" + data +"'>"
				+ $("#result_content").val() + "</option>");
			$("#result_content").val("");
		}, "json");
	});

	$("#removeResult").click(function() {
		$("#result_select option:selected").remove();
	});

	$("#addBagBtn").click(function() {
		if ($("#bag_id").val() != "") {
			$("#bag_select").append("<option value='" 
					+ $("#bag_id").val() + "'>" + $("#searchBag").val() 
					+ "</option>");
		} else {
			alert("<fmt:message key="warning.bag.select.first"/>");
		}
	});

	$("#removeBag").click(function() {
		$("#bag_select option:selected").remove();
	});
	
	if($("#type").val()==1){
		$("#algorithm_th").css('display', 'table-cell');
		$("#hospitalmode_th").css('display', 'table-cell');
		$("#algorithm_td").css('display', 'table-cell');
		$("#hospitalmode_td").css('display', 'table-cell');
	}
});

function item_href() {
	initItemPanel();
	$("#itemPanel").dialog("open");
}
function and_href() {
	var tree = $("#ruleTree").jstree("get_json", -1);
	var method;
	if (tree == "") {
		method = -1;
	} else {
		method = null;
	}
	$("#ruleTree").jstree("create", method, "last", {
		"data" : "<fmt:message key="relation.and"/>",
		"metadata" : {
			"id" : "and"
		}
	}, false, true);
}
function or_href() {
	var tree = $("#ruleTree").jstree("get_json", -1);
	var method;
	if (tree == "") {
		method = -1;
	} else {
		method = null;
	}
	$("#ruleTree").jstree("create", method, "last", {
		"data" : "<fmt:message key="relation.or"/>",
		"metadata" : {
			"id" : "or"
		}
	}, false, true);
}
function not_href() {
	var tree = $("#ruleTree").jstree("get_json", -1);
	var method;
	if (tree == "") {
		method = -1;
	} else {
		method = null;
	}
	$("#ruleTree").jstree("create", method, "last", {
		"data" : "<fmt:message key="relation.not"/>",
		"metadata" : {
			"id" : "not"
		}
	}, false, true);
}
function result_create_href() {
	$("#createResultPanel").css('display', 'block');
	$("#result_content").val($("#searchResult").val());
	$("#resultPanel").dialog({
		width : 800,
		height : 300
	});
}
function typeChange(select) {
	if(select.value==1){
		$("#algorithm_th").css('display', 'table-cell');
		$("#hospitalmode_th").css('display', 'table-cell');
		$("#algorithm_td").css('display', 'table-cell');
		$("#hospitalmode_td").css('display', 'table-cell');
	}else{
		$("#algorithm_th").css('display', 'none');
		$("#hospitalmode_th").css('display', 'none');
		$("#algorithm_td").css('display', 'none');
		$("#hospitalmode_td").css('display', 'none');
	}
}
</script>
</head>

<style>

td {
	padding: 0px 0px 10px 5px;
}

th {
	padding: 0px 0px 10px 5px;
}
</style>

<div class="col-sm-7">
<h1><fmt:message key='rule.edit'/></h1>
<spring:bind path="rule.*">
	<c:if test="${not empty status.errorMessages}">
		<div class="error">
			<c:forEach var="error" items="${status.errorMessages}">
				<img src="<c:url value="/images/iconWarning.gif"/>"
					alt="<fmt:message key="icon.warning"/>" class="icon" />
				<c:out value="${error}" escapeXml="false" />
				<br />
			</c:forEach>
		</div>
	</c:if>
</spring:bind>

<form:form commandName="rule" method="post" action="edit"
	onsubmit="return ruleFormSubmit(this)">
	<form:hidden path="id" />
	<form:hidden path="bagId" />
	<form:hidden path="resultId" />
	<form:hidden path="relationJson" />
	<form:hidden path="core" />
	<form:hidden path="activate" />
	<form:hidden path="credibility" />
	<table>
		<tr>
			<th><appfuse:label styleClass="desc"  key="rule.name" /></th>
			<td><form:input path="name" id="name" cssClass="large" /></td>
		</tr>
		<tr>
			<th valign="top"><appfuse:label styleClass="desc"
					key="rule.content" /></th>
			<td>
				<div>
					<div style="margin-bottom: 10px; font-size: 12px;">
						<fmt:message key="add.point" />
						: <a href='javascript:item_href();'><fmt:message key="item" /></a>
						<a href='javascript:and_href();'><fmt:message
								key="relation.and" /></a> <a href='javascript:or_href();'><fmt:message
								key="relation.or" /></a> <a href='javascript:not_href();'><fmt:message
								key="relation.not" /></a> <a href="javascript:checkRule();"
							style="margin-left: 50px; color: #700;"><fmt:message
								key="rule.check" /></a>
					</div>
					<div id="ruleTree" class="border-radius-6 border-shadow"
						style="padding: 5px;"></div>
				</div>
			</td>
		</tr>

		<tr>
			<th valign="top"><appfuse:label styleClass="desc"
					key="rule.result" /></th>
			<td>
				<div>
					<div style="float: left;">
						<select multiple class="form-control" id="result_select"
							style="width: 300px; ">
							<c:forEach var="result" items="${resultIdList}">
								<option value="<c:out value="${result.id}"/>">
									<c:out value="${result.content}" />
									<c:if test="${result.percent != null}">
										<c:out value="${result.percent}" />
									</c:if>
								</option>
							</c:forEach>
						</select>
					</div>
					<div style="width: 30px; float: right;">
						<input type="button"  value="+"   id="addResult" class="btn btn-info btn-sm"
							style="width: 40px; height: 40px;" > 
						<input type="button" class="btn btn-info btn-sm"
							value="-" id="removeResult" style="width: 40px; height: 40px;" />
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<th valign="top"><appfuse:label styleClass="desc"
					key="rule.category" /></th>
			<td>
				<div>
					<div style="float: left;">
						<select multiple class="form-control" id="bag_select" style="width: 300px;">
							<c:forEach var="bag" items="${bagIdList}">
								<option value="<c:out value="${bag.id}"/>">
									<c:out value="${bag.name}" />
								</option>
							</c:forEach>
						</select>
					</div>
					<div style="width: 30px; float: right;">
						<input type="button" class="btn btn-info btn-sm" value="+" id="addBag"
							style="width: 40px; height: 40px;" /> <input type="button" class="btn btn-info btn-sm"
							value="-" id="removeBag" style="width: 40px; height: 40px;" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<th><appfuse:label styleClass="desc" key="rule.type" /></th>
			<td>
				<form:select path="type" id="type" cssClass="selects medium" onchange="typeChange(this)">
					<form:options items="${typeList}" cssClass="text" />
				</form:select>
			</td>
		</tr>
		<tr>
			<th id="algorithm_th" style="display:none;"><appfuse:label styleClass="desc" key="rule.algorithm" /></th>
			<td id="algorithm_td" style="display:none;">
				<form:select path="algorithm" id="algorithm" cssClass="selects medium">
					<form:options items="${algorithmList}" cssClass="text" />
				</form:select>
			</td>
		</tr>
		<tr>
			<th id="hospitalmode_th" style="display:none;"><appfuse:label styleClass="desc" key="rule.hospitalmode" /></th>
			<td id="hospitalmode_td" style="display:none;">
				<form:select path="hospitalmode" id="hospitalmode" cssClass="selects medium">
					<form:options items="${hospitalModeList}" cssClass="text" />
				</form:select>
			</td>
		</tr>
		<tr>
			<th valign="top"><appfuse:label styleClass="desc"
					key="rule.description" /></th>
			<td>
				<form:textarea path="description" id="description" class="form-control" style="width:300px " />
			</td>
		</tr>
		<tr>
			<th></th>
			<td>
				<div>
					<input type="submit" style="width: 80px;" class="btn btn-info" name="save"
						value="<fmt:message key="button.save"/>" /> <input type="button"
						style="width: 80px;" class="btn btn-info" name="cancel"
						onclick="history.back(-1)"
						value="<fmt:message key="button.cancel"/>" />
				</div>
			</td>
		</tr>
	</table>
</form:form>

<div id="panel">

	<div id="itemPanel" style="width:650px;" title="<fmt:message key="item.add.dialog"/>">
		<div id="searchIndexPanel" style="width: 230px; text-align: left; float: left; display: block;">
			<div>
				<label><fmt:message key="input.search.name" /></label>
			</div>
			<div>
				<input id="searchIndex" class="form-control" style="width: 200px"
					placeholder="input search text" />
			</div>
			<div id="indexPanel" style="width: 220px; text-align: left; display: none;">
				<div>
					<input type="hidden" id="index_id" /> 
					<input type="hidden" id="current_type" /> 
					<label><fmt:message key="index.name" /> : <span id="index_name"></span></label>
				</div>
				<div>
					<label><fmt:message key="index.indexId" /> : <span id="index_number"></span></label>
				</div>
				<div>
					<label><fmt:message key="index.unit" /> : <input style="width: 80px;" type="text" id="index_unit" /></label>
				</div>
				<div id="dataType" style="margin-top: -5px;">
					<div>
						<span><fmt:message key="index.value" /> : </span> 
						<select
							id="first_sign" style="width: 50px;">
							<option>=</option>
							<option>></option>
							<option><</option>
							<option><=</option>
							<option>>=</option>
						</select> 
						<input style="width: 34px;" type="text" id="first_value" /> 
						<select id="relation_sign" style="width: 40px;" onchange="relationSignChanged()">
							<option value=0></option>
							<option value='&&'><fmt:message key="and" /></option>
							<option value='||'><fmt:message key="or" /></option>
						</select>
					</div>
					<div id="second_number_div" style="display: none; margin-top: 3px;">
						<select style="margin-left: 60px; width: 50px;" id="second_sign">
							<option>=</option>
							<option>></option>
							<option><</option>
							<option><=</option>
							<option>>=</option>
						</select> 
						<input style="width: 40px;" type="text" id="second_value" />
					</div>
				</div>
				<div id="enumType">
					<label><fmt:message key="index.value" /> : <select
						id="enum_value" style="width: 120px;">
					</select></label>

				</div>
				<div id="stringType">
					<label><fmt:message key="index.value" /> : <input
						style="width: 120px;" type="text" id="string_value" /></label>
				</div>
				<div>
					<input type="button" class="btn btn-info btn-sm" id="createItemBtn"
						style="margin-left: 60px; width: 80px;"
						value="<fmt:message key="item.create"/>" />
				</div>
			</div>
		</div>
		<div id="exsitItemPanel" style="width: 320px; float: right; text-align: left; 
		 	font-size: 12px; border: 0px solid #E1E1E1;" class="border-radius-6 border-shadow">
			<div style="margin: 12px;">
				<h5>
					<fmt:message key="item.select" />
				</h5>
			</div>
			<div id="reverseChar" style="display: none"></div>
			<div id="exsit_item" style="border-top: 1px solid #E1E1E1;">
				<div class="tag-section">
					<div class="selbtn edit-sel">
						<div style="display: none;"></div>
						<span class="tag-name"></span> <a class="follow-btn add"></a> <a
							class="follow-btn remove"></a>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="resultPanel" title="<fmt:message key="result.add.dialog"/>"
		style="text-align: left;">
		<div id="addResultPanel" style="float: left;">
			<div>
				<label><fmt:message key="input.search.name" /></label>
			</div>
			<div>
				<input id="searchResult" style="width: 200px" /> <input
					type="hidden" id="result_id" />
			</div>
			<div>
				<input type="button" id="addResultBtn" class="btn btn-info btn-info"
					value="<fmt:message key="result.add"/>" /> <a
					href='javascript:result_create_href();' style="margin-left: 20px;"><fmt:message
						key="result.create" /></a>
			</div>
		</div>
		<div id="createResultPanel" style="width:500px;float: right; display: none;"  class="form-inline">
			<div class="input-prepend" style="margin-top:10px;">
				<label class="col-sm-2 add-on" for="result_content" style="margin:8px 2px;"><fmt:message key="result.content" /></label><textarea
					id="result_content" type="text" class="col-sm-10 form-control " style="width: 360px" ></textarea>
			</div>
			<div class="input-prepend" style="margin-top:10px;">
				<label class="col-sm-2 add-on" style="margin:8px 2px;"><fmt:message key="result.category" /></label><input
					id="result_category" type="text" class="col-sm-4 form-control " style="width: 80px" />
				<label class="col-sm-2 add-on" style="margin:8px 2px;"><fmt:message key="result.percent" /></label><input
					id="result_percent" type="text" class="col-sm-4 form-control " style="width: 80px" />
			</div>
			<div class="col-sm-12" style="margin-top:10px;">
				<button class="btn btn-info btn-sm" id="createResultBtn">
					<b><fmt:message key="result.create.add" /></b>
				</button>
			</div>
		</div>

		<div id="bagPanel" title="<fmt:message key="bag.add.dialog"/>"
			style="text-align: left;">
			<div>
				<label><fmt:message key="input.search.name" /></label>
			</div>
			<div>
				<input id="searchBag" style="width: 200px" /> <input type="hidden"
					id="bag_id" />
			</div>
			<div>
				<input type="button" class="btn btn-info btn-sm" id="addBagBtn"
					value="<fmt:message key="bag.add"/>" />
			</div>
		</div>
	</div>
</div>
</div>