import comparators.CategoryComparators;
import io.sphere.client.shop.model.Category;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.junit.*;

import play.test.*;
import sphere.Sphere;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;

public class IntegrationTest extends WithBrowser {

    @Before
    public void prepare() {
        start();
    }

    @Test
    public void categoriesPresent() {
        final List<Category> categories = Sphere.getInstance().categories().getRoots(CategoryComparators.byName());
        assertThat(categories.size()).isGreaterThanOrEqualTo(3);
        browser.goTo("/");
        final FluentWebElementAssert categoriesDisplayAssertion = assertThat(browser.$("#categories", 0));
        for (Category c : categories) {
            categoriesDisplayAssertion.hasText(c.getName());
        }
    }

    @Test
    public void categoryPageNavi() {
        browser.goTo("/");
        browser.$("#categories li a", 0).click();
        browser.await().until("body.category-page").isPresent();
    }
}
