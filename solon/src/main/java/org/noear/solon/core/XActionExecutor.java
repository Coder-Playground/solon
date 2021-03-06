package org.noear.solon.core;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动作执行器
 *
 * @author noear
 * @since 1.0
 * */
public interface XActionExecutor {
    /**
     * 是否匹配
     *
     * @param ctx 上下文
     * @param ct 内容类型
     * */
    boolean matched(XContext ctx, String ct);

    /**
     * 执行
     *
     * @param ctx 上下文
     * @param obj 控制器
     * @param mWrap 函数包装器
     * */
    Object execute(XContext ctx, Object obj, MethodWrap mWrap) throws Throwable;

}
