<%@ page import="changemanagement.ChangeRequest" %>
<%@ page import="changemanagement.Comments" %>
<%@ page import="changemanagement.Contacts" %>

<table>
    <tr>
        <td>
           Category
        </td>
        <td>
            <g:textField name="cmd.requestInstance.category" value="${cmd.requestInstance.category}"/>
        </td>
        <td>

        </td>
        <td>Urgency</td>
        <td>
            <g:select  name="cmd.requestInstance.urgency" from="${["Critical", "High", "Medium", "Low"]}"  />
        </td>
    </tr>
    <tr>
        <td>
            Type
        </td>
        <td>
            <g:textField name="cmd.requestInstance.type" value="${cmd.requestInstance.type}"/>
        </td>
        <td>

        </td>
        <td>Impact</td>
        <td>
            <g:select  name="cmd.requestInstance.impact" from="${["Critical", "High", "Medium", "Low"]}"  />
        </td>
    </tr>
    <tr>
        <td>
            Item
        </td>
        <td>
            <g:textField name="cmd.requestInstance.item" value="${cmd.requestInstance.item}"/>
        </td>
        <td>

        </td>
        <td>Risk</td>
        <td>
            <g:select  name="cmd.requestInstance.risk" from="${["High", "Medium", "Low"]}"  />
        </td>
    </tr>
    <tr>
        <td>
            Created Date
        </td>
        <td colspan="4">
            <g:field type="text" name="cmd.requestInstance.createdDate" placeholder="mm/dd/yyyy"/>
        </td>
    </tr>
    <tr>
        <td>
            Title
        </td>
        <td colspan="4">
            <g:textField size="160" name="cmd.requestInstance.title" value="${cmd.requestInstance.title}"/>
        </td>
    </tr>
    <tr>
        <td>
           Description
        </td>
        <td colspan="4">
            <g:textArea style="width:1165px; height: 50px;" name="cmd.requestInstance.description" value="${cmd.requestInstance.description}"/>
        </td>
    </tr>
    <tr>
        <td>
            Comments
        </td>
        <td colspan="4">
            <g:textArea style="width:1165px; height: 100px;" name="cmd.commentsInstance.comments" value="${cmd.commentsInstance.comments}"/>
        </td>
    </tr>
    <tr>
        <td>
            Requestor Name
        </td>
        <td colspan="2">
            <g:textField size="50" name="cmd.contactsInstance.contactName" value="${cmd.contactsInstance.contactName}"/>
        </td>

        <td>Phone</td>
        <td>
            <g:textField name="cmd.contactsInstance.phone" value="${cmd.contactsInstance.phone}"/>
        </td>
    </tr>
    <tr>
        <td>
            Email
        </td>
        <td colspan="4">
            <g:textField size="100" name="cmd.contactsInstance.email" value="${cmd.contactsInstance.email}"/>
        </td>

    </tr>
</table>

<g:hiddenField name="cmd.requestInstance.state" value="Open" />
<g:hiddenField name="cmd.requestInstance.status" value="Submitted" />
<g:hiddenField name="cmd.requestInstance.phase" value="Submit" />



