package com.di.poc;

import java.util.Objects;

public class Something {

	private String name = "Sonething";

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Something something = (Something) o;
		return name.equals(something.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
