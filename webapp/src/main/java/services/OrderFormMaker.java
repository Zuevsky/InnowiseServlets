package services;

import domen.Product;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import repository.ProductList;

import java.util.Formatter;
import java.util.List;

@UtilityClass
public class OrderFormMaker {

    public static String getOrderForm(String username, ProductList products) {
        Formatter formatter = new Formatter();

        String alreadyChosenProducts;
        String submitButton = StringUtils.EMPTY;

        if (!products.getSelectedProducts().isEmpty()) {
            alreadyChosenProducts = getAlreadyChosenProducts(products.getSelectedProducts());
            submitButton = """
                    <form name="submitForm" method="post" action="/total-order" id="redirectToOrder" align="center">
                    <input type="submit" name="submit" form="redirectToOrder" align="center">
                    </form>
                    """;
        } else {
            alreadyChosenProducts = """
                    <p>Your shopping cart is empty!</p>
                    <p>Please, add some items!</p>
                    """;
        }

        return String.valueOf(formatter.format("""
                <div align="center">
                <h1 align="center">Hello %s!</h1>
                %s
                <p align="center">Make your order</p>
                <form name="selector" method="post" action="/assortment" id="selectForm" align="center">
                %s <br/>
                <input type="submit" name="addItem" value="Add Item" form="selectForm" align="center">
                </form>
                %s
                </div>
                """, username, alreadyChosenProducts, getSelectForProduct(products.getAllProducts()), submitButton));
    }

    private String getSelectForProduct(List<Product> products) {
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

    private String getAlreadyChosenProducts(List<Product> products) {
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
