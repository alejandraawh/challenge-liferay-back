package challenge.web.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import challenge.web.constants.ChallengeWebPortletKeys;
import challenge.web.configuration.UserPortletConfiguration;

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
      
        return "/user-form.jsp";
    }

}
