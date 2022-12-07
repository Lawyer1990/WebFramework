package driver;


import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

import static configs.Config.PATH_TO_DOWNLOADED;
import static configs.Config.SELENOID_BROWSER_VERSION;

public final class InitOptions {
    private InitOptions() {
    }

    public static Capabilities getCapabilities(DriverInitialize.DriverType driverType,
                                               DriverInitialize.Browser browserName) {
        Capabilities capabilities = null;
        switch (driverType) {
            case DEFAULT: {
                switch (browserName) {
                    case CHROME: {
                        capabilities = getChromeOptions(true);
                        break;
                    }
                    case FIREFOX: {
                        capabilities = getFirefoxOptions();
                        break;
                    }
                    case REMOTE: {
                        capabilities = getRemoteDriverOptions(
                                DriverInitialize.Browser.CHROME.getValue(),
                                SELENOID_BROWSER_VERSION,
                                true);
                        break;
                    }
                }
                break;
            }
        }
        return capabilities;
    }

    private static ChromeOptions getChromeOptions(boolean downloadPdf) {
        ChromeOptions optionsChrome = new ChromeOptions();
        optionsChrome.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", PATH_TO_DOWNLOADED);
        prefs.put("plugins.always_open_pdf_externally", downloadPdf);
        optionsChrome.setExperimentalOption("prefs", prefs);
        //optionsChrome.addArguments("--headless");
        optionsChrome.addArguments("--window-size=1920,1080");
        optionsChrome.addArguments("--incognito");
        optionsChrome.addArguments("--disable-popup-blocking");
        optionsChrome.addArguments("--disable-extensions");
        optionsChrome.addArguments("--disable-infobars");
        optionsChrome.addArguments("--disable-gpu");
        optionsChrome.addArguments("--no-sandbox");
        optionsChrome.addArguments("--ignore-certificate-errors");
        optionsChrome.setAcceptInsecureCerts(true);
        return optionsChrome;
    }

    private static ChromeOptions getRemoteDriverOptions(String browserRemoteName, String browserRemoteVersion,
                                                        boolean downloadPdf) {
        ChromeOptions capabilities = new ChromeOptions();
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("enableVNC", true);
            put("enableVideo", false);
            put("sessionTimeout", "3h");
        }};
        capabilities.setCapability("browserName", browserRemoteName.toLowerCase());
        capabilities.setCapability("browserVersion", browserRemoteVersion);
        capabilities.setAcceptInsecureCerts(true);
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("plugins.always_open_pdf_externally", downloadPdf);
        capabilities.setExperimentalOption("prefs", prefs);
        capabilities.setCapability("selenoid:options", map);
        return capabilities;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions optionsFirefox = new FirefoxOptions();
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        optionsFirefox.setBinary(firefoxBinary);
        optionsFirefox.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        optionsFirefox.addPreference("browser.private.browsing.autostart", true);
        return optionsFirefox;
    }
}