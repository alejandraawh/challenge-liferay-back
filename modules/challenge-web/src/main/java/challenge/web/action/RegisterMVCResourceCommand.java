package challenge.web.action;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import challenge.service.model.Registration;
import challenge.service.service.RegistrationLocalService;
import challenge.web.constants.ChallengeWebPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ChallengeWebPortletKeys.CHALLENGEWEB,
		"mvc.command.name=/submit"
	},
	service = MVCResourceCommand.class
)
public class RegisterMVCResourceCommand implements MVCResourceCommand {

	@Reference
	private RegistrationLocalService _registrationLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Override
	public boolean serveResource(ResourceRequest request, ResourceResponse response) {
		JSONObject jsonResponse = JSONFactoryUtil.createJSONObject();

		Map<String, String> errors = validateInput(request);

		if (!errors.isEmpty()) {
			jsonResponse.put("success", false);
			jsonResponse.put("errors", JSONFactoryUtil.createJSONObject(errors));
		} else {
			try {
				long id = _counterLocalService.increment();
				Registration registration = _registrationLocalService.createRegistration(id);

				String username = ParamUtil.getString(request, "username");
				String email = ParamUtil.getString(request, "email");

				registration.setName(username);
				registration.setEmail(email);

				_registrationLocalService.addRegistration(registration);

				jsonResponse.put("success", true);
			} catch (Exception e) {
				jsonResponse.put("success", false);
				jsonResponse.put("error", "Internal error: " + e.getMessage());
			}
		}

		writeJsonResponse(response, jsonResponse);

		return false;
	}

	private Map<String, String> validateInput(ResourceRequest request) {
		Map<String, String> errors = new HashMap<>();

		String username = ParamUtil.getString(request, "username");
		String email = ParamUtil.getString(request, "email");

		if (username == null || username.trim().isEmpty()) {
			errors.put("username", "Name is required.");
		}

		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "Email is required.");
		} else if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			errors.put("email", "Invalid email format.");
		}

		return errors;
	}

	private void writeJsonResponse(ResourceResponse response, JSONObject jsonResponse) {
		response.setContentType("application/json");
		try {
			response.getPortletOutputStream().write(jsonResponse.toString().getBytes());
			response.getPortletOutputStream().flush();
		} catch (IOException e) {
			System.err.println("Error writing JSON response: " + e.getMessage());
		}
	}

}
