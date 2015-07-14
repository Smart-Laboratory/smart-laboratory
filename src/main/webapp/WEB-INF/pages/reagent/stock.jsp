<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="reagent.stock"/></title>
    <meta name="menu" content="Reagent"/>
    <script type="text/javascript" src="../scripts/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/jquery.dataTables.min.css'/>" />
	<script type="text/javascript" src="../scripts/reagent/stock.js"></script>
	
	<style>
		td.details-control {
		    background: url('../images/details_open.png') no-repeat center center;
		    cursor: pointer;
		}
		tr.shown td.details-control {
		    background: url('../images/details_close.png') no-repeat center center;
		}
	</style>

</head>
<body>
<div class="col-sm-10">

<table id="example" class="display" cellspacing="0" width="100%">
    <thead>
        <tr>
        <th></th>
        <th><fmt:message key="reagent.name"/></th>
		<th><fmt:message key="reagent.place"/></th>
		<th><fmt:message key="reagent.brand"/></th>
		<th><fmt:message key="reagent.unit"/></th>
		<th><fmt:message key="reagent.price"/></th>
		<th><fmt:message key="reagent.address"/></th>
		<th><fmt:message key="reagent.condition"/></th>
		<th><fmt:message key="reagent.temperature"/></th>
        <th><fmt:message key="reagent.isselfmade"/></th>
        </tr>
    </thead>
</table>

</div>
</body>