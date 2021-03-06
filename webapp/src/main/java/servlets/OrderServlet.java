package servlets;

import lombok.extern.slf4j.Slf4j;
import repository.ProductList;
import services.EmptyUsernameWarning;
import services.ParamsProvider;
import services.TotalFormMaker;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(urlPatterns = "/order", name = "Order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(ParamsProvider.getUsernameParam());

        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(resp);
        } else {
            try {
                resp.sendRedirect(req.getContextPath() + "/assortment");
            } catch (IOException ex) {
                log.error("Redirect problem in Order (to /assortment).", ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(ParamsProvider.getUsernameParam());

        ProductList products = (ProductList) session.getAttribute(ParamsProvider.getProductsParam());

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(TotalFormMaker.getTotal(username, products.getSelectedProducts()));
            products.clearSelectedProducts();
        } catch (IOException ex) {
            log.error("Writer problem in Order.", ex);
        }

    }
}
