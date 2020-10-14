package org.noear.solon.functions;

@FunctionalInterface
public interface BiConsumerEx<T1,T2> {
    void accept(T1 t1, T2 t2) throws Throwable;
}
