package pages;

import module.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtil;

import java.util.ArrayList;
import java.util.List;

public class SalePage extends BasePage {
    private static final String ORDER_BUTTON = "sale.order.button";
    private static final String ORDER_LIST = "sale.order.table";
    private static final String PRODUCT_LIST = "sale.product.list";
    private static final String CHILD_CHECKBOX_LIST = "div/div";
    private static final String CHILD_TITLE_LIST = "div/div/div[@class='schema-product__title']/a/span";
    private static final String CHILD_DESCRIPTION_LIST = "div/div/div[@class='schema-product__description']/span";
    private static final String CHILD_PRICE_LIST = "div/following-sibling::div/div[1]/div/div/div/div/a/span";
    private static final String CHILD_PROPOSITIONS_LIST = "div/following-sibling::div/div[1]/div/div/div/div/following-sibling::div/a";

    public enum OrderList {
        POPULAR(0),
        CHIP(1),
        EXPENSIVE(2),
        NEW(3),
        WITH_FEEDBACK(4);

        private final int value;

        OrderList(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public SalePage(WebDriver driver) {
        super(driver);
    }

    public SalePage clickOrderButton() {
        actions.click(ORDER_BUTTON);
        return this;
    }

    public SalePage selectOrder(OrderList value) {
        actions.getElements(ORDER_LIST).get(value.getValue()).click();
        return this;
    }

    public List<Product> parseProductList() {
        waitUntilLoadList();
        List<Product> products = new ArrayList<>();
        List<WebElement> elements = actions.getElements(PRODUCT_LIST);
        if (elements.size() != 0)
            elements.forEach(element -> {
                Product product = new Product();
                product.setCheckbox(actions.getChild(element, CHILD_CHECKBOX_LIST));
                product.setTitle(actions.getChild(element, CHILD_TITLE_LIST).getText());
                product.setDescription(actions.getChild(element, CHILD_DESCRIPTION_LIST).getText());
                product.setPrice(actions.getChild(element, CHILD_PRICE_LIST).getText());
                product.setPrepositions(actions.getChild(element, CHILD_PROPOSITIONS_LIST));
                products.add(product);
            });
        return products;
    }

    private void waitUntilLoadList() {
        int count = 5;
        while (count-- > 0) {
            if (actions.getElements(PRODUCT_LIST).size() > 0) break;
            else WaitUtil.setWait(1);
        }
        WaitUtil.setWait(2);
    }
}
