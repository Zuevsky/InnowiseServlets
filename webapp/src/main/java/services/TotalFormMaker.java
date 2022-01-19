package services;

import domain.Product;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Formatter;
import java.util.List;

@UtilityClass
public class TotalFormMaker {

    public String getChosenProducts(List<Product> products) {
        StringBuilder listOfChosenProducts = new StringBuilder();
        for (int i = 1; i <= products.size(); i++) {
            StringBuilder productString = new StringBuilder();
            productString.append("<p>").append(i).append(") ").append(products.get(i - 1).getName()).append(StringUtils.SPACE)
                    .append(products.get(i - 1).getPrice()).append(" $</p>");
            listOfChosenProducts.append(productString);
        }
        return listOfChosenProducts.toString();
    }

    public String getTotalPrice(List<Product> products) {
        double totalPriceInDouble = 0;
        for (Product product : products) {
            totalPriceInDouble += product.getPrice();
        }
        return "<p>Total: " + totalPriceInDouble + "$</p>";
    }
}
