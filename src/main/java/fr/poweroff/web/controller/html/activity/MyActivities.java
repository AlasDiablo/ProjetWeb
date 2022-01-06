package fr.poweroff.web.controller.html.activity;

import fr.poweroff.web.Registries;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Activity - My activity", value = Registries.PATH_MY_ACTIVITY)
public class MyActivities extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if (LoginChecker.performCheck(this, req, resp)) {
            this.getServletContext().getRequestDispatcher(Registries.JSP_MY_ACTIVITY).forward(req, resp);
        }
    }
}
