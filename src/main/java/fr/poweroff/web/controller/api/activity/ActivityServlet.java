package fr.poweroff.web.controller.api.activity;

import com.google.common.net.MediaType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "API Activity", value = "/api/activity")
public class ActivityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.JSON_UTF_8.toString());
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);
            resp.getWriter().println(
                    "{\"error\": 401}"
            );
            return;
        }
        User user;
        try {
            user = User.getFirst(session.getAttribute("email").toString());
            assert user != null;
        } catch (SQLException | NullPointerException e) {
            resp.setStatus(401);
            resp.getWriter().println(
                    "{\"error\": 401}"
            );
            return;
        }


        try {
            List<Activity> activities = user.getActivities();
            Date           start      = Date.valueOf(req.getParameter("start").substring(0, 10));
            Date           end        = Date.valueOf(req.getParameter("end").substring(0, 10));
            List<Activity> filteredActivities = activities.stream().filter(
                    activity -> activity.getStartAt().after(start) && activity.getEndAt().before(end)
            ).collect(Collectors.toList());
            JsonArray output = new JsonArray();
            for (Activity activity: filteredActivities) {
                JsonObject  a     = new JsonObject();
                JsonElement title = Place.reverse(JsonParser.parseString(activity.getCity()).getAsJsonArray());
                a.addProperty("title", title.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                a.addProperty("start", activity.getStartAt().toString());
                a.addProperty("end", activity.getEndAt().toString());
                output.add(a);
            }
            resp.getWriter().println(output);
        } catch (Exception e) {
            resp.setStatus(500);
            JsonObject errorReport = new JsonObject();
            errorReport.addProperty("error", 500);
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            errorReport.addProperty("desc", stringWriter.toString());
            resp.getWriter().append(errorReport.toString());
        }
    }
}
