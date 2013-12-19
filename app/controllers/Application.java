package controllers;

import comparators.CategoryComparator;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.model.Category;
import io.sphere.client.shop.model.Product;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends ReactiveShopController {

    public static Promise<Result> index() {
        final Promise<SearchResult<Product>> searchResult = sphere().products().all().fetchAsync();
        final List<Category> categories = sphere().categories().getRoots(CategoryComparator.byName());
        return searchResult.map(new F.Function<SearchResult<Product>, Result>() {
            @Override
            public Result apply(SearchResult<Product> productSearchResult) throws Throwable {
                final List<Product> products = productSearchResult.getResults();
                return ok(index.render(products, categories));
            }
        });
    }
}
