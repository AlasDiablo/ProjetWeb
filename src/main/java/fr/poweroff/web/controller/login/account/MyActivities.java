package fr.poweroff.web.controller.login.account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "My Activity", value = "/my-activity")
public class MyActivities extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        this.getServletContext().getRequestDispatcher("/activity/my-activity.jsp").forward(req, resp);
    }
}
