package servlets;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import repository.ProductList;
import services.EmptyUsernameWarning;
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
@WebServlet(urlPatterns = "/", name = "ShopMainPageServlet")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        try {
            getServletContext().getRequestDispatcher("/WEB-INF/welcome.jsp").forward(req, resp);
        } catch (IOException ex) {
            log.error("IOException (GET) in ShopMainPage.", ex);
        } catch (ServletException ex) {
            log.error("ServletException (GET) in ShopMainPage.", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(true);

        session.setAttribute(ParamsProvider.getUsernameParam(), StringUtils.EMPTY);
        String username = req.getParameter(ParamsProvider.getUsernameParam());

        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(this, req, resp);
        } else {
            boolean agreement = Optional.ofNullable(req.getParameter(ParamsProvider.getAgreementParam())).isPresent();

            session.setAttribute(ParamsProvider.getUsernameParam(), username);
            session.setAttribute(ParamsProvider.getAgreementParam(), agreement);
            session.setAttribute(ParamsProvider.getProductsParam(), new ProductList());

            if (agreement) {
                try {
                    resp.sendRedirect(req.getContextPath() + "/assortment");
                } catch (IOException ex) {
                    log.error("Redirect problem in ShopMainPage (to /assortment).", ex);
                }
            } else {
                try {
                    resp.sendRedirect(req.getContextPath() + "/access-deny");
                } catch (IOException ex) {
                    log.error("Redirect problem in ShopMainPage (to /access-deny).", ex);
                }
            }
        }
    }
}
