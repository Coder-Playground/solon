package org.noear.solon.func;

@FunctionalInterface
public interface ConsumerEx<T> {
    void accept(T t) throws Throwable;
}
