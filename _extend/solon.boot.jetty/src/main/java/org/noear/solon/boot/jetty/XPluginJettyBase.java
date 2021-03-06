package org.noear.solon.boot.jetty;

import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.noear.solon.XApp;
import org.noear.solon.XUtil;
import org.noear.solon.boot.jetty.http.JtContainerInitializerProxy;
import org.noear.solon.boot.jetty.http.JtHttpContextServlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class XPluginJettyBase {
    protected ServletContextHandler getServletHandler() throws IOException {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setSessionHandler(new SessionHandler());
        handler.setContextPath("/");
        handler.addServlet(JtHttpContextServlet.class, "/");
        handler.setBaseResource(new ResourceCollection(getResourceURLs()));


        //添加容器初始器
        handler.addLifeCycleListener(new JtContainerInitializerProxy(handler.getServletContext()));


        if (XServerProp.session_timeout > 0) {
            handler.getSessionHandler().setMaxInactiveInterval(XServerProp.session_timeout);
        }


        return handler;
    }


    protected String[] getResourceURLs() throws FileNotFoundException {
        URL rootURL = getRootPath();
        if (rootURL == null) {
            throw new FileNotFoundException("Unable to find root");
        }
        String resURL = rootURL.toString();

        boolean isDebug = XApp.cfg().isDebugMode();
        if (isDebug && (resURL.startsWith("jar:") == false)) {
            int endIndex = resURL.indexOf("target");
            String debugResURL = resURL.substring(0, endIndex) + "src/main/resources/";
            return new String[]{debugResURL, resURL};
        }

        return new String[]{resURL};
    }

    protected URL getRootPath() {
        URL root = XUtil.getResource("/");
        if (root != null) {
            return root;
        }
        try {
            String path = XUtil.getResource("").toString();
            if (path.startsWith("jar:")) {
                int endIndex = path.indexOf("!");
                path = path.substring(0, endIndex + 1) + "/";
            } else {
                return null;
            }
            return new URL(path);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
