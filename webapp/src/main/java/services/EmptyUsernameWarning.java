package services;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@UtilityClass
public final class EmptyUsernameWarning {

    public static boolean validateEmptyUsername(String username) {
        return username.trim().equals(StringUtils.EMPTY);
    }

    public static void writeEmptyUsernameWarning(HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        try {
            servlet.getServletContext().getRequestDispatcher("/WEB-INF/empty-username.jsp").forward(req, resp);
        } catch (IOException ex) {
            log.error("IOException (GET) in Assortment.", ex);
        } catch (ServletException ex) {
            log.error("ServletException (GET) in Assortment.", ex);
        }
    }
}
