package servlets;

import domen.Product;
import lombok.extern.slf4j.Slf4j;
import repository.ProductList;
import services.EmptyUsernameWarning;
import services.ParamsProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static services.OrderFormMaker.getOrderForm;

@Slf4j
@WebServlet(urlPatterns = "/assortment", name = "Assortment")
public class RangeOfProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(ParamsProvider.getUsernameParam());

        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(resp);
        } else {
            ProductList products = (ProductList) session.getAttribute(ParamsProvider.getProductsParam());
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(getOrderForm(username, products));
            } catch (IOException ex) {
                log.error("Writer problem in Assortment.", ex);
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