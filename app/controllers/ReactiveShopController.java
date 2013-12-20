package controllers;

import comparators.CategoryComparators;
import io.sphere.client.shop.model.Category;
import sphere.ShopController;

import java.util.List;

public class ReactiveShopController extends ShopController {
    protected static List<Category> rootCategories() {
        return sphere().categories().getRoots(CategoryComparators.byName());
    }
}
