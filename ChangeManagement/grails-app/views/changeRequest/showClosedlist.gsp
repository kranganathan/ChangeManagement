<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'changeRequest.label', default: 'ChangeRequest')}" />
    <title>Change Management</title>
</head>
<body>
<a href="#show-changeRequest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><a href="/changeRequest/index">View Open Requests</a></li>
        <li><a href="/changeRequest/createCompleteChangeRequest">Create New Request</a></li>

    </ul>
</div>
<div id="show-changeRequest" class="content scaffold-show" role="main" >
    <h1>Closed Requests</h1>

    <table width=""100%>
        <tr>

            <th><div class="visible-sm-up">Category</div></th>

            <th><div class="visible-sm-up">Type</div></th>


            <th ><div class="visible-sm-up">Item</div></th>

            <th ><div class="visible-sm-up">Status</div></th>


            <th ><div class="hidden-sm hidden-xs">Created Date</div></th>

            <th ><div class="hidden-sm hidden-xs">Scheduled Start Date</div></th>

            <th ><div class="hidden-sm hidden-xs">Scheduled End Date</div></th>
</div>

</tr>

<g:each var="change" in="${changes}">
    <tr>

        <td ><div class="visible-sm-up"><a href="/changeRequest/show/${change.id}">${change.category }</a></div></td>

        <td><div class="visible-sm-up">${change.type}</div></td>

        <td><div class="visible-sm-up">${change.item} </div></td>

        <td><div class="visible-sm-up">${change.status}</div></td>

        <td><div class="hidden-sm hidden-xs">${change.createdDate}</div></td>


        <td><div class="hidden-sm hidden-xs">${change.scheduledStartDate}</div></td>

        <td><div class="hidden-sm hidden-xs">${change.scheduledEndDate}</div></td>


    </tr>
</g:each>
</table>

<div class="pagination">
    <g:paginate total="${personCount ?: 0}" />
</div>
</div>

</body>
</html>
