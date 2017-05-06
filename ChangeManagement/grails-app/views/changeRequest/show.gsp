<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'changeRequest.label', default: 'ChangeRequest')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-changeRequest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="showAllRequests">View Open Requests</g:link></li>
                <li><g:link class="create" action="createCompleteChangeRequest">Create New Change Request</g:link></li>
                <li><g:link class="list" action="showAllClosedRequests">View Closed Requests</g:link></li>
            </ul>
        </div>
        <div id="show-changeRequest" class="content scaffold-show" role="main" >
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <tr>
                    <td><b>Category :</b> ${changeRequest.category }</td>
                    <td><b>Type : </b>${changeRequest.type} </td>
                    <td><b>Item : </b>${changeRequest.item} </td>
                </tr>
                <tr>
                    <td><b>Urgency :</b> ${changeRequest.urgency }</td>
                    <td><b>Impact : </b>${changeRequest.impact} </td>
                    <td><b>Risk :</b>: </b>${changeRequest.risk} </td>
                </tr>
                <tr>
                    <td><b>State :</b> ${changeRequest.state }</td>
                    <td colspan="2"><b>Status :</b> ${changeRequest.status }</td>
                </tr>
                <tr>
                    <td colspan="3"><b>Title :</b> ${changeRequest.title }</td>
                </tr>
                <tr>
                     <td colspan="3"><b>Description :</b> ${changeRequest.description }</td>
                </tr>

            </table>
            <br>
         </div>
        <div class="container">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#contact"><b><font color="#00008b">Contacts</font></b></a></li>
                <li><a data-toggle="tab" href="#comment"><b><font color="#00008b">Comments</font></b></a></li>
                <li><a data-toggle="tab" href="#milestone"><b><font color="#00008b">Milestones</font></b></a></li>
            </ul>

            <div class="tab-content">
                <div id="contact" class="tab-pane fade in active">
                    <div class="col-md-9 col-sm-6">
                        <b>Contact Name :</b> ${changeRequest.contacts[0].contactName}<br>
                        <b>Phone :</b> ${changeRequest.contacts[0].phone} <br>
                        <b>Email : </b>${changeRequest.contacts[0].email} <br>
                        <b>Contact Type : </b>${changeRequest.contacts[0].contactType} <br>
                    </div>
                </div>



                <div id="comment" class="tab-pane fade">
                    <div class="col-md-9 col-sm-6">
                        <g:each var="comment" in="${changeRequest.comments}">
                            <b>Below comments were added by  ${comment.addedBy} on ${comment.commentAddedDate}<br></b>
                            ${comment.comments} <br><br>
                        </g:each>
                    </div>
                </div>

                <div id="milestone" class="tab-pane fade">
                    <div class="col-md-9 col-sm-6">
                        <br>
                        <table border="1" >
                            <tr>
                                <th>Phase</th>
                                <th>Milestone Name</th>
                                <th>Milestone Date </th>
                            </tr>
                            <g:each var="milestone" in="${changeRequest.milestones}">
                                <tr>

                                    <td ><div class="visible-sm-up"> ${milestone.phase}</div></td>

                                    <td><div class="visible-sm-up">${milestone.milestoneName}</div></td>

                                    <td><div class="visible-sm-up">${milestone.milestoneDate} </div></td>
                                </tr>
                            </g:each>
                        </table>
                    </div>
                </div>

        </div>
</div>

</body>
</html>
