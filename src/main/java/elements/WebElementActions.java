package elements;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static elements.ElementAttribute.*;

@Log4j2
public class WebElementActions {

    protected WebDriverWait wait;
    protected WebDriver driver;
    protected Robot robot;

    public WebElementActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        UiMapping.getInstance();
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            log.info("Can not click a key");
        }
    }

    private void setWaitModal(int seconds) {
        WaitUtil.setWait(seconds);
    }

    private void setWaitModal(String waitingLocator) {
        wait.ignoring(java.util.NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(TimeoutException.class);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(UiMapping.ui(waitingLocator)));
    }

    public void waitModal(String waitingLocator, String waitingAttribute) {
        setWaitModal(waitingLocator);
        wait.until(ExpectedConditions.attributeContains(UiMapping.ui(waitingLocator), STYLE.getValue(), waitingAttribute));
    }

    public void waitModal(String waitingLocator) {
        setWaitModal(waitingLocator);
    }

    //TODO DELETE RIGHT AFTER KNOW LOCATOR OF LOADING PAGE
    public void waitModal(int seconds) {
        setWaitModal(seconds);
    }

    /**
     * Click on an element
     */
    @Step("Click on an element [{locator.}]")
    public void click(String locator) {
        WebElement wel = waitToBeClickable(locator);
        wel.click();
    }

    @Step("Click on an element [{element}]")
    public void click(WebElement element) {
        waitUntilAppear(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    /**
     * Click on an element
     */
    @Step("Click on an element [{locator}]")
    public void click(WebElement element, String locator) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        new Actions(driver).moveToElement(element).perform();
        element.click();
    }

    /**
     * Click on an element with offset
     *
     * @param locator
     * @param element
     * @param offset
     */
    @Step("Click on an element in array [{locator}]")
    public void click(String locator, String element, int offset) {
        waitUntilAppear(locator);
        WebElement el = getElement(locator, element, offset);
        wait.until(ExpectedConditions.elementToBeClickable(el));
        new Actions(driver).moveToElement(el).build().perform();
        el.click();
    }

    @Step("Insert [{text}] to input field [{locator}]")
    public void input(String locator, String text) {
        WebElement wel = waitToBeClickable(locator);
        wel.clear();
        wel.sendKeys(text);
    }

    @Step("Insert [{text}] to input field [{element}]")
    public void input(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    @Step("Insert [{text}] to input field [{locator}]")
    public void inputNoClear(String locator, String text) {
        WebElement wel = waitToBeClickable(locator);
        assert wel != null;
        wel.sendKeys(text);
    }

    @Step("Select from dropdown [{locator}] by value [{value}]")
    public void select(String locator, String value) {
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        drp.selectByValue(value);
    }

    @Step("Select from dropdown [{element}] by value [{value}]")
    public void select(WebElement element, String value) {
        Select drp = new Select(element);
        drp.selectByValue(value);
    }

    @Step("Select from dropdown [{locator}] by value [{value}]")
    public void selectByValueWait(String locator, String value) {
        WebElement wel = waitToBeClickable(locator);
        Select drp = new Select(wel);
        drp.selectByValue(value);
    }

    /**
     * Select option by text
     *
     * @param locator
     * @param text
     */

    @Step("Select from dropdown [{locator}] by text [{text}]")
    public void selectByText(String locator, String text) {
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        drp.selectByVisibleText(text);
    }

    @Step("Select from dropdown [{locator}] by text contains [{text}]")
    public void selectByContainsText(String locator, String text) {
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        List<WebElement> options = drp.getOptions();
        for (WebElement option : options) {
            if (option.getText().contains(text)) {
                drp.selectByVisibleText(option.getText());
                break;
            }
        }
    }

    @Step("Select from dropdown [{locator}] by text [{text}]")
    public void selectByTextWait(String locator, String text) {
        WebElement elem = waitToBeClickable(locator);
        Select drp = new Select(elem);
        drp.selectByVisibleText(text);
    }

    @Step("Select element [{locator}]")
    public void select(WebElement element, String locator, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        new Select(element).selectByValue(text);
    }

    /**
     * Get selected value
     *
     * @param locator
     * @return value
     */
    public String getSelectedValue(String locator) {
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        return drp.getFirstSelectedOption().getText();
    }

    public String getSelectedValue(WebElement element) {
        Select drp = new Select(element);
        return drp.getFirstSelectedOption().getText();
    }

    /**
     * Get select values
     *
     * @param locator
     * @return value
     */
    public List<String> getSelectValues(String locator) {
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        Select drp = new Select(wel);
        return drp.getOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getSelectValues(WebElement element) {
        Select drp = new Select(element);
        return drp.getOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get exact text from select element
     *
     * @param locator
     * @param text
     * @return value
     */
    public String getExactTextFromDropdown(String locator, String text) {
        List list = getSelectValues(locator);
        for (Object l : list) {
            if (l.toString().contains(text))
                return l.toString();
        }
        return StringUtils.SPACE;
    }

    /**
     * Get input value
     *
     * @param locator
     * @return value
     */
    public String getInputValue(String locator) {
        WebElement wel = driver.findElement(UiMapping.ui(locator));
        return wel.getAttribute(VALUE.getValue());
    }

    public List<WebElement> getChilds(WebElement parent, String locator) {
        return parent.findElements(By.xpath(locator));
    }

    public WebElement getChild(WebElement parent, String locator) {
        WebElement element = null;
        try {
            element = parent.findElement(By.xpath(locator));
        } catch (Exception e) {
            log.error("Element is not found", e.getMessage());
        }
        return element;
    }

    /**
     * Get web element from array with offset
     *
     * @param locator
     * @param element
     * @param offset
     * @return Web element
     */
    public WebElement getElement(String locator, String element, int offset) {
        int nameIndex = -1;
        List<WebElement> wels = getElements(locator);
        for (WebElement wel : wels) {
            if (wel.getText().startsWith(element)) {
                nameIndex = wels.indexOf(wel);
            }
        }
        WebElement el = null;
        if (nameIndex != -1) {
            if (offset >= 0) {
                el = wels.get(nameIndex + Math.abs(offset));
            } else {
                el = wels.get(nameIndex - Math.abs(offset));
            }
        }
        return el;
    }

    /**
     * Get web element
     *
     * @param locator
     * @return Web element
     */
    public WebElement getElement(String locator) {
        return driver.findElement(UiMapping.ui(locator));
    }

    /**
     * Get web element with dynamic locator (empty locator template concatenated with dynamic input value)
     *
     * @param locator
     * @param input
     * @return Web element
     */

    public WebElement getElementWithDynamicLocator(String locator, String input) {
        return driver.findElement(UiMapping.dynamicLocator(locator, input));
    }

    public List<WebElement> getElementsWithDynamicLocator(String locator, String input) {
        return driver.findElements(UiMapping.dynamicLocator(locator, input));
    }

    /**
     * Get list of web elements
     *
     * @param locator
     * @return list of WebElements
     */
    public List<WebElement> getElements(String locator) {
        return driver.findElements(UiMapping.ui(locator));
    }

    public List<WebElement> getElementsByXpath(String locator) {
        return driver.findElements(By.xpath(locator));
    }

    @Step("Close Alert modal window in Media center")
    public void closeErrorAlertNewWindow() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.switchTo().window(tabs.get(0));
        executeJsScript("alert(\"Hello world!\");");
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }


    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public List<String> getElementsText(String locator) {
        return getElements(locator).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getElementsInnerHtml(String locator) {
        return getElements(locator).stream()
                .map(s -> s.getAttribute("innerHTML"))
                .collect(Collectors.toList());
    }

    public String getElementText(String locator, String element, int offset) {
        return getElement(locator, element, offset).getText();
    }

    public String getElementsText(WebElement element) {
        return element.getText();
    }

    public File takeScreenShot(String locator) {
        return driver.findElement(UiMapping.ui(locator)).getScreenshotAs(OutputType.FILE);
    }

    public String getInnerHTML(WebElement element) {
        return element.getAttribute("innerHTML");
    }

    public String getElementsAttribute(String locator, String attr) {
        return driver.findElement(UiMapping.ui(locator)).getAttribute(attr);
    }

    public String getElementsAttribute(WebElement el, String attr) {
        return el == null ? StringUtils.EMPTY : el.getAttribute(attr);
    }

    /**
     * Checks that element can be clicked
     *
     * @param locator
     * @return
     */
    public boolean isClickable(String locator) {
        if (isPresent(locator)) {
            WebElement wel = getElement(locator);
            return isClickable(wel);
        }
        return false;
    }

    public boolean isClickable(String locator, String element, int offset) {
        WebElement el = getElement(locator, element, offset);
        return isClickable(el);
    }

    /**
     * Checks is page element clickable
     *
     * @param element
     * @return
     */
    public boolean isClickable(WebElement element) {
        return element.isEnabled() && element.isDisplayed();
    }

    /**
     * Checks that element checkbox is ticked
     *
     * @param locator
     * @return
     */
    public boolean isChecked(String locator) {
        waitUntilAppear(locator);
        return getElement(locator).isSelected();
    }

    public boolean isChecked(WebElement element) {
        return element.isSelected();
    }

    public boolean isChecked(String locator, String element, int offset) {
        WebElement el = getElement(locator, element, offset);
        return el.isSelected();
    }

    public boolean isCheckedAttribute(String locator) {
        return getElement(locator).getAttribute(CHECKED.getValue()) != null;
    }

    /**
     * Checks that element is present on page
     *
     * @param locator
     * @return
     */
    public boolean isPresent(String locator) {
        return !getElements(locator).isEmpty();
    }

    public boolean isPresent(List<WebElement> webElements) {
        List<Boolean> list = new ArrayList<>();
        webElements.forEach(s -> {
            list.add(s.isDisplayed());
        });
        if (list.stream().anyMatch(s -> s)) return true;
        else return false;
    }

    public boolean isDisplayed(String locator) {
        return getElement(locator).isDisplayed();
    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public boolean isPresent(WebElement parent, String locator) {
        return (parent.findElements(By.xpath(locator)).size() != 0);
    }

    public boolean isPresentDynamic(String locator, String input) {
        return !getElementsWithDynamicLocator(locator, input).isEmpty();
    }

    public boolean isNotPresent(String locator) {
        return driver.findElements(UiMapping.ui(locator)).size() == 0;
    }

    public boolean isEnabled(String locator) {
        return getElement(locator).isEnabled();
    }

    public boolean isEnabled(WebElement element) {
        return element.isEnabled();
    }

    /**
     * Focuses to element on page using locator
     *
     * @param locator
     * @return
     */

    @Step("Move cursor to locator [{locator}]")
    public void moveToElement(String locator) {
        waitUntilAppear(locator);
        WebElement wel = getElement(locator);
        moveToElement(wel);
    }

    /**
     * Focuses to element on page
     *
     * @param element
     * @return
     */

    @Step("Move cursor to element [{element}]")
    public void moveToElement(WebElement element) {
        Actions act = new Actions(driver);
        act.moveToElement(element).build().perform();
    }

    @Step("Move cursor to element [{elem}] by X={x}, Y={y}")
    public void moveByOffset(WebElement elem, int x, int y) {
        Actions act = new Actions(driver);
        act.moveToElement(elem, x, y).click().build().perform();
    }

    @Step("Move cursor to locator [{locator}] by X={x}, Y={y}")
    public void moveByOffset(String locator, int x, int y) {
        WebElement elem = getElement(locator);
        moveByOffset(elem, x, y);
    }

    @Step("Move cursor to locator [{locator}] by X={x}, Y={y}")
    public void moveToElement(String locator, int x, int y) {
        WebElement elem = driver.findElement(UiMapping.ui(locator));
        Actions act = new Actions(driver);
        act.moveToElement(elem, x, y).perform();
        WaitUtil.setWait(1);
        act.click().build().perform();
    }

    @Step("Move cursor to position X={x}, Y={y}")
    public void moveByOffset(int x, int y) {
        Actions act = new Actions(driver);
        act.moveByOffset(x, y).build().perform();
        WaitUtil.setWait(1);
        act.click().build().perform();
    }

    /**
     * Clear input field on page
     *
     * @param locator
     */

    @Step("Clear field [{locator}]")
    public void clearField(String locator) {
        WebElement wel = getElement(locator);
        wel.clear();
    }

    public void doubleClick(String locator, String element, int offset) {
        Actions act = new Actions(driver);
        act.doubleClick(getElement(locator, element, offset)).perform();
    }

    @Step("Scroll horizontal line by locator [{locator}]")
    public void scrollToElement(String locator) {
        WebElement element = getElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean waitUntilDisplayed(String locator) {
        wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
        return getElement(locator).isDisplayed();
    }

    public boolean waitUntilVisible(String locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(UiMapping.ui(locator)));
        return getElement(locator).isDisplayed();
    }

    public boolean waitUntilClickable(String locator) {
        wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
        return getElement(locator).isDisplayed();
    }

    public boolean waitUntilDisappear(String locator) {
        int count = 60;
        while (isPresent(locator) && isDisplayed(locator) && count-- > 0) {
            WaitUtil.setWait(1);
        }
        return count != 0;
    }

    public boolean waitUntilAppear(String locator) {
        int count = 45;
        while (getElements(UiMapping.ui(locator)).isEmpty() && count-- > 0) {
            WaitUtil.setWaitMs(500);
        }
        if (!getElements(UiMapping.ui(locator)).isEmpty()) {
            while (!isDisplayed(locator) && count-- > 0) {
                WaitUtil.setWaitMs(500);
            }
        } else throw new NoSuchElementException("Element is not found");
        return count != 0;
    }

    public boolean waitUntilAppear(String locator, int second) {
        int count = second * 2;
        while (getElements(UiMapping.ui(locator)).isEmpty() && count-- > 0) {
            WaitUtil.setWaitMs(500);
        }
        if (!getElements(UiMapping.ui(locator)).isEmpty()) {
            while (!isDisplayed(locator) && count-- > 0) {
                WaitUtil.setWaitMs(500);
            }
        }
        return count != 0;
    }

    public boolean waitUntilAppear(WebElement element) {
        int count = 45;
        while (element == null && count-- > 0) {
            WaitUtil.setWaitMs(500);
        }
        if (element != null) {
            while (!isDisplayed(element) && count-- > 0) {
                WaitUtil.setWaitMs(500);
            }
        } else throw new NoSuchElementException("Element is not found");
        while (!isDisplayed(element) && count-- > 0) {
            WaitUtil.setWaitMs(500);
        }
        return count != 0;
    }

    public boolean waitUntilEnabled(String locator) {
        int count = 60;
        while (!isEnabled(locator) && count-- > 0) {
            WaitUtil.setWaitMs(200);
        }
        return count != 0;
    }

    public By getLocatorValue(String locator) {
        return UiMapping.ui(locator);
    }

    @Step("Press Esc")
    public void clickEsc() {
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    @Step("Press Ctrl + S")
    public void pressCtrlS() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_S);
    }

    @Step("Press Ctrl + A")
    public void pressCtrlA() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);
    }

    @Step("Press Ctrl + C")
    public void pressCtrlC() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_C);
    }

    @Step("Press Ctrl + V")
    public void pressCtrlV() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
    }

    @Step("Press Esc")
    public void cleanFieldUsingKeyboard(String locator) {
        WebElement wel = getElement(locator);
        while (!wel.getAttribute(VALUE.getValue()).equals(StringUtils.EMPTY)) {
            wel.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
    }

    @Step("Press Ctrl + V")
    public void pasteUsingKeyboard(String locator) {
        WebElement wel = getElement(locator);
        wel.sendKeys(Keys.chord(Keys.CONTROL, "v"));
    }

    @Step("Press Ctrl + C")
    public void copyUsingKeyboard(String locator) {
        WebElement wel = getElement(locator);
        wel.sendKeys(Keys.chord(Keys.CONTROL, "c"));
    }

    @Step("Press Ctrl + A")
    public void selectAllUsingKeyboard(String locator) {
        WebElement wel = getElement(locator);
        wel.sendKeys(Keys.chord(Keys.CONTROL, "a"));
    }

    @Step("Press Tab")
    public void clickTab() {
        Actions act = new Actions(driver);
        act.sendKeys(Keys.TAB).build().perform();
    }

    @Step("Press Enter")
    public void clickEnter() {
        Actions act = new Actions(driver);
        act.sendKeys(Keys.ENTER).build().perform();
    }

    @Step("Press A")
    public void clickA() {
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
    }

    @Step("Press Enter")
    public void clickEnterByKeyboard() {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    @Step("Press B")
    public void clickB() {
        robot.keyPress(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_B);
    }

    @Step("Close alert modal window in Media center")
    public void closeErrorAlert() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.switchTo().window(tabs.get(1));
        executeJsScript("alert(\"Hello world!\");");
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Step("Close alert modal window in Media center")
    public void closeErrorAlert(int numberOfWindow) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.switchTo().window(tabs.get(numberOfWindow));
        executeJsScript("alert(\"Hello world!\");");
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Step("Press Backspace")
    public void clickBackspace() {
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
    }

    public List<String> getElementsTextNoModal(String locator) {
        return driver.findElements(UiMapping.ui(locator))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Execute JS script [{script}]")
    public void executeJsScript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }

    @Step("Move cursor to locator [{locator}] click")
    public void clickElementByJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mousedown', {bubbles: true,}));", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseup', {bubbles: true,}));", element);
    }

    @Step("Move cursor to locator [{locator}] click")
    public void clickElementByJSTwo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Upload file
     */
    @Step("Insert [{text}] to input field [{locator}]")
    public void uploadFile(String locator, File file) {

        WebElement wel = waitToBeClickable(locator);
        wel.sendKeys(file.getAbsolutePath());
    }

    private WebElement waitToBeClickable(String locator) {
        int i = 1;
        while (i-- > 0) {
            try {
                WebElement wel = wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
                return wel;
            } catch (TimeoutException e) {
                log.error("Element not found. Reload page");
                driver.navigate().refresh();
                if (i == 0) throw e;
            }
        }
        return null;
    }

    public String getColor(String locator) {
        WebElement wel = waitToBeClickable(locator);
        assert wel != null;
        return wel.getCssValue("background-color");
    }

    public String getFont(String locator) {
        WebElement wel = waitToBeClickable(locator);
        assert wel != null;
        return wel.getCssValue("font-family");
    }

    public String getFont(WebElement wel) {
        assert wel != null;
        return wel.getCssValue("font-family");
    }

    @Step("Insert [{text}] to input field [{locator}]")
    public void inputThrowMove(String locator, String text) {
        WebElement wel = wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
        Actions actions = new Actions(driver);
        actions.moveToElement(wel);
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();
    }

    public void clickEnter(String locator) {
        waitUntilAppear(locator);
        WebElement wel = wait.until(ExpectedConditions.elementToBeClickable(UiMapping.ui(locator)));
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
        actions.moveToElement(wel);
        actions.click();
        actions.build().perform();
    }

    @Step("Insert [{text}] to input field [{locator}]")
    public void inputFile(String locator, String text) {
        getElement(locator).sendKeys(text);
    }
}
