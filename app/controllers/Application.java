package controllers;

import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.model.Product;
import play.*;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.*;

import sphere.ShopController;
import views.html.*;

import java.util.List;

public class Application extends ShopController {

    public static Promise<Result> index() {
        final Promise<SearchResult<Product>> searchResult = sphere().products().all().fetchAsync();
        return searchResult.map(new F.Function<SearchResult<Product>, Result>() {
            @Override
            public Result apply(SearchResult<Product> productSearchResult) throws Throwable {
                final List<Product> products = productSearchResult.getResults();
                return ok(index.render(products));
            }
        });
    }
}
