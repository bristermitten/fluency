package me.bristermitten.fluency;

import org.jetbrains.annotations.NotNull;

public interface ChildFluentBuilder<T,  P extends FluentBuilder<?, ?>> extends FluentBuilder<T, P>{
	@Override @NotNull P done();
}
