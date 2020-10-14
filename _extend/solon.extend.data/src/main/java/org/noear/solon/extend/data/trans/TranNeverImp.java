package org.noear.solon.extend.data.trans;

import org.noear.solon.function.RunnableEx;
import org.noear.solon.extend.data.TranNode;
import org.noear.solon.extend.data.TranManager;

/**
 * 以无事务的方式执行，如果当前有事务则报错（不需要入栈）
 * */
public class TranNeverImp implements TranNode {
    public TranNeverImp() {

    }

    @Override
    public void apply(RunnableEx runnable) throws Throwable {
        //获取当前事务
        //
        if (TranManager.current() != null) {
            //绝不能有事务
            throw new RuntimeException("Never support transactions");
        } else {
            runnable.run();
        }
    }
}

