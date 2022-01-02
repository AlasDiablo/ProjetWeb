package fr.poweroff.web.controller.api.place;

import com.google.common.net.MediaType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import fr.poweroff.web.models.Place;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@WebServlet(name = "API Place", value = "/api/place")
public class PlaceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.JSON_UTF_8.toString());
        String type = req.getParameter("type");

        try {
            JsonObject output = new JsonObject();
            output.addProperty("version", 1);
            if (type.equals("0")) {
                String query = req.getParameter("q");
                output.add("result", Place.search(query));
                resp.getWriter().append(output.toString());
                return;
            } else if (type.equals("1")) {
                JsonArray coordinates;
                try {
                    coordinates = JsonParser.parseString(
                            Arrays.toString(req.getParameterMap().get("q"))
                    ).getAsJsonArray();
                    if (coordinates.size() != 2) {
                        throw new JsonParseException("Coordinate size is not equals to 2");
                    }
                } catch (JsonParseException | NullPointerException ignore) {
                    resp.setStatus(400);
                    resp.getWriter().println(
                            "{\"error\": 400}"
                    );
                    return;
                }
                output.add("result", Place.reverse(coordinates));
                resp.getWriter().append(output.toString());
                return;
            }

        } catch (Exception e) {
            resp.setStatus(500);
            JsonObject errorReport = new JsonObject();
            errorReport.addProperty("error", 500);
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            errorReport.addProperty("desc", stringWriter.toString());
            resp.getWriter().append(errorReport.toString());
            return;
        }
        resp.setStatus(400);
        resp.getWriter().println(
                "{\"error\": 400}"
        );
    }
}
