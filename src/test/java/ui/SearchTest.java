package ui;

import io.netty.util.internal.StringUtil;
import module.Product;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;
import pages.CatalogPage;
import pages.SalePage;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class SearchTest extends BaseTest {

    @Test(description = "Verify the catalog sorting")
    public void verifySortingCatalogTest() {
        onliner.openMainPage()
                .getMainPage()
                .clickCatalogButton();
        onliner.getCatalogPage()
                .clickElectronicButton()
                .clickLeftCatalog(CatalogPage.LeftCatalog.MOBILE_PHONES)
                .clickRightCatalog(CatalogPage.RightCatalog.SMARTPHONES);
        List<Product> products = onliner.getSalePage()
                .clickOrderButton()
                .selectOrder(SalePage.OrderList.EXPENSIVE)
                .parseProductList();
        List<Integer> prices = products.stream()
                .map(Product::getPrice)
                .map(s -> s.split(String.valueOf(StringUtil.COMMA))[0]
                        .replace(StringUtils.SPACE, StringUtils.EMPTY))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; i < prices.size() - 1; i++) {
            assertTrue(prices.get(i) >= prices.get(i + 1));
        }
    }
}
