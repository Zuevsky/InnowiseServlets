package servlets;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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


@Slf4j
@WebServlet(urlPatterns = "/", name = "ShopMainPage")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        try (PrintWriter printWriter = resp.getWriter()) {

            String userForm = """
                    <h1 align="center">Welcome to Online Shop</h1> <br/>
                    <form name="loginForm" method="post" action="/" align="center">
                    <input type="text" name="username" align="center"/> <br/>
                    <input type="checkbox" name="termsAgreement" checked align="center"> I agree with the terms of service <br/>
                    <input type="submit" name="btn" align="center"/>
                    </form>
                    """;

            printWriter.write(userForm);
        } catch (IOException ex) {
            log.error("Writer problem in ShopMainPage.", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(true);

        session.setAttribute(ParamsProvider.getUsernameParam(), StringUtils.EMPTY);
        String username = req.getParameter(ParamsProvider.getUsernameParam());

        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(resp);
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
