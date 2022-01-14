package JavaServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/access-deny", name = "AccessDeny")
public class AccessDenyServlet extends HttpServlet {
    public static final String USERNAME_PARAM = "username";

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
        }

        try {
            PrintWriter writer = resp.getWriter();

            String message = """
                <div align="center">
                <h1>Oops!</h1>
                <p>You shouldn't be here</p>
                <p>Please, agree with the terms of service first.</p>
                <br/>
                <a href="/">Start page</a>
                </div>
                """;

            writer.println(message);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
