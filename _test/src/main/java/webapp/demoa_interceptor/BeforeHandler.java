package webapp.demoa_interceptor;

import org.noear.solon.web.XContext;
import org.noear.solon.web.XHandler;

public class BeforeHandler implements XHandler {
    @Override
    public void handle(XContext c) throws Throwable {
        c.attrSet("_start",System.currentTimeMillis());
    }
}
