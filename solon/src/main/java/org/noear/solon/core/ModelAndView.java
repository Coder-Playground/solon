package org.noear.solon.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * mvc:模型与视图
 *
 * @author noear
 * @since 1.0
 * */
public class ModelAndView extends LinkedHashMap{
    private transient String view;

    public ModelAndView(){super();}
    public ModelAndView(String view) {
        this();
        this.view = view;
    }
    public ModelAndView(String view, Map<String, ?> model) {
        this(view);

        if (model != null) {
            this.putAll(model);
        }
    }

    /** 视图 */
    public String view() {
        return view;
    }
    public ModelAndView view(String view){
        this.view = view;
        return this;
    }

    /** 模型 */
    public Map<String, Object> model() { return this; }

    @Override
    public void clear() {
        super.clear();
        view = null;
    }

    /** 是否为空 */
    public boolean isEmpty() {
        return view == null && size()==0;
    }
}
