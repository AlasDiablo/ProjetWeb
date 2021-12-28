package fr.poweroff.web.controller.people;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/peoplePers", value = "/people-pers")
public class PeoplePers extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String mail = request.getParameter("mail");
        request.setAttribute("mail", mail);

        this.getServletContext().getRequestDispatcher("/login/account/people/peoplePerso.jsp").forward(request, response);

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
