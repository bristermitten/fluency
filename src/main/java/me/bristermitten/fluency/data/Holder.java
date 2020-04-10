package me.bristermitten.fluency.data;

import java.util.Objects;
import java.util.function.Consumer;

public class Holder<T> {
    private T value;

    public Holder(T value) {
        this.value = value;
    }


    public T get() {
        return value;
    }

    public void set(T t) {
        this.value = t;
    }

    public boolean has() {
        return value != null;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "var=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holder<?> holder = (Holder<?>) o;
        return Objects.equals(value, holder.value);
    }

    public void transform(Consumer<T> transformer) {
        if (!has()) {
            return;
        }
        T value = get();
        transformer.accept(value);
        set(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
