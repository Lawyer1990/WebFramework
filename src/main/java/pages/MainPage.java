package pages;

import org.openqa.selenium.WebDriver;

import static configs.Config.APP_URL;

public class MainPage extends BasePage {

    private static final String CATALOG_BUTTON = "main.catalog.button";


    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage clickCatalogButton() {
        actions.click(CATALOG_BUTTON);
        return this;
    }

    public void open() {
        browserManager.navigateTo(APP_URL);
    }
}
