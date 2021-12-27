package fr.poweroff.web;

import fr.poweroff.web.models.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class  HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Connection connection = DataBase.CONNECTION;

        User user = User.create();
        user.setEmail("test@test.fr");
        user.setPasswordHash("ffffffff");
        user.setLastname("Mmmm");
        user.setFirstname("Mmmm");
        user.setBorn(new Date(1321565476));
        user.setLevel(0);
        /*try {
            user.save();
        } catch (SQLException e) {
            throw new IOException(e);
        }*/

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<a href=\"sign-up\">S'inscrire</a>");
        out.println("<a href=\"sign-in\">S'identifier</a>");
        out.println("<a href=\"sign-out\">Se deconnecter</a>");
        out.println(user.getUserId());
        /*try {
            out.println(User.getFirst(user.getUserId()));
        } catch (SQLException e) {
            throw new IOException(e);
        }*/
        out.println("</body></html>");
    }

    public void destroy() {
    }
}