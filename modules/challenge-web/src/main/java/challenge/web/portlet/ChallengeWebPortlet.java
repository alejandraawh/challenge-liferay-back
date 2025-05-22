package challenge.web.portlet;

import challenge.web.constants.ChallengeWebPortletKeys;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import challenge.web.configuration.UserPortletConfiguration;

/**
 * @author laura
 */
@Component(
		configurationPid = "challenge.web.configuration.UserPortletConfiguration",
	property = {
		"com.liferay.portlet.show-portlet-access-denied=true",
		"com.liferay.portlet.show-portlet-configuration-icon=true",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ChallengeWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/user-form.jsp",
		"javax.portlet.name=" + ChallengeWebPortletKeys.CHALLENGEWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.add-default-resource=true"
	},
	service = Portlet.class
)
public class ChallengeWebPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			renderRequest.setAttribute(
				UserPortletConfiguration.class.getName(),
				configurationProvider.getCompanyConfiguration(
						UserPortletConfiguration.class,
						portal.getCompanyId(renderRequest)));
		}
		catch (ConfigurationException configurationException) {
			throw new PortletException(configurationException);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private ConfigurationProvider configurationProvider;

	@Reference
	private Portal portal;
	
}


