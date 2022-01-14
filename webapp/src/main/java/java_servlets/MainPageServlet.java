package java_servlets;

import repository.ProductList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(urlPatterns = "/", name = "ShopMainPage")
public class MainPageServlet extends HttpServlet {

    private static final String AGREEMENT_PARAM = "termsAgreement";
    public static final String USERNAME_PARAM = "username";
    public static final String PRODUCTS = "products";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        session.setAttribute(USERNAME_PARAM, "");

        try {
            PrintWriter printWriter = resp.getWriter();

            String userForm = """
                <h1 align="center">Welcome to Online Shop</h1> <br/>
                <form name="loginForm" method="post" action="/" align="center">
                <input type="text" name="username" align="center"/> <br/>
                <input type="checkbox" name="termsAgreement" checked align="center"> I agree with the terms of service <br/>
                <input type="submit" name="btn" align="center"/>
                </form>
                """;

            printWriter.write(userForm);
            printWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        String username = req.getParameter(USERNAME_PARAM);

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
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        } else {
            boolean agreement = Optional.ofNullable(req.getParameter(AGREEMENT_PARAM)).isPresent();

            session.setAttribute(USERNAME_PARAM, username);
            session.setAttribute(AGREEMENT_PARAM, agreement);
            session.setAttribute(PRODUCTS, new ProductList());

            if (agreement) {
                try {
                    resp.sendRedirect(req.getContextPath() + "/assortment");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    resp.sendRedirect(req.getContextPath() + "/access-deny");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
