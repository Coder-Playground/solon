package webapp.demo1_handler;

import org.noear.solon.annotation.XController;
import org.noear.solon.annotation.XMapping;
import org.noear.solon.web.ModelAndView;
import org.noear.solon.web.XContext;
import org.noear.solon.web.XHandler;

/**
 * 实现简单的 mvc 效果
 * */
@XMapping("/demo1/view/*")
@XController
public class ViewHandler implements XHandler {
    @Override
    public void handle(XContext cxt) throws Throwable {
        ModelAndView model = new ModelAndView("dock.ftl");
        model.put("title","dock");
        model.put("msg","你好 world! in XController");

        cxt.render(model);
    }
}
