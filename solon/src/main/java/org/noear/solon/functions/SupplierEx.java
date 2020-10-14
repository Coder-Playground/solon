package org.noear.solon.functions;

public interface SupplierEx<T> {
    T get() throws Throwable;
}
