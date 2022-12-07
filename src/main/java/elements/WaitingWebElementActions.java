package elements;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

@Log4j2
public class WaitingWebElementActions extends WebElementActions {
    private final String waitingLocator;
    private final String waitingAttribute;

    protected WebDriverWait wait;
    protected WebDriver driver;

    public WaitingWebElementActions(WebDriver driver, WebDriverWait wait, String waitingLocator, String waitingAttribute) {
        super(driver, wait);
        this.driver = driver;
        this.wait = wait;
        this.waitingLocator = waitingLocator;
        this.waitingAttribute = waitingAttribute;
    }

    private void waitModal() {
        if (waitingAttribute != null)
            super.waitModal(waitingLocator, waitingAttribute);
        else super.waitModal(waitingLocator);
    }

    @Step("Click on an element [{locator.}]")
    public void click(String locator) {
        waitModal();
        waitUntilAppear(locator);
        super.click(locator);
    }

    @Step("Click on an element [{element}]")
    public void click(WebElement element) {
        waitModal();
        super.click(element);
    }

    @Step("Click on an element [{locator}]")
    public void click(WebElement element, String locator) {
        waitModal();
        super.click(element, locator);
    }

    @Step("Click on an element in array [{locator}]")
    public void click(String locator, String element, int offset) {
        waitModal();
        super.click(locator, element, offset);
    }

    @Step("Insert [{text}] to input field [{locator}]")
    public void input(String locator, String text) {
        waitModal();
        super.input(locator, text);
    }

    @Step("Insert [{text}] to input field [{element}]")
    public void input(WebElement element, String text) {
        waitModal();
        super.input(element, text);
    }

    @Step("Insert [{text}] to input field [{locator}]")
    public void inputNoClear(String locator, String text) {
        waitModal();
        super.inputNoClear(locator, text);
    }

    @Step("Select from dropdown [{locator}] by value [{value}]")
    public void select(String locator, String value) {
        waitModal();
        super.select(locator, value);
    }

    @Step("Select from dropdown [{element}] by value [{value}]")
    public void select(WebElement element, String value) {
        waitModal();
        super.select(element, value);
    }

    @Step("Select from dropdown [{locator}] by value [{value}]")
    public void selectByValueWait(String locator, String value) {
        waitModal();
        super.selectByValueWait(locator, value);
    }

    @Step("Select from dropdown [{locator}] by text [{text}]")
    public void selectByText(String locator, String text) {
        waitModal();
        super.selectByText(locator, text);
    }

    @Step("Select from dropdown [{locator}] by text contains [{text}]")
    public void selectByContainsText(String locator, String text) {
        waitModal();
        super.selectByContainsText(locator, text);
    }

    @Step("Select from dropdown [{locator}] by text [{text}]")
    public void selectByTextWait(String locator, String text) {
        waitModal();
        super.selectByTextWait(locator, text);
    }

    @Step("Select element [{locator}]")
    public void select(WebElement element, String locator, String text) {
        waitModal();
        super.select(element, locator, text);
    }

    public String getSelectedValue(String locator) {
        waitModal();
        return super.getSelectedValue(locator);
    }

    public String getSelectedValue(WebElement element) {
        waitModal();
        return super.getSelectedValue(element);
    }

    public List<String> getSelectValues(String locator) {
        waitModal();
        return super.getSelectValues(locator);
    }

    public List<String> getSelectValues(WebElement element) {
        waitModal();
        return super.getSelectValues(element);
    }

    public String getInputValue(String locator) {
        waitModal();
        return super.getInputValue(locator);
    }

    public WebElement getElement(String locator, String element, int offset) {
        waitModal();
        return super.getElement(locator, element, offset);
    }

    public WebElement getElement(String locator) {
        waitModal();
        return super.getElement(locator);
    }

    public WebElement getElementWithDynamicLocator(String locator, String input) {
        waitModal();
        return super.getElementWithDynamicLocator(locator, input);
    }

    public List<WebElement> getElementsWithDynamicLocator(String locator, String input) {
        waitModal();
        return super.getElementsWithDynamicLocator(locator, input);
    }

    public List<WebElement> getElements(String locator) {
        waitModal();
        return super.getElements(locator);
    }

    @Override
    public boolean waitUntilAppear(String locator) {
        waitModal();
        return super.waitUntilAppear(locator);
    }

    public List<WebElement> getElementsByXpath(String locator) {
        waitModal();
        return super.getElementsByXpath(locator);
    }

    public List<WebElement> getElements(By locator) {
        waitModal();
        return super.getElements(locator);
    }

    public List<String> getElementsText(String locator) {
        waitModal();
        return super.getElementsText(locator);
    }

    public List<String> getElementsInnerHtml(String locator) {
        waitModal();
        return super.getElementsInnerHtml(locator);
    }

    public String getElementText(String locator, String element, int offset) {
        waitModal();
        return super.getElementText(locator, element, offset);
    }

    public String getElementsText(WebElement element) {
        waitModal();
        return super.getElementsText(element);
    }

    public String getElementsAttribute(String locator, String attr) {
        waitModal();
        return super.getElementsAttribute(locator, attr);
    }

    public String getElementsAttribute(WebElement el, String attr) {
        waitModal();
        return super.getElementsAttribute(el, attr);
    }

    public boolean isClickable(String locator) {
        waitModal();
        return super.isClickable(locator);
    }

    public boolean isClickable(String locator, String element, int offset) {
        waitModal();
        return super.isClickable(locator, element, offset);
    }

    public boolean isChecked(String locator) {
        waitModal();
        return super.isChecked(locator);
    }

    public boolean isChecked(WebElement element) {
        waitModal();
        return super.isChecked(element);
    }

    public boolean isChecked(String locator, String element, int offset) {
        waitModal();
        return super.isChecked(locator, element, offset);
    }

    public boolean isCheckedAttribute(String locator) {
        waitModal();
        return super.isCheckedAttribute(locator);
    }

    public boolean isPresent(String locator) {
        waitModal();
        return super.isPresent(locator);
    }

    public boolean isDisplayed(String locator) {
        waitModal();
        return super.isDisplayed(locator);
    }

    public boolean isDisplayed(WebElement element) {
        waitModal();
        return super.isDisplayed(element);
    }

    public boolean isPresentDynamic(String locator, String input) {
        waitModal();
        return super.isPresentDynamic(locator, input);
    }

    public boolean isEnabled(String locator) {
        waitModal();
        return super.isEnabled(locator);
    }

    public boolean isEnabled(WebElement element) {
        waitModal();
        return super.isEnabled(element);
    }

    @Step("Move cursor to locator [{locator}]")
    public void moveToElement(String locator) {
        waitModal();
        super.moveToElement(locator);
    }

    @Step("Move cursor to element [{element}]")
    public void moveToElement(WebElement element) {
        waitModal();
        super.moveToElement(element);
    }

    @Step("Move cursor to element [{elem}] by X={x}, Y={y}")
    public void moveByOffset(WebElement elem, int x, int y) {
        waitModal();
        super.moveByOffset(elem, x, y);
    }

    @Step("Move cursor to locator [{locator}] by X={x}, Y={y}")
    public void moveByOffset(String locator, int x, int y) {
        waitModal();
        super.moveByOffset(locator, x, y);
    }

    @Step("Clear field [{locator}]")
    public void clearField(String locator) {
        waitModal();
        super.clearField(locator);
    }

    public void doubleClick(String locator, String element, int offset) {
        waitModal();
        super.doubleClick(locator, element, offset);
    }

    @Step("Scroll horizontal line by locator [{locator}]")
    public void scrollToElement(String locator) {
        waitModal();
        super.scrollToElement(locator);
    }

    public boolean waitUntilDisplayed(String locator) {
        waitModal();
        return super.waitUntilDisplayed(locator);
    }

    public boolean waitUntilVisible(String locator) {
        waitModal();
        return super.waitUntilVisible(locator);
    }

    public boolean waitUntilClickable(String locator) {
        waitModal();
        return super.waitUntilClickable(locator);
    }

    public boolean waitUntilDisappear(String locator) {
        waitModal();
        return super.waitUntilDisappear(locator);
    }

    public boolean waitUntilAppear(WebElement element) {
        waitModal();
        return super.waitUntilAppear(element);
    }

    public boolean waitUntilEnabled(String locator) {
        waitModal();
        return super.waitUntilEnabled(locator);
    }

    public void waitUntilModalGone() {
        waitModal();
    }

    @Step("Execute JS script [{script}]")
    public void executeJsScript(String script) {
        waitModal();
        super.executeJsScript(script);
    }

    @Step("Insert [{text}] to input field [{locator}]")
    public void uploadFile(String locator, File file) {
        waitModal();
        super.uploadFile(locator, file);
    }

    public String getColor(String locator) {
        waitModal();
        return super.getColor(locator);
    }

    public String getFont(String locator) {
        waitModal();
        return super.getFont(locator);
    }

    public String getFont(WebElement wel) {
        waitModal();
        return super.getFont(wel);
    }
}
