package challenge.panel.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import challenge.panel.constants.PanelAppPanelCategoryKeys;
import challenge.web.constants.ChallengeWebPortletKeys;

/**
 * @author laura
 */
@Component(
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + PanelAppPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class PanelAppPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return ChallengeWebPortletKeys.CHALLENGEWEB;
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest) throws PortalException {
	    PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
	        httpServletRequest, getGroup(httpServletRequest), getPortletId(), 0,
	        0, PortletRequest.RENDER_PHASE);

	    Group group = groupProvider.getGroup(httpServletRequest);

	    if (group != null) {
	        portletURL.setParameter("p_v_l_s_g_id", String.valueOf(group.getGroupId()));
	    }

	    portletURL.setParameter("mvcRenderCommandName", "/admin/list");

	    return portletURL;
	}


	@Reference(target = "(javax.portlet.name=" +  ChallengeWebPortletKeys.CHALLENGEWEB + ")")
	private Portlet _portlet;

}