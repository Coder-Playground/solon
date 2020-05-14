package org.noear.solon;

import org.noear.solon.core.XMap;
import org.noear.solon.core.XPluginEntity;
import org.noear.solon.core.XProperties;
import org.noear.solon.core.XScaner;
import org.noear.solon.ext.Act2;

import java.net.URL;
import java.util.*;

/**
 * 统一配置加载器
 * */
public final class XAppProperties extends XProperties {
    private XMap _args;
    private List<XPluginEntity> _plugs = new ArrayList<>();

    public XAppProperties() {
        super(System.getProperties());
    }

    public XAppProperties load(XMap args) {
        //1.接收启动参数
        _args = args;

        //2.加载文件的配置
        load(XUtil.getResource("application.properties"));
        load(XUtil.getResource("application.yml"));

        //3.同步启动参数
        _args.forEach((k, v) -> {
            if (k.indexOf(".") >= 0) {
                this.setProperty(k, v);
                System.setProperty(k, v);
            }
        });

        //4.标识debug模式
        if (isDebugMode()) {
            System.setProperty("debug", "1");
        }

        return this;
    }

    public XAppProperties load(URL url) {
        if (url != null) {
            Properties prop = XUtil.getProperties(url);

            if (prop != null) {
                putAll(prop);
                System.getProperties().putAll(prop);
            }
        }

        return this;
    }

    protected void plugsScan() {
        //3.查找插件配置（如果出错，让它抛出异常）
        XScaner.scan("solonplugin", n -> n.endsWith(".properties") || n.endsWith(".yml"))
                .stream()
                .map(k -> XUtil.getResource(k))
                .forEach(url -> plugsScanMapDo(url));

        if (_plugs.size() > 0) {
            //进行优先级顺排（数值要倒排）
            //
            _plugs.sort(Comparator.comparingInt(XPluginEntity::getPriority).reversed());
        }
    }

    private void plugsScanMapDo(URL url) {
        try {
            XAppProperties p = new XAppProperties().load(url);

            String temp = p.get("solon.plugin");

            if (XUtil.isEmpty(temp) == false) {
                XPluginEntity ent = new XPluginEntity();
                ent.className = temp;
                ent.priority = p.getInt("solon.plugin.priority", 0);

                _plugs.add(ent);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Set<Act2<String, String>> _changeEvent = new HashSet<>();

    public void onChange(Act2<String, String> event) {
        _changeEvent.add(event);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        Object obj = super.put(key, value);

        if (key instanceof String && value instanceof String) {
            _changeEvent.forEach(event -> {
                event.run((String) key, (String) value);
            });
        }

        return obj;
    }

    /**
     * 获取启动参数
     */
    public XMap argx() {
        return _args;
    }

    /**
     * 获取插件列表
     */
    public List<XPluginEntity> plugs() {
        return _plugs;
    }


    /**
     * 获取服务端口(默认:8080)
     */
    public int serverPort() {
        return getInt("server.port", 8080);
    }

    /**
     * 是否为debug mode
     */
    public boolean isDebugMode() {
        return argx().getInt("debug") == 1;
    }
}