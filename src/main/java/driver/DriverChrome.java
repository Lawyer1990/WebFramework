package driver;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

import static utils.OSValidateUtil.isUnix;
import static utils.OSValidateUtil.isWindows;

@Log4j2
public final class DriverChrome extends DriverInitialize implements DriverCreate {
    private static final String CHROME_KEY = "webdriver.chrome.driver";
    private static final String WINDOWS_DRIVER = "src/main/resources/driver/chromedriver.exe";
    private static final String LINUX_DRIVER = "src/main/resources/driver/chromedriver";

    DriverChrome() {
    }

    @Override
    public DriverSettings createDriver(Capabilities capabilities) {
        DriverSettings driverSettings = new DriverSettings();
        EventHandler eventDriverHandler = new EventHandler();
        log.info(DRIVER_CREATED, Browser.CHROME);
        if (isWindows()) {
            System.setProperty(CHROME_KEY, WINDOWS_DRIVER);
        } else if (isUnix()) {
            System.setProperty(CHROME_KEY, LINUX_DRIVER);
        }
        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
        ChromeDriver remoteDriver = new ChromeDriver(driverService, (ChromeOptions) capabilities);
        remoteDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        SessionId sessionDriverId = remoteDriver.getSessionId();
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(remoteDriver);
        eventFiringWebDriver.register(eventDriverHandler);
        allowHeadlessDownload(driverService, sessionDriverId);
        log.info(DRIVER_INITIALIZED, Browser.CHROME);
        driverSettings.setEventFiringWebDriver(eventFiringWebDriver);
        return driverSettings;
    }
}
