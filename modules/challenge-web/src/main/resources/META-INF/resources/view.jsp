<%@ include file="/init.jsp" %>

<liferay-portlet:renderURL var="userFormURL">
    <liferay-portlet:param name="mvcRenderCommandName" value="/user/form" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL var="adminListURL">
    <liferay-portlet:param name="mvcRenderCommandName" value="/admin/list" />
</liferay-portlet:renderURL>

<section>
  <div class="px-4 py-5 px-md-5 text-center" style="background-color: hsl(0, 0%, 96%)">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <h1 class="display-4 fw-bold mb-4">Welcome to the user registration module</h1>
          <p class="lead mb-5" style="color: hsl(217, 10%, 50.8%)">
            Here you can access the user registration form or manage existing registrations.
          </p>

          <div class="d-grid col-12 col-md-6 mx-auto">
            <div class="mb-3">
              <aui:a href="${userFormURL}" cssClass="btn btn-primary btn-lg w-100">
                Go to the user form
              </aui:a>
            </div>
            <div>
              <aui:a href="${adminListURL}" cssClass="btn btn-outline-secondary btn-lg w-100">
                Go to the administration view
              </aui:a>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</section>
