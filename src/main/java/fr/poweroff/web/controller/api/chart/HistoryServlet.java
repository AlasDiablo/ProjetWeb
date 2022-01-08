package fr.poweroff.web.controller.api.chart;

import com.google.common.net.MediaType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Contaminated;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("UnstableApiUsage")
@WebServlet(name = "API Data - History", value = Registries.PATH_API_DATA_HISTORY)
public class HistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.JSON_UTF_8.toString());

        try {
            JsonArray dataset = new JsonArray();
            Contaminated.get().forEach(data -> {
                JsonObject element = new JsonObject();
                element.addProperty("count", data.getLeft());
                element.addProperty("date", data.getRight().toString());
                dataset.add(element);
            });
            resp.getWriter().write(dataset.toString());
        } catch (SQLException e) {
            resp.getWriter().write(
                    "{\"error\": 500}"
            );
        }
    }
}
