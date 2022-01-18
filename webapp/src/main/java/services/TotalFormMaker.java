package services;

import domen.Product;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Formatter;
import java.util.List;

@UtilityClass
public class TotalFormMaker {

    public static String getTotal(String username, List<Product> selectedProducts) {
        Formatter formatter = new Formatter();

        return String.valueOf(formatter.format("""
                <div align="center">
                <h1>Dear %s, your order:</h1>
                %s
                %s
                <p>Do you want to make another order?</p>
                <form name="anotherOrderForm" method="get" action="/assortment" id="anotherOrder" align="center">
                <input type="submit" name="btn" value="Yes" form="anotherOrder" align="center">
                </form>
                </div>
                """, username, getChosenProducts(selectedProducts), getTotalPrice(selectedProducts)));
    }

    private String getChosenProducts(List<Product> products) {
        StringBuilder listOfChosenProducts = new StringBuilder();
        for (int i = 1; i <= products.size(); i++) {
            StringBuilder productString = new StringBuilder();
            productString.append("<p>").append(i).append(") ").append(products.get(i - 1).getName()).append(StringUtils.SPACE)
                    .append(products.get(i - 1).getPrice()).append(" $</p>");
            listOfChosenProducts.append(productString);
        }
        return listOfChosenProducts.toString();
    }

    private String getTotalPrice(List<Product> products) {
        double totalPriceInDouble = 0;
        for (Product product : products) {
            totalPriceInDouble += product.getPrice();
        }
        return "<p>Total: " + totalPriceInDouble + "$</p>";
    }
}
