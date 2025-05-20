<%@ include file="/init.jsp" %>

<liferay-portlet:renderURL var="userFormURL">
    <liferay-portlet:param name="mvcRenderCommandName" value="/user/form" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="adminListURL">
    <liferay-portlet:param name="mvcRenderCommandName" value="/admin/list" />
</liferay-portlet:renderURL>

<div style="text-align: center; margin-top: 40px;">
    <h2>Bienvenido al módulo de registro de usuarios</h2>

    <div style="margin-top: 40px;">
        <aui:a href="${userFormURL}" cssClass="btn btn-primary" style="width: 300px; margin-bottom: 20px;">
            Ir al formulario de usuario
        </aui:a>
        <br />
        <aui:a href="${adminListURL}" cssClass="btn btn-secondary" style="width: 300px;">
            Ir a la vista de administración
        </aui:a>
    </div>
</div>
