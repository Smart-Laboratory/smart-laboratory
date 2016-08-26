<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="reagent.detail"/></title>
    <meta name="menu" content="Reagent"/>
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <script type="text/javascript" src="../scripts/reagent/detail.js"></script>
    <script src="<c:url value="/scripts/layer/layer.js"/>"></script>

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

        .table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
            background-color: #F5F5F6;
        }

        .laftnav {
            /*background: #ffffff;*/
            border-right: 1px solid #D9D9D9;
            border-left: 1px solid #D9D9D9;
        }

        .lazy_header {
            height: 40px;
            background: #F7F7F7 !important;
            border-bottom: 1px solid #D9D9D9;
            border-top: 1px solid #D9D9D9;
            line-height: 35px;
            margin-top: 1px;
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

        .no-skin .nav-list > li.active:after {
            border: 1px;
        }

        .no-skin .nav-list > li > a {
            padding-left: 20px;
        }

        ul.nav {
            margin-left: 0px;
        }

        .nav-pills > li > a {
            border-radius: 0px;
        }

        th[aria-selected=true] {
            background-image: none;
            filter: none;
        }

        .ui-jqgrid .ui-jqgrid-labels {
            border-bottom: none;
            background-image: none;
            filter: none;
        }
        .tab-content{
            padding: 10px 12px !important;
        }
        .tabbable{
            margin-top: 10px;
        }

    </style>

</head>
<body>
<div class="row" id="maincontent">

    <div class="tabbable">
        <ul class="nav nav-tabs" id="myTab">
            <li class="active">
                <a data-toggle="tab" href="#ininfo">
                    <i class="blue ace-icon fa  fa-home bigger-120"></i>
                    入库明细
                </a>
            </li>
            <li>
                <a data-toggle="tab" href="#outinfo">
                    <i class="pink ace-icon fa fa-tachometer bigger-120"></i>
                    出库明细
                </a>
            </li>
            </ul>
        </div>
    <div class="tab-content">
        <!--常用信息start-->
        <div id="ininfo" class="tab-pane fade in active">
            <div class="row">
                <div class="col-xs-12 leftContent">
                    <table id="list" class="table table-condensed table-striped"></table>
                    <div id="pager"></div>
                </div>
            </div>
        </div>
        <div id="outinfo" class="tab-pane fade">
            <div class="row">
                <div class="col-xs-12 leftContent">
                    <table id="list2" class="table table-condensed table-striped"></table>
                    <div id="pager2"></div>
                </div>
            </div>
        </div>

    </div>
    <div id="printDialog" align="left">
        <button class="btn btn-success" onclick="document.getElementById('iframe_print').contentWindow.print();">
            <fmt:message key="print"/></button>
        <div id="printFrame" style="height:500px;"></div>
    </div>
</div>
</body>