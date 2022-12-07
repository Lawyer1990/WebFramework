package elements;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static enums.AssertMessages.*;
import static configs.Config.UI_MAPPING_PATH;

/**
 * Creates an singleton object that manages page locators.
 */
@Log4j2
public final class UiMapping {

    private static volatile UiMapping instance;
    private static Properties properties = new Properties();
    private static volatile Map<String, String> locatorMap = new HashMap<>();

    private UiMapping() {
        loadDataFromFile();
        properties.forEach((key, value) -> locatorMap.put((String) key, (String) value));
    }

    public static synchronized UiMapping getInstance() {
        if (instance == null) {
            instance = new UiMapping();
            return instance;
        }
        return instance;
    }

    private static Properties loadDataFromFile() {
        File file = new File(UI_MAPPING_PATH);
        if (file.exists()) {
            try {
                properties.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            } catch (IOException e) {
                log.error(MSG_FAILED_LOAD_FILE, e);
            }
        } else {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                log.error("{} " + MSG_FILE_NOT_FOUND, UI_MAPPING_PATH, e);
            }
        }
        return properties;
    }

    public static String getValue(String key) {
        return locatorMap.get(key);
    }

    public static By ui(String key) {
        String[] partsOfLocators = new String[0];
        try {
            partsOfLocators = locatorMap.get(key).split("\"");
        } catch (NullPointerException e) {
            log.error(MSG_LOCATOR_NOT_SPECIFIED_IN_PROPERTIES.getValue(), key, e);
        }

        String findMethod = "";
        String locatorValue = "";
        try {
            findMethod = partsOfLocators[0];
            locatorValue = partsOfLocators[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error(e.getMessage(), e);
        }

        switch (findMethod) {
            case "id":
                return By.id(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "css":
                return By.cssSelector(locatorValue);
            case "name":
                return By.name(locatorValue);
            default:
                return By.xpath(locatorValue);

        }
    }

    public static By dynamicLocator(String locator, String input) {
        String[] partsOfLocators = new String[0];
        try {
            partsOfLocators = locatorMap.get(locator).split("\"");
        } catch (NullPointerException e) {
            log.error(MSG_LOCATOR_NOT_SPECIFIED_IN_PROPERTIES.getValue(), locator, e);
        }

        String findMethod = "";
        String locatorValue = "";
        try {
            findMethod = partsOfLocators[0];
            locatorValue = String.format(partsOfLocators[1], input);
            log.info("*** Concatenated locator *** = " + locatorValue);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error(e.getMessage(), e);
        }

        switch (findMethod) {
            case "id":
                return By.id(locatorValue);
            case "css":
                return By.cssSelector(locatorValue);
            case "name":
                return By.name(locatorValue);
            default:
                return By.xpath(locatorValue);
        }
    }
}
