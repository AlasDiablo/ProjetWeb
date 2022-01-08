package fr.poweroff.web.controller.api.chart;

import com.google.common.net.MediaType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.poweroff.web.Registries;
import fr.poweroff.web.models.Activity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("UnstableApiUsage")
@WebServlet(name = "API Data - Map", value = Registries.PATH_API_DATA_MAP)
public class MapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.JSON_UTF_8.toString());

        try {
            JsonArray dataset = new JsonArray();
            Activity.getAllGroupeByCity().forEach(data -> {
                JsonArray  formattedData = JsonParser.parseString(data.getRight()).getAsJsonArray();
                JsonObject element       = new JsonObject();
                element.addProperty("longitude", formattedData.get(0).getAsFloat());
                element.addProperty("latitude", formattedData.get(1).getAsFloat());
                element.addProperty("count", data.getLeft());
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
