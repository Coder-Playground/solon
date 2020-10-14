package org.noear.solon.function;

public interface SupplierEx<T> {
    T get() throws Throwable;
}
