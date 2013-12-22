import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import comparators.CategoryComparators;
import io.sphere.client.shop.model.Category;
import org.fest.assertions.Condition;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.junit.*;

import pages.CategoryPage;
import pages.StartPage;
import play.test.*;
import sphere.Sphere;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;

public class IntegrationTest extends WithBrowser {

    StartPage startPage;

    @Before
    public void setUp() {
        start();
        startPage = browser.createPage(StartPage.class);
    }

    @Test
    public void carouselPresent() {
        startPage.go();
        assertThat(browser.$(".carousel-inner img")).hasSize(3);
        assertThat(browser.$(".carousel-inner img").getAttributes("src")).satisfies(new Condition<List<?>>() {
            @Override
            public boolean matches(List<?> value) {
                return Iterables.all(value, new Predicate<Object>() {
                    @Override
                    public boolean apply(Object input) {
                        return input.toString().startsWith("http");
                    }
                });
            }
        });
    }

    @Test
    public void categoryHighlightsEnableVisitingTheCategory() {
        startPage.go();
        final CategoryPage categoryPage = startPage.clickCategoryHighlight(1);
        assertThat(browser.$(".products li")).hasSize().greaterThanOrEqualTo(1);
    }
}
