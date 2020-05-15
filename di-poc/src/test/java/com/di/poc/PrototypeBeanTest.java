package com.di.poc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrototypeBeanTest {

	ApplicationContext ac;

	@Before
	public void initializeContext() {
		ac = ApplicationContext.getInstance();
	}

	@Test
	public void testSingletonBeanCreationWithDefaultConstructor() {
		ac.registerPrototypeBean(SingletonWithDefaultConstructor.class);
		ac.initialize();

		SingletonWithDefaultConstructor bean1 = ac.getBean(SingletonWithDefaultConstructor.class);
		SingletonWithDefaultConstructor bean2 = ac.getBean(SingletonWithDefaultConstructor.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertNotSame(bean1, bean2);
		assertEquals(bean1, bean2);
	}

	@Test
	public void testSingletonBeanCreationWithParameterizedConstructor() {
		ac.registerPrototypeBean(SingletonBeanWithParameterizedConstructor.class, new Something(), new Nothing());
		ac.initialize();

		SingletonBeanWithParameterizedConstructor bean1 = ac.getBean(SingletonBeanWithParameterizedConstructor.class);
		SingletonBeanWithParameterizedConstructor bean2 = ac.getBean(SingletonBeanWithParameterizedConstructor.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertNotSame(bean1, bean2);
		assertEquals(bean1, bean2);
		assertSame(bean1.getSomething(), bean2.getSomething());
		assertEquals(bean1.getSomething(), bean2.getSomething());
		assertSame(bean1.getNothing(), bean2.getNothing());
		assertEquals(bean1.getNothing(), bean2.getNothing());
	}

	@Test
	public void testSingletonBeanCreationWithParameterizedConstructorAutowired() {
		ac.registerPrototypeBean(SingletonBeanWithParameterizedConstructorAutowired.class);
		ac.registerPrototypeBean(Something.class);
		ac.registerPrototypeBean(Nothing.class);
		ac.initialize();

		SingletonBeanWithParameterizedConstructorAutowired bean1 = ac.getBean(SingletonBeanWithParameterizedConstructorAutowired.class);
		SingletonBeanWithParameterizedConstructorAutowired bean2 = ac.getBean(SingletonBeanWithParameterizedConstructorAutowired.class);
		assertNotNull(bean1);
		assertNotNull(bean2);

		assertNotSame(bean1, bean2);
		assertEquals(bean1, bean2);
		assertNotSame(bean1.getSomething(), bean2.getSomething());
		assertEquals(bean1.getSomething(), bean2.getSomething());
		assertNotSame(bean1.getNothing(), bean2.getNothing());
		assertEquals(bean1.getNothing(), bean2.getNothing());
	}
}