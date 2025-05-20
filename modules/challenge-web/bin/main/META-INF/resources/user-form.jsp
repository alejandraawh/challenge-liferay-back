<%@ include file="/init.jsp" %>

<portlet:actionURL name="/submit" var="saveForm">
</portlet:actionURL>

<h2>Registration Form</h2>

<aui:form action="<%= saveForm %>" method="post" name="registroForm">

    <aui:input name="name" label="username" required="true" />
    
    <aui:input name="email" label="email" type="email" required="true" />

    <aui:button type="submit" value="Register" />

</aui:form>

<liferay-ui:error exception="<%= Exception.class %>" key="error" message="There was an error processing the form" />