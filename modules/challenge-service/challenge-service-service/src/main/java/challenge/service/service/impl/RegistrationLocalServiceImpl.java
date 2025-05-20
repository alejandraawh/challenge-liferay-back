/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package challenge.service.service.impl;

import challenge.service.model.Registration;
import challenge.service.service.base.RegistrationLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=challenge.service.model.Registration",
	service = AopService.class
)
public class RegistrationLocalServiceImpl
	extends RegistrationLocalServiceBaseImpl {
	
	public Registration saveRegistro(String name, String email) {

	    long registroId = counterLocalService.increment();

	    Registration registro = createRegistration(registroId);

	    registro.setName(name);
	    registro.setEmail(email);

	    return addRegistration(registro);
	}

}