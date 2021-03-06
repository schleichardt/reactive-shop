package filters;

import com.google.common.collect.ImmutableList;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.filters.expressions.FilterExpressions;
import io.sphere.client.shop.model.Category;
import sphere.Sphere;

public final class CategoryFilters {
    private CategoryFilters() {
    }

    public static FilterExpression byCategory(final String slug) {
        final Category category = Sphere.getInstance().categories().getBySlug(slug);
        return byCategory(category);
    }

    public static FilterExpression byCategory(final Category category) {
        return new FilterExpressions.CategoriesOrSubcategories(ImmutableList.of(category));
    }
}
