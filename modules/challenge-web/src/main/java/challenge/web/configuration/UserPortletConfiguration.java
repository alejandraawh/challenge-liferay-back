package challenge.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
		category = "configurations",
		scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
		id = "challenge.web.configuration.UserPortletConfiguration",
		localization = "content/Language",
		name = "Configuration"
)
public interface UserPortletConfiguration {

	@Meta.AD(deflt = "Welcome to the user registration", required = false)
	public String title();
	
}