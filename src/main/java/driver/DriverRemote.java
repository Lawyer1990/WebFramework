package driver;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static configs.Config.SELENOID_URL;

@Log4j2
public final class DriverRemote extends DriverInitialize implements DriverCreate {
    private static final String REMOTE_PATH = SELENOID_URL + "wd/hub";
    private static final String SESSION_ID = "Session ID is: ";
    private static final String SELENOID_ERROR = "---- SELENOID ERROR ----  ";
    private static final String DRIVER_IS_NOT_INITIALIZED = "---- DRIVER IS NOT INITIALIZED ----  ";

    DriverRemote() {
    }

    @Override
    public DriverSettings createDriver(Capabilities capabilities) {
        DriverSettings driverSettings = new DriverSettings();
        RemoteWebDriver remoteDriver = null;
        EventFiringWebDriver eventFiringWebDriver = null;
        EventHandler eventDriverHandler = new EventHandler();
        log.info(DRIVER_CREATED, Browser.REMOTE);

        try {
            remoteDriver = new RemoteWebDriver(
                    URI.create(REMOTE_PATH).toURL(),
                    capabilities);
            remoteDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            remoteDriver.setFileDetector(new LocalFileDetector());
            remoteDriver.manage().window().setSize(new Dimension(1920, 1080));
            eventFiringWebDriver = new EventFiringWebDriver(remoteDriver);
            eventFiringWebDriver.register(eventDriverHandler);
            SessionId sessionDriverId = ((RemoteWebDriver) eventFiringWebDriver.getWrappedDriver()).getSessionId();
            log.info(DRIVER_INITIALIZED, Browser.REMOTE);
            log.info(SESSION_ID + sessionDriverId.toString());
        } catch (Exception e) {
            log.error(SELENOID_ERROR, e);
        }
        if (remoteDriver == null) {
            throw new NullPointerException(DRIVER_IS_NOT_INITIALIZED);
        }
        driverSettings.setEventFiringWebDriver(eventFiringWebDriver);
        return driverSettings;
    }
}
