<%--
  Created by IntelliJ IDEA.
  User: zhou
  Date: 2016/5/17
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
	<title><fmt:message key="set.device"/></title>
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/set/device.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />

</head>
<style>
    .laftnav{
        /*background: #ffffff;*/
        border-right: 1px solid #D9D9D9;
        border-left: 1px solid #D9D9D9;
    }
    .lazy_header{
        height: 40px;
        background: #F7F7F7 !important;
        border-bottom: 1px solid #D9D9D9;
        border-top: 1px solid #D9D9D9;
        line-height: 35px;
        margin-top:1px;
    }
    .lazy-list-title {
        font-size: 14px;
        max-width: 100px;
        display: inline-block;
        text-overflow: ellipsis;
        -o-text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        padding-left: 5px;
    }
    .no-skin .nav-list>li.active:after{
        border: 1px;
    }
    .no-skin .nav-list>li>a{
        padding-left:20px;
    }
    ul.nav{
        margin-left:0px;
    }
    .nav-pills>li>a{
        border-radius: 0px;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <div  style="padding-top: 5px;" id="toolbar">
            <button type="button" class="btn btn-sm btn-primary " title="添加" onclick="Add()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add" />
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="编辑" onclick="Edit()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除" onclick="Delete()">
                <i class="ace-icon fa fa-times bigger-110"></i>
                <fmt:message key="button.delete" />
            </button>
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
        <div class="col-xs-12 content">
            <table id="tableList"></table>
            <div id="pager"></div>
        </div>
    </div>
</div>
<div style="clear: both"></div>
<div id="addDialog" style="display: none;width: 500px;overflow: hidden" class="row">
    <form id="addForm" class="form-horizontal" action="<c:url value='../set/device/saveDevice'/>" method="post">
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="name"> 编号 </label>
            <div class="col-xs-8">
                <input type="text" id="id" name="id" placeholder="编号" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="name"> 名称 </label>
            <div class="col-xs-8">
                <input type="text" id="name" name="name" placeholder="名称" class="col-xs-8"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="type">类型</label>
            <div class="col-xs-8">
                <select id="type" name="type">
                    <c:forEach var="item" items="${typeList}">
                        <option value='<c:out value="${item.key}"/>' ><c:out value="${item.value}"/> </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="lab"> 所属科室 </label>
            <div class="col-xs-8">
                <select id="lab" name="lab">
                    <c:forEach var="item" items="${departList}">
                        <option value='<c:out value="${item.key}"/>' ><c:out value="${item.value}"/> </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="comport"> COM口 </label>
            <div class="col-xs-8">
                <select class="col-xs-8" id="comport" name="comport">
                    <option value="1">COM1</option>
                    <option value="2">COM2</option>
                    <option value="3">COM3</option>
                    <option value="4">COM4</option>
                    <option value="5">COM5</option>
                    <option value="6">COM6</option>
                    <option value="7">COM7</option>
                    <option value="8">COM8</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="baudrate"> 波特率 </label>
            <div class="col-xs-8">
                <select class="col-xs-8" id="baudrate" name="baudrate">
                    <option value="300">300</option>
                    <option value="600">600</option>
                    <option value="1200">1200</option>
                    <option value="1800">1800</option>
                    <option value="2400">2400</option>
                    <option value="4800">4800</option>
                    <option value="7200">7200</option>
                    <option value="9600">9600</option>
                    <option value="14400">14400</option>
                    <option value="19200">19200</option>
                    <option value="28800">28800</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="parity"> 校验位 </label>
            <div class="col-xs-8">
                <select class="col-xs-8" id="parity" name="parity">
                    <option value="N">无</option>
                    <option value="O">奇校验</option>
                    <option value="E">偶校验</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="databit"> 数据位 </label>
            <div class="col-xs-8">
                <select class="col-xs-8" id="databit" name="databit">
                    <option value="8">8</option>
                    <option value="7">7</option>
                    <option value="6">6</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="databit"> 停止位 </label>
            <div class="col-xs-8">
                <select class="col-xs-8" id="stopbit" name="stopbit">
                    <option value="1">1</option>
                    <option value="2">2</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="handshake"> 协议 </label>
            <div class="col-xs-8">
                <select class="col-xs-8" id="handshake" name="handshake">
                    <option value="0">无</option>
                    <option value="1">XonXof</option>
                    <option value="2">RTS OR CTS</option>
                    <option value="3">ASTM</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="space-4"></div>
            <label class="col-xs-3 control-label no-padding-right" for="datawind">数据窗口</label>
            <div class="col-xs-8">
                <input type="text" id="datawind" name= "datawind" placeholder="datawind" class="col-xs-8"/>
            </div>
        </div>
    </form>
</div>


