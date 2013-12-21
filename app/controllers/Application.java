package controllers;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import io.sphere.client.ProductSort;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.model.Category;
import io.sphere.client.shop.model.Product;
import play.libs.F;
import play.libs.F.Promise;
import play.libs.F.Tuple;
import play.mvc.*;

import static com.google.common.collect.Lists.transform;
import static filters.CategoryFilters.byCategory;
import static comparators.CategoryComparators.byOrderHint;

import sphere.SearchRequest;
import java.util.List;

public class Application extends ReactiveShopController {

    //TODO cache
    public static Promise<Result> index() {
        final int maxSizeCarouselElements = 3;
        final Promise<SearchResult<Product>> carouselProductPromise = expensiveProducts(maxSizeCarouselElements);
        final int elementsInProductAds = 3;
        final List<Category> importantCategories = sphere().categories().getRoots(byOrderHint());
        final List<Category> displayedImportantCategories = importantCategories.subList(0, Math.min(elementsInProductAds + 1, importantCategories.size()));
        final List<Promise<Tuple<Category, Product>>> productsPromise = transform(displayedImportantCategories, new Function<Category, Promise<Tuple<Category, Product>>>() {
            @Override
            public Promise<Tuple<Category, Product>> apply(final Category category) {
                final Promise<SearchResult<Product>> searchResultPromise = sphere().products().all().filter(byCategory(category)).sort(ProductSort.price.desc).pageSize(1).fetchAsync();
                return searchResultPromise.map(new F.Function<SearchResult<Product>, Tuple<Category, Product>>() {
                    @Override
                    public Tuple<Category, Product> apply(SearchResult<Product> productSearchResult) throws Throwable {
                        return new Tuple(category, productSearchResult.getResults().get(0));//TODO this can fail if there is no product in this category
                    }
                });
            }
        });
        final Promise<List<Tuple<Category, Product>>> prodc = Promise.sequence(Iterables.toArray(productsPromise, Promise.class)); //TODO it should not be unchecked!
        return prodc.flatMap(new F.Function<List<Tuple<Category, Product>>, Promise<Result>>() {
            @Override
            public Promise<Result> apply(final List<Tuple<Category, Product>> categorySnippets) throws Throwable {
                return carouselProductPromise.map(new F.Function<SearchResult<Product>, Result>() {
                    @Override
                    public Result apply(SearchResult<Product> productSearchResult) throws Throwable {
                        return ok(views.html.index.render(productSearchResult.getResults(), categorySnippets));
                    }
                });
            }
        });
    }

    private static Promise<SearchResult<Product>> expensiveProducts(int maxSize) {
        return sphere().products().all().sort(ProductSort.price.desc).pageSize(maxSize).fetchAsync();
    }

    public static Promise<Result> pagedProducts() {
        final Promise<SearchResult<Product>> searchResult = sphere().products().all().fetchAsync();
        return searchResult.map(new F.Function<SearchResult<Product>, Result>() {
            @Override
            public Result apply(SearchResult<Product> productSearchResult) throws Throwable {
                final List<Product> products = productSearchResult.getResults();
                return ok(views.html.products.render("index", products, rootCategories()));
            }
        });
    }

    //TODO test unhappy paths
    public static Promise<Result> category(final String slug) {
        final SearchRequest<Product> searchRequest = sphere().products().filter(byCategory(slug));
        return searchRequest.fetchAsync().map(new F.Function<SearchResult<Product>, Result>() {
            @Override
            public Result apply(SearchResult<Product> productSearchResult) throws Throwable {
                return ok(views.html.products.render("category-page", productSearchResult.getResults(), rootCategories()));
            }
        });
    }
}
