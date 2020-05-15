package com.di.poc;

import java.util.Objects;

public class SingletonWithDefaultConstructor {

	private String name = "SingletonWithDefaultConstructor";

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SingletonWithDefaultConstructor that = (SingletonWithDefaultConstructor) o;
		return name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
