package com.di.poc;

import java.util.Objects;

public class SingletonBeanWithParameterizedConstructorAutowired {

	private final Something something;
	private final Nothing nothing;

	@Autowired
	public SingletonBeanWithParameterizedConstructorAutowired(Something something, Nothing nothing) {
		this.something = something;
		this.nothing = nothing;
	}

	public Something getSomething() {
		return something;
	}

	public Nothing getNothing() {
		return nothing;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SingletonBeanWithParameterizedConstructorAutowired that = (SingletonBeanWithParameterizedConstructorAutowired) o;
		return something.equals(that.something) &&
				nothing.equals(that.nothing);
	}

	@Override
	public int hashCode() {
		return Objects.hash(something, nothing);
	}
}
