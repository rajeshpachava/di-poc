package com.di.poc;

public class Main {
	public static void main(String[] args) {

		ApplicationContext context = new ApplicationContext();
		context.registerSingletonBean(Something.class);
		context.registerSingletonBean(Nothing.class);
		context.registerSingletonBean(SingletonBeanWithParameterizedConstructor.class, new Something(), new Nothing());
		context.registerSingletonBean(SingletonBeanWithParameterizedConstructorAutowired.class);
		context.initialize();

		SingletonBeanWithParameterizedConstructorAutowired sb1 = context.getBean(SingletonBeanWithParameterizedConstructorAutowired.class);
		SingletonBeanWithParameterizedConstructorAutowired sb2 = context.getBean(SingletonBeanWithParameterizedConstructorAutowired.class);
		if (sb2 != null && sb1 == sb2
				&& sb1.getSomething() != null
				&& sb1.getNothing() != null
				&& sb1.getSomething() == sb2.getSomething()
				&& sb1.getNothing() == sb2.getNothing()) {
			System.out.println("Singleton SingletonBeanWithAutowire creation successful!");
		} else {
			System.out.println("Singleton bean creation failed!");
		}
		SingletonBeanWithParameterizedConstructor sb3 = context.getBean(SingletonBeanWithParameterizedConstructor.class);
		SingletonBeanWithParameterizedConstructor sb4 = context.getBean(SingletonBeanWithParameterizedConstructor.class);
		if (sb3 != null
				&& sb3 == sb4
				&& sb4.getSomething() != null
				&& sb4.getNothing() != null
				&& sb3.getSomething() == sb4.getSomething()
				&& sb3.getNothing() == sb4.getNothing()) {
			System.out.println("Singleton SingletonBeanWithoutAutowire creation successful!");
		} else {
			System.out.println("Singleton bean creation failed!");
		}

	}
}
