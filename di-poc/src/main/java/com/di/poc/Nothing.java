package com.di.poc;

import java.util.Objects;

public class Nothing {

	private String name = "Nothing";

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Nothing nothing = (Nothing) o;
		return name.equals(nothing.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
