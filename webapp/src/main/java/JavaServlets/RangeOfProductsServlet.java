package JavaServlets;

import Domen.Product;
import Repository.ProductList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = "/assortment", name = "Assortment")
public class RangeOfProductsServlet extends HttpServlet {
    private static final String PRODUCT_ID = "productId";
    public static final String USERNAME_PARAM = "username";
    public static final String PRODUCTS = "products";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(USERNAME_PARAM);

        if (username.equals("")) {
            try {
                PrintWriter printWriter = resp.getWriter();
                String warning = """
                        <p align="center">You should enter your name!</p>
                        <form name="nullNameForm" method="get" action="/" id="nullName" align="center">
                        <input type="submit" name="btn" value="Okay" form="nullName" align="center">
                        </form>
                        """;
                printWriter.write(warning);
                printWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            ProductList products = (ProductList) session.getAttribute(PRODUCTS);
            try{
                PrintWriter writer = resp.getWriter();
                writer.write(getOrderForm(username, products));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        ProductList products = (ProductList) session.getAttribute(PRODUCTS);

        try {
            if (Optional.ofNullable(req.getParameter(PRODUCT_ID)).isPresent()) {
                int selectedProduct = Integer.parseInt(req.getParameter(PRODUCT_ID));
                for (Product product : products.getAllProducts()) {
                    if (selectedProduct == product.getId()) {
                        products.addSelectedProduct(product);
                        break;
                    }
                }
                resp.setIntHeader("Refresh", 1);
            }
        } catch(NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private String getSelectForProduct(ArrayList<Product> products) {
        StringBuilder select = new StringBuilder();

        select.append("<select name=\"productId\" form=\"selectForm\" align=\"center\"><br>");

        for (int i = 0; i < products.size(); i++) {
            String price = Double.toString(products.get(i).getPrice());
            if (products.get(i).getPrice() % 1.0 == 0) {
                price = price.substring(0,price.length()-2);
            }
            select.append("<option value=\"").append(i + 1).append("\">")
                    .append(products.get(i).getName()).append(" ")
                    .append(price).append(" $").append("</option><br>");
        }

        select.append("</select>");
        return select.toString();
    }

    private String getOrderForm(String username, ProductList products) {
        Formatter formatter = new Formatter();

        String alreadyChosenProducts;
        if (!products.getSelectedProducts().isEmpty()) {
            alreadyChosenProducts = getAlreadyChosenProducts(products.getSelectedProducts());
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
                </form>
                <input type="submit" name="addItem" value="Add Item" form="selectForm" align="center">
                <form name="submitForm" method="post" action="/order" id="redirectToOrder" align="center">
                <input type="submit" name="submit" form="redirectToOrder" align="center">
                </form>
                </div>
                """, username, alreadyChosenProducts, getSelectForProduct(products.getAllProducts())));
    }

    private String getAlreadyChosenProducts(ArrayList<Product> products) {
        StringBuilder listOfChosenProducts = new StringBuilder();
        listOfChosenProducts.append("<p id=\"shoppingCart\">You have already chosen:</p>");
        for (int i = 1; i <= products.size(); i++) {
            StringBuilder productString = new StringBuilder();
            productString.append("<p>").append(i).append(") ").append(products.get(i-1).getName()).append(" ")
                    .append(products.get(i-1).getPrice()).append(" $</p>");
            listOfChosenProducts.append(productString);
        }
        return listOfChosenProducts.toString();
    }
}