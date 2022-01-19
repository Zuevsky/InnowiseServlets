package servlets;

import lombok.extern.slf4j.Slf4j;
import repository.ProductList;
import services.EmptyUsernameWarning;
import services.ParamsProvider;
import services.TotalFormMaker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(urlPatterns = "/total-order", name = "TotalOrderServlet")
public class TotalOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(ParamsProvider.getUsernameParam());

        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(this, req, resp);
        } else {
            try {
                resp.sendRedirect(req.getContextPath() + "/assortment");
            } catch (IOException ex) {
                log.error("Redirect problem in TotalOrder (to /assortment).", ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        ProductList products = (ProductList) session.getAttribute(ParamsProvider.getProductsParam());

        String chosenProducts = TotalFormMaker.getChosenProducts(products.getSelectedProducts());
        String totalPrice = TotalFormMaker.getTotalPrice(products.getSelectedProducts());

        session.setAttribute(ParamsProvider.getChosenProducts(), chosenProducts);
        session.setAttribute(ParamsProvider.getTotalPrice(), totalPrice);

        try {
            getServletContext().getRequestDispatcher("/WEB-INF/total.jsp").forward(req, resp);
        } catch (IOException ex) {
            log.error("IOException (POST) in TotalOrder.", ex);
        } catch (ServletException ex) {
            log.error("ServletException (POST) in TotalOrder.", ex);
        }

        products.clearSelectedProducts();
    }
}
