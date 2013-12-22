package pages;

import org.fluentlenium.core.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import controllers.*;

public class StartPage extends FluentPage {
    public String getUrl() {
        return "/";//for other pages use reverse routes, e.g. routes.Application.login().url()
    }

    public void isAt() {
        assertThat(find("body.index")).hasSize(1);
    }

    public CategoryPage clickCategoryHighlight(final int index) {
        find("#category-highlights a.category-page", index).click();
        final CategoryPage categoryPage = new CategoryPage(getDriver());
        categoryPage.isAt();
        return categoryPage;
    }
}
