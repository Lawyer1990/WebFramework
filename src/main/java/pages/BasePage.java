package pages;

import driver.BrowserManager;
import elements.WaitingWebElementActions;
import elements.WebElementActions;
import lombok.Getter;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

import static configs.Config.DRIVER_SLEEPS_MS;
import static configs.Config.IMPLICIT_WAIT;

public class BasePage {
    @Getter
    protected BrowserManager browserManager;
    protected WebElementActions baseActions;
    protected WaitingWebElementActions actions;
    protected WebDriverWait driverWait;
    private static final String LOADER_APP = "app.loaded";
    private static final String MODAL_STYLE_APP = "display: none";

    public BasePage(WebDriver driver) {
        driverWait = (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT),
                Duration.ofMillis(DRIVER_SLEEPS_MS))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        this.baseActions = new WebElementActions(driver, driverWait);
        this.actions = new WaitingWebElementActions(driver, driverWait, LOADER_APP, null);
        this.browserManager = new BrowserManager(driver);
    }
}
