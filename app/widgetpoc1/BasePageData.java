package widgetpoc1;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import concurrent.Promises;
import play.api.libs.concurrent.Promise;
import play.api.templates.Html;
import play.libs.F;
import widgets.Widget;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class BasePageData {
    protected final List<Widget> widgets = new LinkedList<Widget>();

    protected Widget register(final Widget widget) {
        widgets.add(widget);
        return widget;
    }


    public static <T extends BasePageData> F.Promise<T> promise(final T pageData) {
        final List<F.Promise<Html>> htmlPromises = Lists.transform(pageData.widgets, new Function<Widget, F.Promise<Html>>() {
            @Override
            public F.Promise<Html> apply(final Widget widget) {
                return widget.htmlPromise();
            }
        });

        return Promises.sequence(htmlPromises).map(new F.Function<List<Html>, T>() {
            @Override
            public T apply(List<Html> htmls) throws Throwable {
                return pageData;
            }
        });
    }
}
