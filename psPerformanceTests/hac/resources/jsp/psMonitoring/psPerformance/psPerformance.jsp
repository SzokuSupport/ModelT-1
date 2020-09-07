<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="hac" uri="/WEB-INF/custom.tld" %> 
<html>
<head>
<title>PS Performance</title>
<link rel="stylesheet" href="<c:url value="/static/css/table.css"/>" type="text/css" media="screen, projection" />
<link rel="stylesheet" href="<c:url value="/static/css/monitoring/performance.css"/>" type="text/css" media="screen, projection" />

<script type="text/javascript" src="<c:url value="/static/js/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/history.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/psPerformance.js"/>"></script>

</head>
<body>

	<div class="prepend-top span-17 colborder" id="content">
		<button id="toggleSidebarButton">&gt;</button>
		<div class="marginLeft marginBottom">
			<h2>Performance Tests</h2>
			<hac:note additionalCssClass="marginBottom">
				It is not possible to run different tests simultaneously. Tests started at the same time are queued and wait for their turn.
			</hac:note>			
			<div id="tabs">
				<ul>
					<li><a href="#tabs-1">SQL Max</a>
					</li>
				</ul>

				<div id="tabs-1">
					<button id="runSQLMax" data-url="<c:url value="/psMonitoring/psPerformance/sqlmax/"/>">Run SQL max</button>
					<div id="resultMax" class="resultBox">
						<dl>
							<dt>Time to add 10.000 rows</dt>
							<dd>
								<span id="durationAdded"></span> ms
							</dd>
							<dt>Time to add 10.000 rows using max() queries</dt>
							<dd>
								<span id="durationAddedMax"></span> ms
							</dd>
							<dt>Time to add 10.000 rows using max() queries and index</dt>
							<dd>
								<span id="durationAddedMaxIndex"></span> ms
							</dd>
						</dl>
					</div>
				</div>
			</div>

		</div>
	</div>

	<div class="span-6 last" id="sidebar1">
		<div class="prepend-top">
			<h3 class="caps">Page description</h3>
			<div class="box">
				<div class="quiet">
					This page enables you to run the performance test of the database with the SQL <strong>max()</strong> command.
				</div>
			</div>
			<h3 class="caps">See also in the hybris Wiki</h3>
			<div class="box">
				<ul>
					<li> <a href="${wikiPerformance}" target="_blank" class="quiet" >Performance Tuning Overview</a> </li>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>
