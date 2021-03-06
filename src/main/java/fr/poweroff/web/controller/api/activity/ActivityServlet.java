package fr.poweroff.web.controller.api.activity;

import com.google.common.net.MediaType;
import com.google.gson.*;
import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Activity;
import fr.poweroff.web.models.Place;
import fr.poweroff.web.models.User;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
@WebServlet(name = "API Activity", value = Registries.PATH_API_ACTIVITY)
public class ActivityServlet extends HttpServlet {

    @Override
    protected void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
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
            List<Activity> activities;
            if (user.getLevel() == 1 && req.getParameter("all") != null) {
                activities = Activity.getAll();
            } else {
                activities = user.getActivities();
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date       start     = formatter.parse(req.getParameter("start").substring(0, 10));
            Date       end       = formatter.parse(req.getParameter("end").substring(0, 10));
            List<Activity> filteredActivities = activities.stream().filter(
                    activity -> activity.getStartAt().after(start) && activity.getEndAt().before(end)
            ).collect(Collectors.toList());
            JsonArray output = new JsonArray();
            Gson      gson   = new GsonBuilder().create();
            for (Activity activity: filteredActivities) {
                JsonObject  a     = new JsonObject();
                JsonElement title = Place.reverse(JsonParser.parseString(activity.getCity()).getAsJsonArray());

                if (user.getLevel() == 1 && req.getParameter("all") != null) {
                    a.addProperty("id", activity.getActivityId());
                    User owner = activity.getOwner();
                    a.addProperty("title", owner.getUserId() + " : " + title.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                    JsonObject userData = gson.toJsonTree(owner).getAsJsonObject();
                    userData.remove("passwordHash");
                    userData.remove("level");
                    a.add("user", userData);
                } else {
                    a.addProperty("title", title.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                }
                a.addProperty("start", activity.getStartAt().toString());
                a.addProperty("end", activity.getEndAt().toString());
                if (activity.getContact()) {
                    a.addProperty("borderColor", "#FF0000");
                    a.addProperty("backgroundColor", "#FF8585");
                }
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
