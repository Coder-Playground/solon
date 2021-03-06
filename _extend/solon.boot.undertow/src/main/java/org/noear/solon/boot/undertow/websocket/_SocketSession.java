package org.noear.solon.boot.undertow.websocket;

import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;

import org.noear.solon.XUtil;
import org.noear.solon.core.XMethod;
import org.noear.solon.core.XSession;
import org.noear.solon.core.XMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.*;

public class _SocketSession implements XSession {
    public static Map<WebSocketChannel, XSession> sessions = new HashMap<>();
    public static XSession get(WebSocketChannel real) {
        XSession tmp = sessions.get(real);
        if (tmp == null) {
            synchronized (real) {
                tmp = sessions.get(real);
                if (tmp == null) {
                    tmp = new _SocketSession(real);
                    sessions.put(real, tmp);
                }
            }
        }

        return tmp;
    }

    public static void remove(WebSocketChannel real){
        sessions.remove(real);
    }



    WebSocketChannel real;
    public _SocketSession(WebSocketChannel real){
        this.real = real;
    }

    @Override
    public Object real() {
        return real;
    }

    private String _sessionId = XUtil.guid();
    @Override
    public String sessionId() {
        return _sessionId;
    }

    @Override
    public XMethod method() {
        return XMethod.WEBSOCKET;
    }

    private String _path;
    @Override
    public String path() {
        if(_path == null) {
            _path = URI.create(real.getUrl()).getPath();
        }

        return _path;
    }

    @Override
    public void send(String message) {
        WebSockets.sendText(message, real, null);
    }

    @Override
    public void send(byte[] message) {
        ByteBuffer buf = ByteBuffer.wrap(message);
        WebSockets.sendBinary(buf, real, null);
    }

    @Override
    public void send(XMessage message) {
        send(message.content());
    }

    @Override
    public void close() throws IOException {
        real.close();
        sessions.remove(real);
    }

    @Override
    public boolean isValid() {
        return real.isOpen();
    }

    @Override
    public boolean isSecure() {
        return real.isSecure();
    }

    @Override
    public InetSocketAddress getRemoteAddress(){
        return real.getSourceAddress();
    }

    @Override
    public InetSocketAddress getLocalAddress(){
        return real.getDestinationAddress();
    }

    @Override
    public void setAttachment(Object obj) {
        real.setAttribute("attachment",obj);
    }

    @Override
    public <T> T getAttachment() {
        return (T)real.getAttribute("attachment");
    }

    @Override
    public Collection<XSession> getOpenSessions() {
        return new ArrayList<>(sessions.values());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _SocketSession that = (_SocketSession) o;
        return Objects.equals(real, that.real);
    }

    @Override
    public int hashCode() {
        return Objects.hash(real);
    }
}
