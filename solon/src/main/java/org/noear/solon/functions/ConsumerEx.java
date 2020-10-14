package org.noear.solon.functions;

@FunctionalInterface
public interface ConsumerEx<T> {
    void accept(T t) throws Throwable;
}
