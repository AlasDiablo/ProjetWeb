package fr.poweroff.web.controller.html.activity;

import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Activity;
import fr.poweroff.web.models.Place;
import fr.poweroff.web.models.User;
import fr.poweroff.web.util.LoginChecker;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "Activity - Add activity", value = Registries.PATH_ADD_ACTIVITY)
public class AddActivity extends HttpServlet {

    @Override
    protected void doGet(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (LoginChecker.performCheck(this, req, resp)) {
            this.getServletContext().getRequestDispatcher(Registries.JSP_ADD_ACTIVITY).forward(req, resp);
        }
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (LoginChecker.performCheck(this, req, resp)) {
            HttpSession session   = req.getSession();
            String      address   = req.getParameter("address");
            String      startAt   = req.getParameter("start_at");
            String      endAt     = req.getParameter("end_at");
            Activity    activity  = Activity.create();
            DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

            try {
                activity.setStartAt(formatter.parse(startAt));
                activity.setEndAt(formatter.parse(endAt));
            } catch (ParseException e) {
                throw new RuntimeException();
            }

            activity.setCity(Place.search(address).getAsJsonArray().get(0).getAsJsonObject().get("coordinates").getAsJsonArray().toString());

            try {
                activity.setOwner(User.getFirst(session.getAttribute("email").toString()));
                activity.save();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
            resp.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_MY_ACTIVITY);
        }
    }
}
