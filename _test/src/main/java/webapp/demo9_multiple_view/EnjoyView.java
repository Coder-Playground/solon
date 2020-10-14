package webapp.demo9_multiple_view;

import org.noear.solon.annotation.XController;
import org.noear.solon.annotation.XMapping;
import org.noear.solon.web.ModelAndView;
import org.noear.solon.web.XContext;
import org.noear.solon.web.XHandler;

@XMapping("/demo9/view/enjoy")
@XController
public class EnjoyView implements XHandler {
    @Override
    public void handle(XContext ctx) throws Throwable {
        ModelAndView model = new ModelAndView("enjoy.shtm");
        model.put("title","dock");
        model.put("msg","你好 world! in XController");

        ctx.render(model);
    }
}
