package servlets;

import lombok.extern.slf4j.Slf4j;
import services.EmptyUsernameWarning;
import services.ParamsProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(urlPatterns = "/access-deny", name = "AccessDeny")
public class AccessDenyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(ParamsProvider.getUsernameParam());

        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(resp);
        } else {
            try (PrintWriter writer = resp.getWriter()) {
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
            } catch (IOException ex) {
                log.error("Writer problem in AccessDeny.", ex);
            }
        }
    }
}
