package fr.poweroff.web.controller.html.admin;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Activity;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet(name = "Admin - List of all activities", value = Registries.PATH_ADMIN_ACTIVITY)
public class AdminActivities extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if (LoginChecker.performAdminCheck(this, req, resp)) {

            if (req.getParameter("id") != null) {
                int id = Integer.parseInt(req.getParameter("id"));
                try {
                    Objects.requireNonNull(Activity.getFirst(id)).delete();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
                resp.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_ADMIN_ACTIVITY);
                return;
            }

            this.getServletContext().getRequestDispatcher(Registries.JSP_ADMIN_ACTIVITY).forward(req, resp);
        }
    }
}
