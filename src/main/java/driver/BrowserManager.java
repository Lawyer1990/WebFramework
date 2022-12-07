package driver;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import utils.WaitUtil;

import java.util.ArrayList;
import java.util.Set;

public class BrowserManager {

    private WebDriver driver;

    public BrowserManager(WebDriver driver) {
        this.driver = driver;
    }

    public void closeTab() {
        driver.close();
    }

    public void closeTab(int number) {
        WaitUtil.setWait(1);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(number)).close();
    }

    public void closeAllTabs() {
        driver.quit();
    }

    public Set<Cookie> getBrowserCookie() {
        return driver.manage().getCookies();
    }

    public void addCookies(Set<Cookie> cookies) {
        cookies.forEach(s -> {
            driver.manage().addCookie(s);
        });
    }

    public void addCookie(Cookie cookie) {
        driver.manage().addCookie(cookie);
    }

    public void reloadPage() {
        driver.navigate().refresh();
    }

    public void navigateTo(String urlPart) {
        driver.navigate().to(urlPart);
        WaitUtil.setWait(2);
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void switchToAnotherTab(int tabNumber) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        int count = 10;
        while (count-- > 0 && tabs.size() <= 1) {
            WaitUtil.setWaitMs(500);
            tabs = new ArrayList<>(driver.getWindowHandles());
        }
        driver.switchTo().window(tabs.get(tabNumber));
    }

    public void switchToSecondTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        int count = 10;
        while (count-- > 0 && tabs.size() == 1) {
            WaitUtil.setWaitMs(500);
            tabs = new ArrayList<>(driver.getWindowHandles());
        }
        driver.switchTo().window(tabs.get(1));
    }

    public void switchToDefaultTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void switchToAnotherTab(String handle) {
        WaitUtil.setWait(1);
        driver.switchTo().window(handle);
    }


    public void closeExtraTabs() {
        try {
            String currentHandle = driver.getWindowHandle();
            ArrayList<String> handles = new ArrayList<>(driver.getWindowHandles());
            handles.stream()
                    .filter(s -> !s.equals(currentHandle))
                    .forEach(s -> {
                        switchToAnotherTab(s);
                        closeTab();
                        switchToAnotherTab(currentHandle);
                    });
        } catch (NoSuchWindowException ex) {
            System.err.print(ex);
        }
    }

    public int getNumberOfTabs() {
        WaitUtil.setWait(1);
        return driver.getWindowHandles().size();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void switchToFrame(String frame){
        driver.switchTo().frame(frame);
    }

    public void switchToDefaultPage(){
        driver.switchTo().defaultContent();
    }
}
