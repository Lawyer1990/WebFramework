package ui;

import driver.DriverFactory;
import driver.DriverInitialize;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pages.Onliner;

@Log4j2
public class BaseTest {
    private EventFiringWebDriver driver;
    protected Onliner onliner;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        driver = DriverFactory.getInstance(DriverInitialize.Browser.CHROME, DriverInitialize.DriverType.DEFAULT)
                .getEventFiringWebDriver();
        createData();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(ITestContext ctx) {
        driver.quit();
        log.info("FINISHING SUITE: " + ctx.getSuite().getName());
    }

    private void createData() {
        onliner = new Onliner(driver);
    }
}
