package org.noear.solon.upstream;

/**
 * 负载器工厂
 * */
@FunctionalInterface
public interface XUpstreamFactory {
    XUpstream create(String service);
}
