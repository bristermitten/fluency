package me.bristermitten.fluency;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SimpleDataClass {
	private final int age;
	private final String name;
	@Nullable
	private final SimpleDataClass parent;

	public SimpleDataClass(int age, String name, SimpleDataClass parent) {
		this.age = age;
		this.name = name;
		this.parent = parent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SimpleDataClass)) return false;
		SimpleDataClass that = (SimpleDataClass) o;
		return age == that.age &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, name);
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public SimpleDataClass getParent() {
		return parent;
	}
}
