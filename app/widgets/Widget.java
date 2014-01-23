package widgets;

import play.api.templates.Html;
import play.libs.F;

public interface Widget {
    Html html();

    F.Promise<Html> htmlPromise();
}
