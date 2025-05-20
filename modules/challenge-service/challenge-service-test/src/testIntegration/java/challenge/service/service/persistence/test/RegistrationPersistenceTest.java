/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package challenge.service.service.persistence.test;

import challenge.service.exception.NoSuchRegistrationException;
import challenge.service.model.Registration;
import challenge.service.service.RegistrationLocalServiceUtil;
import challenge.service.service.persistence.RegistrationPersistence;
import challenge.service.service.persistence.RegistrationUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class RegistrationPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "challenge.service.service"));

	@Before
	public void setUp() {
		_persistence = RegistrationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Registration> iterator = _registrations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Registration registration = _persistence.create(pk);

		Assert.assertNotNull(registration);

		Assert.assertEquals(registration.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Registration newRegistration = addRegistration();

		_persistence.remove(newRegistration);

		Registration existingRegistration = _persistence.fetchByPrimaryKey(
			newRegistration.getPrimaryKey());

		Assert.assertNull(existingRegistration);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRegistration();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Registration newRegistration = _persistence.create(pk);

		newRegistration.setName(RandomTestUtil.randomString());

		newRegistration.setEmail(RandomTestUtil.randomString());

		_registrations.add(_persistence.update(newRegistration));

		Registration existingRegistration = _persistence.findByPrimaryKey(
			newRegistration.getPrimaryKey());

		Assert.assertEquals(
			existingRegistration.getRegistrationId(),
			newRegistration.getRegistrationId());
		Assert.assertEquals(
			existingRegistration.getName(), newRegistration.getName());
		Assert.assertEquals(
			existingRegistration.getEmail(), newRegistration.getEmail());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Registration newRegistration = addRegistration();

		Registration existingRegistration = _persistence.findByPrimaryKey(
			newRegistration.getPrimaryKey());

		Assert.assertEquals(existingRegistration, newRegistration);
	}

	@Test(expected = NoSuchRegistrationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Registration> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Challenge_Registration", "registrationId", true, "name", true,
			"email", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Registration newRegistration = addRegistration();

		Registration existingRegistration = _persistence.fetchByPrimaryKey(
			newRegistration.getPrimaryKey());

		Assert.assertEquals(existingRegistration, newRegistration);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Registration missingRegistration = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRegistration);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Registration newRegistration1 = addRegistration();
		Registration newRegistration2 = addRegistration();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegistration1.getPrimaryKey());
		primaryKeys.add(newRegistration2.getPrimaryKey());

		Map<Serializable, Registration> registrations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, registrations.size());
		Assert.assertEquals(
			newRegistration1,
			registrations.get(newRegistration1.getPrimaryKey()));
		Assert.assertEquals(
			newRegistration2,
			registrations.get(newRegistration2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Registration> registrations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(registrations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Registration newRegistration = addRegistration();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegistration.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Registration> registrations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, registrations.size());
		Assert.assertEquals(
			newRegistration,
			registrations.get(newRegistration.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Registration> registrations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(registrations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Registration newRegistration = addRegistration();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegistration.getPrimaryKey());

		Map<Serializable, Registration> registrations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, registrations.size());
		Assert.assertEquals(
			newRegistration,
			registrations.get(newRegistration.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			RegistrationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Registration>() {

				@Override
				public void performAction(Registration registration) {
					Assert.assertNotNull(registration);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Registration newRegistration = addRegistration();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Registration.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"registrationId", newRegistration.getRegistrationId()));

		List<Registration> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Registration existingRegistration = result.get(0);

		Assert.assertEquals(existingRegistration, newRegistration);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Registration.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"registrationId", RandomTestUtil.nextLong()));

		List<Registration> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Registration newRegistration = addRegistration();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Registration.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("registrationId"));

		Object newRegistrationId = newRegistration.getRegistrationId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"registrationId", new Object[] {newRegistrationId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRegistrationId = result.get(0);

		Assert.assertEquals(existingRegistrationId, newRegistrationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Registration.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("registrationId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"registrationId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Registration addRegistration() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Registration registration = _persistence.create(pk);

		registration.setName(RandomTestUtil.randomString());

		registration.setEmail(RandomTestUtil.randomString());

		_registrations.add(_persistence.update(registration));

		return registration;
	}

	private List<Registration> _registrations = new ArrayList<Registration>();
	private RegistrationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}