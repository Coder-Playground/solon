package org.noear.solon.function;

@FunctionalInterface
public interface ConsumerEx<T> {
    void accept(T t) throws Throwable;
}
