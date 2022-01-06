package fr.poweroff.web.util;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.User;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginChecker {
    public static boolean performCheck(@NotNull HttpServlet servlet, @NotNull HttpServletRequest req, @NotNull HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            servlet.getServletContext().getRequestDispatcher(Registries.JSP_ERROR_401).forward(req, resp);
            return false;
        }
        User user;
        try {
            user = User.getFirst(session.getAttribute("email").toString());
            Objects.requireNonNull(user);
        } catch (SQLException | NullPointerException e) {
            servlet.getServletContext().getRequestDispatcher(Registries.JSP_ERROR_401).forward(req, resp);
            return false;
        }
        return true;
    }
}
