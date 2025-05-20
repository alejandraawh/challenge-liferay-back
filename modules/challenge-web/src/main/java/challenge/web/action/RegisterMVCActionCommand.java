package challenge.web.action;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import challenge.service.model.Registration;
import challenge.service.service.RegistrationLocalService;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import challenge.web.constants.ChallengeWebPortletKeys;

@Component(
	    immediate = true,
	    property = {
	    	"javax.portlet.name=" + ChallengeWebPortletKeys.CHALLENGEWEB,
	        "mvc.command.name=/submit"
	    },
	    service = MVCActionCommand.class
	)
public class RegisterMVCActionCommand implements MVCActionCommand {
	
   @Reference
    private RegistrationLocalService _registrationLocalService;
   @Reference
   	private CounterLocalService _counterLocalService;
  
	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		 try {
		        String name = ParamUtil.getString(actionRequest, "name");
		        String email = ParamUtil.getString(actionRequest, "email");
		        
		        long newId = _counterLocalService.increment();

		        Registration registration = _registrationLocalService.createRegistration(newId);

		        registration.setName(name);
		        registration.setEmail(email);

		        _registrationLocalService.addRegistration(registration);

		    } catch (Exception e) {
		        throw new PortletException(e);
		    }
	    
		return true;
	}


}
