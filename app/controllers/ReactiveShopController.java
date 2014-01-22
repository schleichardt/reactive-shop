package controllers;

import comparators.CategoryComparators;
import io.sphere.client.shop.model.Category;
import play.api.mvc.Codec;
import play.api.mvc.Results$;
import play.api.templates.Html;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results;
import sphere.ShopController;

import java.util.List;

public class ReactiveShopController extends ShopController {
    protected static List<Category> rootCategories() {
        return sphere().categories().getRoots(CategoryComparators.byName());
    }

    protected static Result render(final Html html) {
        return render(200, html);
    }

    protected static Result render(final int statusCode, final Html html) {
        final Content content = new Content() {
            @Override
            public String body() {
                return html.body();
            }

            @Override
            public String contentType() {
                return "text/html";
            }
        };
        return new Results.Status(Results$.MODULE$.Status(statusCode), content, Codec.utf_8());
    }
}
