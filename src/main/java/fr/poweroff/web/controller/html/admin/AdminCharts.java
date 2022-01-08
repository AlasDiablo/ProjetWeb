package fr.poweroff.web.controller.html.admin;

import fr.poweroff.web.Registries;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Admin - Chart", value = Registries.PATH_ADMIN_CHART)
public class AdminCharts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if (LoginChecker.performAdminCheck(this, req, resp)) {
            this.getServletContext().getRequestDispatcher(Registries.JSP_ADMIN_CHART).forward(req, resp);
        }
    }
}
