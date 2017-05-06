<%@ page import="changemanagement.ChangeRequest" %>
<%@ page import="changemanagement.Comments" %>
<%@ page import="changemanagement.ChangeRequestProcessor" %>

<table>
    <tr>
        <td><b>State :</b> ${pr.state }</td>
        <td colspan="2"><b>Status :</b> ${pr.status }</td>
    </tr>
     <tr>
        <g:if test="${pr.status.equalsIgnoreCase("Submitted")}">
            <td colspan="3"><b>Request Action :</b> <g:select  name="pr.requestAction" from="${["Accept", "Reject"]}"  /></td>
        </g:if>
         <g:if test="${pr.status.equalsIgnoreCase("In Review")}">
           <td colspan="3"><b>Request Action :</b><g:select  name="pr.requestAction" from="${["Schedule", "Cancel"]}"  /></td>
         </g:if>
         <g:if test="${pr.status.equalsIgnoreCase("Scheduled")}">
             <td colspan="3"><b>Request Action :</b><g:select  name="pr.requestAction" from="${["Implementation Start", "Cancel"]}"  /></td>
         </g:if>
         <g:if test="${pr.status.equalsIgnoreCase("In Progress")}">
             <td colspan="3"><b>Request Action :</b><g:select  name="pr.requestAction" from="${["Implementation Completed"]}"  /></td>
         </g:if>
     </tr>
    <g:if test="${pr.status.equalsIgnoreCase("In Review")}">
        <tr>
            <td ><b>Scheduled Start Date :</b><g:field type="text" name="pr.scheduledStDate" placeholder="mm/dd/yyyy"/></td>
            <td colspan="2"><b>Scheduled End Date :</b><g:field type="text" name="pr.scheduledEndDate" placeholder="mm/dd/yyyy" /></td>
        </tr>
    </g:if>
    <g:if test="${pr.status.equalsIgnoreCase("Scheduled")}">
      <tr>
        <td ><b>Scheduled Start Date :</b><g:field type="text" name="pr.scheduledStDate"  value="${pr.scheduledStDate}" placeholder="mm/dd/yyyy"/></td>
        <td colspan="2"><b>Scheduled End Date :</b><g:field type="text" value="${pr.scheduledEndDate}"  name="pr.scheduledEndDate" placeholder="mm/dd/yyyy"/></td>
      </tr>
    </g:if>
    <g:if test="${pr.status.equalsIgnoreCase("In Progress")}">
        <tr>
            <td ><b>Scheduled Start Date :</b><g:field type="text" value="${pr.scheduledStDate}"  name="pr.scheduledStDate" placeholder="mm/dd/yyyy"/></td>
            <td colspan="2"><b>Scheduled End Date :</b><g:field value="${pr.scheduledEndDate}"  type="text" name="pr.scheduledEndDate" placeholder="mm/dd/yyyy"/></td>
        </tr>
    </g:if>
     <tr>
            <g:if test="${!pr.status.equalsIgnoreCase("Completed")}">
            <td>
               <b>Processed By :</b> <g:textField name="pr.processedBy" value="${pr.processedBy}"/>
            </td>
            <td colspan="2">
                <b>Comments :</b> <g:textArea style="width:400px; height: 50px;" name="pr.comments" value="${pr.comments}"/>
            </td>
            </g:if>
     </tr>
</table>

<g:hiddenField name="pr.changeRequestID" value="${pr.changeRequestID}" />




