package services;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@UtilityClass
public final class EmptyUsernameWarning {

    @NonNull
    public static boolean validateEmptyUsername(String username) {
        return username.trim().equals(StringUtils.EMPTY);
    }

    public static void writeEmptyUsernameWarning(HttpServletResponse resp) {
        try (PrintWriter printWriter = resp.getWriter()) {
            String warning = """
                    <p align="center">You should enter your name!</p>
                    <form name="emptyNameForm" method="get" action="/" id="emptyName" align="center">
                    <input type="submit" name="btn" value="Okay" form="emptyName" align="center">
                    </form>
                    """;
            printWriter.write(warning);
        } catch (IOException ex) {
            log.error("Writer problem in emptyNameBlock.", ex);
        }
    }
}
