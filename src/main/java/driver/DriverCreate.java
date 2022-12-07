package driver;

import org.openqa.selenium.Capabilities;

public interface DriverCreate {
    DriverInitialize.DriverSettings createDriver(Capabilities capabilities);
}
