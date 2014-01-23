package controllers;

import comparators.CategoryComparators;
import io.sphere.client.shop.model.Category;
import play.api.mvc.Codec;
import play.api.mvc.Results$;
import play.api.templates.Html;
import play.libs.F;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results;
import sphere.ShopController;

import java.util.List;

public class ReactiveShopController extends ShopController {
    protected static List<Category> rootCategories() {
        return sphere().categories().getRoots(CategoryComparators.byName());
    }


    protected static F.Promise<Result> render(final int statusCode, final F.Promise<Html> htmlPromise) {
        return htmlPromise.map(new F.Function<Html, Result>() {
            @Override
            public Result apply(final Html html) throws Throwable {
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
        });
    }
}
