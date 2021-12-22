package fr.poweroff.web;

import fr.poweroff.web.models.DataBase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class  HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Connection connection = DataBase.CONNECTION;

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<a href=\"sign-up\">S'inscrire</a>");
        out.println("<a href=\"sign-in\">S'identifier</a>");
        out.println("<a href=\"sign-out\">Se deconnecter</a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}