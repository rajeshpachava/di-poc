package com.di.poc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

	Map<Class, Object[]> prototypeClassHolder = new HashMap<>();
	Map<Class, Object[]> singletonClassHolder = new HashMap<>();
	Map<Class, Object> singletonInstanceHolder = new HashMap<>();

	private static ApplicationContext applicationContext = new ApplicationContext();

	public static ApplicationContext getInstance() {
		return applicationContext;
	}


	public <T> void registerSingletonBean(Class<T> instanceType) {
		singletonClassHolder.put(instanceType, null);
	}

	public <T> void registerSingletonBean(Class<T> instanceType, Object... arguments) {
		singletonClassHolder.put(instanceType, arguments);
	}

	public <T> void registerPrototypeBean(Class<T> instanceType) {
		prototypeClassHolder.put(instanceType, null);
	}

	public <T> void registerPrototypeBean(Class<T> instanceType, Object... arguments) {
		prototypeClassHolder.put(instanceType, arguments);
	}

	public void initialize() {
		for (Map.Entry<Class, Object[]> entry : singletonClassHolder.entrySet()) {
			Class type = entry.getKey();
			try {
				singletonInstanceHolder.put(type, resolveInstance(type, singletonClassHolder, singletonInstanceHolder));
			} catch (Exception e) {
				throw new RuntimeException("Failed to create singleton instance of a class with type: " + type);
			}
		}
	}

	private Object resolveInstance(Class type,
								   Map<Class, Object[]> classHolder,
								   Map<Class, Object> instanceHolder) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		if (instanceHolder.get(type) != null) {
			return instanceHolder.get(type);
		}
		Object newInstance = null;
		Constructor[] constructors = type.getConstructors();

		if (constructors.length > 0) {
			for (Constructor constructor : constructors) {
				int constructorParameterCount = constructor.getParameterCount();
				if (classHolder.get(type) != null
						&& constructorParameterCount > 0
						&& constructorParameterCount == classHolder.get(type).length) {
					Class[] argumentTypes = Arrays.stream(classHolder.get(type)).map(o -> o.getClass()).toArray(Class[]::new);
					if (Arrays.equals(constructor.getParameterTypes(), argumentTypes)) {
						newInstance = constructor.newInstance(classHolder.get(type));

					}
				}
			}
		}
		if (instanceHolder.get(type) == null && type.getConstructors().length == 1) {
			Constructor constructor = type.getConstructors()[0];
			Annotation[] annotations = constructor.getAnnotations();

			int constructorParameterCount = constructor.getParameterCount();
			Object[] arguments = new Object[constructorParameterCount];
			if (Arrays.stream(annotations).filter(annotation -> annotation.annotationType().equals(Autowired.class)).count() > 0 && constructorParameterCount > 0) {
				Class[] parameterTypes = constructor.getParameterTypes();
				for (int i = 0; i < constructorParameterCount; i++) {
					Class argType = parameterTypes[i];
					Object instance = instanceHolder.get(argType);
					if (instance == null) {
						instance = resolveInstance(argType, classHolder, instanceHolder);
					}
					arguments[i] = instance;
				}
				if (arguments.length == constructorParameterCount) {
					newInstance = constructor.newInstance(arguments);
				}
			}

		}
		if (instanceHolder.get(type) == null && constructors.length == 1 && constructors[0].getParameterCount() == 0) {
			newInstance = type.newInstance();
		}
		if (classHolder.get(type) != null) {
			instanceHolder.put(type, newInstance);
		}
		return newInstance;
	}

	public <T> T getBean(Class<T> type) {
		if (singletonInstanceHolder.containsKey(type)) {
			return (T) singletonInstanceHolder.get(type);
		}
		//Need to extend for other scopes like request/session/global for web based application context.
		//For all other cases return the new instance on every request of #getBean(Class).
		if (prototypeClassHolder.containsKey(type)) {
			try {
				return (T) resolveInstance(type, prototypeClassHolder, new HashMap<>());
			} catch (Exception e) {
				throw new RuntimeException("Failed to create instance of a class with type: " + type);
			}
		}
		throw new RuntimeException("Unsupported bean type! Bean is not registered in the ApplicationContext!");
	}
}
