	var setting3 = {
	    view : {
	        addHoverDom : eaddHoverDom,
	        removeHoverDom : removeHoverDom,
	        selectedMulti : false,
	        expandSpeed : ""
	    },
	    edit : {
	        drag : {
	            autoExpandTrigger : true,
	            prev : dropPrev,
	            inner : dropInner,
	            next : dropNext
	        },
	        enable : true,
	        editNameSelectAll : true
	    },
	    data : {
	        simpleData : {
	            enable : true
	        }
	    },
	    callback : {
	        beforeDrag : beforeDrag,
	        beforeDrop : beforeDrop,
	        beforeDragOpen : beforeDragOpen,
	        beforeEditName : beforeEditName,
	        beforeRemove : beforeRemove,
	        beforeRename : beforeRename,
	        onDrag : onDrag,
	        onDrop : eonDrop,
	        onExpand : onExpand,
	        onRemove : eonRemove,
	        onRename : eonRename,
	        onClick : eonClick
	    }
	};
	var setting4 = {
	    view : {
	        selectedMulti : false,
	        expandSpeed : ""
	    },
	    data : {
	        simpleData : {
	            enable : true
	        }
	    },
	    callback : {
	        onClick : eonClick
	    }
	};
	function eonClick(event, treeId, treeNode) {
	    location.href = "../description/list?bag=" + treeNode.id;
	}
	
	function eonRemove(e, treeId, treeNode) {
	    showLog("[ " + getTime() + " eonRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
	            + treeNode.name);
	    edataToController("remove", treeNode.id, treeNode.name);
	}
	
	function eonDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime() + " eonDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:"
	            + moveType);
	    showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "
	            + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"));
	    edataToController("drag" + moveType, treeNodes[0].id, targetNode.id);
	}
	
	function eonRename(e, treeId, treeNode) {
	    showLog("[ " + getTime() + " eonRename ]&nbsp;&nbsp;&nbsp;&nbsp; "
	            + treeNode.name);
	    edataToController("rename", treeNode.id, treeNode.name);
	}
	
	function eaddHoverDom(treeId, treeNode) {
	    var sObj = $("#" + treeNode.tId + "_span");
	    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0)
	        return;
	    var addStr = "<span class='button add' id='addBtn_" + treeNode.id
	            + "' title='add node' onfocus='this.blur();'></span>";
	    sObj.after(addStr);
	    var btn = $("#addBtn_" + treeNode.id);
	    if (btn)
	        btn.bind("click", function() {
	            var zTree = $.fn.zTree.getZTreeObj("tree");
	            zTree.addNodes(treeNode, {
	                id : (100 + newCount),
	                pId : treeNode.id,
	                name : "new" + newCount
	            });
	            edataToController("add", treeNode.id, "new" + newCount);
	            newCount++;
	            getData();
	            return false;
	        });
    }
    function selectDesAll() {
	    var zTree = $.fn.zTree.getZTreeObj("etree");
	    zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}
	function setDesTrigger() {
	    var zTree = $.fn.zTree.getZTreeObj("etree");
	    zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr(
	            "checked");
	}
	function edataToController(action, id, name) {
	    $.ajax({
	        type : "POST",
	        url : "../ajax/description/editBag",
	        data : "action=" + action + "&id=" + id + "&name=" + name,
	        dataType : "html",
	        success : function() {
	            /*alert(action + " success!");*/
	        }
	    });
	}
    
    function getDesData(){
    	$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			url : "<c:url value='../ajax/description/getDesBag'/>",
			datatype : "json",
			error : function() {
				alert('data false');
			},
			success : function(data) {
				var o = jQuery.parseJSON(data);
				if (${pageContext.request.remoteUser=='admin'}) {
					$.fn.zTree.init($("#etree"), setting1, o).expandAll(true);
					$("#selectAll").bind("click", selectDesAll);
					$("#callbackTrigger").bind("change", {}, setDesTrigger);	
				} else {
					$.fn.zTree.init($("#etree"), setting2, o).expandAll(true);
				}
			}
		});
    }