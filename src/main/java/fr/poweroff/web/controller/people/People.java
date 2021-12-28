package fr.poweroff.web.controller.people;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/people", value = "/people")
public class People extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        this.getServletContext().getRequestDispatcher("/login/account/people/listPeople.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            this.getServletContext().getRequestDispatcher("/login/account/people/listPeople.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        response.sendRedirect("hello-servlet");
    }
}
