package org.noear.solon.core;


import java.lang.reflect.Method;


public class XCallable implements XHandler {
    private MethodWrap cWrap = null;
    private BeanWrap bWrap = null;

    public XCallable() {
        if (bWrap == null) {
            bWrap = new BeanWrap(this.getClass(), this);

            for (Method m : this.getClass().getDeclaredMethods()) {
                if (m.getName().equals("call")) {
                    cWrap = MethodWrap.get(m);
                    break;
                }
            }
        }
    }

    @Override
    public void handle(XContext x) throws Throwable {
        if(cWrap == null){
            return;
        }

        if (x.getHandled() == false) {
            try {
                innerRender(x, XActionUtil.exeMethod(bWrap.get(), cWrap, x));
            } catch (Throwable ex) {
                x.attrSet("error", ex);
                innerRender(x, ex);
                XMonitor.sendError(x, ex);
            }
        }
    }

    protected void innerRender(XContext x, Object result) throws Throwable {
        x.render(result);
    }
}
