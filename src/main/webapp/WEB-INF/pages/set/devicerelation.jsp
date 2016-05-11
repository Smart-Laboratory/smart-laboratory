<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/31
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="<c:url value="/styles/font-awesome.css"/>" />
<!-- page specific plugin styles -->
<link rel="stylesheet" href="<c:url value="/styles/bootstrap-duallistbox.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/styles/ztree/zTreeStyle.css"/>" type="text/css">
<script src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
<script src="<c:url value="/scripts/jquery.ztree.all-3.5.js"/>"></script>
<script src="<c:url value="/scripts/layer/layer.js"/>"></script>
<style>
    .tab-content{
        padding: 2px 12px;
    }
    .widget-toolbar{
        z-index: 999;
    }
</style>
<div class="row">
    <div class="space-2"></div>
    <!-- PAGE CONTENT BEGINS -->
    <div class="col-xs-2">
        <ul id="treeList" class="ztree"></ul>
    </div>
    <div class="col-xs-10">
        <div class="widget-toolbar no-border">
            <button class="btn btn-xs btn-info" onclick="save()">
                <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
                保存
            </button>

            <button class="btn btn-xs btn-danger" data-toggle="dropdown">
                <i class="ace-icon fa fa-times red2"></i>
                取消
            </button>
        </div>
        <div class="tabbable">
            <ul class="nav nav-tabs" id="myTab">
                <li class="active">
                    <a data-toggle="tab" href="#relation">
                        <i class="green ace-icon fa fa-home bigger-120"></i>
                        测定仪器/部门选择
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#messages">
                        <i class="pink ace-icon fa fa-tachometer bigger-110"></i>
                       参考范围
                        <!--span class="badge badge-danger">4</span-->
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="relation" class="tab-pane fade in active">
                    <div class="row">
                        <div class="col-xs-12">
                            <h4 class="widget-title lighter">
                                <i class="ace-icon fa fa-star orange"></i>
                                仪器选择
                            </h4>
                            <div class="col-xs-12" style="margin-top: -15px" >
                                <div class="form-group">
                                    <select multiple="multiple" size="8" name="devicelist[]" id="devicelist">
                                    </select>
                                    <!--div class="hr hr-16 hr-dotted"></div-->
                                </div>
                            </div>
                            <h4 class="widget-title lighter">
                                <i class="ace-icon fa fa-star orange"></i>
                                科室选择
                            </h4>
                            <div class="col-xs-12"  style="margin-top: -15px">
                                <div class="form-group">
                                    <select multiple="multiple" size="8" name="departlist[]" id="departlist">
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="messages" class="tab-pane fade">
                    <p>参考范围设置.......</p>
                </div>
            </div>
        </div>
    </div><!-- /.col -->
</div>
<script type="text/javascript">
    /***
     * 保存数据
     */
    function save(){
        //获取选中数据
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        var node = zTree.getSelectedNodes()[0];

        var id =node.id||'' ;
        //当前节点没有值或者为根节点则不保存
        if(id=='' || node.level ==0) return false;
        var department= $('select[name="departlist[]"]').val().toString() ||'';
        var instrument= $('select[name="devicelist[]"]').val().toString() ||'';

        department += (department==""?"":",");
        instrument += (instrument==""?"":",");
        //当前选中Tab
        var activeTab =$('#myTab').find('li.active a').attr('href');

        var url ="";
        if(activeTab=='#relation'){
            url = 'devicerelation/saveRelation';
        }else{
            url = 'devicerelation/saveRelation';
        }
        $.ajax({
            url:url,
            type:'POST',
            dataType:"json",
            data:{id:id,department:department,instrument:instrument},
            success:function(data){
                //console.log(data);
                if(data && data.result=='true')
                    layer.msg("数据保存成功");
            }
        })
    }

    /***
     * 初始化选择列表框
     */
    function initDualListbox(){
        var demo1 = $('select[name="devicelist[]"]').bootstrapDualListbox({
            infoText:false,
            filterPlaceHolder:'过滤',
            selectorMinimalHeight:200
        });
        var container1 = demo1.bootstrapDualListbox('getContainer');
        container1.find('.btn').addClass('btn-white btn-info btn-bold');
        var demo2 = $('select[name="departlist[]"]').bootstrapDualListbox({
            infoText:false,
            filterPlaceHolder:'过滤',
            selectorMinimalHeight:200
        });
        var container2 = demo2.bootstrapDualListbox('getContainer');
        container2.find('.btn').addClass('btn-white btn-info btn-bold');
    }

    /**
     * 加载选择列表数据
     */
    function loadDualListbox(id,indexid){
        $.ajax({
            url:'../set/devicerelation/getDataList',
            type:'POST',
            dataType:"json",
            data:{id:id},
            success:function(data){
                //console.log(data);
                if(data){
                    var devicelist  =  data.devicelist;
                    var departlist = data.departmentList;
                    var labDepartment = data.labDepartment||'';
                    var instrument = data.instrument||'';
                    //console.log(devicelist);
                    var selectDev = $('#devicelist');
                    selectDev.empty();
                    for (var k in devicelist) {
                        var option = document.createElement("option");
                        option.value = k;
                        option.text = devicelist[k];
                       // console.log("instrument=="+instrument+"===k==>"+k+"    "+(instrument.toString()).indexOf(k+","))
                        if((instrument.toString()).indexOf(k+",") >=0){
                            option.selected = 'selected';
                        }
                        selectDev[0].options.add(option);
                    }

                    //加载部门
                    var selectDep = $('#departlist');
                    selectDep.empty();
                    for (var k in departlist) {
                        var option = document.createElement("option");
                        option.value = k;
                        option.text = departlist[k];
                        if((labDepartment.toString()).indexOf(k+",") >=0){
                            option.selected = 'selected';
                        }
                        selectDep[0].options.add(option);
                    }
                }
                $('select[name="departlist[]"]').bootstrapDualListbox("refresh");
                $('select[name="devicelist[]"]').bootstrapDualListbox("refresh");
            }

        })
    }
    jQuery(function($) {

        //初始化选择列表
        initDualListbox();

        //加载树形列表
        var zNodes =${treeNodes};
        $.fn.zTree.init($("#treeList"), {
            data: {
                key: {
                    title:"t"
                },
                simpleData: {
                    enable: true
                }
            },
            callback: {
                //beforeClick: beforeClick,
                onClick: function(event, treeId, treeNode, clickFlag){
                    //alert();
                    loadDualListbox(treeNode.id,treeNode.indexid)
                }
            }

        }, zNodes);
    })
</script>