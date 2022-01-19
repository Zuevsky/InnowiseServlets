package services;

import domain.Product;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import repository.ProductList;

import java.util.Formatter;
import java.util.List;

@UtilityClass
public class OrderFormMaker {

    public String getShoppingCart(boolean isShoppingCartFilled, List<Product> products) {
        String shoppingCart;

        if (isShoppingCartFilled) {
            shoppingCart = getAlreadyChosenProducts(products);
        } else {
            shoppingCart = """
                    <p>Your shopping cart is empty!</p>
                    <p>Please, add some items!</p>
                    """;
        }

        return shoppingCart;
    }

    public String getSubmitButton(boolean isShoppingCartFilled) {
        if (isShoppingCartFilled) {
            return """
                    <form name="submitForm" method="post" action="/total-order" id="redirectToOrder" align="center">
                    <input type="submit" name="submit" form="redirectToOrder" align="center">
                    </form>
                    """;
        } else {
            return StringUtils.EMPTY;
        }
    }

    public boolean shoppingCartCheckout(List<Product> products) {
        return !products.isEmpty();
    }

    public String getSelectForProduct(List<Product> products) {
        StringBuilder select = new StringBuilder();

        select.append("<select name=\"productName\" form=\"selectForm\" align=\"center\"><br>");

        for (Product product : products) {
            select.append("<option value=\"").append(product.getName())
                    .append("\">").append(product.getName()).append(StringUtils.SPACE)
                    .append(product.getPrice()).append(" $").append("</option><br>");
        }

        select.append("</select>");
        return select.toString();
    }

    public String getAlreadyChosenProducts(List<Product> products) {
        StringBuilder listOfChosenProducts = new StringBuilder();
        listOfChosenProducts.append("<p id=\"shoppingCart\">You have already chosen:</p>");
        for (int i = 1; i <= products.size(); i++) {
            StringBuilder productString = new StringBuilder();
            productString.append("<p>").append(i).append(") ").append(products.get(i - 1).getName()).append(StringUtils.SPACE)
                    .append(products.get(i - 1).getPrice()).append(" $</p>");
            listOfChosenProducts.append(productString);
        }
        return listOfChosenProducts.toString();
    }
}
