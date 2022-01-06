package fr.poweroff.web.controller.activity;

import fr.poweroff.web.models.Activity;
import fr.poweroff.web.models.Place;
import fr.poweroff.web.models.User;

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

@WebServlet(name = "AddActivity", value = "/add-activity")
public class AddActivity extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.getWriter().println("Unauthorized");
            resp.setStatus(401);
            return;
        }
        this.getServletContext().getRequestDispatcher("/activity/add-activity.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.getWriter().println("Unauthorized");
            resp.setStatus(401);
            return;
        }
        String     address   = req.getParameter("address");
        String     startAt   = req.getParameter("start_at");
        String     endAt     = req.getParameter("end_at");
        Activity   activity  = Activity.create();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

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
        resp.sendRedirect("hello-servlet"); // TODO envoyer vers la page d'activité
    }
}
