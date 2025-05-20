package challenge.web.action;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import challenge.service.model.Registration;
import challenge.service.service.RegistrationLocalService;
import challenge.web.constants.ChallengeWebPortletKeys;

@Component(
	    immediate = true,
	    property = {
	    	"javax.portlet.name=" + ChallengeWebPortletKeys.CHALLENGEWEB,
	        "mvc.command.name=/admin/list"
	    },
	    service = MVCRenderCommand.class
	)
	public class AdminListRenderCommand implements MVCRenderCommand {
		
	 	@Reference
	    private RegistrationLocalService _registrationLocalService;
	

		@Override
		public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		    List<Registration> registrations = _registrationLocalService.getRegistrations(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			
		    renderRequest.setAttribute("registrations", registrations);
			  return "/admin-list.jsp";
		}
	}