package challenge.web.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import challenge.web.constants.ChallengeWebPortletKeys;

@Component(
	    immediate = true,
	    property = {
	    	"javax.portlet.name=" + ChallengeWebPortletKeys.CHALLENGEWEB,
	        "mvc.command.name=/user/form"
	    },
	    service = MVCRenderCommand.class
	)
	public class UserFormRenderCommand implements MVCRenderCommand {


		@Override
		public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
			// TODO Auto-generated method stub
			 return "/user-form.jsp";
		}
	}