package widgetpoc1;

import controllers.ReactiveShopController;
import play.api.mvc.Codec;
import play.api.mvc.Results$;
import play.api.templates.Html;
import play.libs.F;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results;

public class Controller extends ReactiveShopController {
    public static class PageData {
        public String title;

        public PageData(String title) {
            this.title = title;
        }

        public F.Promise<PageData> promise() {
            return F.Promise.pure(this);
        }
    }

    private static F.Promise<Result> render(final Html html, final PageData pageData) {
        final F.Promise<Html> htmlPromise = pageData.promise().map(new F.Function<PageData, Html>() {
            @Override
            public Html apply(PageData pageData) throws Throwable {
                return widgetpoc1.html.Controller.apply(html, pageData);
            }
        });
        return render(200, htmlPromise);
    }

    public static F.Promise<Result> index() {
        final PageData pageData = new PageData("the page title for index");
        return render(widgetpoc1.html.index.apply("additional data"), pageData);
    }
}
