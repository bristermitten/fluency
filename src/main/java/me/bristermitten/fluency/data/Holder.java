package me.bristermitten.fluency.data;

import java.util.Objects;

public class Holder<T> {
    private T t;

    public Holder(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public boolean has() {
        return t != null;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "var=" + t +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holder<?> holder = (Holder<?>) o;
        return Objects.equals(t, holder.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t);
    }
}
