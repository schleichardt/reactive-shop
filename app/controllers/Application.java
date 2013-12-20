package controllers;

import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.model.Product;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.*;

import static filters.CategoryFilters.byCategory;

import sphere.SearchRequest;
import views.html.*;

import java.util.List;

public class Application extends ReactiveShopController {

    public static Promise<Result> index() {
        final Promise<SearchResult<Product>> searchResult = sphere().products().all().fetchAsync();
        return searchResult.map(new F.Function<SearchResult<Product>, Result>() {
            @Override
            public Result apply(SearchResult<Product> productSearchResult) throws Throwable {
                final List<Product> products = productSearchResult.getResults();
                return ok(index.render("index", products, rootCategories()));
            }
        });
    }

    //TODO test unhappy paths
    public static Promise<Result> category(final String slug) {
        final SearchRequest<Product> searchRequest = sphere().products().filter(byCategory(slug));
        return searchRequest.fetchAsync().map(new F.Function<SearchResult<Product>, Result>() {
            @Override
            public Result apply(SearchResult<Product> productSearchResult) throws Throwable {
                return ok(index.render("category-page", productSearchResult.getResults(), rootCategories()));
            }
        });
    }
}
