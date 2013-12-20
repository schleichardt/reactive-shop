package comparators;

import com.google.common.collect.ComparisonChain;
import io.sphere.client.shop.model.Category;

import java.util.Comparator;

/**
 * Utility class with comparators for {@link io.sphere.client.shop.model.Category}.
 */
public class CategoryComparators {
    private CategoryComparators() {
    }

    public static Comparator<Category> byName() {
        return new Comparator<Category>() {
            @Override
            public int compare(Category category, Category category2) {
                return ComparisonChain.start().compare(category.getName(), category2.getName()).result();
            }
        };
    }
}
