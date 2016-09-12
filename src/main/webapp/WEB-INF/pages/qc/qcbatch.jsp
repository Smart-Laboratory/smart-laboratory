<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/7/28
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title>质控品设定</title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/jquery-ui.min.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <link rel="stylesheet" type="text/css"  href="<c:url value="/styles/bootstrap-datetimepicker.min.css"/>" />
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/jquery.bootstrap-duallistbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/layer/extend/layer.ext.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/laydate/laydate.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>

    <style>
        .ui-jqgrid {
            border: 1px solid #ddd;
        }
        ::-webkit-scrollbar-track {
            background-color: #F5F5F5
        }

        ::-webkit-scrollbar {
            width: 6px;
            background-color: #F5F5F5
        }

        ::-webkit-scrollbar-thumb {
            background-color: #999
        }
        .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{
            background-color: #F5F5F6;
        }
        .bootstrap-duallistbox-container .buttons{
            display: none;
        }
        .bootstrap-duallistbox-container .info-container{
            display: none;
        }
        .ui-autocomplete{
            z-index: 99999999!important;
        }
        li {list-style-type:none;}
        #addForm.form-horizontal{
            margin: 10px 50px;
        }
        /*#addForm.form-horizontal .form-group{*/
            /*margin-bottom: 10px !important;*/
        /*}*/

        .testlist{
            /*width:450px;*/
            list-style: none;
            margin-left: 5px !important;
        }
        .testlist li{
            list-style-type:none;
            float: left;
            padding: 0px 5px;
            width:200px;
            line-height: 20px;
            overflow:hidden;
            text-overflow:ellipsis;
            white-space:nowrap;
            vertical-align: middle;
            display:inline;
        }
        .list{
            position: absolute;
            border: 1px solid #c9cbce;
            z-index: 999999;
            background:#fff;
            height:200px;
            top: 32px;
            right: 5px;
            left:5px;
            overflow-y: auto;
            overflow-x:hidden;
            /*width:250px;*/
        }
        .tableList{
            width:400px;
            border: 1px #eee solid;
            margin:5px 10px ;
        }
        .btnCancelChose{
            height: 28px;
            line-height: 28px;
            border: 1px solid #dedede;
            background-color: #f1f1f1;
            color: #333;
            padding: 5px 15px;
            font-weight: 400;
            cursor: pointer;
            border-color: #4898d5;
            background-color: #d15b47;
            color: #fff;
        }
        .btnChose{
            height: 28px;
            line-height: 28px;
            border: 1px solid #dedede;
            background-color: #f1f1f1;
            color: #333;
            padding: 5px 15px;
            font-weight: 400;
            cursor: pointer;
            border-color: #4898d5;
            background-color: #2e8ded;
            color: #fff;
        }
        .EditTable tr:first-child {
   			display: table-row !important;
		}
    </style>
</head>
<div class="row" id="toolbar">
    <div class="col-xs-12">
        <div  style="padding-top: 5px;">
            <button type="button" class="btn btn-sm btn-primary " title="添加" onclick="TSLAB.Custom.Add()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add" />
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="编辑" onclick="TSLAB.Custom.Edit()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除" onclick="TSLAB.Custom.Delete()">
                <i class="ace-icon fa fa-times bigger-110"></i>
                <fmt:message key="button.delete" />
            </button>
            <div class="form-group col-xs-2" style="margin-bottom:5px;">
            	<label class="col-xs-4 control-label no-padding-right" style="text-align:right;" for="deviceSelect">选择仪器 </label>
            	<div class="col-xs-8">
                	<select id="deviceSelect"  name="deviceSelect" class="col-xs-12">
	            		<c:forEach items="${devices }" var="dev">
	            			<option value="${dev.key }">${dev.value }</option>
	            		</c:forEach>
	            	</select>
            	</div>
            </div>
            <div class="input-group col-sm-3 " style="float: right;" >
                <input type="text" id="query" class="form-control search-query" placeholder="输入编号或名称" />
			<span class="input-group-btn">
				<button type="button" class="btn btn-purple btn-sm" onclick="search()">
                    <fmt:message key="button.search"/>
                    <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                </button>
			</span>
            </div>
        </div>

    </div>
</div>
<div class="row" id="maincontent">
    <div class="col-xs-3 leftContent">
        <table id="leftGrid"></table>
        <div id="leftPager"></div>
    </div>
    <div class="col-xs-9 rightContent">
        <table id="rightGrid"></table>
        <div id="rightPager"></div>
    </div>
</div>


<div style="clear: both"></div>
<div id="addDialog" style="display: none;">
    <form id="addForm" action="<c:url value='../micro/cultureresult/save'/>"  class="form-horizontal"  method="post" >
        <input type="hidden" id="id" name="id" />
        <div class="form-group">
            <div class="space-2"></div>
            <label class="col-xs-2 control-label no-padding-right" for="qcBatch">质控批号 </label>
            <div class="col-xs-4">
                <input type="text" id="qcBatch" name="qcBatch" placeholder="质控批号" class="col-xs-12"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="sampleType">样本类型 </label>
            <div class="col-xs-4">
                <input type="text" id="sampleType" name="sampleType" placeholder="样本类型" class="col-xs-12"/>
            </div>
        </div>

            
             


        <div class="form-group">
            <label class="col-xs-2 control-label no-padding-right" for="qcLevel">质控水平 </label>
            <div class="col-xs-4">
                <select id="qcLevel" name="qcLevel" class="col-xs-12">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="qcCode">质控编号 </label>
            <div class="col-xs-4">
                <input type="text" id="qcCode" name="qcCode" placeholder="质控编号" class="col-xs-12"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-2 control-label no-padding-right" for="factory">厂商 </label>
            <div class="col-xs-4">
                <input type="text" id="factory" name="factory" placeholder="厂商" class="col-xs-12"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="labdepart">实验室部门 </label>
            <div class="col-xs-4">
            	<select id="labdepart"  name="labdepart" class="form-control">
            		<c:forEach items="${sections }" var="section">
            			<option value="${section.code }">${section.name }</option>
            		</c:forEach>
            	</select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-2 control-label no-padding-right" for="indate">入库时间 </label>
            <div class="col-xs-4">
                <input type="text" id="indate" name="indate" placeholder="入库时间" value="" class="col-xs-12 text"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="outdate">出库时间 </label>
            <div class="col-xs-4">
                <input type="text" id="outdate"  name="outdate" placeholder="出库时间" value="" class="col-xs-12 text"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-2 control-label no-padding-right" for="medthod">方法学 </label>
            <div class="col-xs-4">
                <input type="text" id="method" name="method" placeholder="方法学" value="" class="col-xs-12 text"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="expDate">失效日期 </label>
            <div class="col-xs-4">
                <input type="text" id="expDate"  name="expDate" placeholder="失效日期" value="" class="col-xs-12 text"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-2 control-label no-padding-right" for="qcBatchName">质控品名称 </label>
            <div class="col-xs-4">
                <input type="text" id="qcBatchName" name="qcBatchName" placeholder="质控品名称" class="col-xs-12"/>
            </div>
            <label class="col-xs-2 control-label no-padding-right" for="deviceid">仪器</label>
            <div class="col-xs-4">
            	<div class="input-group col-xs-12 " style="float: right;" >
            		<input id="deviceid" name="deviceid" type="hidden" />
	                <input type="text" id="deviceVal" name="deviceVal" placeholder="仪器" class="col-xs-12 " disabled />
					<span class="input-group-btn">
						<button type="button" id="deviceEdit" class="btn btn-purple btn-sm" >
		                    	编辑
		                    <i class="ace-icon glyphicon glyphicon-edit icon-on-right"></i>
		                </button>
					</span>
	            </div>
	            
	                <div id="diviceList" class="list" style="display: none">
	                	<input type="text" id="device" name="device" placeholder="请输入仪器id/名称" class="col-xs-12" />
	                    <div style="width: 100%;background: #eee;text-align: right;pointer-events: auto;">
	                        <a id="btnCancelChose" class="btnCancelChose" onclick="TSLAB.Custom.cancelChose()">取消</a><a id="btnChose" class="btnChose" onclick="TSLAB.Custom.choseItem()">选择</a>
	                    </div>
	                    <ul id="testList" class="testlist">
	                    </ul>
	
	                </div>
            </div>
            
            
            
            <!-- <div class="col-sm-4" id="examTag">
                <input type="text" name="deviceidtags" id="deviceidtags" placeholder="输入仪器的中文、拼音" class="col-sm-12"/>
            </div> -->

        </div>
    </form>
</div>
<textarea name="antibiotics" id="antibiotics" style="display: none">${antibiotics}</textarea>
<script type="text/javascript" src="<c:url value="/scripts/qc/qcbatch.js"/>"></script>
