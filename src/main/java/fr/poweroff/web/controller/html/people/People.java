package fr.poweroff.web.controller.html.people;

import fr.poweroff.web.Registries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "People - Peoples", value = Registries.PATH_PEOPLES)
public class People extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher(Registries.JSP_PEOPLES).forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String people = request.getParameter("people");
        request.setAttribute("people", people);
        try {
            this.getServletContext().getRequestDispatcher(Registries.JSP_PEOPLES).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        //response.sendRedirect(this.getServletContext().getContextPath() + Registries.PATH_INDEX);
    }
}
