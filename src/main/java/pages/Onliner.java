package pages;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

@Log4j2
@Getter
public class Onliner extends BasePage {
    private MainPage mainPage;
    private CatalogPage catalogPage;
    private SalePage salePage;

    public Onliner(WebDriver driver) {
        super(driver);
        mainPage = new MainPage(driver);
        catalogPage = new CatalogPage(driver);
        salePage = new SalePage(driver);
    }


    public Onliner openMainPage() {
        getMainPage().getBrowserManager().clearCookies();
        log.info("Cookies after cleanup: ", getMainPage().getBrowserManager().getAllCookies());
        getMainPage().open();
        log.info("Cookies after open login page: ", getMainPage().getBrowserManager().getAllCookies());
        return this;
    }
}
