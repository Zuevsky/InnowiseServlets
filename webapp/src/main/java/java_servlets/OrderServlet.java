package java_servlets;

import domen.Product;
import repository.ProductList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;

@WebServlet(urlPatterns = "/order", name = "Order")
public class OrderServlet extends HttpServlet {
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
            try {
                resp.sendRedirect(req.getContextPath() + "/assortment");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(USERNAME_PARAM);

        ProductList products = (ProductList) session.getAttribute(PRODUCTS);
        try {
            PrintWriter writer = resp.getWriter();
            writer.write(getTotal(username, products.getSelectedProducts()));
            products.clearSelectedProducts();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private String getTotal(String username, ArrayList<Product> selectedProducts) {
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

    private String getChosenProducts(ArrayList<Product> products) {
        StringBuilder listOfChosenProducts = new StringBuilder();
        for (int i = 1; i <= products.size(); i++) {
            StringBuilder productString = new StringBuilder();
            productString.append("<p>").append(i).append(") ").append(products.get(i-1).getName()).append(" ")
                    .append(products.get(i-1).getPrice()).append(" $</p>");
            listOfChosenProducts.append(productString);
        }
        return listOfChosenProducts.toString();
    }

    private String getTotalPrice(ArrayList<Product> products) {
        double totalPriceInDouble = 0;
        for (Product product : products) {
            totalPriceInDouble += product.getPrice();
        }
        return "<p>Total: " + totalPriceInDouble + "$</p>";
    }
}
