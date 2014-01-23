package widgetpoc1;

import controllers.ReactiveShopController;
import play.api.templates.Html;
import play.libs.F;
import play.mvc.Result;
import widgets.Widget;
import widgets.staticexamplewidget.BlindTextWidget;

public class Controller extends ReactiveShopController {
    public static class PageData extends BasePageData {
        public String title;
        public Widget blindText = register(new BlindTextWidget());

        public PageData(String title) {
            this.title = title;
        }
    }

    //TODO refactor, maybe but some code in base class
    private static F.Promise<Result> render(final Html html, final PageData pageData) {
        final F.Promise<Html> htmlPromise = PageData.promise(pageData).map(new F.Function<PageData, Html>() {
            @Override
            public Html apply(PageData x) throws Throwable {
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
