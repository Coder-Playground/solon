package org.noear.solon.boot.jdksocket;

import org.noear.solon.core.*;
import org.noear.solon.extend.xsocket.XListenerProxy;
import org.noear.solon.extend.xsocket.XSocketContextHandler;


public class SocketProcessor {
    private XSocketContextHandler handler;
    private XListener listener;

    public SocketProcessor() {
        handler = new XSocketContextHandler(XMethod.SOCKET);
        listener = XListenerProxy.getGlobal();
    }

    public void onOpen(XSession session) {
        if (listener != null) {
            listener.onOpen(session);
        }
    }


    public void onMessage(XSession session, XMessage message) {
        if (listener != null) {
            listener.onMessage(session, message);
        }

        if (message.getHandled() == false) {
            handler.handle(session, message, false);
        }
    }

    public void onClosed(XSession session) {
        if (listener != null) {
            listener.onClose(session);
        }
    }

    public void onError(XSession session, Throwable error) {
        if (listener != null) {
            listener.onError(session, error);
        }
    }
}
