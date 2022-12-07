package configs;

import java.io.File;

public class Config {

    public static final String APP_URL = "https://www.onliner.by/";
    public static final String SELENOID_URL = "http://192.168.99.102:4444/";
    public static final String SELENOID_BROWSER_VERSION = "101.0";

    public static final int IMPLICIT_WAIT = 10;
    public static final int DRIVER_SLEEPS_MS = 50;
    public static final int EMAIL_ARRIVING_WAIT = 60;

    public static final String UI_MAPPING_PATH = System.getProperty("user.dir") + "/src/main/resources/uiLocators.properties";

    public static final String PATH_TO_DOWNLOADED = "src" + File.separator + "main" + File.separator + "resources" +
            File.separator + "downloadedFiles";

    public static final String AUTOTESTS_PREFIX = "adt";
}