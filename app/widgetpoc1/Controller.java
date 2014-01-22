package widgetpoc1;

import controllers.ReactiveShopController;
import play.api.mvc.Codec;
import play.api.mvc.Results$;
import play.api.templates.Html;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results;

public class Controller extends ReactiveShopController {
    public static class PageData {
        public String title;

        public PageData(String title) {
            this.title = title;
        }

        public Html main(final Html html) {
            return widgetpoc1.html.Controller.render(html, this);
        }
    }

    public static Result index() {
        final PageData pageData = new PageData("the page title for index");
        return render(widgetpoc1.html.index.render(pageData, "additional data"));
    }
}
