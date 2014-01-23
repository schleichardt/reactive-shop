package widgets.staticexamplewidget;

import play.api.templates.Html;
import play.libs.F;
import widgets.BaseWidget;

public abstract class StaticWidget extends BaseWidget {
    private final Html html;

    protected StaticWidget(Html html) {
        this.html = html;
    }

    @Override
    public Html html() {
        return html;
    }

    @Override
    public F.Promise<Html> htmlPromise() {
        return F.Promise.pure(html());
    }
}
