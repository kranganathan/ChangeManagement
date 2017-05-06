<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'changeRequest.label', default: 'ChangeRequest')}" />
        <title>Change Management</title>
    </head>
    <body>
        <a href="#list-changeRequest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="createCompleteChangeRequest">Create New Change Request</g:link></li>
                <li><g:link class="list" action="showAllClosedRequests">View Closed Requests</g:link></li>
            </ul>
        </div>
        <div id="list-changeRequest" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${changeRequestList}" />

            <div class="pagination">
                <g:paginate total="${changeRequestCount ?: 0}" />
            </div>
        </div>
    </body>
</html>