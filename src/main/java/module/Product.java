package module;

import lombok.Data;
import org.openqa.selenium.WebElement;

@Data
public class Product {
    private WebElement checkbox;
    private String title;
    private String description;
    private String price;
    private WebElement prepositions;
}
