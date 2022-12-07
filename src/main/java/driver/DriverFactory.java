package driver;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public final class DriverFactory {
    private static final String DRIVER_CREATED = "DRIVER IS CREATED: ";
    private static final String DRIVER_IS_NOT_CREATED = "DRIVER IS NOT CREATED";
    private static final List<DriverInitialize.DriverSettings> driverSettingsList = new ArrayList<>();

    private DriverFactory() {
    }

    public static DriverInitialize.DriverSettings getDriver(DriverInitialize.DriverType driverType) {
        return driverSettingsList.stream().filter(s -> s.getType().equals(driverType)).findFirst().get();
    }

    public static DriverInitialize.DriverSettings getInstance(DriverInitialize.Browser browserName, DriverInitialize.DriverType driverType) {
        DriverInitialize.DriverSettings driverSetting = new DriverInitialize.DriverSettings();
        if (driverSettingsList.stream().noneMatch(s -> s.getType().equals(driverType))) {
            switch (browserName) {
                case CHROME: {
                    driverSetting = new DriverChrome()
                            .createDriver(InitOptions.getCapabilities(driverType, browserName));
                    break;
                }
                case FIREFOX: {
                    driverSetting = new DriverFireFox()
                            .createDriver(InitOptions.getCapabilities(driverType, browserName));
                    break;
                }
                case REMOTE: {
                    driverSetting = new DriverRemote()
                            .createDriver(InitOptions.getCapabilities(driverType, browserName));
                    break;
                }
            }
        } else return driverSettingsList.stream().filter(s -> s.getType().equals(driverType)).findFirst().get();
        if (driverSetting.getEventFiringWebDriver() != null) {
            driverSetting.setType(driverType);
            driverSettingsList.add(driverSetting);
            log.info(DRIVER_CREATED + browserName);
        } else log.error(DRIVER_IS_NOT_CREATED);
        return driverSetting;
    }

    public static void setDriverNull(DriverInitialize.DriverType driverType) {
        driverSettingsList.removeIf(s -> s.getType().equals(driverType));
    }
}
