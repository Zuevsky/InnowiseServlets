package servlets;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@WebServlet(urlPatterns = "/access-deny", name = "AccessDenyServlet")
public class AccessDenyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");

        HttpSession session = req.getSession(true);

        String username = (String) session.getAttribute(ParamsProvider.getUsernameParam());

        if (EmptyUsernameWarning.validateEmptyUsername(username)) {
            EmptyUsernameWarning.writeEmptyUsernameWarning(this, req, resp);
        } else {
            try {
                getServletContext().getRequestDispatcher("/WEB-INF/access-deny.jsp").forward(req, resp);
            } catch (IOException ex) {
                log.error("IOException (GET) in AccessDeny.", ex);
            } catch (ServletException ex) {
                log.error("ServletException (GET) in AccessDeny.", ex);
            }
        }
    }
}
