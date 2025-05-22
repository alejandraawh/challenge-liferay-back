<%@ include file="/init.jsp"%>

<portlet:resourceURL id="/submit" var="registerResourceURL" />

<section>
  <div class="px-4 py-5 px-md-5 text-center text-lg-start" style="background-color: hsl(0, 0%, 96%)">
    <div class="container">
      <div class="row gx-lg-5 align-items-center">
        <div class="col-lg-6 mb-5 mb-lg-0">
          <h1 class="my-5 display-3 fw-bold ls-tight">Registration Form</h1>
          <p style="color: hsl(217, 10%, 50.8%)">Please enter your details to continue.</p>
        </div>

        <div class="col-lg-6 mb-5 mb-lg-0">
          <div class="card">
            <div class="card-body py-5 px-md-5">
              <form id="registrationForm">
                <div class="mb-4">
                  <label for="<portlet:namespace />username">Enter your name</label>
                  <input name="<portlet:namespace />username" id="<portlet:namespace />username" type="text" class="form-control" />
                  <div id="<portlet:namespace />username-error" class="text-danger small"></div>
                </div>

                <div class="mb-4">
                  <label for="<portlet:namespace />email">Enter your email</label>
                  <input name="<portlet:namespace />email" id="<portlet:namespace />email" type="email" class="form-control" />
                  <div id="<portlet:namespace />email-error" class="text-danger small"></div>
                </div>

               <button type="button" class="btn btn-primary btn-block mb-4" onclick="submitRegistration()">Submit</button>
                <div id="<portlet:namespace />form-success" class="text-success mt-3"></div>
              </form>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</section>

<script>
  function submitRegistration() {
    const namespace = '<portlet:namespace />';

    const usernameInput = document.getElementById(namespace + "username");
    const emailInput = document.getElementById(namespace + "email");
    const usernameError = document.getElementById(namespace + "username-error");
    const emailError = document.getElementById(namespace + "email-error");
    const formSuccess = document.getElementById(namespace + "form-success");

    const username = usernameInput.value.trim();
    const email = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // Clear previous errors and messages
    usernameError.textContent = "";
    emailError.textContent = "";
    formSuccess.textContent = "";

    let valid = true;

    if (!username) {
      usernameError.textContent = "Name is required.";
      valid = false;
    }
    if (!email) {
      emailError.textContent = "Email is required.";
      valid = false;
    } else if (!emailRegex.test(email)) {
      emailError.textContent = "Invalid email format.";
      valid = false;
    }
    if (!valid) return;

    const params = new URLSearchParams();
    params.append(namespace + "username", username);
    params.append(namespace + "email", email);

    fetch("<%= registerResourceURL.toString() %>", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
      },
      body: params.toString(),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.success) {
          formSuccess.textContent = "Registration completed successfully!";
          document.getElementById("registrationForm").reset();
        } else {
          if (data.errors) {
            if (data.errors.username) {
              usernameError.textContent = data.errors.username;
            }
            if (data.errors.email) {
              emailError.textContent = data.errors.email;
            }
          } else {
            alert("There was a server error.");
          }
        }
      });
  }
</script>
