package views.html;

import controllers.WebJarAssets$;
import controllers.routes;

public final class TemplateUtils {
    private TemplateUtils() {
    }

    public static String webJarAsset(final String asset) {
        final String location = WebJarAssets$.MODULE$.locate(asset);
        return routes.WebJarAssets.at(location).url();
    }
}
