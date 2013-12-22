package pages;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;

public class CategoryPage extends FluentPage {
    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public void isAt() {
        assertThat(find("body.category-page")).hasSize(1);
    }
}
