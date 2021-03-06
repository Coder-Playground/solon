package org.noear.solon.boot.undertow;

import org.noear.solon.XApp;
import org.noear.solon.XUtil;
import org.noear.solon.core.Aop;
import org.noear.solon.core.XPlugin;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

public final class XPluginImp implements XPlugin {
    private XPlugin _server = null;


    public static String solon_boot_ver(){
        return "undertow 2.1/" + XApp.cfg().version();
    }

    @Override
    public void start(XApp app) {
        if (app.enableHttp() == false) {
            return;
        }

        Aop.context().beanBuilderAdd(WebFilter.class,(clz,bw,ano)->{});
        Aop.context().beanBuilderAdd(WebServlet.class,(clz, bw, ano)->{});
        Aop.context().beanBuilderAdd(WebListener.class,(clz, bw, ano)->{});

        XServerProp.init();

        Aop.beanOnloaded(() -> {
            start0(app);
        });
    }

    private void start0(XApp app){
        long time_start = System.currentTimeMillis();
        System.out.println("solon.Server:main: Undertow 2.1.09(undertow)");

        Class<?> jspClz = XUtil.loadClass("io.undertow.jsp.JspServletBuilder");

        if (jspClz == null) {
            _server = new XPluginUndertow();
        }else{
            _server = new XPluginUndertowJsp();
        }

        _server.start(app);

        long time_end = System.currentTimeMillis();

        System.out.println("solon.Connector:main: undertow: Started ServerConnector@{HTTP/1.1,[http/1.1]}{0.0.0.0:" + app.port() + "}");
        System.out.println("solon.Server:main: undertow: Started @" + (time_end - time_start) + "ms");
    }

    @Override
    public void stop() throws Throwable {
        if (_server != null) {
            _server.stop();
            _server = null;

            System.out.println("solon.Server:main: undertow: Has Stopped " + solon_boot_ver());
        }
    }
}
