package pages;

import org.openqa.selenium.WebDriver;

public class CatalogPage extends BasePage {

    private static final String ELECTRONIC_BUTTON = "catalog.electronic.button";
    private static final String TABLE_LEFT_VIEW = "catalog.left.table.view";
    private static final String TABLE_RIGHT_VIEW = "catalog.right.table.view";

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public enum LeftCatalog {
        MOBILE_PHONES(0);

        private final int value;

        LeftCatalog(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum RightCatalog {
        SMARTPHONES(0);

        private final int value;

        RightCatalog(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public CatalogPage clickElectronicButton() {
        actions.click(ELECTRONIC_BUTTON);
        return this;
    }

    public CatalogPage clickLeftCatalog(LeftCatalog value) {
        actions.getElements(TABLE_LEFT_VIEW).get(value.getValue()).click();
        return this;
    }

    public CatalogPage clickRightCatalog(RightCatalog value) {
        actions.getElements(TABLE_RIGHT_VIEW).get(value.getValue()).click();
        return this;
    }
}
