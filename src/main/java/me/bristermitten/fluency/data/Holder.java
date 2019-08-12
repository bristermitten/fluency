package me.bristermitten.fluency.data;

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
}
