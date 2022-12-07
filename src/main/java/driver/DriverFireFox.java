package driver;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static utils.OSValidateUtil.isUnix;
import static utils.OSValidateUtil.isWindows;

@Log4j2
public final class DriverFireFox extends DriverInitialize implements DriverCreate {
    private static final String CHROME_KEY = "webdriver.gecko.driver";
    private static final String WINDOWS_DRIVER = "src/main/resources/driver/geckodriver.exe";
    private static final String LINUX_DRIVER = "src/main/resources/driver/geckodriver";

    DriverFireFox() {
    }

    @Override
    public DriverSettings createDriver(Capabilities capabilities) {
        DriverSettings driverSettings = new DriverSettings();
        EventHandler eventDriverHandler = new EventHandler();
        log.info(DRIVER_CREATED, Browser.FIREFOX);
        if (isWindows()) {
            System.setProperty(CHROME_KEY, WINDOWS_DRIVER);
        } else if (isUnix()) {
            System.setProperty(CHROME_KEY, LINUX_DRIVER);
        }
        FirefoxDriver remoteDriver = new FirefoxDriver((FirefoxOptions) capabilities);
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(remoteDriver);
        eventFiringWebDriver.register(eventDriverHandler);
        log.info(DRIVER_INITIALIZED, Browser.FIREFOX);
        driverSettings.setEventFiringWebDriver(eventFiringWebDriver);
        return driverSettings;
    }
}
