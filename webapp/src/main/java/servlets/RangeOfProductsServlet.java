package servlets;

import domain.Product;
import lombok.extern.slf4j.Slf4j;
import repository.ProductList;
import services.EmptyUsernameWarning;
import services.OrderFormMaker;
import services.ParamsProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = "/assortment", name = "AssortmentServlet")
public class RangeOfProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        ProductList products = (ProductList) session.getAttribute(ParamsProvider.getProductsParam());
        String username = (String) session.getAttribute(ParamsProvider.getUsernameParam());

        String shoppingCart = OrderFormMaker.getShoppingCart(OrderFormMaker.shoppingCartCheckout(products.getSelectedProducts()),
                products.getSelectedProducts());
        String selectForProduct = OrderFormMaker.getSelectForProduct(products.getAllProducts());
        String submitButton = OrderFormMaker.getSubmitButton(OrderFormMaker.shoppingCartCheckout(products.getSelectedProducts()));

        session.setAttribute(ParamsProvider.getShoppingCart(), shoppingCart);
        session.setAttribute(ParamsProvider.getSelectForProduct(), selectForProduct);
        session.setAttribute(ParamsProvider.getSubmitButton(), submitButton);


        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(this, req, resp);
        } else {
            try {
                getServletContext().getRequestDispatcher("/WEB-INF/assortment.jsp").forward(req, resp);
            } catch (IOException ex) {
                log.error("IOException (GET) in Assortment.", ex);
            } catch (ServletException ex) {
                log.error("ServletException (GET) in Assortment.", ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        ProductList products = (ProductList) session.getAttribute(ParamsProvider.getProductsParam());

        if (Optional.ofNullable(req.getParameter(ParamsProvider.getProductNameParam())).isPresent()) {
            String selectedProduct = req.getParameter(ParamsProvider.getProductNameParam());
            for (Product product : products.getAllProducts()) {
                if (selectedProduct.equals(product.getName())) {
                    products.addSelectedProduct(product);
                    break;
                }
            }
            resp.setIntHeader("Refresh", 1);
        }
    }
}